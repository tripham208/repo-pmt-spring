package vn.id.pmt.spring.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.id.pmt.spring.dto.ExamDto;
import vn.id.pmt.spring.dto.request.PaginationParams;
import vn.id.pmt.spring.entity.jpa.Exam;
import vn.id.pmt.spring.exception.NotFoundException;
import vn.id.pmt.spring.repository.jpa.ExamRepository;
import vn.id.pmt.spring.service.ExamApiService;
import vn.id.pmt.spring.util.MappingUtil;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ExamApiServiceImpl implements ExamApiService {

    private final ExamRepository repository;
    private final MappingUtil mappingUtil;

    /**
     * Gets exam by id.
     *
     * @param id the id
     * @return Exam by id
     * @throws NotFoundException when not found
     */
    @Override
    public Optional<Object> getExamById(Integer id) throws NotFoundException {
        Optional<Exam> user = repository.findById(id);

        if (user.isEmpty()) {
            throw new NotFoundException("Not found user.");
        } else {
            ExamDto examDto = mappingUtil.map(user.get(), ExamDto.class);
            return Optional.of(examDto);
        }
    }

    /**
     * Gets list exam.
     *
     * @return list Exam
     * @throws NotFoundException when not found
     */
    @Override
    @Cacheable("ListExam")
    public Optional<Object> getListExam() throws NotFoundException {

        Optional<List<Exam>> examResults = Optional.of(repository.findAll());

        if (examResults.get().isEmpty()) {
            throw new NotFoundException("Not found any records.");
        } else {
            List<ExamDto> listExamResultDto = mappingUtil.mapList(examResults.get(), ExamDto.class);
            return Optional.of(listExamResultDto);
        }
    }

    /**
     * Gets list exam by page.
     *
     * @param params the PaginationParams
     * @return the list exam by page
     * @throws NotFoundException when not found
     */
    @Override
    @Cacheable("ListExamPage")
    public Optional<Object> getListExamByPage(PaginationParams params) throws NotFoundException {
        Pageable pageable = PageRequest.of(params.getPage() - 1, params.getPageSize());

        Optional<Page<Exam>> examResults = Optional.of(repository.findAll(pageable));


        if (examResults.get().isEmpty()) {
            throw new NotFoundException("Not found any records");
        } else {
            List<ExamDto> examDtoList = (List<ExamDto>) mappingUtil.mapIterable(examResults.get(), ExamDto.class);
            return Optional.of(examDtoList);
        }
    }

    /**
     * Insert exam.
     *
     * @param examDto the exam dto
     */
    @Override
    public void insertExam(ExamDto examDto) {
        Exam exam = mappingUtil.map(examDto, Exam.class);
        exam.setExamId(null);
        repository.save(exam);
    }

    /**
     * Update exam.
     *
     * @param examDto the exam dto
     * @throws NotFoundException when not found
     */
    @Override
    public void updateExam(ExamDto examDto) throws NotFoundException {
        Optional<Exam> examOptional = repository.findById(examDto.getExamId());

        if (examOptional.isEmpty()) {
            throw new NotFoundException("Not found exam.");
        }

        Exam exam = mappingUtil.map(examDto,Exam.class);

        repository.save(exam);
    }

    /**
     * Delete exam.
     *
     * @param id of the exam
     * @throws NotFoundException when not found
     */
    @Override
    public void deleteExam(Integer id) throws NotFoundException {
        Optional<Exam> examOptional = repository.findById(id);

        if (examOptional.isEmpty()) {
            throw new NotFoundException("Not found exam.");
        }

        repository.deleteById(id);
    }
}
