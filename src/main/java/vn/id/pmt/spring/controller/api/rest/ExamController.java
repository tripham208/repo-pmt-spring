package vn.id.pmt.spring.controller.api.rest;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.id.pmt.spring.constants.ApiResponseResult;
import vn.id.pmt.spring.dto.ExamDto;
import vn.id.pmt.spring.dto.request.PaginationParams;
import vn.id.pmt.spring.dto.response.RestApiResponse;
import vn.id.pmt.spring.service.ExamApiService;

import java.util.Optional;


@Log4j2
@RestController
@RequestMapping("/exam")
@AllArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN')")
public class ExamController {

    private final ExamApiService apiService;


    @GetMapping("/page")
    @PreAuthorize("hasAnyAuthority('admin:read')")
    public ResponseEntity<Object> getExamsWithPage(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize
    ) {
        PaginationParams params = PaginationParams.builder()
                .page(page)
                .pageSize(pageSize)
                .build();

        Optional<Object> exams = apiService.getListExamByPage(params);

        RestApiResponse<Object> response = exams.map(
                        exam -> RestApiResponse.builder()
                                .result(ApiResponseResult.OK)
                                .data(exam)
                                .build())
                .orElseGet(() -> RestApiResponse.builder().result(ApiResponseResult.ER).build());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}/info")
    @PreAuthorize("hasAnyAuthority('admin:read')")
    public ResponseEntity<Object> getExamInfo(@PathVariable Integer id) {
        Optional<Object> user = apiService.getExamById(id);

        RestApiResponse<Object> response = user.map(
                        data -> RestApiResponse.builder()
                                .result(ApiResponseResult.OK)
                                .data(data)
                                .build())
                .orElseGet(() -> RestApiResponse.builder().result(ApiResponseResult.ER).build());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping(value = "/create")
    @PreAuthorize("hasAnyAuthority('admin:create')")
    public ResponseEntity<Object> createExam(@RequestBody ExamDto examDto) {
        apiService.insertExam(examDto);

        RestApiResponse<Object> response = RestApiResponse.builder()
                .result(ApiResponseResult.OK)
                .data("user create successfully!")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping(value = "/update")
    @PreAuthorize("hasAnyAuthority('admin:update')")
    public ResponseEntity<Object> updateExam(@RequestBody ExamDto examDto) {
        apiService.updateExam(examDto);

        RestApiResponse<Object> response = RestApiResponse.builder()
                .result(ApiResponseResult.OK)
                .data("user update successfully!")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping(value = "{id}/delete")
    @PreAuthorize("hasAnyAuthority('admin:delete')")
    public ResponseEntity<Object> deleteExam(@PathVariable Integer id) {
        apiService.deleteExam(id);

        RestApiResponse<Object> response = RestApiResponse.builder()
                .result(ApiResponseResult.OK)
                .data("user update successfully!")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/healthcheck")
    public ResponseEntity<Object> healthCheck() {
        RestApiResponse<Object> response = RestApiResponse.builder().result(ApiResponseResult.OK).data("hello").build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}