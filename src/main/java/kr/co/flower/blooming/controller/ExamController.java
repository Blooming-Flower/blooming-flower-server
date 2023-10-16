package kr.co.flower.blooming.controller;

import java.util.List;
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
import kr.co.flower.blooming.entity.PassageType;
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

    @ApiResponses({@ApiResponse(responseCode = "404", description = "시험지를 찾을 수 없습니다.")})
    @Operation(description = "시험지에 있는 문제들 불러오기", summary = "[시험지] 시험지에 있는 문제들 불러오기")
    @GetMapping(path = "/load/{examId}")
    public ResponseEntity<?> loadExam(@PathVariable(name = "examId") long examId) {
        return ResponseEntity.ok(examService.loadExam(examId));
    }


    @Operation(description = "연도, 교재, 강 검색조건에 따라 조회",
            summary = "[시험지] 강, 지문 번호 조회 - 출제된 문제가 있는것만 조회")
    @GetMapping(path = "/search/passage-numbers")
    public ResponseEntity<?> searchPassageNumbers(
            Pageable pageable, PassageType passageType, String passageYear, String passageName) {
        return ResponseEntity.ok(
                examService.searchPassageNumbersHavingQuestion(pageable, passageType, passageYear, passageName));
    }

    @Operation(description = "지문 유형과 연도에 해당되는 교재명 목록 조회",
            summary = "[시험지] 지문 유형과 연도에 해당되는 교재명 목록 조회 - 출제된 문제가 있는것만 조회")
    @GetMapping(path = "/search/passage-names")
    public ResponseEntity<?> searchPassageNameByTypeAndYear(PassageType passageType,
            String year) {
        return ResponseEntity
                .ok(examService.searchPassageNameHavingQuestion(passageType, year));
    }

    @Operation(description = "문제(발문, 지문, 선지, 답) 조회", summary = "[문제 출제] 문제(발문, 지문, 선지, 답) 조회")
    @GetMapping(path = "/search/questions/{questionIds}")
    public ResponseEntity<?> searchQuestions(@PathVariable(name = "questionIds") List<Long> questionIds) {
        return ResponseEntity.ok(examService.getQuestionAll(questionIds));
    }
    
    // TODO 시험지 답안 불러오기


}
