package kr.co.flower.blooming.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 선지 table
 * 
 * @author shmin
 *
 */
@Entity
@Table(name = "CHOOSE")
@Data
public class ChooseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long chooseId;
    
    @Column(nullable = false)
    private int chooseSeq; // 선지 번호
    
    @Column(nullable = false)
    private String chooseContent; // 선지 내용
}
