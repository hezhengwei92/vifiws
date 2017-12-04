package net.eoutech.utils;

/**
 * Created by huyong on 2017/10/21.
 * 手机验证码验证
 */
public class CheckCodeUtils {
    public static boolean checkCode(String checkCode,String phoneNumber){
        if(phoneNumber==null||"".equals(phoneNumber)){
            return false;
        }
        String vCode = (String) RedisClusterClient.GetValue(phoneNumber);
        if (vCode == null || "".equals(vCode)) {
            return false;
        }
        if (checkCode == null || "".equals(checkCode)) {
            return false;
        }
        if (!checkCode.equals(vCode)) {
            return false;
        }
        return true;
    }
}
