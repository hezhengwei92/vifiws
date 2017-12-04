package net.eoutech.vifi.ws.msg.req;

/**
 * Created by Administrator on 2017/3/30 0030.
 *
 */
public class VwsReqVerificationIdentifyingCode extends VwsReqCommon {
    private String phoneNumber;
    private String identifyingCode;

    public String getIdentifyingCode() {
        return identifyingCode;
    }

    public void setIdentifyingCode(String identifyingCode) {
        this.identifyingCode = identifyingCode;
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
        return "cmd=" + cmd + ", phoneNumber=" + phoneNumber + ",identifyingCode=" + identifyingCode;
    }
}
