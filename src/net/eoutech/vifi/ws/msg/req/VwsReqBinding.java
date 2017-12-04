package net.eoutech.vifi.ws.msg.req;

/**
 * Created by wei on 2017/12/2.
 */
public class VwsReqBinding extends VwsReqCommon{
    private String machineID;//设备ID
    private Integer machineNumber;//设备顺序编号
    private String openId;//商户openId
    private String agentName;//商户名
    private String agentPosition;//商户地址
    private Integer machineCoin;//（币/次）
    private String machinePosition;//设备投放地点


    public String getMachineID() {
        return machineID;
    }

    public void setMachineID(String machineID) {
        this.machineID = machineID;
    }


    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getMachineNumber() {
        return machineNumber;
    }

    public void setMachineNumber(Integer machineNumber) {
        this.machineNumber = machineNumber;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAgentPosition() {
        return agentPosition;
    }

    public void setAgentPosition(String agentPosition) {
        this.agentPosition = agentPosition;
    }

    public Integer getMachineCoin() {
        return machineCoin;
    }

    public void setMachineCoin(Integer machineCoin) {
        this.machineCoin = machineCoin;
    }

    public String getMachinePosition() {
        return machinePosition;
    }

    public void setMachinePosition(String machinePosition) {
        this.machinePosition = machinePosition;
    }

    @Override
    public VwsEnumReqType getRequestType() {
        return VwsEnumReqType.myValueOf ( cmd );
    }
}
