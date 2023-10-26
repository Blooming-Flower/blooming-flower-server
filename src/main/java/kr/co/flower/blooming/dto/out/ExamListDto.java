package kr.co.flower.blooming.dto.out;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExamListDto {
    private long examId;
    private String examTitle; // 시험지 제목
    private String createTime; // 제작일자

    @QueryProjection
    public ExamListDto(long examId, String examTitle, String creatTime) {
        this.examId = examId;
        this.examTitle = examTitle;
        this.createTime = creatTime;
    }
}
