package kr.co.flower.blooming.controller;

import javax.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.flower.blooming.dto.in.QuestionRegistParam;
import kr.co.flower.blooming.dto.in.QuestionUpdateParam;
import kr.co.flower.blooming.entity.PassageType;
import kr.co.flower.blooming.service.QuestionService;
import lombok.RequiredArgsConstructor;

@Tag(name = "문제 API")
@RequestMapping("/api/v1/question")
@RestController
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @Operation(description = "문제 저장", summary = "문제 저장")
    @PostMapping(path = "/save")
    public ResponseEntity<?> saveQuestion(@RequestBody @Valid QuestionRegistParam questionRegistDto) {
        questionService.saveQuestion(questionRegistDto);
        return ResponseEntity.ok().build();
    }

    @ApiResponses({@ApiResponse(responseCode = "404", description = "문제를 찾을 수 없습니다.")})
    @Operation(description = "문제 수정", summary = "문제 수정")
    @PutMapping(path = "/update")
    public ResponseEntity<?> updateQuestion(
            @RequestBody @Valid QuestionUpdateParam questionUpdateDto) {
        questionService.updateQuestion(questionUpdateDto);
        return ResponseEntity.ok().build();
    }

    @ApiResponses({@ApiResponse(responseCode = "404", description = "지문을 찾을 수 없습니다.")})
    @Operation(description = "문제 삭제", summary = "문제 삭제")
    @DeleteMapping(path = "/delete/{questionId}")
    public ResponseEntity<?> deleteQuestion(@PathVariable(name = "questionId") long questionId) {
        questionService.deleteQuestion(questionId);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "연도, 교재, 강 검색조건에 따라 조회", summary = "[문제 출제] 강, 지문 번호 조회")
    @GetMapping(path = "/search/passage-numbers")
    public ResponseEntity<?> searchPassageNumbers(
            Pageable pageable, PassageType passageType, String passageYear, String passageName) {
        return ResponseEntity.ok(questionService.searchPassageNumbers(pageable, passageType, passageYear, passageName));
    }
    
    @Operation(description = "지문 유형과 연도에 해당되는 교재명 목록 조회",
            summary = "[문제 출제] 지문 유형과 연도에 해당되는 교재명 목록 조회")
    @GetMapping(path = "/search/passage-names")
    public ResponseEntity<?> searchPassageNameByTypeAndYear(PassageType passageType,
            String year) {
        return ResponseEntity
                .ok(questionService.searchPassageNameByTypeAndYear(passageType, year));
    }
}
