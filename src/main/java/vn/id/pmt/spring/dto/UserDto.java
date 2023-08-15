package vn.id.pmt.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.id.pmt.spring.constants.auth.Role;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Integer userId;
    private String username;
    private String fullName;
    private LocalDate birthday;
    private Role role;
    private Boolean status;
}