package vn.id.pmt.spring.controller.api.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.id.pmt.spring.constants.ApiResponseResult;
import vn.id.pmt.spring.dto.RestApiResponse;
import vn.id.pmt.spring.service.ExamProfileApiService;

import java.util.Optional;

@Log4j2
@RestController
@RequestMapping("/exam")
@RequiredArgsConstructor
public class ExamController {

    private final ExamProfileApiService apiService;

    @GetMapping("/list")
    public ResponseEntity<Object> getListExam() {
        Optional<Object> exams = apiService.getListExam();

        RestApiResponse<Object> response = exams.map(
                exam -> RestApiResponse.builder()
                        .result(ApiResponseResult.OK)
                        .data(exam)
                        .build())
                .orElseGet(() -> RestApiResponse.builder().result(ApiResponseResult.ER).build());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}