package net.eoutech.base.tcpserver.utils;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosAlert;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.mysql.jdbc.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by WangY on 2017/6/27 0027.
 */
public class JiguangPush {
    private static String masterSecret = "addb09d3e4ea934602d85cfe";
    private static String appKey = "94123a05f5e3adb926020174";
    private static final Logger log = LoggerFactory.getLogger(JiguangPush.class);
    private String pushUrl = "https://api.jpush.cn/v3/push";
    private int time_to_live = 86400;

    public static void main(String[] args) {
        JiguangPush jiguangPush = new JiguangPush();
        String type="Message";
        type="Notification";
//wei: ypfj7d4ucpa70bg1ekgb
        jiguangPush.jiguangPush(type,"2et3bfqdrh2vfx5qc8cp","你是汗汗？","这是标题","这是标题中的内容","url","http://www.baidu.com","活动页面");
    }
    /**
     * 极光推送   系统用通知：Notification   个人用消息：Message
     */
    public static void jiguangPush(String wsType,String userId,String nickname,String title,String msgContent,String type,String content,String newTitle){

        JPushClient jpushClient = new JPushClient(masterSecret, appKey);
        PushResult result;

        if(wsType.equals("Message")){
            //发送自定义消息
            result = pushMessage_Ios_Android(userId,nickname,title,msgContent,jpushClient,type,content,newTitle);
            if(result != null && result.isResultOK()){
                System.out.println("su");
                log.info("针对别名" + nickname + "的信息推送成功！");
            }else{
                System.out.println("fai");
                log.info("针对别名" + nickname + "的信息推送失败！");
            }
        }else if(wsType.equals("Notification")){
            //发送通知
            result = pushNotification_Ios_Android(userId,nickname,title,msgContent,jpushClient,type,content,newTitle);
            if(result != null && result.isResultOK()){
                System.out.println("推送成功");
                log.info("通知推送成功！");
            }else{
                System.out.println("通知推送失败");
                log.info("通知推送失败！");
            }
        }

    }
    //自定义消息
    public static PushResult pushMessage_Ios_Android(String userId, String nickname, String title,String msgContent,JPushClient jpushClient,String type,String content,String newTitle){

        PushPayload payload = buildPushObject_android_ios_alias_alert(userId,nickname,title,msgContent,type,content,newTitle);
        try {
            PushResult result=jpushClient.sendPush(payload);
            return result;
        } catch (APIConnectionException e) {
            log.error("Connection error. Should retry later. ", e);
            return null;
        } catch (APIRequestException e) {
            log.error("Error response from JPush server. Should review and fix it. ", e);
            log.info("HTTP Status: " + e.getStatus());
            log.info("Error Code: " + e.getErrorCode());
            log.info("Error Message: " + e.getErrorMessage());
            log.info("Msg ID: " + e.getMsgId());
            return null;
        }
    }
    //发送通知
    public static PushResult pushNotification_Ios_Android( String userId,String nickName,String title,String msgContent ,JPushClient jpushClient,String type,String content,String newTitle){
        PushPayload payload;
        IosAlert alert = IosAlert.newBuilder()
                .setTitleAndBody(title, null, msgContent)
                .build();
        HashMap<String, String> extras=new HashMap<String, String>();

        extras.put("type",type);
        if(!StringUtils.isNullOrEmpty(content)){
            extras.put("content",content);
        }
        if(!StringUtils.isNullOrEmpty(newTitle)){
            extras.put("title",newTitle);
        }
        if(!StringUtils.isNullOrEmpty(userId)){
            payload=PushPayload.newBuilder()
                    .setPlatform(Platform.all())//设置接受的平台
                    .setAudience(Audience.alias(userId))//Audience设置为all，说明采用广播方式推送，所有用户都可以接收到
                    .setNotification(Notification.newBuilder()
                            .addPlatformNotification(AndroidNotification.newBuilder()
                                    .addExtras(extras)
                                    .setTitle(title)
                                    .setAlert(msgContent)
                                    .build())
                            .addPlatformNotification(IosNotification.newBuilder()
                                    .addExtras(extras)
                                    .setAlert(alert)
                                    .build())
                            .build())
                    .build();
        }else{
            payload=PushPayload.newBuilder()
                    .setPlatform(Platform.all())//设置接受的平台
                    .setAudience(Audience.all())//Audience设置为all，说明采用广播方式推送，所有用户都可以接收到
                    .setNotification(Notification.newBuilder()
                            .addPlatformNotification(AndroidNotification.newBuilder()
                                    .addExtras(extras)
                                    .setTitle(title)
                                    .setAlert(msgContent)
                                    .build())
                            .addPlatformNotification(IosNotification.newBuilder()
                                    .addExtras(extras)
                                    .setAlert(alert)
                                    .build())
                            .build())
                    .build();
        }
        try {
          return  jpushClient.sendPush(payload);
        } catch (APIConnectionException e) {
            log.error("Connection error. Should retry later. ", e);
            return null;
        } catch (APIRequestException e) {
            log.error("Error response from JPush server. Should review and fix it. ", e);
            log.info("HTTP Status: " + e.getStatus());
            log.info("Error Code: " + e.getErrorCode());
            log.info("Error Message: " + e.getErrorMessage());
            log.info("Msg ID: " + e.getMsgId());
            return null;
        }
    }
    //自定义消息
    public static PushPayload buildPushObject_android_ios_alias_alert(String userId,String nickname,String title,String msgContent,String type,String content,String newTitle){

//        if(StringUtils.isNullOrEmpty(userId)){
//            log.info("",nickname+" "+title+"  "+msgContent+"-----------1-------------");
//        }

        Map extra=new HashMap();
        extra.put("type",type);
        if(!StringUtils.isNullOrEmpty(content)){
            extra.put("content",content);
        }
        if(!StringUtils.isNullOrEmpty(newTitle)){
            extra.put("title",newTitle);
        }
        if(!StringUtils.isNullOrEmpty(userId)){
            return PushPayload.newBuilder()
                    .setPlatform(Platform.android_ios())
                    .setAudience(Audience.alias(userId))
                    .setMessage(Message.newBuilder()
                            .setTitle(title)
                            .setMsgContent(msgContent)
                            .addExtras(extra)
                            .build())
                    .setOptions(Options.newBuilder()
                            .setApnsProduction(false)//true-推送生产环境 false-推送开发环境（测试使用参数）
                            .setTimeToLive(90)//消息在JPush服务器的失效时间（测试使用参数）
                            .build())
                    .build();
        }else{
            return PushPayload.newBuilder()
                    .setPlatform(Platform.android_ios())
                    .setAudience(Audience.all())
                    .setMessage(Message.newBuilder()
                            .setTitle(title)
                            .setMsgContent(msgContent)
                            .addExtras(extra)
                            .build())
                    .setOptions(Options.newBuilder()
                            .setApnsProduction(false)//true-推送生产环境 false-推送开发环境（测试使用参数）
                            .setTimeToLive(90)//消息在JPush服务器的失效时间（测试使用参数）
                            .build())
                    .build();
        }
//        return PushPayload.newBuilder()
//                .setPlatform(Platform.android_ios())
//                .setAudience(Audience.alias(userId))
//                .setMessage(Message.newBuilder()
//                        .setTitle(title)
//                        .setMsgContent(msgContent)
//                        .addExtras(extra)
//                        .build())
//                .setOptions(Options.newBuilder()
//                        .setApnsProduction(false)//true-推送生产环境 false-推送开发环境（测试使用参数）
//                        .setTimeToLive(90)//消息在JPush服务器的失效时间（测试使用参数）
//                        .build())
//                .build();
    }

}
