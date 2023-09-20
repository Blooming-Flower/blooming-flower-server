package kr.co.flower.blooming.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.flower.blooming.dto.in.MakeExamParam;
import kr.co.flower.blooming.dto.in.MakeExamParam.ExamQuestionParam;
import kr.co.flower.blooming.dto.in.QuestionTypeParam;
import kr.co.flower.blooming.dto.out.QuestionIdAndCountDto;
import kr.co.flower.blooming.entity.ExamEntity;
import kr.co.flower.blooming.entity.ExamQuestionEntity;
import kr.co.flower.blooming.entity.QuestionEntity;
import kr.co.flower.blooming.exception.FlowerError;
import kr.co.flower.blooming.exception.FlowerException;
import kr.co.flower.blooming.repository.ExamQuestionRepository;
import kr.co.flower.blooming.repository.ExamRepository;
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
    private final ExamRepository examRepository;
    private final ExamQuestionRepository examQuestionRepository;

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

    /**
     * 시험지 제작
     * 
     * @param examParam
     */
    @Transactional
    public void makeExam(MakeExamParam examParam) {
        ExamEntity examEntity = new ExamEntity();
        examEntity.setExamTitle(examParam.getTitle());
        examEntity.setExamSubTitle(examParam.getSubTitle());
        examEntity.setExamLeftFooter(examParam.getLeftFooter());
        examEntity.setExamRightFooter(examParam.getRightFooter());
        examEntity.setExamFormat(examParam.getExamFormat());

        examRepository.save(examEntity);

        List<ExamQuestionParam> questionParams = examParam.getQuestionParams();
        questionParams.forEach(examQuestion -> {
            long questionId = examQuestion.getQuestionId();
            QuestionEntity questionEntity = questionRepository.findById(questionId)
                    .orElseThrow(() -> new FlowerException(FlowerError.ENTITY_NOT_FOUND));

            ExamQuestionEntity examQuestionEntity = new ExamQuestionEntity();
            examQuestionEntity.setExamEntity(examEntity);
            examQuestionEntity.setQuestionEntity(questionEntity);
            examQuestionEntity.setGroupSeq(examQuestion.getGroupSeq());

            examQuestionRepository.save(examQuestionEntity);
        });
    }
}
