package kr.co.flower.blooming.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

/**
 * 문제 순서 table
 * 
 * @author shmin
 *
 */
@Entity
@Table(name = "EXAM_ORDERS")
@Data
public class ExamOrderEntity {
    @Id
    @GeneratedValue
    private long examOrderId;

    @Column(nullable = false)
    private int orderSeq; // 순서

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_id")
    private ExamEntity examEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private QuestionEntity questionEntity;
}
