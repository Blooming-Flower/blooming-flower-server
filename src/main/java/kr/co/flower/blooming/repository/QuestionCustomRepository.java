package kr.co.flower.blooming.repository;

import java.util.List;
import kr.co.flower.blooming.entity.QuestionType;

public interface QuestionCustomRepository {
    /**
     * 지문id와 문제유형에 따라 문제 id검색
     * 
     * @param passageId
     * @param questionTypes
     * @return
     */
    List<Long> findByPassageIdAndTypes(long passageId, List<QuestionType> questionTypes);
}
