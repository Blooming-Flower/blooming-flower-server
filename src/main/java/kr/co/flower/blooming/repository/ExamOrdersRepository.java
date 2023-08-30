package kr.co.flower.blooming.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import kr.co.flower.blooming.entity.ExamOrderEntity;

public interface ExamOrdersRepository extends JpaRepository<ExamOrderEntity, Long> {

}
