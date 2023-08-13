package vn.id.pmt.spring.service;

import org.springframework.security.web.authentication.logout.LogoutHandler;
import vn.id.pmt.spring.dto.request.AuthParams;
import vn.id.pmt.spring.dto.response.AuthResponse;

public interface AuthenticationService extends LogoutHandler {


    void register(AuthParams authParams);

    AuthResponse login(AuthParams request);
}
