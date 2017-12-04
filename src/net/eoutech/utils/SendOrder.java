package net.eoutech.utils;

import io.netty.channel.Channel;
import net.eoutech.base.tcpserver.entity.EouData;
import net.eoutech.vifi.ws.entity.TbConsumeRecord;
import net.eoutech.vifi.ws.entity.TbUser;
import net.eoutech.vifi.ws.vns.service.common.ConsumeRecordService;
import net.eoutech.vifi.ws.vns.service.common.UserService;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class SendOrder extends TimerTask {
    private Channel channel;
    private EouData data;
    private Timer timer;
    private AtomicInteger atomicInteger = new AtomicInteger(0);
    private UserService userService;
    private ConsumeRecordService consumeRecordService;
    private String sOrderID;

    public SendOrder(Channel channel, EouData data, Timer timer,UserService userService,ConsumeRecordService consumeRecordService,String sOrderID) {
        this.channel = channel;
        this.data = data;
        this.timer = timer;
        this.userService = userService;
        this.consumeRecordService = consumeRecordService;
        this.sOrderID = sOrderID;
    }

    @Override
    public void run() {
        LogUtils.info("定时器开始执行，发送投币命令");
        channel.writeAndFlush(data);
        if (atomicInteger.getAndIncrement() >= 2){
            LogUtils.info("定时任务执行了2次，自动结束");
            TbConsumeRecord record = consumeRecordService.selectRecordByOrderId(sOrderID);
            if (record != null){
                TbUser user = userService.selectUserByUserId(record.getIdxUserId());
                if (user != null) {
                    userService.updateUserCurrency(user.getUserCurrency() + record.getCostCurrency(), user.getUkUserId());
                }
            }
            timer.cancel();
        }
    }
}
