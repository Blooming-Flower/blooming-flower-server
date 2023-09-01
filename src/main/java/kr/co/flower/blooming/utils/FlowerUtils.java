package kr.co.flower.blooming.utils;

import static com.cronutils.model.field.expression.FieldExpressionFactory.always;
import static com.cronutils.model.field.expression.FieldExpressionFactory.on;
import static com.cronutils.model.field.expression.FieldExpressionFactory.questionMark;
import java.time.ZonedDateTime;
import com.cronutils.builder.CronBuilder;
import com.cronutils.model.Cron;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.time.ExecutionTime;
import com.cronutils.parser.CronParser;
import lombok.experimental.UtilityClass;

/**
 * Flower Utils
 */
@UtilityClass
public class FlowerUtils {
    private static final CronDefinition cronDefinition =
            CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ);

    private static final CronParser parser = new CronParser(cronDefinition);

    /**
     * fico.planning.schedule-time의 시간 str을 cron expression으로 convert
     * 
     * @param scheduleTime
     * @return
     */
    public static String planningJobCronExpressionConverter(String hour, String minute,
            String second) {
        Cron cron = CronBuilder.cron(cronDefinition)
                .withYear(always()) // 연도 (*)
                .withDoW(questionMark()) // 요일 (?)
                .withMonth(always()) // 월
                .withDoM(always()) // 일
                .withHour(on(Integer.parseInt(hour))) // 시
                .withMinute(on(Integer.parseInt(minute))) // 분
                .withSecond(on(Integer.parseInt(second))) // 초
                .instance();

        return cron.asString();
    }

    /**
     * @param(cron expression)으로 오늘 실행되는 job인지 check
     * @return
     */
    public static boolean cronExpressionIsToday(String expression, ZonedDateTime todayDate) {
        Cron cron = parser.parse(expression).validate();

        ExecutionTime executionTime = ExecutionTime.forCron(cron);

        return executionTime.isMatch(todayDate);
    }
}
