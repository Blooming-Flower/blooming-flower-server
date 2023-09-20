package kr.co.flower.blooming.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.flower.blooming.dto.in.QuestionTypeParam;
import kr.co.flower.blooming.dto.out.QuestionIdAndCountDto;
import kr.co.flower.blooming.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 시험지 CRUD
 * 
 * @author shmin
 *
 */
@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ExamService {
    private final QuestionRepository questionRepository;

    /**
     * 지문id와 유형에 따라 문제수와 문제id return
     * 
     * @param questionTypeParam
     */
    public List<QuestionIdAndCountDto> getQuestionIdAndCount(QuestionTypeParam questionTypeParam) {
        List<QuestionIdAndCountDto> questionIdAndCountDtos = new ArrayList<>();

        List<Long> passageIds = questionTypeParam.getPassageIds();
        for (long passageId : passageIds) {
            List<Long> questionIds = questionRepository.findByPassageIdAndTypes(passageId,
                    questionTypeParam.getQuestionTypes());

            questionIdAndCountDtos.add(QuestionIdAndCountDto.builder()
                    .passageId(passageId)
                    .questionIds(questionIds)
                    .count(questionIds.size())
                    .build());
        }

        return questionIdAndCountDtos;
    }
}
