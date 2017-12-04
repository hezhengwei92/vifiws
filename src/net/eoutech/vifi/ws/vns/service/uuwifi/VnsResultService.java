package net.eoutech.vifi.ws.vns.service.uuwifi;

import io.netty.channel.ChannelHandlerContext;
import net.eoutech.base.tcpserver.entity.EouData;
import net.eoutech.base.tcpserver.utils.StaticMsg;
import net.eoutech.utils.*;
import net.eoutech.vifi.ws.entity.TbConsumeRecord;
import net.eoutech.vifi.ws.entity.TbUser;
import net.eoutech.vifi.ws.vns.service.common.ConsumeRecordService;
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
public class VnsResultService {
    @Autowired
    ConsumeRecordService consumeRecordService;
    @Autowired
    UserService userService;

    public synchronized int doAuthorization(ChannelHandlerContext ctx, byte[] content, byte circle) {
        try {
            LogUtils.info("进入结果处理阶段");
            Integer action = content[0] & 0xFF;
            Integer result = content[1] & 0xFF;
            LogUtils.info("action=" + action + ",result=" + result);

            if (action == 0x02) {//设备回复
                //获取设备编号
                String sVid = StaticMsg.getPipe2Vid().get(ctx.channel());
                if (sVid == null || "".equals(sVid)) {
                    LogUtils.info("设备号为空，不进行接下来的处理");
                    return 0;
                }
                int iCircle = circle & 0xFF;
                String sKey = sVid + iCircle;
                String sOrderId = StaticMsg.getVid2Order().get(sKey);
                if (sOrderId == null || "".equals(sOrderId)) {
                    LogUtils.info("根据映射找不到订单号");
                    return 0;
                }
                TbConsumeRecord consumeRecord = consumeRecordService.selectRecordByOrderId(sOrderId);
                if (consumeRecord == null) {
                    LogUtils.info("根据订单号找不到对应的记录");
                    return 0;
                }
                Timer timer = StaticMsg.getOrder2Timer().get(sOrderId);
                if (timer != null) {
                    timer.cancel();
                    LogUtils.info("收到通知，移除定时发送命令");
                }
                if (result == 0) {
                    LogUtils.info("操作成功");
                    //查询存库的订单记录并更新
                    consumeRecordService.updateRecordStatus(1, sOrderId);
                    StaticMsg.getOrder2Timer().remove(sOrderId);//移除订单和定时器的对应
                    StaticMsg.getVid2Order().remove(sKey);//移除设备号+循环码和订单号的对应
                } else if (result == 1) {
                    LogUtils.info("操作失败");
                    EouData data = StaticMsg.getVid2Data().get(sVid);
                    if (StaticMsg.getVid2Times().get(sKey) != null) {
                        Integer times = StaticMsg.getVid2Times().get(sKey);
                        if (times >= 3) {
                            LogUtils.info("重发3次了，不发了，启动机器失败");
                            StaticMsg.getVid2Times().remove(sKey);//移除订单号+循环码和次数的对应
                            //退币给用户
                            TbUser user = userService.selectUserByUserId(consumeRecord.getIdxUserId());
                            if (user != null) {
                                userService.updateUserCurrency(user.getUserCurrency() + consumeRecord.getCostCurrency(), user.getUkUserId());
                            }
                            StaticMsg.getOrder2Timer().remove(sOrderId);//移除订单和定时器的对应
                            StaticMsg.getVid2Order().remove(sKey);//移除设备号+循环码和订单号的对应
                            StaticMsg.getVid2Data().remove(sVid);//移除设备号和订单的命令
                            return 0;
                        }
                        StaticMsg.getVid2Times().put(sKey, times + 1);//获取到当前次数累加
                    }
                    if (data != null) {
                        ctx.writeAndFlush(data);
                    } else {
                        LogUtils.info("需回复数据的映射出现问题，数据不存在");
                    }
                } else {
                    LogUtils.info("返回结果不符合要求");
                }
            } else if (action == 0x03) {
                if (result == 0) {
                    if (StaticMsg.getCtx2Pluse().get(ctx.channel()) != null) {
                        LogUtils.info("移除命令");
                        StaticMsg.getCtx2Pluse().remove(ctx.channel());//移除命令
                    }
                    if (StaticMsg.getCtx2Status().get(ctx.channel()) != null) {
                        LogUtils.info("移除计数");
                        StaticMsg.getCtx2Status().remove(ctx.channel());//移除计数
                    }
                } else {
                    if (StaticMsg.getCtx2Pluse().get(ctx.channel()) != null) {
                        if (StaticMsg.getCtx2Status().get(ctx.channel()) != null) {
                            Integer status = StaticMsg.getCtx2Status().get(ctx.channel());
                            if (status >= 3) {
                                LogUtils.info("移除命令和计数");
                                StaticMsg.getCtx2Pluse().remove(ctx.channel());//移除命令
                                StaticMsg.getCtx2Status().remove(ctx.channel());//移除计数
                                return 0;
                            }
                            EouData data = StaticMsg.getCtx2Pluse().get(ctx.channel());
                            StaticMsg.getCtx2Status().put(ctx.channel(), status + 1);
                            ctx.channel().writeAndFlush(data);
                        }else {
                            LogUtils.info("映射的计数不存在");
                        }
                    }else {
                        LogUtils.info("映射的命令不存在");
                    }
                }
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            myInfo("VnsResultService get throw exception:" + EuStringUtil.myExceptionString(e));
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
