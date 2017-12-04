//package net.eoutech.utils;
//
//import net.eoutech.base.tcpserver.utils.JiguangPush;
//
//import java.util.Date;
//import java.util.Timer;
//import java.util.TimerTask;
//
///**
// * Created by WangY on 2017/7/28 0028.
// */
//public class JGThread extends TimerTask {
//    public static void init() {
//        LogUtils.info("准备执行线程");
//        Date date = new Date();
////        Runnable runnable = new Runnable() {
////            @Override
////            public void run() {
////                LogUtils.info("开始执行线程");
////                JedisUtil.changeDatabase(1);
//////                System.out.println(JedisUtil.get(JedisUtil.JGPushKey.getBytes()));
////            }
////        };
//        Timer timer = new Timer(true);
//        JGThread jg = new JGThread();
//        timer.schedule(jg, date, 10 * 1000);
////        ScheduledExecutorService service = Executors
////                .newSingleThreadScheduledExecutor();
////        // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
////        service.scheduleAtFixedRate(runnable, 10, 5, TimeUnit.SECONDS);
//    }
//
//    @Override
//    public void run() {
//        try {
//            if (RedisClusterClient.RightPop(RedisClusterClient.JGPushKey) != null) {
//                JGPush push = (JGPush) RedisClusterClient.RightPop(RedisClusterClient.JGPushKey);
//                if (push != null) {
//                    LogUtils.info("开始执行线程，准备发送通知");
//                    JiguangPush.jiguangPush(push.getWsType(), push.getUserId(), push.getNickname(), push.getTitle(), push.getMsgContent(), push.getType(), push.getContent(), push.getNewTitle());
//                }
////            byte[] jPush = JedisUtil.rpop(JedisUtil.JGPushKey.getBytes(),1);
////            if (!(push == null) && jPush.length != 0) {
////                LogUtils.info("发送通知");
////                JGPush push = ObjectUtil.bytesToObject(jPush);
////                JiguangPush.jiguangPush(push.getWsType(), push.getUserId(), push.getNickname(), push.getTitle(), push.getMsgContent(), push.getType(), push.getContent(),push.getNewTitle());
////            }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            LogUtils.info("异常：" + e.getMessage());
//        }
//    }
//}
