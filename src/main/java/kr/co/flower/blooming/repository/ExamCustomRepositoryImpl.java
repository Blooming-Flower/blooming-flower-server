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
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.flower.blooming.dto.out.ExamListDto;
import kr.co.flower.blooming.dto.out.PassageNumberAndQuestionCountDto;
import kr.co.flower.blooming.dto.out.QExamListDto;
import kr.co.flower.blooming.dto.out.QPassageNumberAndQuestionCountDto;
import kr.co.flower.blooming.entity.ExamQuestionEntity;
import kr.co.flower.blooming.entity.PassageType;
import static kr.co.flower.blooming.entity.QExamQuestionEntity.examQuestionEntity;
import static kr.co.flower.blooming.entity.QQuestionEntity.questionEntity;
import static kr.co.flower.blooming.entity.QExamEntity.examEntity;
import static kr.co.flower.blooming.entity.QPassageEntity.passageEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExamCustomRepositoryImpl implements ExamCustomRepository {
    private final JPAQueryFactory queryFactory;

    /**
     * 동적 검색 쿼리, filter, order에 따라 paging
     * 
     * @param pageable
     * @param examTitle
     * @return
     */
    @Override
    public Page<ExamListDto> findExamList(Pageable pageable, String examTitle) {
        List<ExamListDto> content = getExamListDto(pageable, examTitle);

        Long count = queryFactory.select(examEntity.count())
                .from(examEntity)
                .where(containsExamTitle(examTitle))
                .fetchOne();

        return PageableExecutionUtils.getPage(content, pageable,
                () -> count);
    }

    /**
     * exam list content 가져옴
     * 
     * @param pageable
     * @param examTitle
     * @return
     */
    private List<ExamListDto> getExamListDto(Pageable pageable, String examTitle) {
        return queryFactory.select(new QExamListDto(examEntity.examTitle, examEntity.createTime))
                .from(examEntity)
                .where(containsExamTitle(examTitle))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(getOrderSpecifiers(pageable.getSort()).toArray(OrderSpecifier[]::new))
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
                new PathBuilder<>(examEntity.getType(), examEntity.getMetadata());

        sort.forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String property = order.getProperty();

            orderSpecifiers.add(new OrderSpecifier<>(direction, orderByExpression.get(property)));

        });

        return orderSpecifiers;
    }

    /**
     * 시험지 이름 포함되었는지 검사
     * 
     * @param examTitle
     * @return
     */
    private BooleanExpression containsExamTitle(String examTitle) {
        return examTitle != null ? examEntity.examTitle.eq(examTitle) : null;
    }
    
    
    /**
     * question이 하나라도 있는 passage type, year인 지문 명 찾기 
     */
    @Override
    public List<String> searchPassageNameHavingQuestion(PassageType passageType, String year) {
        return queryFactory.select(passageEntity.passageName)
                .distinct()
                .from(passageEntity)
                .innerJoin(questionEntity)
                .on(passageEntity.passageId.eq(questionEntity.passageEntity.passageId))
                .where(passageEntity.passageType.eq(passageType), 
                        passageEntity.passageYear.eq(year))
                .fetch();
    }
    
    /**
     * 검색 조건에 따라 question이 하나라도 있는 지문 (강) 조회
     * 
     * @param pageable
     * @param passageType
     * @param passageYear
     * @param passageName
     * @return
     */
    @Override
    public List<PassageNumberAndQuestionCountDto> searchPassageNumbersHavingQuestion(Pageable pageable, PassageType passageType,
            String passageYear, String passageName) {
        List<String> passageUnitGroup = queryFactory.select(passageEntity.passageUnit).from(passageEntity)
                .innerJoin(questionEntity)
                .on(passageEntity.passageId.eq(questionEntity.passageEntity.passageId))
                .where( passageEntity.passageType.eq(passageType), 
                        passageEntity.passageYear.eq(passageYear),
                        passageEntity.passageName.eq(passageName))
                .groupBy(passageEntity.passageUnit)
                .orderBy(passageEntity.passageUnit.length().asc(), 
                        passageEntity.passageUnit.asc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        return queryFactory 
                .select(new QPassageNumberAndQuestionCountDto(passageEntity.passageName, passageEntity.passageUnit,
                        passageEntity.passageNumber, passageEntity.passageId.max(), questionEntity.count()))
                .from(passageEntity).innerJoin(questionEntity)
                .on(passageEntity.passageId.eq(questionEntity.passageEntity.passageId))
                .where(passageEntity.passageType.eq(passageType), 
                        passageEntity.passageYear.eq(passageYear),
                        passageEntity.passageName.eq(passageName),  
                        passageEntity.passageUnit.in(passageUnitGroup))
                .groupBy(passageEntity.passageUnit, passageEntity.passageNumber)
                .fetch();
    }
    
    /**
     * 해당 시험지에 맞는 question조회
     * 
     * @param examId
     * @return
     */
    @Override
    public List<ExamQuestionEntity> findExamQuestions(long examId){
    	return queryFactory.selectFrom(examQuestionEntity)
    			.leftJoin(examQuestionEntity.examEntity, examEntity)
    			.fetchJoin()
    			.leftJoin(examQuestionEntity.questionEntity, questionEntity)
    			.fetchJoin()
    			.where(examQuestionEntity.examEntity.examId.eq(examId))
				.fetch();
				
    }

}
