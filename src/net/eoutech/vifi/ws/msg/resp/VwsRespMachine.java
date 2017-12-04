package net.eoutech.vifi.ws.msg.resp;

import net.eoutech.vifi.ws.entity.TbMachine;

import java.util.List;
import java.util.Map;

/**
 * Created by wei on 2017/11/29.
 */
public class VwsRespMachine extends VwsRespCommon{

    private Integer nowMachine;//当前设备
    private Integer sumMachine;//此区域总设备
    private String data;//此区域所有设备

    public Integer getNowMachine() {
        return nowMachine;
    }

    public void setNowMachine(Integer nowMachine) {
        this.nowMachine = nowMachine;
    }

    public Integer getSumMachine() {
        return sumMachine;
    }

    public void setSumMachine(Integer sumMachine) {
        this.sumMachine = sumMachine;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder().append("{");
        sb.append( toIntJS( "code", code, "," ) ).append(toStrJS( "msg", msg, "," ))
                .append(toIntJS( "nowMachine", nowMachine, "," )).append(toIntJS( "sumMachine", sumMachine, "," )).append(toIntJS( "data", data, "}" ));
        return sb.toString();
    }
}
