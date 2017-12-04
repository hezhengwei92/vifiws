package net.eoutech.vifi.ws.vns.service;

import net.eoutech.utils.MD5Utils;
import net.eoutech.vifi.ws.msg.req.VwsReqUpdatePWD;
import net.eoutech.vifi.ws.msg.resp.VwsRespCommon;
import org.springframework.stereotype.Service;

/**
 * Created by wei on 2017/12/2.
 */
@Service
public class VwsAppApiUpdatePWDService extends VwsAppServiceCommon<VwsReqUpdatePWD, VwsRespCommon> {
    @Override
    public int handle() {
        //密码双重md5
        String md5StrPwd=MD5Utils.md5(MD5Utils.md5(req.getPwd()));
        int aint=agentService.updatePwdByOpenId(md5StrPwd,req.getOpenId());
        if (aint==1){
            resp.setMsg("密码修改成功");
            return this.resp.setCode(200);
        }else{
            resp.setMsg("密码修改失败，请重试");
            resp.setCode(403);
        }

        return 0;
    }
}
