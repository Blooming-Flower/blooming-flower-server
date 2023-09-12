package kr.co.flower.blooming.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.flower.blooming.dto.in.PassageRegistDto;
import kr.co.flower.blooming.entity.PassageEntity;
import kr.co.flower.blooming.entity.PassageType;
import kr.co.flower.blooming.exception.FlowerException;
import kr.co.flower.blooming.repository.PassageRepository;
import static kr.co.flower.blooming.entity.QPassageEntity.passageEntity;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("[Passage] Service Test")
public class PassageServiceTest {

    @Autowired
    private PassageService passageService;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Autowired
    private PassageRepository passageRepository;

    private String name = "지문이름";
    private PassageType type = PassageType.EBS;
    private String year = "2023";
    private String unit = "지문유닛";
    private String number = "4";
    private String content = "지문 내용";

    @Test
    @DisplayName("지문 저장 TEST")
    @Transactional
    void savePassageTest() {
        // given
        PassageRegistDto registDto = makePassageRegistDto();

        // when
        passageService.savePassage(registDto);

        // then
        PassageEntity passage = jpaQueryFactory.selectFrom(passageEntity)
                .where(passageEntity.passageName.eq(name)).fetchFirst();

        assertNotNull(passage);
        assertEquals(passage.getPassageName(), name);
        assertEquals(passage.getPassageType(), type);
        assertEquals(passage.getPassageYear(), year);
        assertEquals(passage.getPassageUnit(), unit);
        assertEquals(passage.getPassageNumber(), number);
        assertEquals(passage.getPassageContent(), content);
    }

    @Test
    @DisplayName("지문 수정 TEST")
    @Transactional
    void updatePassageTest() {
        // given
        String updateName = "수정된이름";

        PassageRegistDto registDto = makePassageRegistDto();

        passageService.savePassage(registDto);

        PassageEntity passage = jpaQueryFactory.selectFrom(passageEntity)
                .where(passageEntity.passageName.eq(name)).fetchFirst();

        // when
        long passageId = passage.getPassageId();
        assertThrows(FlowerException.class, () -> {
            passageService.updatePassage(registDto);
        });

        registDto.setPassageId(passageId);
        registDto.setPassageName(updateName);

        // then
        passageService.updatePassage(registDto);

        PassageEntity updateNamePassage = jpaQueryFactory.selectFrom(passageEntity)
                .where(passageEntity.passageName.eq(updateName)).fetchFirst();

        assertNotNull(updateNamePassage);
        assertEquals(updateNamePassage.getPassageName(), updateName);
        assertEquals(updateNamePassage.getPassageType(), type);
        assertEquals(updateNamePassage.getPassageYear(), year);
        assertEquals(updateNamePassage.getPassageUnit(), unit);
        assertEquals(updateNamePassage.getPassageNumber(), number);
        assertEquals(updateNamePassage.getPassageContent(), content);
    }

    @Test
    @DisplayName("지문 삭제 TEST")
    @Transactional
    void deletePassageTest() {
        // given
        PassageRegistDto registDto = makePassageRegistDto();

        passageService.savePassage(registDto);

        PassageEntity passage = jpaQueryFactory.selectFrom(passageEntity)
                .where(passageEntity.passageName.eq(name)).fetchFirst();

        long passageId = passage.getPassageId();

        // when
        assertThrows(FlowerException.class, () -> {
            passageService.deletePassage(0);
        });

        passageService.deletePassage(passageId);

        // then

        Optional<PassageEntity> optPassage = passageRepository.findById(passageId);

        assertFalse(optPassage.isPresent());
    }

    @Test
    @DisplayName("지문 단건 조회 TEST")
    @Transactional
    void findPassageTest() {
        // given
        PassageRegistDto registDto = makePassageRegistDto();

        passageService.savePassage(registDto);

        PassageEntity passage = jpaQueryFactory.selectFrom(passageEntity)
                .where(passageEntity.passageName.eq(name)).fetchFirst();

        long passageId = passage.getPassageId();

        // when
//        assertThrows(FlowerException.class, () -> {
//            passageService.findPassage(0);
//        });
//
//        PassageEntity findPassage = passageService.findPassage(passageId);

        // then
//        assertNotNull(findPassage);
//        assertEquals(findPassage.getPassageName(), name);
//        assertEquals(findPassage.getPassageType(), type);
//        assertEquals(findPassage.getPassageYear(), year);
//        assertEquals(findPassage.getPassageUnit(), unit);
//        assertEquals(findPassage.getPassageNumber(), number);
//        assertEquals(findPassage.getPassageContent(), content);
    }

    private PassageRegistDto makePassageRegistDto() {
        PassageRegistDto passageRegistDto = new PassageRegistDto();
        passageRegistDto.setPassageName(name);
        passageRegistDto.setPassageType(type);
        passageRegistDto.setPassageUnit(unit);
        passageRegistDto.setPassageYear(year);
        passageRegistDto.setPassageNumber(number);
        passageRegistDto.setPassageContent(content);

        return passageRegistDto;
    }
}
