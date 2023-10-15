package kr.co.flower.blooming.dto.out;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 지문id + 유형에 맞는 문제id list와 문제수 dto
 * 
 * @author shmin
 *
 */
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionIdAndCountDto {
	private long passageId;
	private List<Long> questionIds = new ArrayList<>();
	private long count;
}
