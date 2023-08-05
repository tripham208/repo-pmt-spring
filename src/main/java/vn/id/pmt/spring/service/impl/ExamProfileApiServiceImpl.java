package vn.id.pmt.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.id.pmt.spring.dto.ExamDto;
import vn.id.pmt.spring.dto.request.PaginationParams;
import vn.id.pmt.spring.entity.jpa.Exam;
import vn.id.pmt.spring.exception.NotFoundException;
import vn.id.pmt.spring.repository.jpa.ExamRepository;
import vn.id.pmt.spring.service.ExamProfileApiService;
import vn.id.pmt.spring.util.CSVUtil;
import vn.id.pmt.spring.util.MappingUtil;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExamProfileApiServiceImpl implements ExamProfileApiService {

    private final ExamRepository examRepository;
    private final MappingUtil mappingUtil;


    /**
     * @return Exam
     */
    @Override
    public Optional<Object> getExamById() throws NotFoundException {
        return Optional.empty();
    }

    /**
     * @return list Exam
     * @throws NotFoundException when not found
     */
    @Override
    public Optional<Object> getListExam() throws NotFoundException {
        Optional<List<Exam>> exams = Optional.of(examRepository.findAll());

        if (exams.get().isEmpty()) {
            throw new NotFoundException("Not found any records.");
        } else {
            List<ExamDto> listExamDto = mappingUtil.mapList(exams.get(), ExamDto.class);
            return Optional.of(listExamDto);
        }
    }

    @Override
    public Optional<Object> getListExamByPage(PaginationParams params) throws NotFoundException {
        Pageable pageable = PageRequest.of(params.getPage() - 1, params.getPageSize());

        Optional<Page<Exam>> exams = Optional.of(examRepository.findAll(pageable));


        if (exams.get().isEmpty()) {
            throw new NotFoundException("Not found any records");
        } else {
            List<ExamDto> listExamDto = (List<ExamDto>) mappingUtil.mapIterable(exams.get(), ExamDto.class);
            return Optional.of(listExamDto);
        }
    }

    @Override
    public void insertExamsByFile(MultipartFile file) {
        try {
            List<ExamDto> examDtoList = CSVUtil.csvToList(file.getInputStream(), ExamDto.class);
            List<Exam> exams = mappingUtil.mapList(examDtoList, Exam.class);
            examRepository.saveAll(exams);

        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }

    }

    @Override
    public ByteArrayInputStream exportCSV() {
        List<Exam> exams = examRepository.findAll();

        return CSVUtil.exportCSV(exams);
    }

    public void insertExam(Exam exam) {
        examRepository.save(exam);
    }


}
