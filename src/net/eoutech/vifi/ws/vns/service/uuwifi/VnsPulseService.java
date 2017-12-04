package net.eoutech.vifi.ws.vns.service.uuwifi;

import io.netty.channel.ChannelHandlerContext;
import net.eoutech.base.tcpserver.entity.EouData;
import net.eoutech.base.tcpserver.utils.OrderUtil;
import net.eoutech.base.tcpserver.utils.StaticMsg;
import net.eoutech.utils.EuStringUtil;
import net.eoutech.utils.LogUtils;
import net.eoutech.vifi.ws.entity.TbConsumeRecord;
import net.eoutech.vifi.ws.entity.TbMachine;
import net.eoutech.vifi.ws.entity.TbUser;
import net.eoutech.vifi.ws.vns.service.common.ConsumeRecordService;
import net.eoutech.vifi.ws.vns.service.common.MachineService;
import net.eoutech.vifi.ws.vns.service.common.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Timer;

/**
 * 返回结果
 * Created by WangY on 2017/07/06.
 */
@Service
public class VnsPulseService {
    @Autowired
    ConsumeRecordService consumeRecordService;
    @Autowired
    UserService userService;
    @Autowired
    MachineService machineService;

    public synchronized int doAuthorization(ChannelHandlerContext ctx, byte circle) {
        try {
            String sVid = StaticMsg.getPipe2Vid().get(ctx.channel());
            if (sVid == null || "".equals(sVid)) {
                LogUtils.info("设备号为空，不进行接下来的处理");
                return 0;
            }
            TbMachine machine = machineService.selectMachineByVid(sVid);
            if (machine != null) {
                LogUtils.info("机器信息不为空，回复脉冲信息");
                Integer iPulseWide = machine.getMachinePulse();
                Integer iPulseInterval = machine.getMachineInterval();
                byte bPulseWide = 0;
                if (iPulseWide == 20) {
                    bPulseWide = 1;
                } else if (iPulseWide == 40) {
                    bPulseWide = 2;
                } else if (iPulseWide == 60) {
                    bPulseWide = 3;
                }
                byte bPulseInterval = iPulseInterval.byteValue();
                LogUtils.info("脉冲宽度：" + bPulseWide + ",H=" + iPulseWide + "ms,脉冲间隔：" + bPulseInterval + ",L=" + iPulseWide * iPulseInterval + "ms");
                EouData data = OrderUtil.ResponsePulse(circle, new byte[]{bPulseWide, bPulseInterval});
                StaticMsg.getCtx2Pluse().put(ctx.channel(),data);
                StaticMsg.getCtx2Status().put(ctx.channel(),1);
                ctx.writeAndFlush(data);
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            myInfo("VnsPulseService get throw exception:" + EuStringUtil.myExceptionString(e));
            return 500;
        }
    }

    /**
     * **********************vsw日志**************
     */
    private void myInfo(String pattern, Object... args) {
        LogUtils.log(LogUtils.INFO, LogUtils.INFO, buildLogParams(pattern, args));
    }

    private String[] buildLogParams(String pattern, Object... args) {

        return new String[]{MessageFormat.format(pattern, args)};
    }
}
