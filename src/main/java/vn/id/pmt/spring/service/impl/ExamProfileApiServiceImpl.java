package vn.id.pmt.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import vn.id.pmt.spring.dto.ExamDto;
import vn.id.pmt.spring.entity.jpa.Exam;
import vn.id.pmt.spring.exception.NotFoundException;
import vn.id.pmt.spring.repository.jpa.ExamRepository;
import vn.id.pmt.spring.service.ExamProfileApiService;
import vn.id.pmt.spring.util.MappingUtil;

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

}
