package net.eoutech.base.tcpserver.entity;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by WangY on 2017/3/8 0008.
 * 需要转化为字符串的内容
 *
 */
public class EouInfo extends EouMsg{

    private String iccid;//设备内卡的唯一编号值
    private String ssid;//该设备分配到的Session对应的编号值
    private String ver;//固件版本号
    private String vid;//设备的唯一编号值

    public EouInfo() {
        super();
    }

    public EouInfo(byte[] content) {
        String str = "";
        for (int i = 0; i < content.length; i ++){
            str += (char)content[i];
        }
        System.out.println("字符串是："+str);
        EouInfo info = JSONObject.parseObject(str,EouInfo.class);
        iccid = info.getIccid();
        vid = info.getVid();
        ssid = info.getSsid();
        ver = info.getVer();
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

}
