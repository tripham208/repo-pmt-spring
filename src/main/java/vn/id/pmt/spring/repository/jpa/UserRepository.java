package vn.id.pmt.spring.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.id.pmt.spring.entity.jpa.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
