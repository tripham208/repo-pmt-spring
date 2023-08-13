package vn.id.pmt.spring.controller.api.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.id.pmt.spring.constants.ApiResponseResult;
import vn.id.pmt.spring.dto.request.AuthParams;
import vn.id.pmt.spring.dto.response.AuthResponse;
import vn.id.pmt.spring.dto.response.RestApiResponse;
import vn.id.pmt.spring.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthenticationService service;

    @GetMapping("/login")
    public ResponseEntity<Object> authenticate(@RequestBody AuthParams request) {
        AuthResponse authResponse =  service.login(request);

        RestApiResponse<Object> response = RestApiResponse.builder().result(ApiResponseResult.OK).data(authResponse).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody  AuthParams request) {
        service.register(request);
        RestApiResponse<Object> response = RestApiResponse.builder().result(ApiResponseResult.OK).data("user create success").build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/hello")
    public ResponseEntity<Object> healthCheck() {
        RestApiResponse<Object> response = RestApiResponse.builder().result(ApiResponseResult.OK).data("hello").build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/access-denied")
    public ResponseEntity<Object> accessDenied() {
        RestApiResponse<Object> response = RestApiResponse.builder().result(ApiResponseResult.ER).data("access-denied").build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

}
