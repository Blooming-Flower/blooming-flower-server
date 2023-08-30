package kr.co.flower.blooming.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PassageType {
    TEXT_BOOK("교과서"), MOCK_TEST("모의고사"), EBS("EBS"), SUB_TEXT_BOOK("부교재"), OUT_SIDE_PASSAGE("외부 지문");
    
    private String passageType;
}
