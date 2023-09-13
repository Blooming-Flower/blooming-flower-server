package kr.co.flower.blooming.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.flower.blooming.dto.out.PassageListDto;
import kr.co.flower.blooming.dto.out.QPassageListDto;
import kr.co.flower.blooming.entity.PassageType;

import static kr.co.flower.blooming.entity.QQuestionEntity.questionEntity;
import static kr.co.flower.blooming.entity.QPassageEntity.passageEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PassageCustomRepositoryImpl implements PassageCustomRepository {

    private final JPAQueryFactory queryFactory;

    /**
     * 동적 검색 쿼리. filter, order에 따라 paging
     * 
     * @param pageable
     * @param passageType
     * @param passageYear
     * @param passageName
     * @param passageUnit
     * @param passageNumber
     * @return
     */
    @Override
    public Page<PassageListDto> findPassageAll(Pageable pageable, String passageYear,
            String passageName) {
        List<PassageListDto> content = getPassageListDto(pageable, passageYear, passageName);

        Long count = queryFactory.select(passageEntity.count())
                .from(passageEntity)
                .where(eqPassageYear(passageYear), containsPassageName(passageName))
                .fetchOne();

        return PageableExecutionUtils.getPage(content, pageable,
                () -> count);
    }

    /**
     * content를 가져옴
     * 
     * @param pageable
     * @param passageType
     * @param passageYear
     * @param passageName
     * @param passageUnit
     * @param passageNumber
     * @return passageEntity.questionEntities.size()
     */
    private List<PassageListDto> getPassageListDto(Pageable pageable, String passageYear,
            String passageName) {
        return queryFactory
                .select(new QPassageListDto(passageEntity.passageId, passageEntity.passageType,
                        passageEntity.passageName,
                        passageEntity.passageUnit, passageEntity.passageNumber,
                        JPAExpressions.select(questionEntity.count()).from(questionEntity)
                                .where(passageEntity.passageId
                                        .eq(questionEntity.passageEntity.passageId))))
                .from(passageEntity)
                .where(eqPassageYear(passageYear), containsPassageName(passageName))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(passageEntity.passageName.asc(),
                        passageEntity.passageUnit.asc(), passageEntity.passageNumber.asc())
                // .orderBy(getOrderSpecifiers(pageable.getSort()).toArray(OrderSpecifier[]::new))
                .fetch();
    }

    /**
     * 동적 정렬
     * 
     * @param sort
     * @return
     */
    private List<OrderSpecifier<?>> getOrderSpecifiers(Sort sort) {
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
        PathBuilder orderByExpression =
                new PathBuilder<>(passageEntity.getType(), passageEntity.getMetadata());

        sort.forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String property = order.getProperty();

            orderSpecifiers.add(new OrderSpecifier<>(direction, orderByExpression.get(property)));

        });

        return orderSpecifiers;
    }


    /**
     * 교재 연도 비교
     * 
     * @param passageYear
     * @return
     */
    private BooleanExpression eqPassageYear(String passageYear) {
        return passageYear != null ? passageEntity.passageYear.eq(passageYear) : null;
    }

    /**
     * 교재 이름 포함되어있는지 비교
     * 
     * @param passageName
     * @return
     */
    private BooleanExpression containsPassageName(String passageName) {
        return passageName != null ? passageEntity.passageName.contains(passageName) : null;
    }

    /**
     * 교재 이름 같은 지 비교
     * 
     * @param passageName
     * @return
     */
    private BooleanExpression eqPassageName(String passageName) {
        return passageName != null ? passageEntity.passageName.eq(passageName) : null;
    }

    /**
     * 교재 종류 비교
     * 
     * @param passageType
     * @return
     */
    private BooleanExpression eqPassageType(PassageType passageType) {
        return passageType != null ? passageEntity.passageType.eq(passageType) : null;
    }

    /**
     * 강 비교
     * 
     * @param passageUnit
     * @return
     */
    private BooleanExpression eqPassageUnit(String passageUnit) {
        return passageUnit != null ? passageEntity.passageUnit.eq(passageUnit) : null;
    }

    /**
     * 교재 종류별, 입력된 교재이름이 포함된 교재 이름 목록 검색
     */
    @Override
    public List<String> searchPassageNameList(PassageType passageType, String passageName) {
        return queryFactory.select(passageEntity.passageName)
                .from(passageEntity)
                .where(passageEntity.passageType.eq(passageType),
                        passageEntity.passageName.contains(passageName))
                .fetch();
    }


    /**
     * 검색 조건에 따라 지문 (강) 조회
     * 
     * 페이징 처리
     * 
     * @param pageable
     * @param passageType
     * @param passageYear
     * @param passageName
     * @return
     */
    @Override
    public Page<String> searchPassageUnitGroupByUnit(Pageable pageable, PassageType passageType,
            String passageYear, String passageName) {
        List<String> passageUnits = getPassageUnit(pageable, passageType, passageYear, passageName);

        Long count = queryFactory.select(passageEntity.count())
                .from(passageEntity)
                .where(eqPassageType(passageType) ,eqPassageYear(passageYear), eqPassageName(passageName))
                .fetchOne();

        return PageableExecutionUtils.getPage(passageUnits, pageable,
                () -> count);
    }

    /**
     * 검색 조건에 따라 지문 (강) 조회 - content
     * 
     * @param pageable
     * @param passageType
     * @param passageYear
     * @param passageName
     * @return
     */
    private List<String> getPassageUnit(Pageable pageable, PassageType passageType,
            String passageYear, String passageName) {
        return queryFactory.select(passageEntity.passageUnit)
                .from(passageEntity)
                .where(eqPassageType(passageType) ,eqPassageYear(passageYear), eqPassageName(passageName))
                .groupBy(passageEntity.passageUnit)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(passageEntity.passageUnit.asc())
                .fetch();
    }



}
