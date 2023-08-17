package vn.id.pmt.spring.service;

import org.springframework.web.multipart.MultipartFile;
import vn.id.pmt.spring.dto.UserDto;
import vn.id.pmt.spring.dto.request.PaginationParams;
import vn.id.pmt.spring.exception.AlreadyExistsException;
import vn.id.pmt.spring.exception.NotFoundException;

import java.io.ByteArrayInputStream;
import java.util.Optional;

/**
 * UserProfileApiService for User Profile API
 */
public interface UserApiService {

    /**
     * Gets user by id.
     *
     * @param id the id
     * @return the user by id
     * @throws NotFoundException when not found user by id
     */
    Optional<Object> getUserById(Integer id) throws NotFoundException;

    /**
     * Gets user by id.
     *
     * @param username :
     * @return the user
     * @throws NotFoundException when not found user by username
     */
    Optional<Object> getUserByUsername(String username) throws NotFoundException;

    /**
     * Gets list user by page.
     *
     * @param params the params
     * @return the list user by page
     * @throws NotFoundException when not found any user
     */
    Optional<Object> getListUserByPage(PaginationParams params) throws NotFoundException;

    /**
     * Insert user by file.
     *
     * @param file the file
     */
    void insertUserByFile(MultipartFile file);

    /**
     * Update user.
     *
     * @param userDto the user dto
     * @throws NotFoundException when not found user by id
     */
    void updateUser(UserDto userDto) throws NotFoundException;

    /**
     * Insert user.
     *
     * @param userDto the user dto
     * @throws AlreadyExistsException when already exists user
     */
    void insertUser(UserDto userDto) throws AlreadyExistsException;

    /**
     * Disable user.
     *
     * @param id the id
     * @throws NotFoundException when not found user by id
     */
    void disableUser(Integer id) throws NotFoundException;

    /**
     * Export csv file
     *
     * @return the byte array input stream : csv
     */
    ByteArrayInputStream exportCSV();
}
