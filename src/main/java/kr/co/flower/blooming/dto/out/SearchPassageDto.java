package kr.co.flower.blooming.dto.out;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.flower.blooming.entity.QuestionType;
import lombok.Data;

/**
 * 지문 수정 누르면 나오는 화면
 * 
 * 지문, (지문에 대한 문제 유형들 + 문제 id, 문제 code) list
 * 
 * @author shmin
 *
 */
@Data
public class SearchPassageDto {
	private String passageContent;
	private Map<String, List<SearchQuestionDto>> questions = new HashMap<>();

	@Data
	public static class SearchQuestionDto {
		private long questionId;
		private QuestionType questionType;
	}
}
