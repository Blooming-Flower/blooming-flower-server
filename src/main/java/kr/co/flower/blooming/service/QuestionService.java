package kr.co.flower.blooming.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.flower.blooming.dto.in.QuestionRegistParam;
import kr.co.flower.blooming.dto.in.QuestionRegistParam.QuestionParam;
import kr.co.flower.blooming.dto.in.QuestionUpdateParam;
import kr.co.flower.blooming.dto.out.PassageGroupByUnitDto;
import kr.co.flower.blooming.dto.out.PassageNumberAndQuestionCountDto;
import kr.co.flower.blooming.entity.PassageEntity;
import kr.co.flower.blooming.entity.PassageType;
import kr.co.flower.blooming.entity.QuestionContentEntity;
import kr.co.flower.blooming.entity.QuestionEntity;
import kr.co.flower.blooming.entity.QuestionType;
import kr.co.flower.blooming.exception.FlowerError;
import kr.co.flower.blooming.exception.FlowerException;
import kr.co.flower.blooming.repository.ChooseAnswerRepository;
import kr.co.flower.blooming.repository.PassageRepository;
import kr.co.flower.blooming.repository.QuestionContentRepository;
import kr.co.flower.blooming.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 문제 CRUD
 * 
 * @author shmin
 *
 */
@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final PassageRepository passageRepository;
    private final QuestionContentRepository questionContentRepository;
    private final ChooseAnswerRepository chooseAnswerRepository;

    /**
     * 문제 저장
     * 
     * @param questionRegistDto
     */
    @Transactional
    public void saveQuestion(QuestionRegistParam questionRegistParam) {
        QuestionContentEntity questionContentEntity = new QuestionContentEntity();
        questionContentEntity.setQuestionTitle(questionRegistParam.getQuestionTitle());
        questionContentEntity.setQuestionContent(questionRegistParam.getQuestionContent());
        questionContentRepository.save(questionContentEntity);

        PassageEntity passage = passageRepository.findById(questionRegistParam.getPassageId())
                .orElseThrow(() -> new FlowerException(FlowerError.ENTITY_NOT_FOUND));

        UUID uuid = UUID.randomUUID();

        List<QuestionParam> questionParams = questionRegistParam.getQuestionParams();
        questionParams.forEach(question -> {
            QuestionEntity questionEntity = new QuestionEntity();

            questionEntity.setQuestionCode(uuid.toString());
            questionEntity.setQuestionType(question.getQuestionType());
            questionEntity.setQuestionSubTitle(question.getQuestionSubTitle());
            questionEntity.setPastYn(question.isPastYn());
            questionEntity.setSubBox(question.getSubBox());
            questionEntity.setPassageEntity(passage);
            questionEntity.setQuestionContentEntity(questionContentEntity);

            questionRepository.save(questionEntity);

            // choose, answer bulk insert
            chooseAnswerRepository.bulkSaveChoose(question.getChooseList(),
                    questionEntity.getQuestionId());
            chooseAnswerRepository.bulkSaveAnswer(question.getAnswerList(),
                    questionEntity.getQuestionId());
        });
    }

    /**
     * 문제 수정
     * 
     * @param questionRegistDto
     */
    @Transactional
    public void updateQuestion(QuestionUpdateParam questionUpdateParam) {
        // question code로 question entity 조회
        List<QuestionEntity> questionEntityByCode =
                questionRepository.findByQuestionCode(questionUpdateParam.getQuestionCode());

        if(questionEntityByCode.isEmpty()) {
            throw new FlowerException(FlowerError.ENTITY_NOT_FOUND);
        }
        
        if (questionEntityByCode.size() > 1) {
            // 복합 지문
            if (questionUpdateParam.getQuestionId() == 0) {
                // 복합 지문 -> question title, question content 만 수정
                updateQuestionContent(questionEntityByCode.get(0), questionUpdateParam);
            } else {
                // 문제 수정
                QuestionEntity questionEntity =
                        questionRepository.findById(questionUpdateParam.getQuestionId())
                                .orElseThrow(
                                        () -> new FlowerException(FlowerError.ENTITY_NOT_FOUND));
                updateQuestionOne(questionEntity, questionUpdateParam);
            }
        } else {
            // 단일 지문 -> title, content, 문제 수정
            QuestionEntity questionEntity =
                    questionRepository.findById(questionUpdateParam.getQuestionId())
                            .orElseThrow(() -> new FlowerException(FlowerError.ENTITY_NOT_FOUND));

            updateQuestionContent(questionEntity, questionUpdateParam);
            updateQuestionOne(questionEntity, questionUpdateParam);
        }
    }

    /**
     * 문제의 title, content 수정
     */
    private void updateQuestionContent(QuestionEntity questionEntity,
            QuestionUpdateParam questionUpdateParam) {
        QuestionContentEntity questionContentEntity = questionEntity.getQuestionContentEntity();
        questionContentEntity.setQuestionTitle(questionUpdateParam.getQuestionTitle());
        questionContentEntity.setQuestionContent(questionUpdateParam.getQuestionContent());
    }

    /**
     * 단일 문제 수정
     */
    private void updateQuestionOne(QuestionEntity questionEntity,
            QuestionUpdateParam questionUpdateParam) {
        PassageEntity passage = passageRepository.findById(questionUpdateParam.getPassageId())
                .orElseThrow(() -> new FlowerException(FlowerError.ENTITY_NOT_FOUND));

        questionEntity.setQuestionType(questionUpdateParam.getQuestionType());
        questionEntity.setQuestionSubTitle(questionUpdateParam.getQuestionSubTitle());
        questionEntity.setPastYn(questionUpdateParam.isPastYn());
        questionEntity.setSubBox(questionUpdateParam.getSubBox());
        questionEntity.setPassageEntity(passage);

        // choose, answer bulk update
        chooseAnswerRepository.bulkUpdateChoose(questionUpdateParam.getChooseList(),
                questionEntity.getQuestionId());
        chooseAnswerRepository.bulkUpdateAnswer(questionUpdateParam.getAnswerList(),
                questionEntity.getQuestionId());
    }

    /**
     * 문제 id로 삭제
     * 
     * @param questionId
     */
    @Transactional
    public void deleteQuestionById(long questionId) {
        questionRepository.findById(questionId)
                .orElseThrow(() -> new FlowerException(FlowerError.ENTITY_NOT_FOUND));

        chooseAnswerRepository.bulkDeleteChoose(questionId);
        chooseAnswerRepository.bulkDeleteAnswer(questionId);
        questionRepository.deleteById(questionId);
    }

    /**
     * 문제 코드로 삭제
     * 
     * @param questionId
     */
    @Transactional
    public void deleteQuestionByCode(String questionCode) {
        questionRepository.findByQuestionCode(questionCode).forEach(question -> {
            deleteQuestionById(question.getQuestionId());
        });
    }

    /**
     * 검색 조건에 따라 지문 번호 - 지문 강에 따라 그루핑 하여 조회
     * 
     * 페이징 처리
     * 
     * @param pabeable
     * @param passageType
     * @param passageYear
     * @param passageName
     */
    public List<PassageGroupByUnitDto> searchPassageNumbers(Pageable pageable,
            PassageType passageType,
            String passageYear, String passageName) {
        List<PassageGroupByUnitDto> byUnitDtos = new ArrayList<>();
        // 페이징된 passage number, question count
        List<PassageNumberAndQuestionCountDto> content = passageRepository
                .searchPassageUnitGroupByUnit(pageable, passageType, passageYear, passageName)
                .getContent();

        // passage unit으로 grouping
        Map<String, List<PassageNumberAndQuestionCountDto>> groupByUnit = content.stream()
                .collect(Collectors.groupingBy(PassageNumberAndQuestionCountDto::getPassageUnit));

        for (Entry<String, List<PassageNumberAndQuestionCountDto>> entry : groupByUnit.entrySet()) {
            byUnitDtos.add(
                    PassageGroupByUnitDto.builder().passageUnit(entry.getKey())
                            .passageInfo(entry.getValue()).build());
        }

        return byUnitDtos;
    }

    /**
     * 지문 유형과 연도에 해당되는 교재명 목록 조회
     * 
     * @param passageType
     * @param year
     * @return
     */
    public List<String> searchPassageNameByTypeAndYear(PassageType passageType, String year) {
        return passageRepository.searchPassageNameByTypeAndYear(passageType, year);
    }

}
