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
public class ExamDto implements Serializable {
    private Integer examId;
    private Integer userId;
    private Integer math;
    private Integer english;
    private Integer physics;
    private LocalDate date;
}