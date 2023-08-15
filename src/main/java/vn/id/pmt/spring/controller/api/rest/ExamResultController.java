package vn.id.pmt.spring.controller.api.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.InputStreamResource;
import vn.id.pmt.spring.constants.ApiResponseResult;
import vn.id.pmt.spring.dto.request.ExamResultParams;
import vn.id.pmt.spring.dto.response.RestApiResponse;
import vn.id.pmt.spring.dto.request.PaginationParams;
import vn.id.pmt.spring.entity.jpa.User;
import vn.id.pmt.spring.service.ExamResultApiService;
import vn.id.pmt.spring.service.impl.UserApiServiceImpl;
import vn.id.pmt.spring.util.CSVUtil;

import java.util.Optional;


@Log4j2
@RestController
@RequestMapping("/exam_result")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public class ExamResultController {

    private final ExamResultApiService apiService;
    private final UserApiServiceImpl userApiService;

    @GetMapping("/list")
    @PreAuthorize("hasAnyAuthority('admin:read')")
    public ResponseEntity<Object> getAllExamResult() {
        Optional<Object> exams = apiService.getListExamResult();

        RestApiResponse<Object> response = exams.map(
                        exam -> RestApiResponse.builder()
                                .result(ApiResponseResult.OK)
                                .data(exam)
                                .build())
                .orElseGet(() -> RestApiResponse.builder().result(ApiResponseResult.ER).build());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/page")
    @PreAuthorize("hasAnyAuthority('admin:read')")
    public ResponseEntity<Object> getExamResultsWithPage(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize
    ) {
        PaginationParams params = PaginationParams.builder()
                .page(page)
                .pageSize(pageSize)
                .build();

        Optional<Object> exams = apiService.getListExamResultByPage(params);

        RestApiResponse<Object> response = exams.map(
                        exam -> RestApiResponse.builder()
                                .result(ApiResponseResult.OK)
                                .data(exam)
                                .build())
                .orElseGet(() -> RestApiResponse.builder().result(ApiResponseResult.ER).build());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/upload/csv")
    @PreAuthorize("hasAnyAuthority('admin:create')")
    public ResponseEntity<Object> insertExamResultsWithFile(@RequestParam("file") MultipartFile file) {

        ResponseEntity<Object> response;

        if (CSVUtil.isCSVFormat(file)) {
            try {
                apiService.insertExamResultByFile(file);

                response = ResponseEntity.status(HttpStatus.OK)
                        .body(RestApiResponse.builder()
                                .result(ApiResponseResult.OK)
                                .data("Uploaded the file successfully: " + file.getOriginalFilename())
                                .build());

            } catch (Exception e) {
                response = ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                        .body(RestApiResponse.builder()
                                .result(ApiResponseResult.ER)
                                .data("Could not upload the file: " + file.getOriginalFilename() + "!" + e.getMessage())
                                .build());

            }
            return response;
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(RestApiResponse.builder()
                        .result(ApiResponseResult.ER)
                        .data("Please upload a csv file!")
                        .build());
    }

    @GetMapping("/download/csv")
    @PreAuthorize("hasAnyAuthority('admin:read')")
    public ResponseEntity<Resource> exportExamResultsToCSV() {
        String filename = "exam_results.csv";
        InputStreamResource file = new InputStreamResource(apiService.exportExamResultsToCSV());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(file);
    }


    @GetMapping("/{examId}/{userId}")
    @PreAuthorize("hasAnyAuthority('admin:read')")
    public ResponseEntity<Object> getResultByUserAndExamId(
            @PathVariable Integer examId,
            @PathVariable Integer userId
    ) {
        ExamResultParams params = ExamResultParams.builder()
                .userId(userId)
                .examId(examId)
                .build();

        return getObjectResponseEntity(params);
    }

    @GetMapping("{examId}/my_result")
    @PreAuthorize("hasAnyAuthority('user:read','admin:read')")
    public ResponseEntity<Object> getUserExamResult(
            @PathVariable Integer examId,
            Authentication authentication
    ) {
        User user = (User) userApiService.loadUserByUsername(authentication.getName());

        ExamResultParams params = ExamResultParams.builder()
                .userId(user.getUserId())
                .examId(examId)
                .build();

        return getObjectResponseEntity(params);
    }

    private ResponseEntity<Object> getObjectResponseEntity(ExamResultParams params) {
        Optional<Object> result = apiService.getResultByUserAndExamId(params);

        RestApiResponse<Object> response = result.map(
                        data -> RestApiResponse.builder()
                                .result(ApiResponseResult.OK)
                                .data(data)
                                .build())
                .orElseGet(() -> RestApiResponse.builder().result(ApiResponseResult.ER).build());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/download/csv/{examId}/{userId}")
    @PreAuthorize("hasAnyAuthority('admin:read')")
    public ResponseEntity<Resource> exportResultByUserAndExamIdCSV(
            @PathVariable Integer examId,
            @PathVariable Integer userId
    ) {
        ExamResultParams params = ExamResultParams.builder()
                .userId(userId)
                .examId(examId)
                .build();

        String filename = String.format("exam_result_%d_%d.csv", userId, examId);
        InputStreamResource file = new InputStreamResource(apiService.exportResultByUserAndExamToCSV(params));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(file);
    }

    @GetMapping("/download/csv/{examId}/my_result")
    @PreAuthorize("hasAnyAuthority('user:read','admin:read')")
    public ResponseEntity<Resource> exportUserExamResultCSV(
            @PathVariable Integer examId,
            Authentication authentication
    ) {
        User user = (User) userApiService.loadUserByUsername(authentication.getName());

        ExamResultParams params = ExamResultParams.builder()
                .userId(user.getUserId())
                .examId(examId)
                .build();

        String filename = String.format("exam_result_%d_%d.csv", user.getUserId(), examId);
        InputStreamResource file = new InputStreamResource(apiService.exportResultByUserAndExamToCSV(params));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(file);
    }

    @GetMapping("/healthcheck")
    public ResponseEntity<Object> healthCheck() {
        RestApiResponse<Object> response = RestApiResponse.builder().result(ApiResponseResult.OK).data("hello").build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}