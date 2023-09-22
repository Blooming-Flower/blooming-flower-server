package kr.co.flower.blooming.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import kr.co.flower.blooming.dto.out.ExamListDto;

public interface ExamCustomRepository {
    /**
     * 동적 검색 쿼리, filter, order에 따라 paging
     * 
     * @param pageable
     * @param examTitle
     * @return
     */
    Page<ExamListDto> findExamList(Pageable pageable, String examTitle);
}
