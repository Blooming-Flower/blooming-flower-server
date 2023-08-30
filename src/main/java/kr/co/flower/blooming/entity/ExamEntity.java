package kr.co.flower.blooming.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;

/**
 * 시험지 table
 * 
 * @author shmin
 *
 */
@Entity
@Table(name = "EXAM")
@Data
public class ExamEntity extends BaseEntity {
    @Id
    @GeneratedValue
    private long examId;

    @Column(nullable = false)
    private String examTitle; // 시험지 title
    
    @Column(nullable = false)
    private String examSubTitle; // 머리말 문구
    
    private String examLeftFooter; // 꼬리말(왼)
    private String examRightFooter; // 꼬리말(오)

    @Enumerated(EnumType.STRING)
    private ExamFormat examFormat; // 포맷

    @OneToMany(mappedBy = "examOrderId")
    private List<ExamOrderEntity> examOrderEntities = new ArrayList<>();
}
