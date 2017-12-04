package net.eoutech.vifi.ws.msg.resp;

/**
 * Created by wei on 2017/11/28.
 */
public class VwsRespStart extends VwsRespCommon{
    private String orderID;

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder().append("{");
        sb.append( toIntJS( "code", code, "," ) ).append(toStrJS( "msg", msg, "," )).append(toStrJS( "orderID", orderID, "}" ));
        return sb.toString();
    }

}
