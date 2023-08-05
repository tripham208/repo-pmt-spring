package vn.id.pmt.spring.service;

import org.springframework.web.multipart.MultipartFile;
import vn.id.pmt.spring.dto.request.PaginationParams;
import vn.id.pmt.spring.exception.NotFoundException;

import java.io.ByteArrayInputStream;
import java.util.Optional;

/**
 * UserProfileApiService for User Profile API
 */
public interface ExamProfileApiService {
    /**
     * @return Exam
     */
    Optional<Object> getExamById() throws NotFoundException;

    /**
     * @return list Exam
     * @throws NotFoundException when not found
     */
    Optional<Object> getListExam() throws NotFoundException;

    Optional<Object> getListExamByPage(PaginationParams params) throws NotFoundException;

    void insertExamsByFile(MultipartFile file);
    ByteArrayInputStream exportCSV();

}
