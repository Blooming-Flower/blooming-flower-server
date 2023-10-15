package kr.co.flower.blooming.dto.out;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;

import kr.co.flower.blooming.entity.QuestionType;
import lombok.Data;

/**
 * question 정보 들
 * 
 * @author shmin
 *
 */
@Data
public class QuestionInfo {
	private long questionId;
	private String questionCode; // 문제 code
	private QuestionType questionType; // 유형
	private String subBox; // 보기 박스
	private String questionSubTitle; // sub 발문(복합지문일 경우)
	private boolean pastYn; // 기출 여부
	private String questionTitle; // 발문
	private String questionContent; // 문제 지문
	private String passageName; // 교재 이름
}
