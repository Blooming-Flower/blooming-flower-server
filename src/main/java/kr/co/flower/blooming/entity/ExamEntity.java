package kr.co.flower.blooming.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * 시험지 table
 * 
 * @author shmin
 *
 */
@Entity
@Table(name = "EXAM")
@Getter
@Setter
public class ExamEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long examId;

    @Column(nullable = false)
    private String examTitle; // 시험지 title
    
    @Column(nullable = false)
    private String examSubTitle; // 머리말 문구
    
    private String examLeftFooter; // 꼬리말(왼)
    private String examRightFooter; // 꼬리말(오)

    @Enumerated(EnumType.STRING)
    private ExamFormat examFormat; // 포맷

    @OneToMany(mappedBy = "examEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ExamQuestionEntity> examQuestionEntities = new ArrayList<>();
}
