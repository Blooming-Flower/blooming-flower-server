package kr.co.flower.blooming.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.flower.blooming.dto.in.MakeExamParam;
import kr.co.flower.blooming.dto.in.QuestionTypeParam;
import kr.co.flower.blooming.service.ExamService;
import lombok.RequiredArgsConstructor;

@Tag(name = "시험지 API")
@RequestMapping("/api/v1/exam")
@RestController
@RequiredArgsConstructor
public class ExamController {

    private final ExamService examService;

    @Operation(description = "지문id와 유형에 따라 문제수와 문제id return", summary = "[시험지] 문제수와 문제id return")
    @PostMapping(path = "/find/question-info")
    public ResponseEntity<?> getQuestionIdAndCount(
            @RequestBody QuestionTypeParam questionTypeParam) {
        return ResponseEntity.ok(examService.getQuestionIdAndCount(questionTypeParam));
    }

    @ApiResponses({@ApiResponse(responseCode = "404", description = "문제를 찾을 수 없습니다.")})
    @Operation(description = "시험지 제작", summary = "[시험지] 시험지 제작")
    @PostMapping(path = "/make/exam")
    public ResponseEntity<?> makeExam(@RequestBody MakeExamParam examParam) {
        examService.makeExam(examParam);
        return ResponseEntity.ok().build();
    }

    // 시험지에 있는 문제들 불러오기

    // 시험지 삭제

    // 시험지 목록 보기

}
