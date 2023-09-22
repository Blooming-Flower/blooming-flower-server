package kr.co.flower.blooming.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

/**
 * EXAM, QUESTION Table 연결 테이블
 * 
 * @author shmin
 *
 */
@Entity
@Table(name = "EXAM_QUESTION")
@Data
public class ExamQuestionEntity {
    @Id
    @GeneratedValue
    private long examQuestionId;

    private int groupSeq; // 교재별 순서 번호
    
    private String groupName; // 교재명(연도)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private QuestionEntity questionEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_id")
    private ExamEntity examEntity;

}
