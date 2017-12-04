package net.eoutech.utils;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2016/11/17 0017.
 */
public class CardStatusScheduler implements Job {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//        JobDetail jobDetail = jobExecutionContext.getJobDetail();
//        JobDataMap dataMap = jobDetail.getJobDataMap();
//        String uid = (String) dataMap.get("uid");
//        String vid = (String) dataMap.get("vid");
//        CardStatusService cardStatusService = (CardStatusService) dataMap.get("object");
//        String jobName = (String) dataMap.get("jobName");
//        String status = cardStatusService.selectByVifiId(vid).getStatus();
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//        logger.info("开始执行单独更新状态了:uid="+uid+"--vid:"+vid);
//        logger.info("GET:NOW THE CARD STAUS IS:"+status );
//        if (status.equals("0")){
//            cardStatusService.updateCardStatusAndUserId(uid,vid,"1");
//            logger.info("执行时间："+format.format(new Date()));
//        }
//
//        try {
//            TimeoutScheduler.removeJob(jobName);
//        } catch (SchedulerException e) {
//            e.printStackTrace();
//        }
//        logger.info("定时器已结束！--------------------------------------");
    }
}
