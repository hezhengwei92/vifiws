package net.eoutech.vifi.ws.vns.service;

import com.mysql.jdbc.StringUtils;
import net.eoutech.utils.RedisClusterClient;
import net.eoutech.vifi.ws.msg.req.VwsReqVerificationIdentifyingCode;
import net.eoutech.vifi.ws.msg.resp.VwsRespCommon;
import org.springframework.stereotype.Service;

/**
 * Created by WangY on 2017/3/30 0030.
 * 验证 手机验证码
 */

@Service
public class VwAppApiVerificationIdentifyingCodeService extends VwsAppServiceCommon<VwsReqVerificationIdentifyingCode, VwsRespCommon> {

    @Override
    public int handle() {
        try {
            if (StringUtils.isNullOrEmpty(req.getPhoneNumber())) {
                this.resp.setMsg("手机号未填写，请填写");
                return this.resp.setCode(446);
            }
            if (StringUtils.isNullOrEmpty(req.getIdentifyingCode())) {
                this.resp.setMsg("验证码未填写，请填写");
                return this.resp.setCode(448);
            }

            String phoneNumber = req.getPhoneNumber();
            String identifyingCode = req.getIdentifyingCode();

            String checkCode = (String) RedisClusterClient.GetValue(phoneNumber);

            if (checkCode == null || "".equals(checkCode)){
                this.resp.setMsg("您未获取验证码或验证码已使用");
                return this.resp.setCode(498);
            }

            System.out.println("checkCode" + checkCode);

            if (!checkCode.equals(identifyingCode)) {
                this.resp.setMsg("验证码错误");
                return this.resp.setCode(472);
            }
            //删掉记录
            RedisClusterClient.RemoveKey(phoneNumber);
            this.resp.setMsg("验证成功");
            this.resp.setCode(200);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
