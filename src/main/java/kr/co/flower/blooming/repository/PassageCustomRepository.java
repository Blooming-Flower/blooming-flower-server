package kr.co.flower.blooming.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import kr.co.flower.blooming.dto.out.PassageListDto;
import kr.co.flower.blooming.dto.out.PassageNumberAndQuestionCountDto;
import kr.co.flower.blooming.entity.PassageType;

public interface PassageCustomRepository {

    /**
     * 동적 검색을 위한 query filtering
     * 
     * 페이징 처리
     * 
     * @param pageable
     * @param passageType
     * @param passageYear
     * @param passageName
     * @param passageUnit
     * @param passageNumber
     * @return
     */
    Page<PassageListDto> findPassageAll(Pageable pageable, String passageYear, String passageName);

    /**
     * 교재 종류별, 입력된 교재이름이 포함된 교재 이름 목록 검색
     * 
     * @param passageType
     * @param passageName
     * @return
     */
    List<String> searchPassageNameList(PassageType passageType, String passageName);

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
    Page<PassageNumberAndQuestionCountDto> searchPassageUnitGroupByUnit(Pageable pageable,
            PassageType passageType,
            String passageYear,
            String passageName);


    /**
     * 지문 유형과 연도에 해당되는 교재명 목록 조회
     * 
     * @param passageType
     * @param year
     * @return
     */
    List<String> searchPassageNameByTypeAndYear(PassageType passageType, String year);
}
