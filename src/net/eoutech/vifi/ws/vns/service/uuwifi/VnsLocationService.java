package net.eoutech.vifi.ws.vns.service.uuwifi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import net.eoutech.base.tcpserver.EouResp;
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
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by SUU on 2016/5/25.
 */
@Service
public class VnsLocationService {
    @Autowired
    MachineService machineService;

    private static SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss.SSS");

    public synchronized int doAuthorization(ChannelHandlerContext ctx, byte[] content, byte circle) {
        LogUtils.info(format.format(new Date()) + "---获取小区信息");
        try {
            String sJsonMsg = "";
            for (int i = 0; i < content.length; i++) {
                sJsonMsg += (char) content[i];
            }
            LogUtils.info("小区信息：" + sJsonMsg);
            Map<String, Object> mResponse = new ConcurrentHashMap<>();
            mResponse.put("code", 200);

            String sCode = JSON.toJSONString(mResponse);
            byte[] bMsg = sCode.getBytes();
            ctx.channel().writeAndFlush(OrderUtil.ResponseCode(circle, bMsg));
        } catch (Exception e) {
            e.printStackTrace();
            myInfo("VnsLocationService get throw exception:" + EuStringUtil.myExceptionString(e));
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
