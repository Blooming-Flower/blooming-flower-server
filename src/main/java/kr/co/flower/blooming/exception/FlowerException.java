package kr.co.flower.blooming.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Custom Exception
 */
@AllArgsConstructor
@Getter
@Setter
public class FlowerException extends RuntimeException {
    private FlowerError flowerError;
}
