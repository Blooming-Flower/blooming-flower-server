package kr.co.flower.blooming.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.flower.blooming.dto.in.PassageRegistParam;
import kr.co.flower.blooming.dto.out.PassageListDto;
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
    public ResponseEntity<?> savePassage(@RequestBody @Valid PassageRegistParam passageRegistDto) {
        passageService.savePassage(passageRegistDto);
        return ResponseEntity.ok().build();
    }

    @ApiResponses({@ApiResponse(responseCode = "404", description = "지문을 찾을 수 없습니다.")})
    @Operation(description = "지문 수정", summary = "지문 수정")
    @PutMapping(path = "/update")
    public ResponseEntity<?> updatePassage(
            @RequestBody @Valid PassageRegistParam passageRegistDto) {
        passageService.updatePassage(passageRegistDto);
        return ResponseEntity.ok().build();
    }

    @ApiResponses({@ApiResponse(responseCode = "404", description = "지문을 찾을 수 없습니다.")})
    @Operation(description = "지문 삭제", summary = "지문 삭제")
    @DeleteMapping(path = "/delete/{passageId}")
    public ResponseEntity<?> deletePassage(@PathVariable(name = "passageId") long passageId) {
        passageService.deletePassage(passageId);
        return ResponseEntity.ok().build();
    }

    @ApiResponses({@ApiResponse(responseCode = "404", description = "지문을 찾을 수 없습니다.")})
    @Operation(description = "지문 단건 조회", summary = "지문 수정 버튼 - 지문, 문제 유형 list 조회")
    @GetMapping(path = "/search/{passageId}")
    public ResponseEntity<?> searchPassageInfo(@PathVariable(name = "passageId") long passageId) {
        return ResponseEntity.ok(passageService.searchPassageInfo(passageId));
    }

    @ApiResponses({@ApiResponse(responseCode = "404", description = "지문을 찾을 수 없습니다.")})
    @Operation(description = "검색 조건 & 정렬 순으로 지문 목록 조회", summary = "검색 조건 & 정렬 순으로 지문 목록 조회")
    @GetMapping(path = "/search/list")
    public ResponseEntity<?> searchListPassage(Pageable pageable,
            @RequestParam(required = false) String passageYear,
            @RequestParam(required = false) String passageName) {
        Page<PassageListDto> passageList =
                passageService.searchPassageList(pageable, passageYear, passageName);
        return ResponseEntity.ok(passageList);
    }

    @Operation(description = "교재 종류별, 입력된 교재이름이 포함된 교재 이름 목록 검색",
            summary = "교재 종류별, 입력된 교재이름이 포함된 교재 이름 목록 검색")
    @GetMapping(path = "/search/name")
    public ResponseEntity<?> searchPassageNameList(PassageType passageType, String passageName) {
        return ResponseEntity.ok(passageService.searchPassageNameList(passageType, passageName));
    }

    @Operation(description = "만약 같은 지문이라면 지문 id와 content return후 지문 수정 로직 타도록 해야 함",
            summary = "[지문 저장] 종류, 연도, 교재, 강, 번호 다 채웠을 때 이미 저장된 지문인지 check")
    @GetMapping(path = "/check/exist/passage")
    public ResponseEntity<?> checkExistPassage(
            PassageType passageType,
            String passageYear,
            String passageName,
            String passageUnit,
            String passageNumber) {
        return ResponseEntity.ok(passageService.checkExistPassage(passageType, passageYear,
                passageName, passageUnit, passageNumber));
    }
}
