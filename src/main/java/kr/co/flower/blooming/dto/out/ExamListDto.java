package kr.co.flower.blooming.dto.out;

import java.time.LocalDateTime;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExamListDto {
    private String examTitle; // 시험지 제목
    private LocalDateTime createTime; // 제작일자

    @QueryProjection
    public ExamListDto(String examTitle, LocalDateTime creatTime) {
        this.examTitle = examTitle;
        this.createTime = creatTime;
    }
}
