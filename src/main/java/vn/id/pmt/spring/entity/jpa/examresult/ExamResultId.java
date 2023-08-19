package vn.id.pmt.spring.entity.jpa.examresult;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamResultId implements Serializable {
    private Integer examId;
    private Integer userId;
}
