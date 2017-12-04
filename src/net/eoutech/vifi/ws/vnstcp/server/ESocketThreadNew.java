package net.eoutech.vifi.ws.vnstcp.server;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import net.eoutech.base.tcpserver.EouHandler;
import net.eoutech.base.tcpserver.EouResp;
import net.eoutech.base.tcpserver.entity.MsgContent;
import net.eoutech.base.tcpserver.utils.StaticMsg;
import net.eoutech.utils.DataProcess;
import net.eoutech.utils.EuStringUtil;
import net.eoutech.utils.LogUtils;
import net.eoutech.vifi.ws.vns.service.uuwifi.VnsAuthorGETServiceNew;
import net.eoutech.vifi.ws.vns.service.uuwifi.VnsLocationService;
import net.eoutech.vifi.ws.vns.service.uuwifi.VnsPulseService;
import net.eoutech.vifi.ws.vns.service.uuwifi.VnsResultService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class ESocketThreadNew implements EouHandler {

    private ApplicationContext ac;
    private static VnsAuthorGETServiceNew service;
    private static VnsLocationService locationService;
    private static VnsPulseService vnsPulseService;
    private static VnsResultService resultService;
//    private static VnsSearchFlowService searchService;
//    private static VnsRestartCardService restartCardService;
//    private static VnsReLinkService reLinkService;
//    private static VnsChangePwdService changePwdService;
//    private static VnsWhiteListService whiteListService;
//    private static VnsOut2GService out2GService;
//    private static VnsOutLinkService outLinkService;
//    private static VnsUSIMCardService usimCardService;
//    private static Vns4GLinkService link4GService;

    public ESocketThreadNew() {
        ac = new FileSystemXmlApplicationContext("classpath*:applicationContext.xml");
        if (ESocketThreadNew.service == null) {
            ESocketThreadNew.service = ac.getBean(VnsAuthorGETServiceNew.class);
        }
        if (ESocketThreadNew.resultService == null) {
            ESocketThreadNew.resultService = ac.getBean(VnsResultService.class);
        }
        if (ESocketThreadNew.vnsPulseService == null) {
            ESocketThreadNew.vnsPulseService = ac.getBean(VnsPulseService.class);
        }
        if (ESocketThreadNew.locationService == null) {
            ESocketThreadNew.locationService = ac.getBean(VnsLocationService.class);
        }
    }

    @Override
    public void doMsgHandle(ChannelHandlerContext ctx, MsgContent reqContent, MsgContent respContent, byte circle) {
        // 取消息类型
        try {
            int choose = reqContent.getAction() & 0xFF;
            LogUtils.info("ACTION:"+choose+"--We need process the data");
//            FlumeRpcClientUtils.append("ACTION:"+choose+"--We need process the data");
            switch (choose) {
                case 0x01://该内容是设备验证信息
                    service.doAuthorization(ctx, reqContent.getContent(), circle);
                    break;
                case 0x10://该内容是设备发送给服务端的应答结果
                    resultService.doAuthorization(ctx,reqContent.getContent(),circle);
                    break;
                case 0x03://该内容设备请求服务端获取机器的脉冲信息
                    vnsPulseService.doAuthorization(ctx,circle);
                    break;
                case 0x04://改内容是设备获取的小区信息
                    locationService.doAuthorization(ctx,reqContent.getContent(),circle);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.error("ESocketThreadNew thread throw exception:" + EuStringUtil.myExceptionString(e));
        }

//        LogUtils.info("send bytes:" + resp.toString());
    }

}
