package kr.co.flower.blooming.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import kr.co.flower.blooming.entity.ExamEntity;

public interface ExamRepository extends JpaRepository<ExamEntity, Long>, ExamCustomRepository {

}
