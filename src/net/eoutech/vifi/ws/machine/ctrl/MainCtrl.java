package net.eoutech.vifi.ws.machine.ctrl;

import com.alibaba.fastjson.JSON;
import net.eoutech.vifi.ws.entity.*;
import net.eoutech.vifi.ws.vns.service.common.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Java on 2017/11/27.
 */
@Controller
public class MainCtrl {
    @Autowired
    UserService userService;
    @Autowired
    ConsumeRecordService consumeRecordService;
    @Autowired
    UserRecordService userRecordService;
    @Autowired
    PackageService packageService;
    @Autowired
    MachineService machineService;

    @RequestMapping("/index")

    public String index(HttpServletRequest res, HttpServletResponse resp, Model model ,String uid,String productId) {
      //  String uid =res.getParameter("uid");
        TbUser tbUser = userService.selectUserByUserId(uid);

        TbMachine tbMachine = machineService.selectMachineByVid(productId);
        if(tbMachine!=null){
            List<TbMachine> tbMachines = machineService.selectMachineByPositionCode(tbMachine.getMachinePositionCode());
            model.addAttribute("tbMachines",tbMachines);
            model.addAttribute("machinesSize",tbMachines.size());
        }else {
            model.addAttribute("tbMachines",null);
        }

        /**
         * 获取所有的重置套餐
         */
        List<TbPackage> tbPackages = packageService.selectAll();
        model.addAttribute("coin",tbUser.getUserCurrency());
        model.addAttribute("tbPackages",tbPackages);
        return "index";
    }

    @RequestMapping("/person")
    public String person(HttpServletRequest res, HttpServletResponse resp, Model model) {
        String uid =res.getParameter("uid");
        TbUser tbUser = userService.selectUserByUserId(uid);
        List<TbUserRecord> tbUserRecords = userRecordService.selectRecordById(uid,0,6);
        List<TbConsumeRecord> tbConsumeRecords = consumeRecordService.selectById(uid,0,6);
        model.addAttribute("tbUserRecords",tbUserRecords);
        model.addAttribute("tbConsumeRecords",tbConsumeRecords);
        model.addAttribute("tbUser",tbUser);
        return "person";
    }

    /**
     * 根据uid以及page获取对应的消费记录
     * @param res
     * @return
     */
    @RequestMapping("/getMoreReocrd")
    @ResponseBody
    public String getMore(HttpServletRequest res){
        String uid =res.getParameter("uid");
        String page =res.getParameter("page");
        List<TbConsumeRecord> tbConsumeRecords = null;
        Map<String,String> map = new HashMap<String,String>();
        if(null!=uid&&page!=null){
             tbConsumeRecords = consumeRecordService.selectById(uid,Integer.parseInt(page),6);
            if(null!=tbConsumeRecords){
                String sData = JSON.toJSONString(tbConsumeRecords);
                return sData;
            }else {
                map.put("code","400");
                map.put("msg","参数为空");
            }
        }else {
            map.put("code","500");
            map.put("msg","参数异常");
        }
        String sData2 = JSON.toJSONString(map);
        return sData2;
    }

    /**
     * 根据uid 以及page获取之后的消费记录
     * @param res
     * @return
     */
    @RequestMapping("/getMoreConsume")
    @ResponseBody
    public String getMoreConsume(HttpServletRequest res){
        String uid =res.getParameter("uid");
        String page =res.getParameter("page");
        List<TbUserRecord> tbUserRecords = null;
        Map<String,String> map = new HashMap<String,String>();
        if(uid!=null&&page!=null){
            tbUserRecords = userRecordService.selectRecordById(uid,Integer.parseInt(page),6);
            if (null != tbUserRecords){
                String sData = JSON.toJSONString(tbUserRecords);
                return sData;
            }else {
                map.put("code","400");
                map.put("msg","参数为空");
            }
        }else {
            map.put("code","500");
            map.put("msg","参数异常");
        }
        String sData2 = JSON.toJSONString(map);
        return sData2;
    }
}
