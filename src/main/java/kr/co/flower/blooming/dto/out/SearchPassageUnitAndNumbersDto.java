package kr.co.flower.blooming.dto.out;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;

/**
 * 문제 출제에서 강에 따른 지문번호 들이 나오는 dto
 * 
 * @author shmin
 *
 */
@Data
public class SearchPassageUnitAndNumbersDto {
    private List<String> passageNameList = new ArrayList<>();
    private Map<String, PassageNumberAndQuestionCountDto> passageInfo = new HashMap<>(); // key :
                                                                                         // unit

    @Data
    public static class PassageNumberAndQuestionCountDto {
        private String passageUnit; // 강
        private String passageNumber; // 지문 번호
        private int questionCount; // 해당 지문에 따른 문제 숫자
    }
}
