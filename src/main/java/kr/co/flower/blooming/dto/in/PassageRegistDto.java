package kr.co.flower.blooming.dto.in;

import kr.co.flower.blooming.entity.PassageType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PassageRegistDto {
    private long passageId;
    private PassageType passageType; // 교재 종류
    private String passageYear; // 연도
    private String passageName; // 교재 이름
    private String passageUnit; // UNIT
    private String passageNumber; // 지문 번호
    private String passageContent; // 지문
}
