package kr.co.flower.blooming.config;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import com.querydsl.jpa.impl.JPAQueryFactory;

/**
 * queryDSL을 사용하기 위한 config
 */
@TestConfiguration
@ActiveProfiles("test")
public class TestQueryDSLConfig {

    @PersistenceContext
    EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }
}
