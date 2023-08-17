package vn.id.pmt.spring.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.id.pmt.spring.dto.UserDto;
import vn.id.pmt.spring.dto.request.PaginationParams;
import vn.id.pmt.spring.entity.jpa.User;
import vn.id.pmt.spring.exception.AlreadyExistsException;
import vn.id.pmt.spring.exception.NotFoundException;
import vn.id.pmt.spring.repository.jpa.UserRepository;
import vn.id.pmt.spring.service.UserApiService;
import vn.id.pmt.spring.util.CSVUtil;
import vn.id.pmt.spring.util.MappingUtil;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The User api service.
 */
@Service
@AllArgsConstructor
public class UserApiServiceImpl implements UserApiService, UserDetailsService {

    private final UserRepository userRepository;
    private final MappingUtil mappingUtil;
    private  PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User " + username + " not found!");
        }
        return user;
    }

    /**
     * Gets user by id.
     *
     * @param id the id
     * @return the user by id
     * @throws NotFoundException when not found user by id
     */
    @Override
    public Optional<Object> getUserById(Integer id) throws NotFoundException {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new NotFoundException("Not found user.");
        } else {
            UserDto userDto = mappingUtil.map(user.get(), UserDto.class);
            return Optional.of(userDto);
        }
    }

    /**
     * Gets user by id.
     *
     * @param username :
     * @return the user
     * @throws NotFoundException when not found user by username
     */
    @Override
    public Optional<Object> getUserByUsername(String username) throws NotFoundException {

        User user = userRepository.findByUsername(username);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("User " + username + " not found!");
        } else {
            UserDto userDto = mappingUtil.map(user, UserDto.class);
            return Optional.of(userDto);
        }
    }

    /**
     * Gets list user by page.
     *
     * @param params the params
     * @return the list user by page
     * @throws NotFoundException when not found any user
     */
    @Override
    @Cacheable("UserPage")
    public Optional<Object> getListUserByPage(PaginationParams params) throws NotFoundException {
        Pageable pageable = PageRequest.of(params.getPage() - 1, params.getPageSize());

        Optional<Page<User>> users = Optional.of(userRepository.findAll(pageable));

        if (users.get().isEmpty()) {
            throw new NotFoundException("Not found any records");
        } else {
            List<UserDto> userDtoList = (List<UserDto>) mappingUtil.mapIterable(users.get(), UserDto.class);
            return Optional.of(userDtoList);
        }
    }

    /**
     * Insert user by file.
     *
     * @param file the file
     */
    @Override
    public void insertUserByFile(MultipartFile file) {
        try {
            List<UserDto> userDtoList = CSVUtil.csvToList(file.getInputStream(), UserDto.class);
            List<User> users = mappingUtil.mapList(userDtoList, User.class);

            users = users.stream()
                    .peek(user ->
                            user.setPassword(passwordEncoder.encode(user.getUsername() + "123"))
                    ).collect(Collectors.toList());

            userRepository.saveAll(users);

        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    /**
     * Update user.
     *
     * @param userDto the user dto
     * @throws NotFoundException when not found user by id
     */
    @Override
    public void updateUser(UserDto userDto) throws NotFoundException {
        Optional<User> user = userRepository.findById(userDto.getUserId());

        if (user.isEmpty()) {
            throw new NotFoundException("Not found user.");
        }

        user.stream().peek(
                data -> {
                    data.setRole(userDto.getRole());
                    data.setFullName(userDto.getFullName());
                    data.setBirthday(userDto.getBirthday());
                }
        ).findFirst().ifPresent(userRepository::saveAndFlush);
    }

    /**
     * Insert user.
     *
     * @param userDto the user dto
     * @throws AlreadyExistsException when already exists user
     */
    @Override
    public void insertUser(UserDto userDto) throws AlreadyExistsException {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByUsername(userDto.getUsername()));

        if (userOptional.isPresent()) {
            throw new AlreadyExistsException("Username " + userDto.getUsername() + "is exits!");
        }

        User user = mappingUtil.map(userDto, User.class);
        user.setUserId(null);
        user.setPassword(passwordEncoder.encode(user.getUsername() + "123"));
        userRepository.save(user);
    }

    /**
     * Disable user.
     *
     * @param id the id
     * @throws NotFoundException when not found user by id
     */
    @Override
    public void disableUser(Integer id) throws NotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new NotFoundException("Not found user.");
        } else {
            user.stream().peek(
                    data -> data.setStatus(false)
            ).findFirst().ifPresent(userRepository::saveAndFlush);
        }
    }

    /**
     * Export csv file
     *
     * @return the byte array input stream : csv
     */
    @Override
    public ByteArrayInputStream exportCSV() {
        List<User> userList = userRepository.findAll();
        List<UserDto> userDtoList = mappingUtil.mapList(userList, UserDto.class);
        return CSVUtil.exportCSV(userDtoList);
    }
}
