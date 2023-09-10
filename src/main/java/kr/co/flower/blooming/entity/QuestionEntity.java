package kr.co.flower.blooming.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import kr.co.flower.blooming.dto.in.AnswerDto;
import kr.co.flower.blooming.dto.in.ChooseDto;
import lombok.Data;

/**
 * 문제 table
 * 
 * @author shmin
 *
 */
@Entity
@Table(name = "QUESTION")
@Data
public class QuestionEntity extends BaseEntity {
	@Id
	@GeneratedValue
	private long questionId;

	@Column(nullable = false)
	private String questionCode; // 문제 code

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private QuestionType questionType; // 유형

	@Column(nullable = false)
	private String questionTitle; // 발문

	private String questionSubTitle; // sub 발문(복합지문일 경우)

	private boolean pastYn; // 기출 여부

	private boolean saveYn; // 실제 저장 여부

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "passage_id")
	private PassageEntity passageEntity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "question_content_id")
	private QuestionContentEntity questionPassageEntity;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "question_id")
	private List<ChooseEntity> chooseEntities = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "question_id")
	private List<AnswerEntity> answerEntities = new ArrayList<>();

	public void setChooseEntities(List<ChooseDto> chooseDtos) {
		List<ChooseEntity> chooseList = chooseDtos.stream().map(dto -> {
			ChooseEntity chooseEntity = new ChooseEntity();
			chooseEntity.setChooseSeq(dto.getChooseSeq());
			chooseEntity.setChooseContent(dto.getChooseContent());

			return chooseEntity;
		}).collect(Collectors.toList());

		this.chooseEntities.clear();
		if (!chooseList.isEmpty()) {
			this.chooseEntities.addAll(chooseList);
		}
	}

	public void setAnswerEntities(List<AnswerDto> answerDtos) {
		List<AnswerEntity> answerList = answerDtos.stream().map(dto -> {
			AnswerEntity answerEntity = new AnswerEntity();
			answerEntity.setAnswerContent(dto.getAnswerContent());

			return answerEntity;
		}).collect(Collectors.toList());

		this.answerEntities.clear();
		if (!answerList.isEmpty()) {
			this.answerEntities.addAll(answerList);
		}
	}
}
