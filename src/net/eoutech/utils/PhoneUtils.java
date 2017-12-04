package net.eoutech.utils;

import com.mysql.jdbc.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by huyong on 2017/10/13.
 */
public class PhoneUtils {
    public static int checkPhoneNumber(String phone){
        if (!StringUtils.isNullOrEmpty(phone)){//手机格式验证
            Pattern pattern = Pattern.compile("^1\\d{10}$|^(0\\d{2,3}-?|\\(0\\d{2,3}\\))?[1-9]\\d{4,7}(-\\d{1,8})?$");
            Matcher matcher = pattern.matcher(phone);
            if (!matcher.matches()) {
                return -2;
            }else {
                return 0;
            }
        }else {
            return -1;
        }
    }
}
