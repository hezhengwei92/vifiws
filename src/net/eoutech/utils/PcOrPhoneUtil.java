package net.eoutech.utils;

/**
 * Created by huyong on 2017/10/19.
 * 判断请求来自手机还是PC
 */
public class PcOrPhoneUtil {
    /**
     * android : 所有android设备
     * mac os : iphone ipad
     * windows phone:Nokia等windows系统的手机
     */
    public static boolean judgePcPhone(String requestHeader){
        String[] deviceArray = new String[]{"android","mac os","windows phone"};
//        if(requestHeader == null)
//            return false;
//         Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36  pc内置浏览器
//        System.out.println(requestHeader);
        requestHeader = requestHeader.toLowerCase();
        for (String aDeviceArray : deviceArray) {
            if (requestHeader.indexOf(aDeviceArray) > 0) {
                return true;
            }
        }
        return false;

    }

    public static boolean judgeIfunk(String requestHeader){
//        System.out.println(requestHeader);
        LogUtils.info("PC公司请求user-agent"+requestHeader);
        requestHeader = requestHeader.toLowerCase();
        return requestHeader.indexOf("chrome/57.0.2987.133") > 0;
    }

}
