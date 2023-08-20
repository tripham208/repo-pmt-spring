package vn.id.pmt.spring.service;

import org.springframework.security.web.authentication.logout.LogoutHandler;
import vn.id.pmt.spring.dto.request.AuthParams;
import vn.id.pmt.spring.dto.response.AuthResponse;

public interface AuthenticationService extends LogoutHandler {

    /**
     * register user
     *
     * @param authParams info of user register
     */
    void register(AuthParams authParams);

    /**
     * login account
     *
     * @param request info of account
     * @return token access
     */
    AuthResponse login(AuthParams request);
}
