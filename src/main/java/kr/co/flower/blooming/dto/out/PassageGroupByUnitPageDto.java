package kr.co.flower.blooming.dto.out;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class PassageGroupByUnitPageDto {
    private int pageSize;
    private List<PassageGroupByUnitDto> list = new ArrayList<>();

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PassageGroupByUnitDto {
        private String passageUnit;
        private List<PassageNumberAndQuestionCountDto> passageInfo;
    }
}
