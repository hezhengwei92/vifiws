package net.eoutech.utils;

import com.mysql.jdbc.StringUtils;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.ParseException;
import java.util.Calendar;

/**
 * 挂单超时调度器
 *
 * @author WYI
 */
public class TimeoutScheduler {
//	private static Logger logger = LoggerFactory.getLogger(TimeoutScheduler.class);

    private static Scheduler scheduler;
    /**
     * 默认分组
     */
    private static final String DEFAULT_GROUP = Scheduler.DEFAULT_GROUP;
    /**
     * 任务名前缀
     */
    private static final String PRE_JOB_NAME = "JOB_";
    /**
     * 触发器名前缀
     */
    private static final String PRE_TRIGGER_NAME = "TRIGGER_";

    /**
     * 初始化并启动调度器
     */
    public static void init() {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        try {
            scheduler = schedulerFactory.getScheduler();
            scheduler.start();
//			logger.info("TimeoutScheduler start success!");
        } catch (SchedulerException e) {
//			logger.error(e.getMessage());
        }
    }

    /**
     * 上下卡添加定时任务
     *
     * @param uid
     * @param vid
     * @throws SchedulerException
     * @throws ParseException
     */
    public static void scheduleJob(String uid, String vid, String jobName, Job job, String cronStr, Object object) throws SchedulerException, ParseException {
        if (scheduler != null) {
            JobDetail jobDetail = new JobDetail(PRE_JOB_NAME + jobName, DEFAULT_GROUP, job.getClass());
            jobDetail.getJobDataMap().put("uid", uid);
            jobDetail.getJobDataMap().put("vid", vid);
            jobDetail.getJobDataMap().put("object", object);
            jobDetail.getJobDataMap().put("jobName", jobName);

            CronTrigger orderTrigger = new CronTrigger(PRE_TRIGGER_NAME + jobName, DEFAULT_GROUP);
            orderTrigger.setCronExpression(cronStr);

            scheduler.scheduleJob(jobDetail, orderTrigger);
        } else {
            throw new SchedulerException("TimeoutScheduler is not initialized, Can not add Job!");
        }
    }

    /**
     * 移除任务
     *
     * @param trigger
     * @throws SchedulerException
     */
    public static boolean removeJob(String trigger) throws SchedulerException {
        boolean flag = false;
        if (StringUtils.isNullOrEmpty(trigger)) {
            throw new SchedulerException("Trigger is null, Can not remove Job!");
        } else if (scheduler != null) {
            String jobName = PRE_JOB_NAME + trigger;
            flag = scheduler.deleteJob(jobName, DEFAULT_GROUP);
        } else {
            throw new SchedulerException("TimeoutScheduler is not initialized, Can not remove Job!");
        }
        LogUtils.info("removeJob " + flag);

        return flag;
    }

    public static String cornExpression(Integer minuteUp) {
        Calendar calendar = java.util.Calendar.getInstance();
        int second = calendar.get(java.util.Calendar.SECOND);
        int hour = calendar.get(java.util.Calendar.HOUR_OF_DAY);
        int minute = calendar.get(java.util.Calendar.MINUTE) + minuteUp;
        if (minute >= 60) {
            minute = minute - 60;
            hour = hour + 1;
            if (hour >= 24) {
                hour = hour - 24;
            }
        }
        String corn = String.valueOf(second) + " " + String.valueOf(minute) + " " + String.valueOf(hour) + " * * ? *";//创建corn表达式
        return corn;
    }

}
