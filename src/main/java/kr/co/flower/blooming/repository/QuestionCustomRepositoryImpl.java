package kr.co.flower.blooming.repository;

import java.util.List;
import com.querydsl.jpa.impl.JPAQueryFactory;
import static kr.co.flower.blooming.entity.QQuestionEntity.questionEntity;

import kr.co.flower.blooming.entity.QuestionEntity;
import kr.co.flower.blooming.entity.QuestionType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class QuestionCustomRepositoryImpl implements QuestionCustomRepository {

    private final JPAQueryFactory queryFactory;

    /**
     * 지문id와 문제유형에 따라 문제 id검색
     */
    @Override
    public List<Long> findByPassageIdAndTypes(long passageId, List<QuestionType> questionTypes) {
        return queryFactory.select(questionEntity.questionId)
                .from(questionEntity)
                .where(questionEntity.passageEntity.passageId.eq(passageId),
                        questionEntity.questionType.in(questionTypes))
                .fetch();
    }
    
    /**
     * questionCode로 문제 찾기
     */
    @Override
    public List<QuestionEntity> findByQuestionCode(String questionCode){
    	return queryFactory.selectFrom(questionEntity)
    			.where(questionEntity.questionCode.eq(questionCode))
    			.fetch();
    }
}
