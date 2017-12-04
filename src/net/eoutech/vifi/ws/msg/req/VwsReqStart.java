package net.eoutech.vifi.ws.msg.req;

/**
 * Created by wei on 2017/11/28.
 */
public class VwsReqStart extends VwsReqCommon{
    private Integer costCurrency;
    private String machineID;

    public Integer getCostCurrency() {
        return costCurrency;
    }

    public void setCostCurrency(Integer costCurrency) {
        this.costCurrency = costCurrency;
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
