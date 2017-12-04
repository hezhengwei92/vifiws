package net.eoutech.vifi.ws.msg.req;

/**
 * Created by wei on 2017/11/28.
 */
public class VwsReqOrderVerfy extends VwsReqCommon{
    private String orderID;

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    @Override
    public VwsEnumReqType getRequestType() {
        return VwsEnumReqType.myValueOf ( cmd );
    }
}
