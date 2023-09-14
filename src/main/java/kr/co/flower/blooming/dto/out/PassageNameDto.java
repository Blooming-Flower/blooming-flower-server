package kr.co.flower.blooming.dto.out;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PassageNameDto {
	private long passageId;
	private String passageName;

	@QueryProjection
	public PassageNameDto(long passageId, String passageName) {
		this.passageId = passageId;
		this.passageName = passageName;
	}
}
