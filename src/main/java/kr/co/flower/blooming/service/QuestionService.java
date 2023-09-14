package kr.co.flower.blooming.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.flower.blooming.dto.in.QuestionRegistParam;
import kr.co.flower.blooming.dto.in.QuestionRegistParam.QuestionDto;
import kr.co.flower.blooming.dto.in.QuestionUpdateParam;
import kr.co.flower.blooming.dto.out.PassageNumberAndQuestionCountDto;
import kr.co.flower.blooming.entity.PassageEntity;
import kr.co.flower.blooming.entity.PassageType;
import kr.co.flower.blooming.entity.QuestionContentEntity;
import kr.co.flower.blooming.entity.QuestionEntity;
import kr.co.flower.blooming.exception.FlowerError;
import kr.co.flower.blooming.exception.FlowerException;
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

    /**
     * 문제 저장
     * 
     * @param questionRegistDto
     */
    @Transactional
    public void saveQuestion(QuestionRegistParam questionRegistDto) {
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
    public void updateQuestion(QuestionUpdateParam questionUpdateDto) {
        QuestionEntity questionEntity =
                questionRepository.findById(questionUpdateDto.getQuestionId())
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
        questionRepository.findById(questionId)
                .orElseThrow(() -> new FlowerException(FlowerError.ENTITY_NOT_FOUND));

        questionRepository.deleteById(questionId);
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
    public Map<String, List<PassageNumberAndQuestionCountDto>> searchPassageNumbers(
            Pageable pageable, PassageType passageType, String passageYear, String passageName) {
        // 페이징된 passage number, question count
        List<PassageNumberAndQuestionCountDto> content = passageRepository
                .searchPassageUnitGroupByUnit(pageable, passageType, passageYear, passageName)
                .getContent();

        // passage unit으로 grouping
        return content.stream().collect(Collectors
                .groupingBy(PassageNumberAndQuestionCountDto::getPassageUnit));
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
