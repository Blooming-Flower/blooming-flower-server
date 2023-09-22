package kr.co.flower.blooming.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.flower.blooming.dto.in.MakeExamParam;
import kr.co.flower.blooming.dto.in.QuestionTypeParam;
import kr.co.flower.blooming.dto.out.ExamListDto;
import kr.co.flower.blooming.dto.out.ExamPagesDto;
import kr.co.flower.blooming.dto.out.QuestionIdAndCountDto;
import kr.co.flower.blooming.dto.out.SearchPassageDto.SearchQuestionDtos;
import kr.co.flower.blooming.entity.ExamEntity;
import kr.co.flower.blooming.entity.ExamQuestionEntity;
import kr.co.flower.blooming.entity.QuestionEntity;
import kr.co.flower.blooming.exception.FlowerError;
import kr.co.flower.blooming.exception.FlowerException;
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
    private final QuestionService questionService;

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


        List<ExamQuestionEntity> examQuestionEntities =
                examParam.getQuestionParams().stream().map(examQuestion -> {
                    long questionId = examQuestion.getQuestionId();
                    QuestionEntity questionEntity = questionRepository.findById(questionId)
                            .orElseThrow(() -> new FlowerException(FlowerError.ENTITY_NOT_FOUND));

                    ExamQuestionEntity examQuestionEntity = new ExamQuestionEntity();
                    examQuestionEntity.setExamEntity(examEntity);
                    examQuestionEntity.setQuestionEntity(questionEntity);
                    examQuestionEntity.setGroupSeq(examQuestion.getGroupSeq());

                    return examQuestionEntity;
                }).collect(Collectors.toList());

        examEntity.setExamQuestionEntities(examQuestionEntities);

        examRepository.save(examEntity);
    }

    /**
     * 동적 검색 쿼리, filter, order에 따라 paging
     * 
     * @param pageable
     * @param examTitle
     * @return
     */
    public Page<ExamListDto> findExamList(Pageable pageable, String examTitle) {
        return examRepository.findExamList(pageable, examTitle);
    }

    /**
     * 시험지 삭제
     * 
     * @param examId
     */
    @Transactional
    public void deleteExam(long examId) {
        examRepository.deleteById(examId);
    }

    /**
     * 시험지 문제들 불러오기
     * 
     * @param examId
     */
    public ExamPagesDto loadExam(long examId) {
        ExamEntity examEntity = examRepository.findById(examId)
                .orElseThrow(() -> new FlowerException(FlowerError.ENTITY_NOT_FOUND));

        List<ExamQuestionEntity> examQuestionEntities = examEntity.getExamQuestionEntities();

        
        // 문제들 섞는 로직
        List<SearchQuestionDtos> questionResult = new ArrayList<>();
        Map<Integer, List<ExamQuestionEntity>> questionGroup = examQuestionEntities.stream()
                .collect(Collectors.groupingBy(ExamQuestionEntity::getGroupSeq));
        
        List<Integer> groupSeq =
                questionGroup.keySet().stream().sorted().collect(Collectors.toList());
        
        for(int seq : groupSeq) {
            // 문제 code 별, 문제(발문, 지문, 선지, 답) dto 로 변환
            List<SearchQuestionDtos> questions = questionService.convertQuestionDtos(questionGroup.get(seq).stream()
                    .map(ExamQuestionEntity::getQuestionEntity).collect(Collectors.toList()));
            
            // shuffle
            Collections.shuffle(questions);
            
            questionResult.addAll(questions);
        }

        ExamPagesDto examPagesDto = new ExamPagesDto();
        examPagesDto.setTitle(examEntity.getExamTitle());
        examPagesDto.setSubTitle(examEntity.getExamSubTitle());
        examPagesDto.setLeftFooter(examEntity.getExamLeftFooter());
        examPagesDto.setRightFooter(examEntity.getExamRightFooter());
        examPagesDto.setExamFormat(examEntity.getExamFormat());
        examPagesDto.setQuestions(questionResult);

        return examPagesDto;
    }
}
