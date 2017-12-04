package net.eoutech.utils;

/**
 * Created by huyong on 2017/10/23.
 * 后台检测参数工具
 */
public class ParaCheckUtil {

    //检测传入的所有参数是否有为空的
    public static int checkPara(String ...paras){    //para是参数数组
        int i=1;
        for (String para:paras){
            if(isNullOrEmpty(para)){
                return i;
            }
            i++;
        }
        return 0;
    }

    //检测传入的参数是否为空或包含空格
    public static int checkParam(String ...paras){    //para是参数数组
        int i=1;
        for (String para:paras){
            if(isNullOrEmpty(para)||para.contains(" ")){
                return i;
            }
            i++;
        }
        return 0;
    }

    //判断null或者空
    private static boolean isNullOrEmpty(String toTest) {
        return toTest == null || toTest.length() == 0;
    }


}
