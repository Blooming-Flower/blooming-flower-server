package kr.co.flower.blooming.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import kr.co.flower.blooming.dto.out.ExamListDto;
import kr.co.flower.blooming.dto.out.PassageNumberAndQuestionCountDto;
import kr.co.flower.blooming.entity.PassageType;

public interface ExamCustomRepository {
    /**
     * 동적 검색 쿼리, filter, order에 따라 paging
     * 
     * @param pageable
     * @param examTitle
     * @return
     */
    Page<ExamListDto> findExamList(Pageable pageable, String examTitle);



    /**
     * question이 하나라도 있는 passage type, year인 지문 명 찾기
     * 
     * @param passageType
     * @param year
     * @return
     */
    List<String> searchPassageNameHavingQuestion(PassageType passageType, String year);


    /**
     * 검색 조건에 따라 question이 하나라도 있는 지문 (강) 조회
     * 
     * @param pageable
     * @param passageType
     * @param passageYear
     * @param passageName
     * @return
     */
    List<PassageNumberAndQuestionCountDto> searchPassageNumbersHavingQuestion(Pageable pageable,
            PassageType passageType,
            String passageYear, String passageName);
}
