package kr.co.flower.blooming.dto.out;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

/**
 * 문제 출제에서 강에 따른 지문번호 들이 나오는 dto
 * 
 * @author shmin
 *
 */
@Data
public class PassageNumberAndQuestionCountDto {
	private String passageName; // 지문 이름
	private String passageUnit; // 강
	private String passageNumber; // 지문 번호
	private long passageId; // 지문 id
	private long questionCount; // 해당 지문에 따른 문제 숫자

	@QueryProjection
	public PassageNumberAndQuestionCountDto(String passageName, String passageUnit, String passageNumber,
			long passageId, long questionCount) {
		this.passageName = passageName;
		this.passageUnit = passageUnit;
		this.passageNumber = passageNumber;
		this.passageId = passageId;
		this.questionCount = questionCount;
	}
}
