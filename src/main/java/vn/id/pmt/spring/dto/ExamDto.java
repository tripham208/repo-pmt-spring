package vn.id.pmt.spring.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExamDto {
    private Integer examId;
    private Integer userId;
    private Integer math;
    private Integer english;
    private Integer physics;
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyMMdd")
    private LocalDate date;
}
