package net.eoutech.vifi.ws.machine.ctrl;

import com.alibaba.fastjson.JSONObject;
import net.eoutech.utils.WXPayUtil;
import net.eoutech.vifi.ws.entity.TbAgent;
import net.eoutech.vifi.ws.vns.service.common.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * Created by Java on 2017/12/1.
 */
@Controller
@RequestMapping("/seller")
public class SellerLogin {

    @Autowired
    AgentService agentService;//// TODO: 2017/12/1

    @RequestMapping("/login")
    public String login(String code,String state,Model model){

        String openId = WXPayUtil.getOpenId(code);

       TbAgent tbAgent =  agentService.selectAgentByOpenId(openId);
        if(tbAgent!=null){
            return null;//// TODO: 2017/12/1
        }else {
            return "seller/register";//// TODO: 2017/12/1 到时候改成获取基础信息的code
        }
    }


    @RequestMapping("/register")
    public String register(){
        return "seller/register";
    }


    @RequestMapping("/register2")
    public String registerTow(){
        return "seller/register-2";
    }

    @RequestMapping("backPwd")
    public String backPwd(){
        return "seller/backPwd";
    }
    @RequestMapping("backPwd2")
    public String backPwd2(){
        return "seller/backPwd2";
    }

    @ResponseBody
    @RequestMapping("/newUser")
    public String newUser(String code,String PhoneNumber,String Pwd){
        JSONObject newUser = WXPayUtil.getUid(code);
        TbAgent tbAgent = new TbAgent();
        if (newUser!=null){
            String openId = newUser.getString("openid");
            String idxAgentId = PhoneNumber.substring(PhoneNumber.length()-4,PhoneNumber.length())+ openId.substring(openId.length()-4,openId.length());
            tbAgent.setIdxAgentId(idxAgentId);
            tbAgent.setUkAgentAccount(PhoneNumber);
            tbAgent.setAgentPassword( DigestUtils.md5DigestAsHex(Pwd.getBytes()));
            tbAgent.setOpenId(openId);
            tbAgent.setIsLogin(1);
            tbAgent.setAgentAuthority(9);
            tbAgent.setAgentAvatar(newUser.getString("headimgurl"));
            tbAgent.setAgentNickname(newUser.getString("nickname"));
            tbAgent.setModifyTime(new Date());
            tbAgent.setModifyBy("admin");
            tbAgent.setCreateTime(new Date());
            tbAgent.setCreateBy("admin");
        }

        agentService.insertAgent(tbAgent);
        return "";
    }

    @ResponseBody
    @RequestMapping("/updateUser")
    public String updateUser(){

        return "";
    }
}
