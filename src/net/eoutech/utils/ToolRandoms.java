package net.eoutech.utils;

import org.apache.log4j.Logger;

import java.util.Random;
import java.util.UUID;

/**
 * 随机数类
 * dongcb678@163.com
 */
public abstract class ToolRandoms {

    @SuppressWarnings("unused")
    private static Logger log = Logger.getLogger(ToolRandoms.class);

    private static final Random random = new Random();

    public static final char authCode[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    public static final char authIdentifyCode[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    public static final int length = authCode.length;

    /**
     * 生成验证码
     *
     * @return
     */
    public static char getAuthCodeChar() {
        return authCode[number()];
    }

    /**
     * 生成验证码
     *
     * @return
     */
    public static synchronized String getAuthCode(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(authCode[number()]);
        }
        return sb.toString();
    }

    /**
     * 生成手机号验证码
     *
     * @return
     */
    public static String getIdentifyCode(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(authIdentifyCode[number(9)]);
        }
        return sb.toString();
    }

    /**
     * 获取UUID by jdk
     * 2012-9-7 下午2:22:18
     *
     * @return
     */
    public static String getUuid(boolean is32bit) {
        String uuid = UUID.randomUUID().toString();
        if (is32bit) {
            return uuid.toString().replace("-", "");
        }
        return uuid;
    }

    /**
     * 产生两个数之间的随机数
     *
     * @return int 随机数字
     */
    public static int number() {
        return (int) (Math.random() * 35);
    }

    /**
     * 产生0--number的随机数,不包括num
     *
     * @param number 数字
     * @return int 随机数字
     */
    public static int number(int number) {
        return random.nextInt(number);
    }

    /**
     * 生成RGB随机数
     *
     * @return
     */
    public static int[] getRandomRgb() {
        int[] rgb = new int[3];
        for (int i = 0; i < 3; i++) {
            rgb[i] = random.nextInt(255);
        }
        return rgb;
    }

}
