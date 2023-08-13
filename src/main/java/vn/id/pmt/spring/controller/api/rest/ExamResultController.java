package vn.id.pmt.spring.controller.api.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.InputStreamResource;
import vn.id.pmt.spring.constants.ApiResponseResult;
import vn.id.pmt.spring.dto.response.RestApiResponse;
import vn.id.pmt.spring.dto.request.PaginationParams;
import vn.id.pmt.spring.service.ExamResultApiService;
import vn.id.pmt.spring.util.CSVUtil;

import java.util.Optional;


@Log4j2
@RestController
@RequestMapping("/exam_result")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public class ExamResultController {

    private final ExamResultApiService apiService;

    @GetMapping("/list")
    @PreAuthorize("hasAnyAuthority('admin:read')")
    public ResponseEntity<Object> getAllExam() {
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
    public ResponseEntity<Object> getExamsWithPage(
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
    public ResponseEntity<Object> getExamsWithPage(@RequestParam("file") MultipartFile file) {

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
    public ResponseEntity<Resource> getCSV() {
        String filename = "exams.csv";
        InputStreamResource file = new InputStreamResource(apiService.exportCSV());

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