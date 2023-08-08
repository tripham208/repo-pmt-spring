package vn.id.pmt.spring.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Column;
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
    private String typeExam;
    private Float listening;
    private Float reading;
    private Float writing;
    private Float speaking;
    private Float overall;
    private LocalDate expirationDate;
}
