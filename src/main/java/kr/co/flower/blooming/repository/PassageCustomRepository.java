package kr.co.flower.blooming.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import kr.co.flower.blooming.dto.out.PassageListDto;
import kr.co.flower.blooming.entity.PassageType;

public interface PassageCustomRepository {

	/**
	 * 동적 검색을 위한 query filtering, 정렬조건에 따라 동적으로 paging 생성
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
}
