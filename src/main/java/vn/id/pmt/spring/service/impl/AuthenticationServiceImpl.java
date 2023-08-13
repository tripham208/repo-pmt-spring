package vn.id.pmt.spring.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.id.pmt.spring.constants.auth.Role;
import vn.id.pmt.spring.dto.request.AuthParams;
import vn.id.pmt.spring.dto.response.AuthResponse;
import vn.id.pmt.spring.entity.jpa.User;
import vn.id.pmt.spring.exception.AlreadyExistsException;
import vn.id.pmt.spring.exception.NotFoundException;
import vn.id.pmt.spring.repository.jpa.UserRepository;
import vn.id.pmt.spring.service.AuthenticationService;
import vn.id.pmt.spring.util.JwtUtil;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;


    @Override
    public void register(AuthParams authParams) {
        Optional<User> userResult = Optional.ofNullable(userRepository.findByUsername(authParams.getUsername()));

        if (userResult.isPresent()) {
            throw new AlreadyExistsException("username " + authParams.getUsername() + " already exists!");
        } else {
            User user = User.builder()
                    .username(authParams.getUsername())
                    .password(passwordEncoder.encode(authParams.getPassword()))
                    .fullName(authParams.getFullName())
                    .role(Role.USER)
                    .build();

            userRepository.save(user);
        }
    }

    @Override
    public AuthResponse login(AuthParams authParams) {
        Optional<User> userResult = Optional.ofNullable(userRepository.findByUsername(authParams.getUsername()));

        if (userResult.isEmpty()) {
            throw new NotFoundException("username " + authParams.getUsername() + " not found!");
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authParams.getUsername(),
                        authParams.getPassword()
                )
        );
        var jwtToken = jwtUtil.generateToken(userResult.get());

        return AuthResponse.builder()
                .username(userResult.get().getUsername())
                .accessToken(jwtToken)
                .build();

        /*

        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();*/
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        jwt = authHeader.substring(7);
    }
}
