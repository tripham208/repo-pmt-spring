package vn.id.pmt.spring.entity.jpa;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.id.pmt.spring.entity.jpa.examresult.ExamResult;

import java.time.LocalDate;
import java.util.List;

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
    @Column(name = "type_exam")
    private String typeExam;
    @Column(name = "execute_date")
    private LocalDate executeDate;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "exam_id", referencedColumnName = "exam_id")
    private List<ExamResult> examResults;
}
