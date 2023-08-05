package vn.id.pmt.spring.entity.jpa.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;


import java.io.Serializable;
import java.sql.Timestamp;

@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@MappedSuperclass
public class BaseModel implements Serializable {

    @Column(name = "created_at")
    protected Timestamp createdAt;

    @Column(name = "updated_at")
    protected Timestamp updatedAt;

    @Column(name = "is_deleted")
    protected Boolean isDeleted;

}
