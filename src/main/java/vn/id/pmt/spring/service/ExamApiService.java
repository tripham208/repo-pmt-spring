package vn.id.pmt.spring.service;

import vn.id.pmt.spring.dto.ExamDto;
import vn.id.pmt.spring.dto.request.PaginationParams;
import vn.id.pmt.spring.exception.NotFoundException;

import java.util.Optional;


/**
 * The interface Exam api service.
 */
public interface ExamApiService {
    /**
     * Gets exam by id.
     *
     * @param id the id
     * @return Exam by id
     * @throws NotFoundException when not found
     */
    Optional<Object> getExamById(Integer id) throws NotFoundException;

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
     * Insert exam.
     *
     * @param examDto the exam dto
     */
    void insertExam(ExamDto examDto) ;

    /**
     * Update exam.
     *
     * @param examDto the exam dto
     * @throws NotFoundException when not found
     */
    void updateExam(ExamDto examDto) throws NotFoundException;

    /**
     * Delete exam.
     *
     * @param id of the exam
     * @throws NotFoundException when not found
     */
    void deleteExam(Integer id) throws NotFoundException;

}
