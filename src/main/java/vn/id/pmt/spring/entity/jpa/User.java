package vn.id.pmt.spring.entity.jpa;

import jakarta.persistence.*;
import lombok.*;
import vn.id.pmt.spring.entity.jpa.base.BaseModel;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
@EqualsAndHashCode(callSuper = true)
public class User extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;
}
