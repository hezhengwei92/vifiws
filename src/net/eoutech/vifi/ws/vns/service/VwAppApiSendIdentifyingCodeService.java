package net.eoutech.vifi.ws.vns.service;

import com.mysql.jdbc.StringUtils;
import net.eoutech.utils.LogUtils;
import net.eoutech.utils.RedisClusterClient;
import net.eoutech.utils.SendIdentifyingCode;
import net.eoutech.utils.ToolRandoms;
import net.eoutech.vifi.ws.entity.TbUser;
import net.eoutech.vifi.ws.msg.req.VwsReqSendIdentifyingCode;
import net.eoutech.vifi.ws.msg.resp.VwsRespCommon;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/3/30 0030.
 * 发送手机验证码
 */
@Service
public class VwAppApiSendIdentifyingCodeService extends VwsAppServiceCommon<VwsReqSendIdentifyingCode, VwsRespCommon> {
    @Override
    public int handle() {

        if (StringUtils.isNullOrEmpty(req.getPhoneNumber())) {
            this.resp.setMsg("手机号未填写，请填写");
            return this.resp.setCode(446);
        }

        if (!StringUtils.isNullOrEmpty(req.getPhoneNumber())) {//手机格式验证
            Pattern pattern = Pattern.compile("^1\\d{10}$|^(0\\d{2,3}-?|\\(0\\d{2,3}\\))?[1-9]\\d{4,7}(-\\d{1,8})?$");
            Matcher matcher = pattern.matcher(req.getPhoneNumber());
            if (!matcher.matches()) {
                LogUtils.dbg("dbg|SJH:400_BAD_REQUEST");
                this.resp.setMsg("手机号码格式不正确，请确定后输入");
                return this.resp.setCode(430);
            }
        }

        String message = "";
        String identifyingCode = ToolRandoms.getIdentifyCode(6);
        String phoneNumber = req.getPhoneNumber();

        if (RedisClusterClient.GetValue(phoneNumber) != null) {
            identifyingCode = (String) RedisClusterClient.GetValue(phoneNumber);
        } else {
            RedisClusterClient.PutValue(phoneNumber, identifyingCode, 1800L);
        }
        String action = req.getAction();
        if ("REGISTER".equals(action)) {//注册
            message += "【摇摇机】您的验证码为：" + identifyingCode + "，此验证码仅用于注册摇摇机账号，30分钟有效";
        } else if ("FORGETPWD".equals(action)) {//找回密码
            TbUser user = userService.selectUserByPhoneNumber(phoneNumber);
            if (user == null) {
                LogUtils.info("info|SJH:USER DOES NOT EXIST");
                resp.setMsg("当前手机号未注册");
                return this.resp.setCode(432);
            }
            message += "【摇摇机】您的验证码为：" + identifyingCode + "，此验证码仅用于摇摇机账号找回密码使用，30分钟有效";
        } else if ("MODIFYPWD".equals(action)) {//修改密码
            message += "【摇摇机】您的验证码为：" + identifyingCode + "，此验证码仅用于修改摇摇机账号密码使用，30分钟有效";
        } else if ("ORIGINAL".equals(action)) {//验证原手机号
            message += "【摇摇机】您的验证码为：" + identifyingCode + "，此验证码仅用于手机号验证使用，30分钟有效";
        } else if ("REBIND".equals(action)) {//绑定新手机号
            message += "【摇摇机】您的验证码为：" + identifyingCode + "，此验证码仅用于摇摇机账号重新绑定手机使用，30分钟有效";
        } else if ("BIND".equals(action)) {//绑定手机号
            message += "【摇摇机】您的验证码为：" + identifyingCode + "，此验证码仅用于摇摇机账号绑定手机使用，30分钟有效";
        } else {
            this.resp.setMsg("未匹配相关行为");
            return this.resp.setCode(447);
        }
        System.out.println("identifyingCode" + identifyingCode);

        try {
            /**
             * 先不发短信
             */
            SendIdentifyingCode.sendTextSms(phoneNumber, message);
            resp.setMsg("发送成功");
            resp.setCode(200);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

}
