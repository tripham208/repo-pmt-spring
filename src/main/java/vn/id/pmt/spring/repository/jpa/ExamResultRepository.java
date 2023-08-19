package vn.id.pmt.spring.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.id.pmt.spring.entity.jpa.examresult.ExamResult;

import java.util.Optional;

public interface ExamResultRepository extends JpaRepository<ExamResult, Integer> {

    Optional<ExamResult> findByExamIdAndUserId(Integer examId, Integer userId);
}
