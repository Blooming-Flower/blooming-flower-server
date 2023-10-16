package kr.co.flower.blooming.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.flower.blooming.dto.in.MakeExamParam;
import kr.co.flower.blooming.dto.in.QuestionTypeParam;
import kr.co.flower.blooming.dto.out.ChooseDto;
import kr.co.flower.blooming.dto.out.ExamListDto;
import kr.co.flower.blooming.dto.out.ExamPagesDto;
import kr.co.flower.blooming.dto.out.PassageGroupByUnitPageDto;
import kr.co.flower.blooming.dto.out.PassageNumberAndQuestionCountDto;
import kr.co.flower.blooming.dto.out.ExamPagesDto.ExamPageGroupDto;
import kr.co.flower.blooming.dto.out.PassageGroupByUnitPageDto.PassageGroupByUnitDto;
import kr.co.flower.blooming.dto.out.QuestionIdAndCountDto;
import kr.co.flower.blooming.dto.out.QuestionInfo;
import kr.co.flower.blooming.dto.out.SearchPassageDto.SearchQuestionDtos;
import kr.co.flower.blooming.entity.AnswerDto;
import kr.co.flower.blooming.entity.ExamEntity;
import kr.co.flower.blooming.entity.ExamQuestionEntity;
import kr.co.flower.blooming.entity.PassageEntity;
import kr.co.flower.blooming.entity.PassageType;
import kr.co.flower.blooming.entity.QuestionEntity;
import kr.co.flower.blooming.exception.FlowerError;
import kr.co.flower.blooming.exception.FlowerException;
import kr.co.flower.blooming.repository.ExamRepository;
import kr.co.flower.blooming.repository.PassageRepository;
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
    private final PassageRepository passageRepository;

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
                    examQuestionEntity.setGroupName(examQuestion.getGroupName());

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
        List<ExamPageGroupDto> examQuestions = new ArrayList<>();
        Map<Integer, List<ExamQuestionEntity>> questionGroup = examQuestionEntities.stream()
                .collect(Collectors.groupingBy(ExamQuestionEntity::getGroupSeq));

        List<Integer> groupSeq =
                questionGroup.keySet().stream().sorted().collect(Collectors.toList());

        for (int seq : groupSeq) {
            // 문제 code 별, 문제(발문, 지문, 선지, 답) dto 로 변환
            List<SearchQuestionDtos> questions = questionService.convertQuestionDtos(questionGroup
                    .get(seq).stream()
                    .map(ExamQuestionEntity::getQuestionEntity).collect(Collectors.toList()));

            // shuffle
            Collections.shuffle(questions);

            ExamPageGroupDto examPageGroupDto = new ExamPageGroupDto();
            examPageGroupDto.setGroupName(questionGroup.get(seq).get(0).getGroupName());
            examPageGroupDto.setQuestions(questions);
            examQuestions.add(examPageGroupDto);
        }

        ExamPagesDto examPagesDto = new ExamPagesDto();
        examPagesDto.setTitle(examEntity.getExamTitle());
        examPagesDto.setSubTitle(examEntity.getExamSubTitle());
        examPagesDto.setLeftFooter(examEntity.getExamLeftFooter());
        examPagesDto.setRightFooter(examEntity.getExamRightFooter());
        examPagesDto.setExamFormat(examEntity.getExamFormat());
        examPagesDto.setExamQuestions(examQuestions);

        return examPagesDto;
    }

    /**
     * 검색 조건에 따라 지문 번호 - 지문 강에 따라 그루핑 하여 조회
     * 
     * 출제된 문제가 있는 것들 filtering
     * 
     * 페이징 처리
     * 
     * @param pabeable
     * @param passageType
     * @param passageYear
     * @param passageName
     */
    public PassageGroupByUnitPageDto searchPassageNumbersHavingQuestion(Pageable pageable,
            PassageType passageType,
            String passageYear, String passageName) {
        PassageGroupByUnitPageDto groupByUnitPageDto = new PassageGroupByUnitPageDto();
        List<PassageGroupByUnitDto> byUnitDtos = new ArrayList<>();

        List<PassageNumberAndQuestionCountDto> content =
                examRepository.searchPassageNumbersHavingQuestion(pageable,
                        passageType, passageYear, passageName);

        // passage unit으로 grouping
        Map<String, List<PassageNumberAndQuestionCountDto>> groupByUnit = content.stream()
                .collect(Collectors.groupingBy(PassageNumberAndQuestionCountDto::getPassageUnit));

        for (Entry<String, List<PassageNumberAndQuestionCountDto>> entry : groupByUnit.entrySet()) {
            byUnitDtos.add(
                    PassageGroupByUnitDto.builder().passageUnit(entry.getKey())
                            .passageInfo(entry.getValue()).build());
        }

        groupByUnitPageDto.setList(byUnitDtos);

        int unitNum = groupByUnit.keySet().size();
        int pageSize = unitNum / pageable.getPageSize() + 1;
        groupByUnitPageDto.setPageSize(pageSize);

        return groupByUnitPageDto;
    }

    /**
     * question이 하나라도 있는 passage type, year인 지문 명 찾기
     * 
     * @param passageType
     * @param year
     * @return
     */
    public List<String> searchPassageNameHavingQuestion(PassageType passageType, String year) {
        return examRepository.searchPassageNameHavingQuestion(passageType, year);
    }

    /**
     * questionId에 해당하는 데이터 전부 조회
     * 
     * @param questionIds
     * @return
     */
    public List<QuestionInfo> getQuestionAll(List<Long> questionIds) {
        List<QuestionInfo> questionInfos = new ArrayList<>();

        List<QuestionEntity> questionEntities = questionRepository.findAllById(questionIds);

        for (QuestionEntity questionEntity : questionEntities) {
            questionInfos.add(QuestionInfo.builder()
                    .questionId(questionEntity.getQuestionId())
                    .questionCode(questionEntity.getQuestionCode())
                    .questionType(questionEntity.getQuestionType())
                    .subBox(questionEntity.getSubBox())
                    .questionSubTitle(questionEntity.getQuestionSubTitle())
                    .pastYn(questionEntity.isPastYn())
                    .questionTitle(questionEntity.getQuestionContentEntity().getQuestionTitle())
                    .questionContent(questionEntity.getQuestionContentEntity().getQuestionContent())
                    .passageType(questionEntity.getPassageEntity().getPassageType())
                    .passageYear(questionEntity.getPassageEntity().getPassageYear())
                    .passageName(questionEntity.getPassageEntity().getPassageName())
                    .passageUnit(questionEntity.getPassageEntity().getPassageUnit())
                    .passageNumber(questionEntity.getPassageEntity().getPassageNumber())
                    .chooseList(questionEntity.getChooseEntities().stream().map(chooseEntity -> {
                        ChooseDto chooseDto = new ChooseDto();
                        chooseDto.setSeq(chooseEntity.getChooseSeq());
                        chooseDto.setContent(chooseEntity.getChooseContent());

                        return chooseDto;
                    }).collect(Collectors.toList()))
                    .answerList(questionEntity.getAnswerEntities().stream().map(answerEntity -> {
                        AnswerDto answerDto = new AnswerDto();
                        answerDto.setContent(answerEntity.getAnswerContent());

                        return answerDto;
                    }).collect(Collectors.toList()))
                    .build());
        }

        return questionInfos;
    }
}
