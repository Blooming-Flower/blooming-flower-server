package kr.co.flower.blooming.dto.in;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import kr.co.flower.blooming.entity.QuestionType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionRegistDto {
	private long questionId;

	private QuestionType questionType; // 유형

	@NotBlank
	private String questionTitle; // 발문

	@NotBlank
	private String questionContent; // 지문

	@NotBlank
	private String questionAnswer; // 정답

	private boolean pastYn; // 기출 여부

	private long passageId; // 지문 id

	private List<ChooseDto> chooseList = new ArrayList<>();

	private List<AnswerDto> answerList = new ArrayList<>();

}
