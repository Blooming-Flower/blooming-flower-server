package kr.co.flower.blooming.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum QuestionType {
	// TODO 유형 추가
	TOPIC("주제"), TITLE("제목"), PURPOSE("목적"), IMPORTANT("요지"), 
	REASONING_OF_MIND("심경추론"), AUTHOR_OPINION("필자의 주장");

	private String questionType;
}
