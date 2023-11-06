package kr.co.flower.blooming.service;

import java.io.IOException;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BackupService {
    private static final int EXIT_CODE_SUCCESS = 0;
    private static final String BACKUP_SCRIPT =
            "/home/ec2-user/deploy/blooming-flower-server/shell/db_backup.sh";

    /**
     * DB Backup
     * 
     * backup script 실행
     * 
     * @return
     */
    public boolean backup() {
        boolean isSuccess = false;

        try {
            // 실행
            ProcessBuilder processBuilder = new ProcessBuilder(BACKUP_SCRIPT);
            Process process = processBuilder.start();

            // 프로세스가 종료될때 까지 대기
            int exitCode = process.waitFor();

            // exitCode를 통해 성공, 실패 여부 판단
            if (exitCode == EXIT_CODE_SUCCESS) {
                isSuccess = true;
            }

        } catch (IOException e) {
            log.error("do not execute process", e);

        } catch (InterruptedException e) {
            log.error("error occured during excute process", e);

            Thread.currentThread().interrupt();
        }

        return isSuccess;
    }
}
