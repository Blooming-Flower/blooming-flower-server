package kr.co.flower.blooming.dto.out;

import java.util.ArrayList;
import java.util.List;
import kr.co.flower.blooming.dto.out.SearchPassageDto.SearchQuestionDtos;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * question 정보 들
 * 
 * @author shmin
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionInfo {
	private String passageYear; // 지문 연도
	private String passageName; // 교재 이름

	@Builder.Default
	private List<SearchQuestionDtos> questionInfo = new ArrayList<>();

}
