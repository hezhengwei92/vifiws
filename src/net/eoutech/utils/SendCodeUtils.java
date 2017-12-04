package net.eoutech.utils;

import org.springframework.data.redis.connection.RedisClusterConnection;

/**
 * Created by huyong on 2017/10/14.
 * 发送手机验证码
 */
public class SendCodeUtils {

    public static boolean sendPhoneCode(String phoneNumber,String msg){

        String identifyingCode = ToolRandoms.getIdentifyCode(6);

       if (RedisClusterClient.GetValue(phoneNumber) != null) {
            identifyingCode =  (String) RedisClusterClient.GetValue(phoneNumber);
//            RedisClusterClient.put(phoneNumber, identifyingCode, 1800);
        } else {
            RedisClusterClient.PutValue(phoneNumber, identifyingCode, 1800L);
        }
        String message ="您的验证码为：" + identifyingCode + "，"+msg;
//        System.out.println(message);
        try {
            SendIdentifyingCode.sendTextSms(phoneNumber, message);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
