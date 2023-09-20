package kr.co.flower.blooming.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PassageType {
	P1("교과서"), P2("모의고사"), P3("EBS(고3)(1)"),P4("EBS(고3)(2)"), P5("부교재"), P6("외부 지문");
    
    private String passageType;
}
