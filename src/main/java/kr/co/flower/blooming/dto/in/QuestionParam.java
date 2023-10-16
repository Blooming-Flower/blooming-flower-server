package kr.co.flower.blooming.dto.in;

import java.util.ArrayList;
import java.util.List;

import com.querydsl.core.annotations.QueryProjection;

import kr.co.flower.blooming.dto.out.ChooseDto;
import kr.co.flower.blooming.entity.AnswerDto;
import kr.co.flower.blooming.entity.AnswerEntity;
import kr.co.flower.blooming.entity.ChooseEntity;
import kr.co.flower.blooming.entity.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionParam {
	private long questionId;
	private String questionCode; // 문제 code
	private QuestionType questionType; // 유형
	private String subBox; // 보기 박스
	private String questionSubTitle; // sub 발문(복합지문일 경우)
	private boolean pastYn; // 기출 여부

	private long passageId;
	private String passageYear; // 연도
	private String passageName; // 교재 이름

	private String questionTitle; // 발문
	private String questionContent;

	@Builder.Default
	private List<ChooseEntity> chooseDtos = new ArrayList<>();

	@Builder.Default
	private List<AnswerEntity> answerDtos = new ArrayList<>();

//	@QueryProjection
//	public QuestionParam(long questionId, String questionCode, QuestionType questionType, String subBox,
//			String questionSubTitle, boolean pastYn, long passageId, String passageYear, String passageName,
//			String questionTitle, String questionContent) {
//		this.questionId = questionId;
//		this.questionCode = questionCode;
//		this.questionType = questionType;
//		this.subBox = subBox;
//		this.questionSubTitle = questionSubTitle;
//		this.pastYn = pastYn;
//		this.passageId = passageId;
//		this.passageYear = passageYear;
//		this.passageName = passageName;
//		this.questionTitle = questionTitle;
//		this.questionContent = questionContent;
//	}

}
