package kr.co.flower.blooming.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import kr.co.flower.blooming.entity.QuestionEntity;

public interface QuestionRepository
        extends JpaRepository<QuestionEntity, Long>, QuestionCustomRepository {

}
