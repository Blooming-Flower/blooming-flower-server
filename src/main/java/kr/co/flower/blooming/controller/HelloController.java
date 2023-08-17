package kr.co.flower.blooming.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping("/api")
@RestController
@Tag(name = "test controller")
public class HelloController {

    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Agent를 찾을 수 없습니다.")
    })
    @Operation(description = "test api")
    @GetMapping(path = "/test")
    public ResponseEntity<?> test(String name) {
        return ResponseEntity.ok().build();
    }
}
