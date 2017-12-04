package net.eoutech.vifi.ws.machine.ctrl;

import com.alibaba.fastjson.JSON;
import net.eoutech.utils.EuStringUtil;
import net.eoutech.vifi.ws.msg.req.*;
import net.eoutech.vifi.ws.msg.resp.VwsRespBinding;
import net.eoutech.vifi.ws.msg.resp.VwsRespCommon;
import net.eoutech.vifi.ws.msg.resp.VwsRespUnBinding;
import net.eoutech.vifi.ws.vns.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by wei on 2017/12/2.
 */
@Controller
public class AgentCtrl {
    @Autowired
    VwAppApiSendIdentifyingCodeService sendIdentifyingCodeService;
    @Autowired
    VwAppApiVerificationIdentifyingCodeService vwAppApiVerificationIdentifyingCodeService;
    @Autowired
    VwsAppApiBindingService bindingService;
    @Autowired
    VwsAppApiUnBindingService unBindingService;
    @Autowired
    VwsAppApiUpdatePWDService updatePWDService;
    /**
     * 发送验证码
     * 请求参数
     * phoneNumber：手机号
     * aciton:区分注册 找回密码 等
     * @param request
     * @return
     */
    @RequestMapping(value = "/sendCode", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getSendCode(HttpServletRequest request){

        Map<String,String[]> reqStr = request.getParameterMap();
        String reqJson = EuStringUtil.createJson(reqStr);

        VwsReqSendIdentifyingCode req= JSON.parseObject(reqJson,VwsReqSendIdentifyingCode.class);
        VwsRespCommon resp=new VwsRespCommon();
        sendIdentifyingCodeService.doHandle(req,resp);

        return resp.toString();
    }

    /**
     * 验证验证码
     * 请求参数：
     * phoneNumber：手机号
     * identifyingCode：验证码
     * @param request
     * @return
     */
    @RequestMapping(value = "/verCode", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getVerCode(HttpServletRequest request){

        Map<String,String[]> reqStr = request.getParameterMap();
        String reqJson = EuStringUtil.createJson(reqStr);

        VwsReqVerificationIdentifyingCode req= JSON.parseObject(reqJson,VwsReqVerificationIdentifyingCode.class);
        VwsRespCommon resp=new VwsRespCommon();
        vwAppApiVerificationIdentifyingCodeService.doHandle(req,resp);

        return resp.toString();
    }

    /**
     * 商户绑定设备
     * 请求参数：需要关联的数据  machineID  machineNumber  openId   agentPosition  machineCoin machinePosition
     * 还需商户名 商户地址
     * @param request
     * @return
     */

    @RequestMapping(value = "/binding", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getBinding(HttpServletRequest request){

        Map<String,String[]> reqStr = request.getParameterMap();
        String reqJson = EuStringUtil.createJson(reqStr);

        VwsReqBinding req= JSON.parseObject(reqJson,VwsReqBinding.class);
        VwsRespBinding resp=new VwsRespBinding();
        bindingService.doHandle(req,resp);

        return resp.toString();
    }

    /**
     * 商户解除绑定
     * 请求参数
     * @param request
     * @return
     */
    @RequestMapping(value = "/unBinding", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getUnBinding(HttpServletRequest request){

        Map<String,String[]> reqStr = request.getParameterMap();
        String reqJson = EuStringUtil.createJson(reqStr);

        VwsReqUnBinding req= JSON.parseObject(reqJson,VwsReqUnBinding.class);
        VwsRespUnBinding resp=new VwsRespUnBinding();
        unBindingService.doHandle(req,resp);

        return resp.toString();
    }

    /**
     * 商户更新密码
     */
    @RequestMapping(value = "/pwd", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getUpdatePWD(HttpServletRequest request){

        Map<String,String[]> reqStr = request.getParameterMap();
        String reqJson = EuStringUtil.createJson(reqStr);

        VwsReqUpdatePWD req= JSON.parseObject(reqJson,VwsReqUpdatePWD.class);
        VwsRespCommon resp=new VwsRespCommon();
        updatePWDService.doHandle(req,resp);

        return resp.toString();
    }

}
