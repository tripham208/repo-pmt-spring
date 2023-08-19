package vn.id.pmt.spring.entity.jpa.examresult;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "exam_result")
@IdClass(ExamResultId.class)
public class ExamResult implements Serializable {
    @Id
    @Column(name = "exam_id")
    private Integer examId;
    @Id
    @Column(name = "user_id")
    private Integer userId;
    @Column
    private Float overall;
    @Formula("(select exam.type_exam from exam where exam.exam_id = exam_id)")
    private String typeExam;

    @Formula("(select exam.execute_date from exam where exam.exam_id = exam_id)")
    private LocalDate executeDate;

    @Formula("(select users.full_name from users where users.user_id = user_id)")
    private String fullName;
}
