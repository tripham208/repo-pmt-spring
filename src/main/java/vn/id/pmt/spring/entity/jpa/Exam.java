package vn.id.pmt.spring.entity.jpa;


import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "exam")
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exam_id")
    private Integer examId;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "type_exam")
    private String typeExam;
    @Column
    private Float listening;
    @Column
    private Float reading;
    @Column
    private Float writing;
    @Column
    private Float speaking;
    @Column
    private Float overall;
    @Column(name = "expiration_date")
    private LocalDate expirationDate;
}
