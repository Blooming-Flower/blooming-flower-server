package kr.co.flower.blooming.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.flower.blooming.service.BackupService;
import lombok.RequiredArgsConstructor;

@RestController
@Tag(name = "DB Backup API")
@RequestMapping("/backup")
@RequiredArgsConstructor
public class BackupController {

    private final BackupService backupService;
    
    @PostMapping
    @Operation(summary = "DB 백업 API")
    public ResponseEntity<?> backup() {
        return ResponseEntity.ok(backupService.backup());
    }
}
