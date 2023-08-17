package vn.id.pmt.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExamResultDto implements Serializable {
    private Integer examId;
    private Integer userId;
    private String typeExam;
    private Float overall;
}
