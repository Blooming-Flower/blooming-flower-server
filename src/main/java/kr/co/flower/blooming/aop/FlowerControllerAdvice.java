package kr.co.flower.blooming.aop;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import kr.co.flower.blooming.dto.out.FlowerErrorDto;
import kr.co.flower.blooming.exception.FlowerException;
import lombok.extern.slf4j.Slf4j;

/**
 * ExceptionHandler에 의해 ErrorDto로 convert
 */
@RestControllerAdvice
@Slf4j
public class FlowerControllerAdvice {
    @ExceptionHandler(FlowerException.class)
    public ResponseEntity<?> feignException(FlowerException e) {
        log.error("Flower Server Exception occur :: {}", e.getFlowerError().getMessage());
        return new ResponseEntity<>(FlowerErrorDto.createErrorDto(e.getFlowerError()),
                e.getFlowerError().getHttpStatus());
    }
}
