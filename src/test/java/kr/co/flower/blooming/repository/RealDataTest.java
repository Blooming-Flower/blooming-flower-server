package kr.co.flower.blooming.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.JpaQueryCreator;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.flower.blooming.dto.out.SearchPassageUnitAndNumbersDto;
import kr.co.flower.blooming.entity.PassageEntity;
import kr.co.flower.blooming.entity.PassageType;
import static kr.co.flower.blooming.entity.QPassageEntity.passageEntity;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;;

@SpringBootTest
public class RealDataTest {

    @Autowired
    private JPAQueryFactory queryFactory;
    
    @Autowired
    private PassageRepository passageRepository;

    @Test
    void 지문번호가져오는테스트() {
        // given
        String passageYear = "2023";
        String passageUnit = "test";
        PassageType passageType = PassageType.P1;

        // when
        Pageable pageable = PageRequest.of(0, 10);
        Page<String> units = passageRepository.searchPassageUnitGroupByUnit(pageable, passageYear, null, null);
        
        System.out.println(units);
//        List<PassageEntity> passageList = queryFactory.select(passageEntity)
//                .from(passageEntity)
//                .where(passageEntity.passageYear.eq(passageYear))
//                .groupBy(passageEntity.passageUnit)
//                .fetch();

//        Map<String, List<SearchPassageUnitAndNumbersDto>> collect =
//                passageList.stream().collect(Collectors.groupingBy(PassageEntity::getPassageUnit,
//                        Collectors.mapping(passage -> {
//                            SearchPassageUnitAndNumbersDto passageUnitAndNumbersDto =
//                                    new SearchPassageUnitAndNumbersDto();
//                            passageUnitAndNumbersDto.setPassageUnit(passage.getPassageUnit());
//                            passageUnitAndNumbersDto.setPassageNumber(passage.getPassageNumber());
//                            passageUnitAndNumbersDto
//                                    .setQuestionCount(passage.getQuestionEntities().size());
//                            return passageUnitAndNumbersDto;
//                        }, Collectors.toList())));

    }
}
