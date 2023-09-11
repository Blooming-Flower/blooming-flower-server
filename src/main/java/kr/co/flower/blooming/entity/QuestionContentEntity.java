package kr.co.flower.blooming.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Data;

/**
 * 문제 지문 table
 * 
 * @author shmin
 *
 */
@Entity
@Table(name = "QUESTION_CONTENT")
@Data
public class QuestionContentEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long questionContentId;

	@Column(nullable = false)
	private String questionTitle; // 발문
	
	@Lob
	@Column(nullable = false)
	private String questionContent;
}
