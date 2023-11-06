package kr.co.flower.blooming.dto.out;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import kr.co.flower.blooming.entity.AnswerDto;
import kr.co.flower.blooming.entity.PassageType;
import kr.co.flower.blooming.entity.QuestionType;
import lombok.Data;

/**
 * 지문 수정 누르면 나오는 화면
 * 
 * 지문, (지문에 대한 문제 유형들 + 문제 id, 문제 code) list
 * 
 * { passageContent: "" // 원본 지문 questions: [ { questionCode : "", questionTitle: questionContent:
 * question: [ questionId: questionType: questionSubTitle: subBox: pastYn: choose: [ seq: content:
 * ], answer : [ answer : ] ] } ] }
 * 
 * @author shmin
 *
 */
@Data
public class SearchPassageDto {
    private long passageId;
    private String passageContent;
    private List<SearchQuestionDtos> questionInfo = new ArrayList<>();

    @Data
    public static class SearchQuestionDtos {
        private String questionCode;
        private String questionTitle;
        private String questionContent;
        private List<SearchQuestionDto> question = new ArrayList<>();

        @Data
        public static class SearchQuestionDto {
            private long questionId;
            private QuestionType questionType;
            private String questionSubTitle;
            private String subBox;
            private boolean pastYn;
            private List<ChooseDto> choose = new ArrayList<>();
            private List<AnswerDto> answer = new ArrayList<>();
            private PassageType passageType;
            private String passageYear; // 연도
            private String passageUnit; // 강
            private String passageNumber; // 지문 번호
        }
    }
}
