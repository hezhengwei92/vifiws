package net.eoutech.vifi.ws.vns.service;

import net.eoutech.utils.LogUtils;
import net.eoutech.vifi.ws.entity.TbMachine;
import net.eoutech.vifi.ws.msg.req.VwsReqShow;
import net.eoutech.vifi.ws.msg.resp.VwsRespShow;
import org.springframework.stereotype.Service;

/**
 * Created by wei on 2017/11/30.
 */
@Service
public class VwsAppApiShowService extends VwsAppServiceCommon<VwsReqShow, VwsRespShow> {
    @Override
    public int handle() {
        /**
         * 返回商户需要看到的 设备详情
         */
        TbMachine machine = machineService.selectMachineByVid(req.getMachineID());
        if (null != machine) {
            resp.setMachineType(machine.getMachineType());
            resp.setMachineID(machine.getUkMachineId());
            resp.setMachineNumber(machine.getMachineNumber());
            resp.setMachineStatus(machine.getMachineStatus());
            resp.setMachineAgentId(machine.getMachineAgentId());
            resp.setMachinePosition(machine.getMachinePosition());
            resp.setQrCode(dictionaryService.selectByKey("QRCode"));
        } else {
            LogUtils.info("machine null");
        }
        return 0;
    }
}
