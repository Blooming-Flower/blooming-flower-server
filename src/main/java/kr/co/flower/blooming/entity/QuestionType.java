package kr.co.flower.blooming.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum QuestionType {
    // TODO 유형 추가
    TOPIC("주제"), TITLE("제목");

    private String questionType;
}
