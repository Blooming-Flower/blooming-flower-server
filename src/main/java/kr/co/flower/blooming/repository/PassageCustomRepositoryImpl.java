package kr.co.flower.blooming.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.flower.blooming.dto.out.PassageListDto;
import kr.co.flower.blooming.dto.out.QPassageListDto;
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
	public Page<PassageListDto> findPassageAll(Pageable pageable, String passageYear, String passageName) {
		List<PassageListDto> content = getPassageListDto(pageable, passageYear, passageName);

		return PageableExecutionUtils.getPage(content, pageable, () -> getCount(passageYear, passageName));
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
	private List<PassageListDto> getPassageListDto(Pageable pageable, String passageYear, String passageName) {
		return queryFactory
				.select(new QPassageListDto(passageEntity.passageType, passageEntity.passageName,
						passageEntity.passageUnit, passageEntity.passageNumber,
						JPAExpressions.select(questionEntity.count()).from(questionEntity)
								.where(passageEntity.passageId.eq(questionEntity.passageEntity.passageId))))
				.from(passageEntity).where(getPredicateOfWhere(passageYear, passageName)).offset(pageable.getOffset())
				.limit(pageable.getPageSize()).orderBy(questionEntity.passageEntity.passageName.asc(),
						questionEntity.passageEntity.passageUnit.asc(), questionEntity.passageEntity.passageNumber.asc())
//				.orderBy(getOrderSpecifiers(pageable.getSort()).toArray(OrderSpecifier[]::new))
				.fetch();
	}

	/**
	 * count 계산
	 * 
	 * @param passageType
	 * @param passageYear
	 * @param passageName
	 * @param passageUnit
	 * @param passageNumber
	 * @return
	 */
	private Long getCount(String passageYear, String passageName) {
		return queryFactory.select(passageEntity.count())
				.from(passageEntity)
				.where(getPredicateOfWhere(passageYear, passageName))
				.fetchOne();
	}

	/**
	 * 동적 정렬
	 * 
	 * @param sort
	 * @return
	 */
	private List<OrderSpecifier<?>> getOrderSpecifiers(Sort sort) {
		List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
		PathBuilder orderByExpression = new PathBuilder<>(passageEntity.getType(), passageEntity.getMetadata());

		sort.forEach(order -> {
			Order direction = order.isAscending() ? Order.ASC : Order.DESC;
			String property = order.getProperty();

			orderSpecifiers.add(new OrderSpecifier<>(direction, orderByExpression.get(property)));

		});

		return orderSpecifiers;
	}

	/**
	 * 동적 filterings
	 * 
	 * @param passageType
	 * @param passageYear
	 * @param passageName
	 * @param passageUnit
	 * @param passageNumber
	 * @return
	 */
	private Predicate[] getPredicateOfWhere(String passageYear, String passageName) {
		return new Predicate[] { eqPassageYear(passageYear), eqPassageName(passageName) };
	}

	private BooleanExpression eqPassageYear(String passageYear) {
		return passageYear != null ? passageEntity.passageYear.eq(passageYear) : null;
	}

	private BooleanExpression eqPassageName(String passageName) {
		return passageName != null ? passageEntity.passageName.contains(passageName) : null;
	}

}
