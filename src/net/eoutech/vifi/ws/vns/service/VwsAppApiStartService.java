package net.eoutech.vifi.ws.vns.service;

import io.netty.channel.Channel;
import net.eoutech.base.tcpserver.entity.EouData;
import net.eoutech.base.tcpserver.utils.OrderUtil;
import net.eoutech.base.tcpserver.utils.StaticMsg;
import net.eoutech.utils.EuStringUtil;
import net.eoutech.utils.LogUtils;
import net.eoutech.utils.SendOrder;
import net.eoutech.vifi.ws.entity.TbConsumeRecord;
import net.eoutech.vifi.ws.entity.TbMachine;
import net.eoutech.vifi.ws.entity.TbUser;
import net.eoutech.vifi.ws.msg.req.VwsReqStart;
import net.eoutech.vifi.ws.msg.resp.VwsRespStart;
import org.springframework.stereotype.Service;

import java.sql.Statement;
import java.util.Date;
import java.util.Timer;

/**
 * Created by wei on 2017/11/28.
 * 用户投币 启动机器  生成订单号  等待硬件回传启动机器
 * 启动成功  在硬件启动成功 返回数据后 再更新数据
 * 启动失败
 */
@Service
public class VwsAppApiStartService extends VwsAppServiceCommon<VwsReqStart, VwsRespStart> {
    @Override
    public int handle() {
        //一个用户分 支付宝和微信账号
        TbUser user = userService.selectUserByUserId(req.getUid());
        if (null == user) {
            resp.setCode(200);
            resp.setMsg("无此用户，请跳转到充值页面！");
            return 0;
        }
        if (null != user && user.getUserCurrency() <= 0) {
            resp.setCode(200);
            resp.setMsg("可用游戏币为0，请先充值");
            return 0;
        }
        if (null != user && user.getUserCurrency() < req.getCostCurrency()) {
            resp.setCode(200);
            resp.setMsg("游戏币不足，请先充值");
            return 0;
        }

        //根据机器编号查询
        TbMachine machine = machineService.selectMachineByVid(req.getMachineID());
        if (null == machine) {
            resp.setCode(200);
            resp.setMsg("无此设备，请换台机器");
            return 0;
        }
        Channel channel = StaticMsg.getVid2Pipe().get(machine.getUkMachineId());
        if (channel == null) {
            LogUtils.info("设备未连接");
            resp.setCode(200);
            resp.setMsg("设备未连接，请更换机器");
            return 0;
        }
        LogUtils.info("发送投币命令");
        byte bCircle = StaticMsg.getCircleCode();
        EouData data = OrderUtil.StartDevice(bCircle, new byte[]{req.getCostCurrency().byteValue()});
        StaticMsg.getVid2Data().put(machine.getUkMachineId(), data);//存放这条命令
        channel.writeAndFlush(data);
        try {
            String orderID = EuStringUtil.createOrderID();
            TbConsumeRecord consumeRecord = new TbConsumeRecord();
            consumeRecord.setIdxUserId(req.getUid());
            consumeRecord.setUkOrderId(orderID);
            consumeRecord.setIdxMachineId(machine.getUkMachineId());
            consumeRecord.setMachineNumber(6);
            consumeRecord.setIdxAgentId(machine.getMachineAgentId());
            consumeRecord.setMachinePosition(machine.getMachinePosition());
            consumeRecord.setPositionId(machine.getMachinePositionCode());
            consumeRecord.setUserType(user.getUserType());
            consumeRecord.setCostCurrency(req.getCostCurrency());
            consumeRecord.setCostStatus(0);//默认状态为0
            consumeRecord.setCreateBy(req.getUid());
            consumeRecord.setCreateTime(new Date());
            int status = consumeRecordService.insetRecord(consumeRecord);
            int userCurrency = user.getUserCurrency() - req.getCostCurrency();
            userService.updateByUID(req.getUid(), userCurrency);
            resp.setCode(200);
            resp.setMsg("订单生成成功");
            resp.setOrderID(orderID);
            int iCircle = bCircle & 0xFF;
            StaticMsg.getVid2Order().put(machine.getUkMachineId() + iCircle, orderID);
            Timer timer = new Timer(true);
            //从现在起过delay毫秒以后，每隔period毫秒执行一次
            SendOrder order = new SendOrder(channel, data, timer, userService, consumeRecordService, orderID);
            timer.schedule(order, 5 * 1000, 5 * 1000);
            StaticMsg.getOrder2Timer().put(orderID, timer);
            System.out.println(resp.getOrderID());
        } catch (Exception e) {
            resp.setCode(500);
            resp.setMsg("SQL Exception");
            e.printStackTrace();
        }
        return 0;
    }
}
