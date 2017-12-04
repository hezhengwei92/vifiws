package net.eoutech.vifi.ws.vns.service;

import com.alibaba.fastjson.JSON;
import net.eoutech.vifi.ws.entity.TbFeedback;
import net.eoutech.vifi.ws.entity.TbMachine;
import net.eoutech.vifi.ws.entity.TbPackage;
import net.eoutech.vifi.ws.entity.TbUser;
import net.eoutech.vifi.ws.msg.req.VwsReqMachine;
import net.eoutech.vifi.ws.msg.resp.VwsRespMachine;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by wei on 2017/11/29.
 */
@Service
public class VwsAppApiMachineService extends VwsAppServiceCommon<VwsReqMachine, VwsRespMachine> {
    @Override
    public int handle() {

        TbMachine machine = machineService.selectMachineByVid(req.getMachineID());
        if (null == machine) {
            resp.setCode(200);
            resp.setMsg("无此设备，请换台设备");
            return 0;
        }
        List<TbMachine> machineList = machineService.selectMachineByPositionCode(machine.getMachinePositionCode());
        List<Map<String, Object>> respMachine = new ArrayList<>();
        for (TbMachine tbmachine : machineList) {
            Map<String, Object> machineMap = new HashMap<>();
            machineMap.put("machineID", tbmachine.getUkMachineId());
            machineMap.put("machineNum", tbmachine.getMachineNumber());
            respMachine.add(machineMap);
        }
        String data = JSON.toJSONString(respMachine);
        resp.setNowMachine(machine.getMachineNumber());
        resp.setSumMachine(machineList.size());
        resp.setData(data);
        resp.setCode(200);
        resp.setMsg("success");
        return 0;
    }
}
