package net.eoutech.vifi.ws.msg.req;

/**
 * Created by Administrator on 2017/3/30 0030.
 */
public class VwsReqSendIdentifyingCode extends VwsReqCommon {
    private String phoneNumber;
    private String action;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public VwsEnumReqType getRequestType() {
        return VwsEnumReqType.myValueOf(cmd);
    }

    @Override
    public String toString() {
        return "uid=" + uid + ", cmd=" + cmd + ", phoneNumber=" + phoneNumber + ",action=" + action;
    }
}
