package kr.co.flower.blooming.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.flower.blooming.dto.in.PassageContentUpdateParam;
import kr.co.flower.blooming.dto.in.PassageRegistParam;
import kr.co.flower.blooming.dto.out.CheckExistPassageDto;
import kr.co.flower.blooming.dto.out.PassageListDto;
import kr.co.flower.blooming.dto.out.SearchPassageDto;
import kr.co.flower.blooming.dto.out.SearchPassageDto.SearchQuestionDtos;
import kr.co.flower.blooming.entity.PassageEntity;
import kr.co.flower.blooming.entity.PassageType;
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
    private final QuestionService questionService;

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
     * 원본 지문만 수정
     * 
     * @param passageContentUpdateParam
     */
    @Transactional
    public void updatePassageContent(PassageContentUpdateParam passageContentUpdateParam) {
        PassageEntity passageEntity =
                passageRepository.findById(passageContentUpdateParam.getPassageId())
                        .orElseThrow(() -> new FlowerException(FlowerError.ENTITY_NOT_FOUND));

        passageEntity.setPassageContent(passageContentUpdateParam.getPassageContent());
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

        // 문제 code 별, 문제(발문, 지문, 선지, 답) dto 로 변환
        List<SearchQuestionDtos> questions =
                questionService.convertQuestionDtos(passage.getQuestionEntities());

        SearchPassageDto searchPassageDto = new SearchPassageDto();
        searchPassageDto.setPassageId(passage.getPassageId());
        searchPassageDto.setPassageContent(passage.getPassageContent());
        searchPassageDto.setQuestionInfo(questions);

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
