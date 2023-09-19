package kr.co.flower.blooming.dto.in;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import kr.co.flower.blooming.entity.QuestionType;
import lombok.Getter;
import lombok.Setter;

/**
 * 단일 문제 대한 dto
 * 
 * @author shmin
 *
 */
@Getter
@Setter
public class QuestionUpdateParam {
	private long questionId;
	
	private long passageId; // 지문 id

	@NotBlank
	private String questionContent; // 지문

	@NotBlank
	private String questionTitle; // 발문
	
	private String questionSubTitle; // sub 발문(복합 지문일 경우)

	private boolean pastYn; // 기출 여부

	private QuestionType questionType; // 유형
	
	private String subBox; // 보기박스

	private List<ChooseParam> chooseList = new ArrayList<>();

	private List<AnswerParam> answerList = new ArrayList<>();

}
