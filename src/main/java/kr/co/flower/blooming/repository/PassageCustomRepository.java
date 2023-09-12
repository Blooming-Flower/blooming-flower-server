package kr.co.flower.blooming.repository;

import java.util.List;

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

	/**
	 * 교재 종류별, 입력된 교재이름이 포함된 교재 이름 목록 검색
	 * 
	 * @param passageType
	 * @param passageName
	 * @return
	 */
	List<String> searchPassageNameList(PassageType passageType, String passageName);
}
