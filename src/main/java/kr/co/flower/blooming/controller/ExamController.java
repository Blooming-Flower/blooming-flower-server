package kr.co.flower.blooming.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @ApiResponses({@ApiResponse(responseCode = "404", description = "시험지를 찾을 수 없습니다.")})
    @Operation(description = "시험지 제작", summary = "[시험지] 시험지 제작")
    @PostMapping(path = "/make")
    public ResponseEntity<?> makeExam(@RequestBody MakeExamParam examParam) {
        examService.makeExam(examParam);
        return ResponseEntity.ok().build();
    }

    @ApiResponses({@ApiResponse(responseCode = "404", description = "시험지를 찾을 수 없습니다.")})
    @Operation(description = "시험지 삭제", summary = "[시험지] 시험지 삭제")
    @DeleteMapping(path = "/delete/{examId}")
    public ResponseEntity<?> deleteExam(@PathVariable(name = "examId") long examId) {
        examService.deleteExam(examId);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "시험지 목록 조회", summary = "[시험지] 시험지 목록 조회")
    @GetMapping(path = "/search")
    public ResponseEntity<?> searchExam(Pageable pageable,
            @RequestParam(required = false) String examTitle) {
        return ResponseEntity.ok(examService.findExamList(pageable, examTitle));
    }
    
    // TODO 시험지에 있는 문제들 불러오기
    @ApiResponses({@ApiResponse(responseCode = "404", description = "시험지를 찾을 수 없습니다.")})
    @Operation(description = "시험지에 있는 문제들 불러오기", summary = "[시험지] 시험지에 있는 문제들 불러오기")
    @GetMapping(path = "/load/{examId}")
    public ResponseEntity<?> loadExam(@PathVariable(name = "examId") long examId) {
        return ResponseEntity.ok(examService.loadExam(examId));
    }
    
    // TODO 시험지 답안 불러오기
}
