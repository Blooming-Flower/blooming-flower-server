package kr.co.flower.blooming.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.flower.blooming.dto.in.QuestionTypeParam;
import kr.co.flower.blooming.service.ExamService;
import lombok.RequiredArgsConstructor;

@Tag(name = "시험지 API")
@RequestMapping("/api/v1/exam")
@RestController
@RequiredArgsConstructor
public class ExamController {

    private final ExamService examService;

    @Operation(description = "지문id와 유형에 따라 문제수와 문제id return", summary = "[시험지 제작] 문제수와 문제id return")
    @PostMapping(path = "/find/question-info")
    public ResponseEntity<?> getQuestionIdAndCount(
            @RequestBody QuestionTypeParam questionTypeParam) {
        return ResponseEntity.ok(examService.getQuestionIdAndCount(questionTypeParam));
    }
    // 시험지 제작

    // 시험지에 있는 문제들 불러오기

    // 시험지 삭제

    // 시험지 목록 보기

}
