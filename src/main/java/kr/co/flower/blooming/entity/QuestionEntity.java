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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private QuestionType questionType; // 유형

	@Column(nullable = false)
	private String questionTitle; // 발문

	@Lob
	@Column(nullable = false)
	private String questionContent; // 지문

	@Column(nullable = false)
	private String questionAnswer; // 정답

	private boolean pastYn; // 기출 여부

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "passage_id")
	private PassageEntity passageEntity;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "question_id")
	private List<ChooseEntity> chooseEntities = new ArrayList<>();

	public void setChooseEntities(List<ChooseDto> chooseDtos) {
		List<ChooseEntity> chooseList = chooseDtos.stream().map(dto -> {
			ChooseEntity chooseEntity = new ChooseEntity();
			chooseEntity.setChooseSeq(dto.getChooseSeq());
			chooseEntity.setChooseContent(dto.getChooseContent());

			return chooseEntity;
		}).toList();

		this.chooseEntities.clear();
		this.chooseEntities.addAll(chooseList);
	}
}
