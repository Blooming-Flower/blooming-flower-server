package kr.co.flower.blooming.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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
@Table(name = "QUESTION_Content")
@Data
public class QuestionContentEntity {
	@Id
	@GeneratedValue
	private long questionPassageId;
	
	@Lob
	@Column(nullable = false)
	private String questionContent;
}
