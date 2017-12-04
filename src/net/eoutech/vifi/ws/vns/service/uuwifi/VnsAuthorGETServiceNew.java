package net.eoutech.vifi.ws.vns.service.uuwifi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import net.eoutech.base.tcpserver.utils.OrderUtil;
import net.eoutech.base.tcpserver.utils.StaticMsg;
import net.eoutech.utils.EuStringUtil;
import net.eoutech.utils.LogUtils;

import net.eoutech.vifi.ws.entity.TbCoin;
import net.eoutech.vifi.ws.entity.TbMachine;
import net.eoutech.vifi.ws.vns.service.common.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by SUU on 2016/5/25.
 */
@Service
public class VnsAuthorGETServiceNew {
    @Autowired
    MachineService machineService;

    private static SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss.SSS");

    public synchronized int doAuthorization(ChannelHandlerContext ctx, byte[] content, byte circle) {
        LogUtils.info(format.format(new Date()) + "---正常连接");
        try {
            String sJsonMsg = "";
            for (int i = 0; i < content.length; i++) {
                sJsonMsg += (char) content[i];
            }
            JSONObject jObject = JSONObject.parseObject(sJsonMsg);
            String sMsgType = jObject.getString("msgType");
            String sVid = jObject.getString("vid");
            String sCoin = jObject.getString("coin");
            LogUtils.info("设备号：" + sVid + "-设备类型：" + sMsgType + "-投币数：" + sCoin);
            if (sVid != null && !"".equals(sVid)) {
                Map<String, Object> mResponse = new ConcurrentHashMap<>();
                int iCode = 201;
                TbMachine machine = machineService.selectMachineByVid(sVid);
                if (machine != null) {
                    Integer iCoin = -1;
                    if (sCoin != null && !"".equals(sCoin)) {
                        iCoin = Integer.parseInt(sCoin);
                    }
                    iCode = 200;
                    if (sMsgType != null && !"".equals(sMsgType)) {
                        machineService.updateMachineInfo(sMsgType, sVid);
                    }
                    //更新设备使用的次数
                    TbCoin coin = machineService.selectCoinRecord(sVid);

                    if (coin == null) {//没记录，只插入
                        machineService.insertCoinInfo(sVid, iCoin, -1);
                    } else {
                        if (coin.getMachineCoinNumber() > iCoin) {//有记录，并且现在的计数小于已存在记录的值，需要更新已存在记录的状态
                            LogUtils.info("此次硬币个数比已存在的少");
                            machineService.insertCoinInfo(sVid, iCoin, coin.getUkKeyId());
                        } else if (iCoin > coin.getMachineCoinNumber()) {//有记录，只插入
                            LogUtils.info("此次硬币个数比已存在的多");
                            machineService.insertCoinInfo(sVid, iCoin, -1);
                        } else {
                            LogUtils.info("硬币个数相同，此时间内没有投币");
                        }
                    }
                }
                mResponse.put("code", iCode);

                StaticMsg.getVid2Pipe().put(sVid, ctx.channel());//设备号和通道的映射
                StaticMsg.getPipe2Vid().put(ctx.channel(), sVid);//映射和设备号的映射

                String sCode = JSON.toJSONString(mResponse);
                byte[] bMsg = sCode.getBytes();
                ctx.channel().writeAndFlush(OrderUtil.ResponseCode(circle, bMsg));
            }
        } catch (Exception e) {
            e.printStackTrace();
            myInfo("VnsAuthorGETServiceNew get throw exception:" + EuStringUtil.myExceptionString(e));
        }
        return 1;
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
