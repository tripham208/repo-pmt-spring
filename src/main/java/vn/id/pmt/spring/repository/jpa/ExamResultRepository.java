package vn.id.pmt.spring.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.id.pmt.spring.entity.jpa.ExamResult;

public interface ExamResultRepository extends JpaRepository<ExamResult, Integer> {
}
