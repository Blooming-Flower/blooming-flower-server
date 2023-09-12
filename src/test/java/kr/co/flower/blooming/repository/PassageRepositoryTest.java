package kr.co.flower.blooming.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import kr.co.flower.blooming.config.TestQueryDSLConfig;
import kr.co.flower.blooming.dto.out.PassageListDto;
import kr.co.flower.blooming.entity.PassageEntity;
import kr.co.flower.blooming.entity.PassageType;

@ActiveProfiles("test")
@Import(TestQueryDSLConfig.class)
@DataJpaTest
@DisplayName("[Passage] Repository Test")
public class PassageRepositoryTest {

    @Autowired
    private PassageRepository passageRepository;

    @Test
    @DisplayName("동적 검색 TEST")
    void findPassageAllTest() {
        // select
        // passageent0_.passage_type as col_0_0_,
        // passageent0_.passage_name as col_1_0_,
        // passageent0_.passage_unit as col_2_0_,
        // passageent0_.passage_number as col_3_0_,
        // (select
        // count(questionen1_.question_id)
        // from
        // question questionen1_
        // where
        // passageent0_.passage_id = questionen1_.question_id) as col_4_0_
        // from
        // passage passageent0_
        // where
        // passageent0_.passage_year=?
        // and (
        // passageent0_.passage_name like ? escape '!'
        // )
        // order by
        // passageent0_.passage_name desc,
        // passageent0_.passage_unit asc limit ?

        // given
        passageSave();

        List<Sort.Order> orders = new ArrayList<>();
        orders.add(Sort.Order.desc("passageName"));
        orders.add(Sort.Order.asc("passageUnit"));

        Sort sort = Sort.by(orders);

        Pageable pageable = PageRequest.of(0, 10, sort);

        // when
		Page<PassageListDto> passageList = passageRepository.findPassageAll(pageable, "2023", "테스트");

        // then
        List<PassageListDto> content = passageList.getContent();
        assertEquals(content.size(), 5);
    }

    private void passageSave() {
        List<PassageEntity> list = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < 100; i++) {
            PassageEntity passageEntity = new PassageEntity();

            String year = String.valueOf(rand.nextInt(1000));
            String unit = String.valueOf(rand.nextInt(1000));
            String name = String.valueOf(rand.nextInt(1000));
            String number = String.valueOf(rand.nextInt(1000));
            String passageContent = String.valueOf(rand.nextInt(1000));

            if (i % 10 == 0) {
                name = "테스트";
                if (i % 20 == 0) {
                    year = "2023";
                }
            }

            passageEntity.setPassageType(PassageType.P1);
            passageEntity.setPassageYear(year);
            passageEntity.setPassageName("지문" + name);
            passageEntity.setPassageUnit("지문 유닛" + unit);
            passageEntity.setPassageNumber("넘버" + number);
            passageEntity.setPassageContent("지문 내용" + passageContent);

            list.add(passageEntity);
        }

        passageRepository.saveAll(list);
    }
}
