package vn.id.pmt.spring.service;

import org.springframework.web.multipart.MultipartFile;
import vn.id.pmt.spring.dto.request.PaginationParams;
import vn.id.pmt.spring.exception.NotFoundException;

import java.io.ByteArrayInputStream;
import java.util.Optional;

/**
 * UserProfileApiService for User Profile API
 */
public interface ExamResultApiService {
    /**
     * Gets exam by id.
     *
     * @return Exam by id
     * @throws NotFoundException when not found
     */
    Optional<Object> getExamById() throws NotFoundException;

    /**
     * Gets list exam.
     *
     * @return list Exam
     * @throws NotFoundException when not found
     */
    Optional<Object> getListExam() throws NotFoundException;

    /**
     * Gets list exam by page.
     *
     * @param params the PaginationParams
     * @return the list exam by page
     * @throws NotFoundException when not found
     */
    Optional<Object> getListExamByPage(PaginationParams params) throws NotFoundException;

    /**
     * Insert exams by file.
     *
     * @param file the file
     */
    void insertExamsByFile(MultipartFile file);

    /**
     * Export csv byte array input stream.
     *
     * @return the byte array input stream
     */
    ByteArrayInputStream exportCSV();

}
