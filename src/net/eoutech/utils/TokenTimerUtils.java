package net.eoutech.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

/**
 * Created by Java on 2017/11/29.
 */
@Component
public class TokenTimerUtils {


    private static String token = "";

    public static String getToken() {
        return token;
    }

    /**
     * 写死的获取对应公众号的token
     */
    public String  token(){
     String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx1f4ed91c7207f884&secret=e05a557cae5a9da402984dc7c6aa1297";
        JSONObject jsStr = JSONObject.parseObject(HttpRequestUtil.getURLContent(url));
/*        String str = "{\"access_token\":\"JimN-JCPviT59rVPvhyK-vAb8U0JDVdF48Gt_CS6sGxiiP2y4WkJi65JMDU8HPNICZJ7uroBz-irRwzIgWc5dgNx-nSVeqt3tAlP2l5aM-7hlvP8wy-4IZm22-G_ym1GFMJgAGANUR\",\"expires_in\":7200}";
        JSONObject jsStr = JSONObject.parseObject(str);*/
        token=jsStr.getString("access_token");
        return token;
    }

}
