package kr.co.flower.blooming.dto.out;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CheckExistPassageDto {
    private long passageId; // 지문 id
    private String passageContent; // 지문 내용

    @QueryProjection
    public CheckExistPassageDto(long passageId, String passageContent) {
        this.passageId = passageId;
        this.passageContent = passageContent;
    }
}
