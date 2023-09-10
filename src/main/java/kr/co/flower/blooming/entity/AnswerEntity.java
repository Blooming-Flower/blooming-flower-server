package kr.co.flower.blooming.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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
@Table(name = "Choose")
@Data
public class AnswerEntity {
	@Id
	@GeneratedValue
	private long answerId;

	@Column(nullable = false)
	private String answerContent; // 정답

}
