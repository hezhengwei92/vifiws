package net.eoutech.vifi.ws.machine.ctrl;

import com.alibaba.fastjson.JSON;
import net.eoutech.utils.EuStringUtil;
import net.eoutech.vifi.ws.msg.req.VwsReqOrderVerfy;
import net.eoutech.vifi.ws.msg.req.VwsReqStart;
import net.eoutech.vifi.ws.msg.resp.VwsRespOrderVerfy;
import net.eoutech.vifi.ws.msg.resp.VwsRespStart;
import net.eoutech.vifi.ws.vns.service.VwsAppApiOrderVerfyService;
import net.eoutech.vifi.ws.vns.service.VwsAppApiStartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by wei on 2017/11/28.
 */
@Controller
public class StartCtrl {
    @Autowired
    VwsAppApiStartService startService;
    @Autowired
    VwsAppApiOrderVerfyService orderVerfyService;

    /**
     * 投币启动机器
     * 请求参数：
     * costCurrency：花费的币数
     * machineID：设备ID
     * 返回参数： orderID 此次交易编号
     * @param request
     * @return
     */
    @RequestMapping(value = "/start", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getStart(HttpServletRequest request){

        Map<String,String[]> reqStr = request.getParameterMap();
        String reqJson = EuStringUtil.createJson(reqStr);

        VwsReqStart req=JSON.parseObject(reqJson,VwsReqStart.class);
        VwsRespStart resp=new VwsRespStart();
        startService.doHandle(req,resp);
        return resp.toString();
    }

    /** 投币后判断机器是否启动
     * 请求参数：orderID 此次交易编号
     * 返回参数：isOK   0 失败   1 成功
     * @param request
     * @return
     */
    @RequestMapping(value = "/orderVerify", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getOrderVerify(HttpServletRequest request){

        Map<String,String[]> reqStr = request.getParameterMap();
        String reqJson = EuStringUtil.createJson(reqStr);

        VwsReqOrderVerfy req=JSON.parseObject(reqJson,VwsReqOrderVerfy.class);
        VwsRespOrderVerfy resp=new VwsRespOrderVerfy();
        orderVerfyService.doHandle(req,resp);
        orderVerfyService.doHandle(req,resp);
        return resp.toString();
    }
}
