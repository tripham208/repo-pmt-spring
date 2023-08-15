package vn.id.pmt.spring.entity.jpa;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import vn.id.pmt.spring.constants.auth.Role;
import vn.id.pmt.spring.entity.jpa.base.BaseModel;
import vn.id.pmt.spring.entity.jpa.listener.ScdType1Listener;

import java.time.LocalDate;
import java.util.Collection;


@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "users")
@EqualsAndHashCode(callSuper = true)
@EntityListeners(ScdType1Listener.class)
public class User extends BaseModel implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;
    @Column
    private String password;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(name = "full_name")
    private String fullName;

    @Column
    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return status;
    }
}
