package kr.co.flower.blooming.dto.out;

import kr.co.flower.blooming.exception.FlowerError;
import lombok.Getter;
import lombok.Setter;

/**
 * SchedulerError발생 시 convert 해주는 DTO
 */
@Setter
@Getter
public class FlowerErrorDto {
    private int errorCode;
    private String message;

    public static FlowerErrorDto createErrorDto(FlowerError flowerError) {
        FlowerErrorDto errorDto = new FlowerErrorDto();
        errorDto.setErrorCode(flowerError.getErrorCode());
        errorDto.setMessage(flowerError.getMessage());
        return errorDto;
    }
}
