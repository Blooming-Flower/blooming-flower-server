package kr.co.flower.blooming.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PassageType {
    P1("교과서"), P2("모의고사"), P3("EBS"), P4("부교재"), P5("외부 지문");
    
    private String passageType;
}
