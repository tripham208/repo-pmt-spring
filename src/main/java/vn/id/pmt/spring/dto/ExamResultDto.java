package vn.id.pmt.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExamResultDto {
    private Integer examId;
    private Integer userId;
    private String typeExam;
    private Float listening;
    private Float reading;
    private Float writing;
    private Float speaking;
    private Float overall;
}
