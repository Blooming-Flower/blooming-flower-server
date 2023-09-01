package kr.co.flower.blooming.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import lombok.Data;

/**
 * 상수 값 설정 저장 class
 */
@Data
@Configuration
@ConfigurationProperties(prefix = FlowerConfig.PREFIX)
@PropertySource(factory = YamlPropertySourceFactory.class, value = {"classpath:application.yml"})
public class FlowerConfig {
    public static final String PREFIX = "flower";
    
    //    flower:
    //        backup:
    //          hour: ${BACKUP_HOUR:02:00}
    //          minute: ${BACKUP_MINUTE:00}
    //          second: ${BACKUP_SECOND:00}

    private BackupProperties backup = new BackupProperties();

    @Data
    public class BackupProperties {
        private String hour;
        private String minute;
        private String second;
    }
}
