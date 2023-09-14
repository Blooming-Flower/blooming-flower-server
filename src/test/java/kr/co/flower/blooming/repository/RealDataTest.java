package kr.co.flower.blooming.repository;

import org.hibernate.query.criteria.internal.expression.function.AggregationFunction.COUNT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.JpaQueryCreator;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.flower.blooming.dto.out.PassageNumberAndQuestionCountDto;
import kr.co.flower.blooming.entity.PassageEntity;
import kr.co.flower.blooming.entity.PassageType;
import kr.co.flower.blooming.entity.QPassageEntity;
import static kr.co.flower.blooming.entity.QQuestionEntity.questionEntity;
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
        PassageType passageType = PassageType.P1;
        String passageName = "test1";

        // when
        Pageable pageable = PageRequest.of(0, 10);
        // Page<String> units = passageRepository.searchPassageUnitGroupByUnit(pageable,
        // passageType, passageYear, null);

        // System.out.println(units);

        // select p.passage_unit, p.passage_number, count(question_id) from passage p
        // left join question q on p.passage_id = q.passage_id
        // where passage_unit in (
        // select passage_unit from passage group by passage_unit order by length(passage_unit),
        // passage_unit asc limit 10)
        // group by p.passage_unit, p.passage_number;t



        List<String> where = queryFactory.select(passageEntity.passageUnit).from(passageEntity)
                .where(passageEntity.passageType.eq(passageType),
                        passageEntity.passageYear.eq(passageYear),
                        passageEntity.passageName.eq(passageName))
                .groupBy(passageEntity.passageUnit)
                .orderBy(passageEntity.passageUnit.length().asc(), passageEntity.passageUnit.asc())
                .limit(10)
                .fetch();


        List<Tuple> fetch = queryFactory
                .select(passageEntity.passageUnit, passageEntity.passageNumber,
                        questionEntity.count())
                .from(passageEntity)
                .leftJoin(questionEntity)
                .on(passageEntity.passageId.eq(questionEntity.passageEntity.passageId))
                .where(
                        passageEntity.passageUnit.in(where))
                .groupBy(passageEntity.passageUnit, passageEntity.passageNumber)
                .fetch();

        System.out.println(fetch);



        // queryFactory.select(passageEntity.passageUnit)
        // .from(passageEntity)
        //// .where(eqPassageType(passageType) ,eqPassageYear(passageYear),
        // eqPassageName(passageName))
        // .groupBy(passageEntity.passageUnit)
        // .offset(pageable.getOffset())
        // .limit(pageable.getPageSize())
        // .orderBy(passageEntity.passageUnit.asc())
        // .fetch();

    }
}
