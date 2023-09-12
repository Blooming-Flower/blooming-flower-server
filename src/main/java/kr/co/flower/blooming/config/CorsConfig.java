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
		registry.addMapping("/**").allowedOriginPatterns("*")
		.allowedHeaders("Accept", "Origin", "X-Requested-With",
				"Content-Type", "Access-Control-Allow-Origin", "Access-Control-Allow-Headers")
		.allowedMethods("*")
				.exposedHeaders("Content-Disposition");
	}
}
