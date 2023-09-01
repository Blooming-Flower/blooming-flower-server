package kr.co.flower.blooming.backup.scheduler;

import java.util.Date;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * RDB Backup하는 Spring batch 실행
 */
@Slf4j
@RequiredArgsConstructor
public class BackupSchedulerJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("[BACK_UP] {} DATABASE backup start!!", new Date());
    }

}
