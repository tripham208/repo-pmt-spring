package vn.id.pmt.spring.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.id.pmt.spring.entity.jpa.Exam;

public interface ExamRepository extends JpaRepository<Exam, Integer> {
}
