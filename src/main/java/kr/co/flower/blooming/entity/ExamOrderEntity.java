package kr.co.flower.blooming.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "EXAM_ORDER")
@Data
public class ExamOrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long examOrderId;

    @Column(nullable = false)
    private int orderSeq; // 순서
    
    @Column(nullable = false)
    private String passageName; // 교재명
    
    @Column(nullable = false)
    private String passageYear; // 연도

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_id")
    private ExamEntity examEntity;
}
