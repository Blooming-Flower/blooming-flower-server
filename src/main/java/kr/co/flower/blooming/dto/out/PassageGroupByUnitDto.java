package kr.co.flower.blooming.dto.out;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * passage unit으로 그루핑 된 passage
 * 
 * @author shmin
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PassageGroupByUnitDto {
	private String passageUnit;
	private List<PassageNumberAndQuestionCountDto> passageInfo;
}
