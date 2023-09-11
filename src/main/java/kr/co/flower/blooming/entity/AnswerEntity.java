package kr.co.flower.blooming.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 정답 table
 * 
 * @author shmin
 *
 */
@Entity
@Table(name = "ANSWER")
@Data
public class AnswerEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long answerId;

	@Column(nullable = false)
	private String answerContent; // 정답

}
