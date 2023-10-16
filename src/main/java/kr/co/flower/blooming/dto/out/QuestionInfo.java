package kr.co.flower.blooming.dto.out;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;

import kr.co.flower.blooming.dto.out.SearchPassageDto.SearchQuestionDtos;
import kr.co.flower.blooming.entity.AnswerDto;
import kr.co.flower.blooming.entity.PassageType;
import kr.co.flower.blooming.entity.QuestionType;
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
//    private long questionId;
//    private String questionCode; // 문제 code
//    private QuestionType questionType; // 유형
//    private String subBox; // 보기 박스
//    private String questionSubTitle; // sub 발문(복합지문일 경우)
//    private boolean pastYn; // 기출 여부
//    private String questionTitle; // 발문
//    private String questionContent; // 문제 지문
//    private PassageType passageType; // 교재 타입
//    private String passageYear; // 지문 연도
//    private String passageName; // 교재 이름
//    private String passageUnit; // 강
//    private String passageNumber; // 지문 번호
//
//    @Builder.Default
//    private List<ChooseDto> chooseList = new ArrayList<>();
//
//    @Builder.Default
//    private List<AnswerDto> answerList = new ArrayList<>();

	private String passageYear; // 지문 연도
	private String passageName; // 교재 이름

	@Builder.Default
	private List<SearchQuestionDtos> questionInfo = new ArrayList<>();

}
