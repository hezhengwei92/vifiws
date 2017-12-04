package net.eoutech.vifi.ws.vns.service;

import net.eoutech.utils.LogUtils;
import net.eoutech.vifi.ws.entity.TbMachine;
import net.eoutech.vifi.ws.msg.req.VwsReqUnBinding;
import net.eoutech.vifi.ws.msg.resp.VwsRespUnBinding;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by wei on 2017/12/1.
 * 商户解绑 设备
 */
@Service
public class VwsAppApiUnBindingService extends VwsAppServiceCommon<VwsReqUnBinding, VwsRespUnBinding> {
    @Override
    public int handle() {
        TbMachine machine=machineService.selectMachineByVidAndUid(req.getMachineID(),req.getOpenId());
        if (null==machine){
            resp.setMsg("无此设备，不能解绑");
            return resp.setCode(403);
        }
        machine.setMachineNumber(0);//设备顺序编号
        machine.setMachinePosition("");//设备投放地点
        machine.setMachineAgentId("");//代理商openId
        machine.setMachineCoin(1);//单次投币数 (币/次)
        machine.setModifyTime(new Date());
        machine.setModifyBy("wawaji");

        int mint=machineService.updateMachine(machine);

        resp.setMsg("解绑成功");
        resp.setCode(200);
        LogUtils.info("unbinding machine :"+mint);

        return 0;
    }
}
