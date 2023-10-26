package kr.co.flower.blooming.dto.in;

import lombok.Data;

@Data
public class ExamTitleChangeParam {
    private long examId;
    private String newTitle;
}
