package kr.co.flower.blooming.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.flower.blooming.dto.in.QuestionRegistDto;
import kr.co.flower.blooming.dto.in.QuestionUpdateDto;
import kr.co.flower.blooming.dto.in.QuestionRegistDto.QuestionDto;
import kr.co.flower.blooming.entity.PassageEntity;
import kr.co.flower.blooming.entity.QuestionEntity;
import kr.co.flower.blooming.entity.QuestionContentEntity;
import kr.co.flower.blooming.entity.QuestionContentRepository;
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
	private final QuestionContentRepository questionContentRepository;

	/**
	 * 문제 저장
	 * 
	 * @param questionRegistDto
	 */
	@Transactional
	public void saveQuestion(QuestionRegistDto questionRegistDto) {
		QuestionContentEntity questionContentEntity = new QuestionContentEntity();
		questionContentEntity.setQuestionTitle(questionRegistDto.getQuestionTitle());
		questionContentEntity.setQuestionContent(questionRegistDto.getQuestionContent());
		questionContentRepository.save(questionContentEntity);

		PassageEntity passage = passageRepository.findById(questionRegistDto.getPassageId())
				.orElseThrow(() -> new FlowerException(FlowerError.ENTITY_NOT_FOUND));

		UUID uuid = UUID.randomUUID();

		List<QuestionDto> questionDtos = questionRegistDto.getQuestionDtos();
		questionDtos.forEach(question -> {
			QuestionEntity questionEntity = new QuestionEntity();

			questionEntity.setQuestionCode(uuid.toString());
			questionEntity.setQuestionType(question.getQuestionType());
			questionEntity.setQuestionSubTitle(question.getQuestionSubTitle());
			questionEntity.setPastYn(question.isPastYn());
			questionEntity.setSubBox(question.getSubBox());
			questionEntity.setChooseEntities(question.getChooseList());
			questionEntity.setAnswerEntities(question.getAnswerList());
			questionEntity.setPassageEntity(passage);
			questionEntity.setQuestionContentEntity(questionContentEntity);

			questionRepository.save(questionEntity);
		});
	}

	/**
	 * 문제 수정
	 * 
	 * @param questionRegistDto
	 */
	@Transactional
	public void updateQuestion(QuestionUpdateDto questionUpdateDto) {
		QuestionEntity questionEntity = questionRepository.findById(questionUpdateDto.getQuestionId())
				.orElseThrow(() -> new FlowerException(FlowerError.ENTITY_NOT_FOUND));

		QuestionContentEntity questionContentEntity = questionEntity.getQuestionContentEntity();
		questionContentEntity.setQuestionTitle(questionUpdateDto.getQuestionTitle());
		questionContentEntity.setQuestionContent(questionUpdateDto.getQuestionContent());
		questionContentRepository.save(questionContentEntity);

		PassageEntity passage = passageRepository.findById(questionUpdateDto.getPassageId())
				.orElseThrow(() -> new FlowerException(FlowerError.ENTITY_NOT_FOUND));

		questionEntity.setQuestionType(questionUpdateDto.getQuestionType());
		questionEntity.setQuestionSubTitle(questionUpdateDto.getQuestionSubTitle());
		questionEntity.setPastYn(questionUpdateDto.isPastYn());
		questionEntity.setSubBox(questionUpdateDto.getSubBox());
		questionEntity.setChooseEntities(questionUpdateDto.getChooseList());
		questionEntity.setAnswerEntities(questionUpdateDto.getAnswerList());
		questionEntity.setPassageEntity(passage);
		questionEntity.setQuestionContentEntity(questionContentEntity);

		questionRepository.save(questionEntity);

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

}
