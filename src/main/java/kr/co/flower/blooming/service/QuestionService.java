package kr.co.flower.blooming.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.flower.blooming.dto.in.QuestionRegistDto;
import kr.co.flower.blooming.entity.PassageEntity;
import kr.co.flower.blooming.entity.QuestionEntity;
import kr.co.flower.blooming.exception.FlowerError;
import kr.co.flower.blooming.exception.FlowerException;
import kr.co.flower.blooming.repository.PassageRepository;
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

    /**
     * 문제 저장
     * 
     * @param questionRegistDto
     */
    @Transactional
    public void saveQuestion(QuestionRegistDto questionRegistDto) {
        QuestionEntity questionEntity = new QuestionEntity();

        setQuestionEntity(questionEntity, questionRegistDto);

        questionRepository.save(questionEntity);
    }

    /**
     * 문제 수정
     * 
     * @param questionRegistDto
     */
    @Transactional
    public void updateQuestion(QuestionRegistDto questionRegistDto) {
        QuestionEntity questionEntity =
                questionRepository.findById(questionRegistDto.getQuestionId())
                        .orElseThrow(() -> new FlowerException(FlowerError.ENTITY_NOT_FOUND));

        setQuestionEntity(questionEntity, questionRegistDto);
    }

    /**
     * 문제 삭제
     * 
     * @param questionId
     */
    @Transactional
    public void deleteQuestion(long questionId) {
        questionRepository.findById(questionId)
                .orElseThrow(() -> new FlowerException(FlowerError.ENTITY_NOT_FOUND));

        questionRepository.deleteById(questionId);
    }

    private void setQuestionEntity(QuestionEntity questionEntity,
            QuestionRegistDto questionRegistDto) {
        questionEntity.setQuestionType(questionRegistDto.getQuestionType());
        questionEntity.setQuestionTitle(questionRegistDto.getQuestionTitle());
        questionEntity.setQuestionContent(questionRegistDto.getQuestionContent());
        questionEntity.setQuestionAnswer(questionRegistDto.getQuestionAnswer());
        questionEntity.setPastYn(questionRegistDto.isPastYn());
        questionEntity.setChooseEntities(questionRegistDto.getChooseList());

        PassageEntity passage = passageRepository.findById(questionRegistDto.getPassageId())
                .orElseThrow(() -> new FlowerException(FlowerError.ENTITY_NOT_FOUND));

        questionEntity.setPassageEntity(passage);
    }
}
