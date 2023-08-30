package kr.co.flower.blooming.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.flower.blooming.dto.in.PassageRegistDto;
import kr.co.flower.blooming.dto.out.PassageListDto;
import kr.co.flower.blooming.entity.PassageEntity;
import kr.co.flower.blooming.entity.PassageType;
import kr.co.flower.blooming.service.PassageService;
import lombok.RequiredArgsConstructor;

@Tag(name = "지문 API")
@RequestMapping("/api/v1/passage")
@RestController
@RequiredArgsConstructor
public class PassageController {
    private final PassageService passageService;

    @Operation(description = "지문 저장", summary = "지문 저장")
    @PostMapping(path = "/save")
    public ResponseEntity<?> savePassage(@RequestBody PassageRegistDto passageRegistDto) {
        passageService.savePassage(passageRegistDto);
        return ResponseEntity.ok().build();
    }

    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "지문을 찾을 수 없습니다.")
    })
    @Operation(description = "지문 수정", summary = "지문 수정")
    @PutMapping(path = "/update")
    public ResponseEntity<?> updatePassage(@RequestBody PassageRegistDto passageRegistDto) {
        passageService.updatePassage(passageRegistDto);
        return ResponseEntity.ok().build();
    }

    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "지문을 찾을 수 없습니다.")
    })
    @Operation(description = "지문 삭제", summary = "지문 삭제")
    @DeleteMapping(path = "/delete/{passageId}")
    public ResponseEntity<?> deletePassage(@PathVariable(name = "passageId") long passageId) {
        passageService.deletePassage(passageId);
        return ResponseEntity.ok().build();
    }

    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "지문을 찾을 수 없습니다.")
    })
    @Operation(description = "지문 단건 조회", summary = "지문 단건 조회")
    @GetMapping(path = "/find/{passageId}")
    public ResponseEntity<?> findPassage(@PathVariable(name = "passageId") long passageId) {
        PassageEntity passage = passageService.findPassage(passageId);
        return ResponseEntity.ok(passage);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "지문을 찾을 수 없습니다.")
    })
    @Operation(description = "검색 조건 & 정렬 순으로 지문 목록 조회", summary = "검색 조건 & 정렬 순으로 지문 목록 조회")
    @GetMapping(path = "/find/list")
    public ResponseEntity<?> findListPassage(Pageable pageable,
            @RequestParam(required = false) PassageType passageType,
            @RequestParam(required = false) String passageYear,
            @RequestParam(required = false) String passageName,
            @RequestParam(required = false) String passageUnit,
            @RequestParam(required = false) String passageNumber) {
        Page<PassageListDto> passageList = passageService.findPassageAll(pageable, passageType,
                passageYear, passageName, passageUnit, passageNumber);
        return ResponseEntity.ok(passageList);
    }
}
