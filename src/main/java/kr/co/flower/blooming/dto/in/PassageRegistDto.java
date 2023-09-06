package kr.co.flower.blooming.dto.in;

import javax.validation.constraints.NotBlank;

import kr.co.flower.blooming.entity.PassageType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PassageRegistDto {
    private long passageId;

    private PassageType passageType; // 교재 종류
    
    @NotBlank
    private String passageYear; // 연도
    
    @NotBlank
    private String passageName; // 교재 이름
    
    private String passageUnit = ""; // UNIT
    
    @NotBlank
    private String passageNumber; // 지문 번호
    
    @NotBlank
    private String passageContent; // 지문
}
