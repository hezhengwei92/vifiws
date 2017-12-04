package net.eoutech.base.tcpserver;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import net.eoutech.base.tcpserver.entity.EouData;
import net.eoutech.base.tcpserver.entity.MsgContent;
import net.eoutech.base.tcpserver.utils.OrderUtil;
import net.eoutech.base.tcpserver.utils.StaticMsg;
import net.eoutech.utils.*;
//import net.eoutech.vifi.ws.vns.service.common.ConfigureService;
//import net.eoutech.vifi.ws.vns.service.common.ViFiDeviceService;
//import net.eoutech.vifi.ws.vns.service.uuwifi.VaUUWIFISessionServicce;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.text.SimpleDateFormat;
import java.util.*;

public class EouMsgServerHandler extends SimpleChannelInboundHandler<Object> {
    private ApplicationContext ac;
    //    private static VaUUWIFISessionServicce sessionServicce;
//    private static ViFiDeviceService vifiDeviceService;
//    private static ConfigureService configureService;
    private EouHandler eouHandler;

    public EouMsgServerHandler(EouHandler eouHandler) {
        this.eouHandler = eouHandler;
        ac = new FileSystemXmlApplicationContext("classpath*:applicationContext.xml");
//        if (EouMsgServerHandler.sessionServicce == null) {
//            EouMsgServerHandler.sessionServicce = ac.getBean(VaUUWIFISessionServicce.class);
//        }
//        if (EouMsgServerHandler.vifiDeviceService == null) {
//            EouMsgServerHandler.vifiDeviceService = ac.getBean(ViFiDeviceService.class);
//        }
//        if (EouMsgServerHandler.configureService == null) {
//            EouMsgServerHandler.configureService = ac.getBean(ConfigureService.class);
//        }
    }

    private static SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss.SSS");

    protected void channelRead0(ChannelHandlerContext ctx, Object obj) throws Exception {
        LogUtils.info("进入EouMsgServerHandler");
        LogUtils.info("channelRead0通道活动：" + ctx.channel().isActive());
        try {
            Channel channel = ctx.channel();
            if (!(obj instanceof EouData)) {//当数据解码后得不到对应的协议帧对象，说明数据有问题，返回给设备
                net.eoutech.base.tcpserver.entity.EouLogger.dbg(new Object[]{"NOT EouData instance!"});
                LogUtils.info("NOT EouData instance!");
                ctx.writeAndFlush(nullData());
            } else {//数据解码后获得对应的协议帧数据
                EouData req = (EouData) obj;
                LogUtils.info("REQ>>>>{0},FROM:{1}", req.toString(), channel.remoteAddress());
                LogUtils.info("Sender:{0},Receiver:{1}", req.getSender(), req.getReceiver());
                if (req.getSender() == 1 && req.getReceiver() == 4) {//从设备或APP发送过来，云端接收
                    //根据包个数来获取对应的数据
                    List<MsgContent> list = new ArrayList<>();
                    ContentMsg(req.getPkgNum(), req.getMsgList(), list, ctx, req.getCircle(), req.getReceiver());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.info("EouMsgServerHandler异常：" + e.getCause().getMessage());
        }
    }

    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        LogUtils.info(ctx.channel() + "---channelReadComplete通道活动：" + ctx.channel().isActive());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LogUtils.info(ctx.channel() + "---channelActive---连接WS服务器");
        ctx.channel().writeAndFlush(OrderUtil.WakeData(StaticMsg.getCircleCode(), new byte[]{0x00}));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        LogUtils.info(ctx.channel() + "---channelInactive通道活动：" + ctx.channel().isActive());
        LogUtils.info("-----channelInactive-----" + ctx.channel() + "断开连接");
        if (StaticMsg.getPipe2Vid().get(ctx.channel()) != null) {
            LogUtils.info("有设备号");
            String vid = StaticMsg.getPipe2Vid().get(ctx.channel());
            LogUtils.info("vid:" + vid);
//            if (StaticMsg.getVid2Virtual().get(vid) != null) {
//                StaticMsg.getVid2Virtual().get(vid).writeAndFlush(OrderUtil.RestCard((byte) 0xFF, vid.getBytes()));//通知AAA重置某张卡
//            }
            if (StaticMsg.getVid2Job().get(vid) != null) {//移除定时器，防止上次的定时器没有清除
                LogUtils.info("设备断开连接，移除定时器" + StaticMsg.getVid2Job().get(vid));
                TimeoutScheduler.removeJob(StaticMsg.getVid2Job().get(vid));
                StaticMsg.getVid2Job().remove(vid);
            }
//            vifiDeviceService.updateLastConnectIp("", 0, vid);//更新设备在线状态和连接的ip
//            ChannelScheduler channelScheduler = new ChannelScheduler();

//            String corn = TimeoutScheduler.cornExpression(Integer.parseInt(configureService.getChannelMinute()));//N分钟定时
            String jobName = ToolRandoms.getAuthCode(32);
            LogUtils.info("jobName:" + jobName);
//            TimeoutScheduler.scheduleJob("", vid, jobName, channelScheduler, corn, sessionServicce);
            LogUtils.info("加入定时器");

            StaticMsg.getVid2Job().put(vid, jobName);
            LogUtils.info("暂时取消移除设定");
        }
        if (StaticMsg.getCtx2List().get(ctx.channel()) != null) {
            LogUtils.info("INACTIVE通道关闭，移除");
            StaticMsg.getCtx2List().remove(ctx.channel());
        }
        ctx.channel().close();
        LogUtils.info("强制关闭通道");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LogUtils.error(ctx.channel() + "---exceptionCaught---" + cause.getMessage() + "---" + Arrays.toString(cause.getStackTrace()));
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
            throws Exception {
        LogUtils.info("触发事件");
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state().equals(IdleState.READER_IDLE)) {
                //未进行读操作
                LogUtils.info("SERVER_READER_IDLE");
            } else if (event.state().equals(IdleState.WRITER_IDLE)) {
                //未进行写操作
                LogUtils.info("SERVER_WRITER_IDLE");
            } else if (event.state().equals(IdleState.ALL_IDLE)) {
                LogUtils.info("SERVER_ALL_IDLE");
            } else {
                LogUtils.info("SERVER_NO_IDLE");
            }
        }
    }

    public void ContentMsg(byte pkgNum, List<MsgContent> msgList, List<MsgContent> list, ChannelHandlerContext ctx, byte circle, byte receiver) {
        //分别获取内容
        for (int i = 0; i < pkgNum; i++) {
            //获取包序列内容
            MsgContent reqContent = msgList.get(i);
            MsgContent respContent = new MsgContent();
            //处理数据
            this.eouHandler.doMsgHandle(ctx, reqContent, respContent, circle);
        }
    }

    public EouData nullData() {
        EouData data = new EouData();
        data.setSender((byte) 4);
        data.setReceiver((byte) 1);
        data.setCircle((byte) 0);
        data.setLength((short) 5);
        data.setPkgNum((byte) 1);
        List<MsgContent> list = new ArrayList<>();
        MsgContent content = new MsgContent();
        content.setPkgLen((short) 2);
        content.setAction((byte) 0);
        content.setContent(new byte[]{0});
        list.add(content);
        data.setMsgList(list);
        data.setCrc(EouData.creatCRC(data));
        return data;
    }
}
