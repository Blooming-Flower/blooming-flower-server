package kr.co.flower.blooming.dto.out;

import java.util.ArrayList;
import java.util.List;
import kr.co.flower.blooming.dto.out.SearchPassageDto.SearchQuestionDtos;
import kr.co.flower.blooming.entity.ExamFormat;
import lombok.Getter;
import lombok.Setter;

/**
 * 시험지 문제들 불러오기
 * 
 * @author shmin
 *
 */
@Getter
@Setter
public class ExamPagesDto {
    private String title; // 시험지 title
    private String subTitle; // 머리말 문구
    private String leftFooter; // 꼬리말(왼)
    private String rightFooter; // 꼬리말(오)
    private ExamFormat examFormat; // 포맷

    private List<ExamPageGroupDto> examQuestions = new ArrayList<>();
    /**
     * 시험지 만들 때 교재명(연도)로 grouping 한 대로 뿌려줌
     * 
     * @author shmin
     *
     */
    @Getter
    @Setter
    public static class ExamPageGroupDto {
        private String groupName;
        // 문제 list
        private List<SearchQuestionDtos> questionInfo = new ArrayList<>();
    }
}
