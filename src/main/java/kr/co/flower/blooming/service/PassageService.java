package kr.co.flower.blooming.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.flower.blooming.dto.in.PassageRegistParam;
import kr.co.flower.blooming.dto.out.CheckExistPassageDto;
import kr.co.flower.blooming.dto.out.ChooseDto;
import kr.co.flower.blooming.dto.out.PassageListDto;
import kr.co.flower.blooming.dto.out.SearchPassageDto;
import kr.co.flower.blooming.dto.out.SearchPassageDto.SearchQuestionDtos;
import kr.co.flower.blooming.dto.out.SearchPassageDto.SearchQuestionDtos.SearchQuestionDto;
import kr.co.flower.blooming.entity.AnswerDto;
import kr.co.flower.blooming.entity.PassageEntity;
import kr.co.flower.blooming.entity.PassageType;
import kr.co.flower.blooming.entity.QuestionEntity;
import kr.co.flower.blooming.exception.FlowerError;
import kr.co.flower.blooming.exception.FlowerException;
import kr.co.flower.blooming.repository.PassageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 지문 CRUD
 * 
 * @author shmin
 *
 */
@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PassageService {
    private final PassageRepository passageRepository;

    /**
     * 지문 저장
     * 
     * @param passageRegistDto
     */
    @Transactional
    public void savePassage(PassageRegistParam passageRegistParam) {
        PassageEntity passageEntity = new PassageEntity();

        setPassageEntity(passageEntity, passageRegistParam);

        passageRepository.save(passageEntity);
    }

    /**
     * 지문 수정
     * 
     * @param passageRegistDto
     */
    @Transactional
    public void updatePassage(PassageRegistParam passageRegistParam) {
        PassageEntity passageEntity = passageRepository.findById(passageRegistParam.getPassageId())
                .orElseThrow(() -> new FlowerException(FlowerError.ENTITY_NOT_FOUND));

        setPassageEntity(passageEntity, passageRegistParam);
    }

    /**
     * 지문 삭제
     * 
     * @param passageId
     */
    @Transactional
    public void deletePassage(long passageId) {
        passageRepository.findById(passageId)
                .orElseThrow(() -> new FlowerException(FlowerError.ENTITY_NOT_FOUND));

        passageRepository.deleteById(passageId);
    }

    /**
     * 지문 수정을 누르면 보이는 화면
     * 
     * @param passageId
     * @return
     */
    public SearchPassageDto searchPassageInfo(long passageId) {
        PassageEntity passage = passageRepository.findById(passageId)
                .orElseThrow(() -> new FlowerException(FlowerError.ENTITY_NOT_FOUND));

        SearchPassageDto searchPassageDto = new SearchPassageDto();
        List<SearchQuestionDtos> questions = new ArrayList<>();

        // code 별로 questionEntity grouping
        List<QuestionEntity> questionEntities = passage.getQuestionEntities();
        Map<String, List<QuestionEntity>> groupByCode = questionEntities.stream()
                .collect(Collectors.groupingBy(QuestionEntity::getQuestionCode));

        for (Entry<String, List<QuestionEntity>> entry : groupByCode.entrySet()) {
            String questionCode = entry.getKey();
            List<QuestionEntity> questionByCode = entry.getValue();

            SearchQuestionDtos searchQuestionDtos = new SearchQuestionDtos();
            searchQuestionDtos.setQuestionCode(questionCode);
            searchQuestionDtos.setQuestionTitle(
                    questionByCode.get(0).getQuestionContentEntity().getQuestionTitle());
            searchQuestionDtos.setQuestionContent(
                    questionByCode.get(0).getQuestionContentEntity().getQuestionContent());

            List<SearchQuestionDto> searchQuestionDto = new ArrayList<>();
            for (QuestionEntity question : questionByCode) {
                SearchQuestionDto questionDto = new SearchQuestionDto();
                questionDto.setQuestionId(question.getQuestionId());
                questionDto.setQuestionType(question.getQuestionType());
                questionDto.setQuestionSubTitle(question.getQuestionSubTitle());
                questionDto.setSubBox(question.getSubBox());
                questionDto.setPastYn(question.isPastYn());
                questionDto.setChoose(question.getChooseEntities().stream().map(choose -> {
                    ChooseDto chooseDto = new ChooseDto();
                    chooseDto.setSeq(choose.getChooseSeq());
                    chooseDto.setContent(choose.getChooseContent());

                    return chooseDto;
                }).collect(Collectors.toList()));
                questionDto.setAnswer(question.getAnswerEntities().stream().map(answer -> {
                    AnswerDto answerDto = new AnswerDto();
                    answerDto.setContent(answer.getAnswerContent());

                    return answerDto;
                }).collect(Collectors.toList()));

                searchQuestionDto.add(questionDto);
            }

            searchQuestionDtos.setQuestion(searchQuestionDto);
            questions.add(searchQuestionDtos);
        }

        searchPassageDto.setPassageContent(passage.getPassageContent());
        searchPassageDto.setQuestions(questions);

        return searchPassageDto;
    }

    /**
     * 지문 목록 조회
     * 
     * @param pageRequest
     * @return
     */
    public Page<PassageListDto> searchPassageList(Pageable pageable, String passageYear,
            String passageName) {
        return passageRepository.findPassageAll(pageable, passageYear, passageName);
    }

    /**
     * 교재 종류에 따라 현재 작성된 교재 이름이 포함된 교재 이름 목록 조회
     * 
     * @param passageType
     * @param passageName
     */
    public List<String> searchPassageNameList(PassageType passageType, String passageName) {
        return passageRepository.searchPassageNameList(passageType, passageName);
    }

    /**
     * 같은 지문의 종류, 연도, 교재, 강, 번호에 대해선 유니크 해야 한다.
     * 
     * @param passageType
     * @param passageYear
     * @param passageName
     * @param passageUnit
     * @param passageNumber
     * @return
     */
    public CheckExistPassageDto checkExistPassage(PassageType passageType, String passageYear,
            String passageName,
            String passageUnit, String passageNumber) {
        return passageRepository.checkExistPassage(passageType, passageYear, passageName,
                passageUnit, passageNumber);
    }

    private void setPassageEntity(PassageEntity passageEntity,
            PassageRegistParam passageRegistParam) {
        passageEntity.setPassageType(passageRegistParam.getPassageType());
        passageEntity.setPassageYear(passageRegistParam.getPassageYear());
        passageEntity.setPassageName(passageRegistParam.getPassageName());
        passageEntity.setPassageUnit(passageRegistParam.getPassageUnit());
        passageEntity.setPassageNumber(passageRegistParam.getPassageNumber());
        passageEntity.setPassageContent(passageRegistParam.getPassageContent());
    }
}
