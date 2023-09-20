package kr.co.flower.blooming.dto.in;

import java.util.ArrayList;
import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.flower.blooming.entity.QuestionType;
import lombok.Getter;
import lombok.Setter;

/**
 * 지문id와 문제 type
 * 
 * @author shmin
 *
 */
@Getter
@Setter
public class QuestionTypeParam {
    @Schema(example = "[\"Q1\",\"Q2\",\"Q3\",\"Q4\",\"Q5\",\"Q6\",\"Q7\",\"Q8\",\"Q9\",\"Q10\",\"Q11\",\"Q12\",\"Q13\",\"Q14\",\"Q15\",\"Q16\",\"Q17\",\"Q18\",\"Q19\",\"Q20\",\"Q21\",\"Q22\"]")
    private List<QuestionType> questionTypes = new ArrayList<>();
    private List<Long> passageIds = new ArrayList<>();
}
