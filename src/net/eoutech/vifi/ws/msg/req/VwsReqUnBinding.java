package net.eoutech.vifi.ws.msg.req;

/**
 * Created by wei on 2017/12/2.
 */
public class VwsReqUnBinding extends VwsReqCommon{
    private String openId;
    private String machineID;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getMachineID() {
        return machineID;
    }

    public void setMachineID(String machineID) {
        this.machineID = machineID;
    }

    @Override
    public VwsEnumReqType getRequestType() {
        return VwsEnumReqType.myValueOf ( cmd );
    }
}
