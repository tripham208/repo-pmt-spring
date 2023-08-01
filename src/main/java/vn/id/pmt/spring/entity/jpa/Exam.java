package vn.id.pmt.spring.entity.jpa;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "exam")
public class Exam {
    @Id
    @Column(name = "exam_id", nullable = false, insertable = false, updatable = false)
    private Integer examId;
    @Column(name = "user_id")
    private Integer userId;
    @Column
    private Integer math;
    @Column
    private Integer english;
    @Column
    private Integer physics;
    @Column
    private LocalDate date;
}
