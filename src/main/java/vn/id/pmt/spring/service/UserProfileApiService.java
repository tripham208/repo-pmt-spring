package vn.id.pmt.spring.service;

import org.springframework.http.ResponseEntity;
import vn.id.pmt.spring.exception.NotFoundException;

/**
 * UserProfileApiService for User Profile API
 */
public interface UserProfileApiService {
    /**
     * @return User
     */
    ResponseEntity<Object> getUserById() throws NotFoundException;

    /**
     *
     * @return list User
     * @throws NotFoundException when not found
     */
    ResponseEntity<Object> getListUser() throws NotFoundException;

}
