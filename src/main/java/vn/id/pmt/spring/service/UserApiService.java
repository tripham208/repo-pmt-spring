package vn.id.pmt.spring.service;

import org.springframework.http.ResponseEntity;
import vn.id.pmt.spring.exception.NotFoundException;

import java.util.Optional;

/**
 * UserProfileApiService for User Profile API
 */
public interface UserApiService {
    /**
     * @return User
     */
    Optional<Object> getUserById() throws NotFoundException;

    /**
     *
     * @return list User
     * @throws NotFoundException when not found
     */
    Optional<Object> getListUser() throws NotFoundException;

}
