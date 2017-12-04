package net.eoutech.vifi.ws.msg.req;

/**
 * Created by wei on 2017/11/29.
 */

public class VwsReqMachine extends VwsReqCommon{
    private String machineID;

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
