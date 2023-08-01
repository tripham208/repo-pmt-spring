package vn.id.pmt.spring.service;

import vn.id.pmt.spring.exception.NotFoundException;

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

}
