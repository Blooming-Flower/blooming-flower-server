package kr.co.flower.blooming.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import kr.co.flower.blooming.entity.PassageEntity;

public interface PassageRepository
        extends JpaRepository<PassageEntity, Long>, PassageCustomRepository {

}
