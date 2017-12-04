package net.eoutech.vifi.ws.msg.req;

/**
 * Created by wei on 2017/11/28.
 */
public class VwsReqPackage extends VwsReqCommon{

    @Override
    public VwsEnumReqType getRequestType() {
        return VwsEnumReqType.myValueOf ( cmd );
    }
}
