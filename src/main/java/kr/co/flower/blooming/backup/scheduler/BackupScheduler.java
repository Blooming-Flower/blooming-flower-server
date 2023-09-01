package kr.co.flower.blooming.backup.scheduler;

import javax.annotation.PostConstruct;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.context.annotation.Configuration;
import kr.co.flower.blooming.config.FlowerConfig;
import kr.co.flower.blooming.config.FlowerConfig.BackupProperties;
import kr.co.flower.blooming.utils.FlowerUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 매일 DBMS Backup을 만들기 위해 trigger 생성해서 정해진 시간에 schedule
 */
@RequiredArgsConstructor
@Configuration
@Slf4j
public class BackupScheduler {
    private static final String BACKUP_JOB_KEY = "backupJobKey";
    private static final String BACKUP_JOB_GROUP = "backupJobGroup";

    private final FlowerConfig flowerBaseConfig;
    private final Scheduler scheduler;

    @PostConstruct
    public void backupJobSchedule() throws SchedulerException {
        try {
            // convert planning cron expression
            BackupProperties backupProperties = flowerBaseConfig.getBackup();
            String planningCronExpression =
                    FlowerUtils.planningJobCronExpressionConverter(
                            backupProperties.getHour(),
                            backupProperties.getMinute(),
                            backupProperties.getSecond());

            Trigger t = scheduler
                    .getTrigger(TriggerKey.triggerKey(BACKUP_JOB_KEY, BACKUP_JOB_GROUP));
            if (t == null) {
                Trigger trigger = TriggerBuilder.newTrigger()
                        .withIdentity(
                                BACKUP_JOB_KEY, BACKUP_JOB_GROUP)
                        .withSchedule(
                                CronScheduleBuilder.cronSchedule(planningCronExpression))
                        .build();

                JobDetail jobDetail = JobBuilder.newJob(BackupSchedulerJob.class)
                        .withIdentity(BACKUP_JOB_KEY, BACKUP_JOB_GROUP)
                        .build();
                scheduler.scheduleJob(jobDetail, trigger);
            }
        } catch (org.quartz.SchedulerException e) {
            log.error("quartz scheduler schedule error :: {}", e);
            throw e;
        }
    }
}
