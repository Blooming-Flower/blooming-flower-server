package kr.co.flower.blooming.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import kr.co.flower.blooming.entity.ExamQuestionEntity;

public interface ExamQuestionRepository
        extends JpaRepository<ExamQuestionEntity, Long> {

}
