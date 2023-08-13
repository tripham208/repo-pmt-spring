package vn.id.pmt.spring.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthParams {
    @NotNull
    private String username;
    @NotNull
    private String password;
    private String fullName;
}
