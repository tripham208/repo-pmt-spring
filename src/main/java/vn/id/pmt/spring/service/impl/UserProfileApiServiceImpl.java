package vn.id.pmt.spring.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vn.id.pmt.spring.exception.NotFoundException;
import vn.id.pmt.spring.service.UserProfileApiService;

import java.util.Optional;

@Service
public class UserProfileApiServiceImpl implements UserProfileApiService {

    /**
     * @return User
     */
    @Override
    public ResponseEntity<Object> getUserById() throws NotFoundException {
        return null;
    }

    /**
     * @return list User
     * @throws NotFoundException when not found
     */
    @Override
    public ResponseEntity<Object> getListUser() throws NotFoundException {
        return null;
    }

}
