package kr.co.flower.blooming.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum QuestionType {
	Q1("주제"), Q2("제목"), Q3("목적"), Q4("심경추론"), 
	Q5("필자의 주장"), Q6("요지"), Q7("의미추론"), Q8("지칭"), 
	Q9("내용불일치"), Q10("내용일치"), Q11("어법"), Q12("어법ABC"),
	Q13("어휘"), Q14("어휘ABC"), Q15("빈칸추론"), Q16("순서"),
	Q17("주어진 문장"), Q18("요약문"), Q19("연결사"), Q20("서술(해석영작)"), 
	Q21("서술(단어빈칸)"), Q22("서술(빈칸)");

    private String questionType;
}
