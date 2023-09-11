package kr.co.flower.blooming.dto.out;

import com.querydsl.core.annotations.QueryProjection;
import kr.co.flower.blooming.entity.PassageType;
import lombok.Data;

@Data
public class PassageListDto {
	private long passageId;
    private PassageType passageType;
    private String passageName; // 교재 이름
    private String passageUnit; // UNIT
    private String passageNumber; // 지문 번호
    private long questionCount; // 문제 수

    @QueryProjection
    public PassageListDto(long passageId, PassageType passageType, String passageName, String passageUnit,
            String passageNumber,
            long questionCount) {
        this.passageType = passageType;
        this.passageName = passageName;
        this.passageUnit = passageUnit;
        this.passageNumber = passageNumber;
        this.questionCount = questionCount;
    }
}
