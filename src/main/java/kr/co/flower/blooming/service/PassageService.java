package kr.co.flower.blooming.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.flower.blooming.dto.in.PassageRegistDto;
import kr.co.flower.blooming.dto.out.PassageListDto;
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

    /**
     * 지문 저장
     * 
     * @param passageRegistDto
     */
    @Transactional
    public void savePassage(PassageRegistDto passageRegistDto) {
        PassageEntity passageEntity = new PassageEntity();

        setPassageEntity(passageEntity, passageRegistDto);

        passageRepository.save(passageEntity);
    }

    /**
     * 지문 수정
     * 
     * @param passageRegistDto
     */
    @Transactional
    public void updatePassage(PassageRegistDto passageRegistDto) {
        PassageEntity passageEntity = passageRepository.findById(passageRegistDto.getPassageId())
                .orElseThrow(() -> new FlowerException(FlowerError.ENTITY_NOT_FOUND));

        setPassageEntity(passageEntity, passageRegistDto);
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
     * 단일 지문 조회
     * 
     * @param passageId
     * @return
     */
    public PassageEntity findPassage(long passageId) {
        return passageRepository.findById(passageId)
                .orElseThrow(() -> new FlowerException(FlowerError.ENTITY_NOT_FOUND));
    }

    /**
     * 지문 목록 조회
     * 
     * @param pageRequest
     * @return
     */
    public Page<PassageListDto> searchPassageList(Pageable pageable, PassageType passageType,
            String passageYear, String passageName, String passageUnit, String passageNumber) {
        return passageRepository.findPassageAll(pageable, passageType, passageYear, passageName,
                passageUnit, passageNumber);
    }

    private void setPassageEntity(PassageEntity passageEntity, PassageRegistDto passageRegistDto) {
        passageEntity.setPassageType(passageRegistDto.getPassageType());
        passageEntity.setPassageYear(passageRegistDto.getPassageYear());
        passageEntity.setPassageName(passageRegistDto.getPassageName());
        passageEntity.setPassageUnit(passageRegistDto.getPassageUnit());
        passageEntity.setPassageNumber(passageRegistDto.getPassageNumber());
        passageEntity.setPassageContent(passageRegistDto.getPassageContent());
    }
}
