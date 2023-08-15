package vn.id.pmt.spring.controller.api.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.id.pmt.spring.constants.ApiResponseResult;
import vn.id.pmt.spring.dto.UserDto;
import vn.id.pmt.spring.dto.request.PaginationParams;
import vn.id.pmt.spring.dto.response.RestApiResponse;
import vn.id.pmt.spring.service.impl.UserApiServiceImpl;
import vn.id.pmt.spring.util.CSVUtil;

import java.util.Optional;


@Log4j2
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public class UserController {

    private final UserApiServiceImpl apiService;


    @GetMapping("/page")
    @PreAuthorize("hasAnyAuthority('admin:read')")
    public ResponseEntity<Object> getUsersWithPage(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize
    ) {
        PaginationParams params = PaginationParams.builder()
                .page(page)
                .pageSize(pageSize)
                .build();

        Optional<Object> exams = apiService.getListUserByPage(params);

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
    public ResponseEntity<Object> getUsersInfo(@PathVariable Integer id) {
        Optional<Object> user = apiService.getUserById(id);

        RestApiResponse<Object> response = user.map(
                        data -> RestApiResponse.builder()
                                .result(ApiResponseResult.OK)
                                .data(data)
                                .build())
                .orElseGet(() -> RestApiResponse.builder().result(ApiResponseResult.ER).build());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(value = "/info")
    @PreAuthorize("hasAnyAuthority('admin:read','user:read')")
    public ResponseEntity<Object> getInfo(Authentication authentication) {
        UserDetails user = apiService.loadUserByUsername(authentication.getName());

        RestApiResponse<Object> response = RestApiResponse.builder()
                                .result(ApiResponseResult.OK)
                                .data(user)
                                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping(value = "/create")
    @PreAuthorize("hasAnyAuthority('admin:create')")
    public ResponseEntity<Object> createUser(@RequestBody UserDto userDto) {
        apiService.insertUser(userDto);

        RestApiResponse<Object> response = RestApiResponse.builder()
                .result(ApiResponseResult.OK)
                .data("user create successfully!")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping(value = "/update")
    @PreAuthorize("hasAnyAuthority('admin:update')")
    public ResponseEntity<Object> updateUser(@RequestBody UserDto userDto) {
        apiService.updateUser(userDto);

        RestApiResponse<Object> response = RestApiResponse.builder()
                .result(ApiResponseResult.OK)
                .data("user update successfully!")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping(value = "{id}/disable")
    @PreAuthorize("hasAnyAuthority('admin:update')")
    public ResponseEntity<Object> disableUser(@PathVariable Integer id) {
        apiService.disableUser(id);

        RestApiResponse<Object> response = RestApiResponse.builder()
                .result(ApiResponseResult.OK)
                .data("user update successfully!")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }



    @PostMapping("/upload/csv")
    @PreAuthorize("hasAnyAuthority('admin:create')")
    public ResponseEntity<Object> insertUsersWithFile(@RequestParam("file") MultipartFile file) {

        ResponseEntity<Object> response;

        if (CSVUtil.isCSVFormat(file)) {
            try {
                apiService.insertUserByFile(file);

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
    public ResponseEntity<Resource> getCSV() {
        String filename = "users.csv";
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