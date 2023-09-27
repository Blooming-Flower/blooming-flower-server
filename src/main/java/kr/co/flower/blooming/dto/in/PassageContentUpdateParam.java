package kr.co.flower.blooming.dto.in;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PassageContentUpdateParam {
    private long passageId;
    private String passageContent;
}
