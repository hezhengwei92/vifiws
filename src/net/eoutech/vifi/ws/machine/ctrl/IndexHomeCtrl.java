package net.eoutech.vifi.ws.machine.ctrl;

import com.alibaba.fastjson.JSON;
import net.eoutech.utils.ViFiStaticParam;
import net.eoutech.vifi.ws.dao.TbUserDao;
import net.eoutech.vifi.ws.entity.TbAgent;
import net.eoutech.vifi.ws.entity.TbUser;
import net.eoutech.vifi.ws.machine.msg.req.IndexHomeReq;
import net.eoutech.vifi.ws.machine.msg.resp.IndexHomeResp;
import net.eoutech.vifi.ws.machine.service.IndexHomeService;
import net.eoutech.vifi.ws.msg.req.*;
import net.eoutech.vifi.ws.msg.resp.*;
import net.eoutech.vifi.ws.vns.service.*;
import net.eoutech.vifi.ws.vns.service.common.AgentService;
import net.eoutech.vifi.ws.vns.service.common.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * Created by wei on 2017/11/27.
 */
@Controller
public class IndexHomeCtrl {
    @Autowired
    IndexHomeService indexHomeService;
    @Autowired
    VwsAppApiConsumeRecordService consumeRecordService;
    @Autowired
    VwsAppApiPackageService packageService;
    @Autowired
    VwsAppApiStartService startService;
    @Autowired
    VwsAppApiMachineService machineService;
    @Autowired
    VwsAppApiOrderVerfyService orderVerfyService;
    @Autowired
    VwAppApiSendIdentifyingCodeService sendIdentifyingCodeService;
    @Autowired
    AgentService agentService;
    @Autowired
    VwsAppApiBindingService bindingService;

//    @Autowired
//    VwAppApiMachineService appApiMachineService;
    @RequestMapping(value = "/json", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getIndexJson(HttpServletRequest request){

        Map<String,String[]> reqStr = request.getParameterMap();
        String reqJson = JSON.toJSONString(reqStr);
        reqJson = reqJson.replace("[","");
        reqJson = reqJson.replace("]","");
//        IndexHomeReq req= JSON.parseObject(reqJson,IndexHomeReq.class);
//        TbUser u=indexHomeService.queryUser(req.getUid());
//        System.out.println("-=-=-=-=-=-=  "+u.getUkUserId());
//        IndexHomeResp resp = new IndexHomeResp();
//        resp.setUid("aaaaa");
//        resp.setName("tom");
//        resp.setAge("20");
//        System.out.println("     "+resp.toString());

//        VwsReqConsumerRecord req=JSON.parseObject(reqJson,VwsReqConsumerRecord.class);
//        VwsRespConsumeRecord resp=new VwsRespConsumeRecord();
//        consumeRecordService.doHandle(req,resp);
//        appApiMachineService.handle();

//        VwsReqPackage req=JSON.parseObject(reqJson,VwsReqPackage.class);
//        VwsRespPackage resp=new VwsRespPackage();
//        packageService.doHandle(req,resp);

//        VwsReqStart req=JSON.parseObject(reqJson,VwsReqStart.class);
//        VwsRespStart resp=new VwsRespStart();
//        startService.doHandle(req,resp);

//        VwsReqOrderVerfy req=JSON.parseObject(reqJson,VwsReqOrderVerfy.class);
//        VwsRespOrderVerfy resp=new VwsRespOrderVerfy();
//        orderVerfyService.doHandle(req,resp);

//        VwsReqMachine req=JSON.parseObject(reqJson,VwsReqMachine.class);
//        VwsRespMachine resp=new VwsRespMachine();
//        machineService.doHandle(req,resp);
//        System.out.println(resp.toString());
//

//        VwsReqSendIdentifyingCode req=JSON.parseObject(reqJson,VwsReqSendIdentifyingCode.class);
//        VwsRespCommon resp=new VwsRespCommon();
//        sendIdentifyingCodeService.doHandle(req,resp);
//        TbAgent agent=new TbAgent();
//        agent.setAgentAuthority(1);
//        agent.setAgentAvatar("ddd");
//        agent.setAgentNickname("ddd");
//        agent.setAgentPassword("dd");
//        agent.setIdxAgentId("2");
//        agent.setIsLogin(2);
//        agent.setCreateTime(new Date());
//        agent.setCreateBy("aa");
//        agent.setModifyBy("a");
//        agent.setOpenId("aa");
//        agent.setUkAgentAccount("15659105067");
//        agent.setModifyTime(new Date());
//        agentService.insertAgent(agent);


        VwsReqBinding req=JSON.parseObject(reqJson,VwsReqBinding.class);
        VwsRespBinding resp=new VwsRespBinding();
        bindingService.doHandle(req,resp);

        return resp.toString();
    }
}
