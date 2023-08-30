package kr.co.flower.blooming.exception;

import org.springframework.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Custom Error Define
 */
@Getter
@AllArgsConstructor
public enum FlowerError {
    ENTITY_NOT_FOUND(1000, "해당 entity를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);


    private int errorCode;
    private String message;
    private HttpStatus httpStatus;
}
