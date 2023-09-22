package kr.co.flower.blooming.dto.in;

import java.util.ArrayList;
import java.util.List;
import kr.co.flower.blooming.entity.ExamFormat;
import lombok.Getter;

/**
 * 시험지 제작 param
 * 
 * @author shmin
 *
 */
@Getter
public class MakeExamParam {
    private String title; // 시험지 title
    private String subTitle; // 머리말 문구
    private String leftFooter; // 왼쪽 꼬리말
    private String rightFooter; // 오른쪽 꼬리말
    private ExamFormat examFormat; // 시험지 포맷
    private List<ExamQuestionParam> questionParams = new ArrayList<>();

    @Getter
    public static class ExamQuestionParam {
        private long questionId; // 문제 id
        private int groupSeq; // 그룹(교재명) 순서
        private String groupName; // 교재명(연도)
    }
}
