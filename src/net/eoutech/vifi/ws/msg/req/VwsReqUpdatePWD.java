package net.eoutech.vifi.ws.msg.req;

/**
 * Created by wei on 2017/12/2.
 */
public class VwsReqUpdatePWD extends VwsReqCommon{
    private String openId;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    @Override
    public VwsEnumReqType getRequestType() {
        return VwsEnumReqType.myValueOf ( cmd );
    }
}
