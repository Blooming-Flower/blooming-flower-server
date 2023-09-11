package kr.co.flower.blooming.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CORS configuration
 * 
 * @author shmin
 *
 */
@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // TODO 추후 프론트 서버로 MAPPING 정보 바꿔줌
                .allowedOrigins("*")
//                .allowedHeaders("*")
                .allowedMethods("*")
                .maxAge(3000);
    }
}
