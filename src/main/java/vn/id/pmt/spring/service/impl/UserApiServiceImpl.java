package vn.id.pmt.spring.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.id.pmt.spring.entity.jpa.User;
import vn.id.pmt.spring.exception.NotFoundException;
import vn.id.pmt.spring.repository.jpa.UserRepository;
import vn.id.pmt.spring.service.UserApiService;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserApiServiceImpl implements UserApiService, UserDetailsService {

    UserRepository userRepository;

    /**
     * @return User
     */
    @Override
    public Optional<Object> getUserById() {
        return Optional.empty();
    }

    /**
     * @return list User
     * @throws NotFoundException when not found
     */
    @Override
    public Optional<Object> getListUser() {
        return Optional.empty();
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User " + username + " not found!");
        }
        return user;
    }
}
