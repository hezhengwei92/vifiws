package net.eoutech.vifi.ws.machine.ctrl;

import com.alibaba.fastjson.JSONObject;
import net.eoutech.utils.EuStringUtil;
import net.eoutech.utils.WXPayUtil;
import net.eoutech.vifi.ws.entity.TbUser;
import net.eoutech.vifi.ws.vns.service.common.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by Java on 2017/11/29.
 */

@Controller
public class LoginCtrl {
    @Autowired
    UserService userService;

    @RequestMapping("/login")
    public String userLogin(HttpServletRequest res){
        String code = res.getParameter("code");
        String productId = res.getParameter("state");
        String openid = WXPayUtil.getOpenId(code);
        String uid = "";
        if(openid!=null){
            uid = "tom";
            System.out.println("我通过openid得到了uid");
        }else {
            return "redirect:https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxce707c694c026d58&redirect_uri=http%3a%2f%2fwww.hzhltec.com%2fvrsws%2f&response_type=code&scope=snsapi_userinfo&state=productId#wechat_redirect";
        }
        //return "redirect:https://www.hzhltec.com/vrsws/index?uid="+uid;
        return "redirect: index?uid="+uid+"&productId="+productId;
    }

    @RequestMapping("/register")
    public String userRegister(HttpServletRequest res){
        String code = res.getParameter("code");
        String productId = res.getParameter("state");
        JSONObject userBaseInfo = WXPayUtil.getUid(code);
        String uid = "";
        TbUser tbUser = new TbUser();
        if(userBaseInfo!=null){

            tbUser.setUkUserId(EuStringUtil.createUID());
            tbUser.setThirdOpenId((String) userBaseInfo.get("openid"));
            tbUser.setThirdUserId((String) userBaseInfo.get("unionid"));
            tbUser.setUserAvatar((String) userBaseInfo.get("headimgurl"));
            tbUser.setUserNickname((String) userBaseInfo.get("nickname"));
            tbUser.setUserNickname("0");
            tbUser.setUserLevelId(0);
            tbUser.setUserLevelName("普通用户");
            tbUser.setUserLevelPoint(0);
            tbUser.setUserType("WX");
            tbUser.setUserStatus(0);
            tbUser.setUserCurrency(0);
            tbUser.setOnlineStatus(0);
            tbUser.setOnlineTime(new Date());
            tbUser.setCreateTime(new Date());

            int count = userService.insertUser(tbUser);
            if(count==1){
                uid = "tom";
                return "redirect: index?uid="+uid+"&productId="+productId;
            }else if (count<1){
                System.out.println("tbuser插入出错");
                return "404";
            }
        }
        return "404";
    }


}
