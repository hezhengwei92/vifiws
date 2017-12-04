package net.eoutech.vifi.ws.vns.service;

import net.eoutech.utils.LogUtils;
import net.eoutech.vifi.ws.entity.TbAgent;
import net.eoutech.vifi.ws.entity.TbMachine;
import net.eoutech.vifi.ws.entity.TbSale;
import net.eoutech.vifi.ws.msg.req.VwsReqBinding;
import net.eoutech.vifi.ws.msg.resp.VwsRespBinding;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by wei on 2017/12/1.
 * 商家绑定设备  并修改  tbsale中设备激活状态
 */
@Service
public class VwsAppApiBindingService extends VwsAppServiceCommon<VwsReqBinding, VwsRespBinding> {
    @Override
    public int handle() {

        TbMachine machine = machineService.selectMachineByVid(req.getMachineID());
        if (null == machine) {
            resp.setMsg("无此设备，请联系客服");
            return resp.setCode(403);
        }

        TbAgent agent = agentService.selectAgentByOpenId(req.getOpenId());
        if (null == agent) {
            resp.setMsg("无此商户信息，请注册");
            return resp.setCode(403);
        } else {
            /**
             * 代理商绑定设备 更新TbMachine
             */
            machine.setMachineNumber(req.getMachineNumber());//设备顺序编号
            machine.setMachinePosition(req.getMachinePosition());//设备投放地点
            machine.setMachineAgentId(agent.getIdxAgentId());//代理商openId
            machine.setMachineCoin(req.getMachineCoin());//单次投币数 (币/次)
            machine.setModifyTime(new Date());
            machine.setModifyBy("wawaji");
            int mint = machineService.updateMachine(machine);
            LogUtils.info("Binding machine :" + mint + " machineID:" + machine.getUkMachineId());
            /**
             * 更新 TbSale 设备显示已经售出 并关联商户信息
             */
            TbSale sale = saleService.selectByMachine(req.getMachineID());

            sale.setIsActive(1);//0：未激活，1：已激活
            sale.setIdxAgentAccount(agent.getIdxAgentId());//买家的账号
            sale.setActiveTime(new Date());//用户激活设备的时间
            int sint = saleService.updateSale(sale);
            resp.setMsg("绑定成功");
            resp.setCode(200);
            LogUtils.info("update sale:" + sint + " saleAccount:" + sale.getIdxSaleAccount() + " machineID:" + sale.getIdxMachineId());

        }

        return 0;
    }
}
