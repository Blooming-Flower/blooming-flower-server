package kr.co.flower.blooming.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 지문 table
 * 
 * @author shmin
 *
 */
@Entity
@Table(name = "PASSAGE")
@Data
@ToString(exclude = {"questionEntities"})
public class PassageEntity extends BaseEntity {
    @Id
    @GeneratedValue
    private long passageId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PassageType passageType; // 교재 종류

    private String passageYear; // 연도

    @Column(nullable = false)
    private String passageName; // 교재 이름

    @Column(nullable = false)
    private String passageUnit; // 강

    @Column(nullable = false)
    private String passageNumber; // 지문 번호

    @Lob
    @Column(nullable = false)
    private String passageContent; // 지문

    @OneToMany(mappedBy = "questionId", fetch = FetchType.LAZY)
    private List<QuestionEntity> questionEntities = new ArrayList<>();



}
