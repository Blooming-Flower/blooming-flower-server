package kr.co.flower.blooming.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.flower.blooming.dto.in.PassageRegistParam;
import kr.co.flower.blooming.dto.out.CheckExistPassageDto;
import kr.co.flower.blooming.dto.out.PassageListDto;
import kr.co.flower.blooming.dto.out.SearchPassageDto;
import kr.co.flower.blooming.dto.out.SearchPassageDto.SearchQuestionDto;
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
        SearchPassageDto searchPassageDto = new SearchPassageDto();

        PassageEntity passage = passageRepository.findById(passageId)
                .orElseThrow(() -> new FlowerException(FlowerError.ENTITY_NOT_FOUND));

        List<QuestionEntity> questionEntities = passage.getQuestionEntities();

        Map<String, List<SearchQuestionDto>> collect = questionEntities.stream()
                .collect(Collectors.groupingBy(QuestionEntity::getQuestionCode,
                        Collectors.mapping(q -> {
                            SearchQuestionDto searchQuestionDto = new SearchQuestionDto();
                            searchQuestionDto.setQuestionType(q.getQuestionType());
                            searchQuestionDto.setQuestionId(q.getQuestionId());

                            return searchQuestionDto;
                        }, Collectors.toList())));

        searchPassageDto.setPassageContent(passage.getPassageContent());
        searchPassageDto.setQuestions(collect);

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

    public CheckExistPassageDto checkExistPassage(
            PassageType passageType,
            String passageYear,
            String passageName,
            String passageUnit,
            String passageNumber) {
        return passageRepository.checkExistPassage(passageType, passageYear, passageName, passageUnit, passageNumber);
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
