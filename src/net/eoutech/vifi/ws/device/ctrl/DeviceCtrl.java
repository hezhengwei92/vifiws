package net.eoutech.vifi.ws.device.ctrl;

import net.eoutech.utils.LogUtils;
import net.eoutech.vifi.ws.entity.TbDictionary;
import net.eoutech.vifi.ws.vns.service.common.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by WangY on 2017/4/18 0018.
 */
@Controller
public class DeviceCtrl {
    @Autowired
    private DictionaryService dictionaryService;

    @RequestMapping(value = "/device/wwj", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String queryServer(HttpServletRequest request) throws Exception {

        String sVid = request.getParameter("devid");
        String ip = "";
        Integer port = 0;
        TbDictionary dictionary = dictionaryService.selectDictionaryByKey("IP_PORT");
        if (dictionary != null) {
            String sIpPort = dictionary.getKeyValue();
            ip = sIpPort.substring(0, sIpPort.indexOf(":"));
            port = Integer.parseInt(sIpPort.substring(sIpPort.indexOf(":") + 1, sIpPort.length()));
        }
        LogUtils.info("vid:" + sVid + "--ip:" + ip + "--port:" + port);
        return resp2String(port, ip);
    }

    public String resp2String(int port, String ip) {
        StringBuilder sb = new StringBuilder().append("{");
        sb.append(toStrJS("ip", ip, ",")).append(toIntJS("port", port, "}"));
        LogUtils.info("返回的IP和端口：" + sb.toString());
        return sb.toString();
    }

    protected StringBuilder toStrJS(String name, Object value, String ending) {
        if (value == null) {
            value = "";
        }
        return new StringBuilder("\"").append(name).append("\":\"").append(value).append("\"").append(ending);
    }

    protected StringBuilder toIntJS(String name, Object value, String ending) {
        if (value == null) {
            value = "0";
        }
        return new StringBuilder("\"").append(name).append("\":").append(value).append(ending);
    }
////    @RequestMapping(value = "/upload/avatar", method = {RequestMethod.POST, RequestMethod.GET}, produces = "text/json;charset=UTF-8")
////    @ResponseBody
////    public String upload(InputStream pic, FormDataContentDisposition picDisposition,HttpServletRequest request ,ServletContext servletContext) throws IOException {
////        log.info("app upload begin and pic size is " + picDisposition.getSize());
////        JSONResult result = null;
////        if (picDisposition.getSize() > 20971520) {//20M
////            result = new JSONResult(ResultCode.fail, "文件不能大于20M！");
////            log.info("app upload fail");
////        } else {
////            String account = (String) request.getAttribute(AppUtil.reqAttrAccountName);
////            User user = new User();
////            user.setAccount(account);
////            User existsUser = userService.findSingleUserByAcc(user);
////            String filepath = servletContext.getRealPath("/resources/upload");
////            String fileName = account + ".PNG";
////            byte[] upload = IOUtils.toByteArray(pic);
////
////            File file = new File(filepath);
////            if (!file.exists()) {//create resources image folder
////                file.mkdirs();
////            }
////            File picFile = new File(file, fileName);
////            if (picFile.exists()) {//delete exists file
////                picFile.delete();
////            }
////            FileUtils.writeByteArrayToFile(picFile, upload);
////            existsUser.setFilePath(fileName);
////
////            userService.updateUser(existsUser);
////            existsUser = userService.findSingleUserByAcc(existsUser);
////
////            result = new JSONResult(ResultCode.success, "suc");
////            result.appendExtra("user", existsUser);
////
////            log.info("app use upload success");
////        }
////        return result.toString();
////    }

}
