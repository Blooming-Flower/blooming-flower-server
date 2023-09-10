package kr.co.flower.blooming.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.flower.blooming.dto.in.QuestionRegistDto;
import kr.co.flower.blooming.dto.in.QuestionRegistDto.QuestionDto;
import kr.co.flower.blooming.entity.PassageEntity;
import kr.co.flower.blooming.entity.QuestionEntity;
import kr.co.flower.blooming.entity.QuestionPassageEntity;
import kr.co.flower.blooming.entity.QuestionPassageRepository;
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
	private final QuestionPassageRepository questionPassageRepository;

	/**
	 * 문제 저장
	 * 
	 * @param questionRegistDto
	 */
	@Transactional
	public void saveQuestion(QuestionRegistDto questionRegistDto) {
		QuestionEntity questionEntity = new QuestionEntity();

		setQuestionEntity(questionEntity, questionRegistDto);
	}

	/**
	 * 문제 수정
	 * 
	 * @param questionRegistDto
	 */
	@Transactional
	public void updateQuestion(QuestionRegistDto questionRegistDto) {
		QuestionEntity questionEntity = questionRepository.findById(questionRegistDto.getQuestionId())
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
		questionRepository.findById(questionId).orElseThrow(() -> new FlowerException(FlowerError.ENTITY_NOT_FOUND));

		questionRepository.deleteById(questionId);
	}

	private void setQuestionEntity(QuestionEntity questionEntity, QuestionRegistDto questionRegistDto) {
		QuestionPassageEntity questionPassageEntity = new QuestionPassageEntity();
		questionPassageEntity.setQuestionContent(questionRegistDto.getQuestionContent());
		questionPassageRepository.save(questionPassageEntity);

		PassageEntity passage = passageRepository.findById(questionRegistDto.getPassageId())
				.orElseThrow(() -> new FlowerException(FlowerError.ENTITY_NOT_FOUND));

		UUID uuid = UUID.randomUUID();

		List<QuestionDto> questionDtos = questionRegistDto.getQuestionDtos();
		questionDtos.forEach(question -> {
			questionEntity.setQuestionCode(uuid.toString());
			questionEntity.setQuestionType(question.getQuestionType());
			questionEntity.setQuestionTitle(questionRegistDto.getQuestionTitle());
			questionEntity.setQuestionSubTitle(question.getQuestionSubTitle());
			questionEntity.setAnswerEntities(question.getAnswerList());
			questionEntity.setPastYn(question.isPastYn());
			questionEntity.setChooseEntities(question.getChooseList());
			questionEntity.setAnswerEntities(question.getAnswerList());
			questionEntity.setPassageEntity(passage);

			questionRepository.save(questionEntity);
		});

	}
}
