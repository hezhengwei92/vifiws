package net.eoutech.vifi.ws.msg.req;

/**
 * Created by wei on 2017/11/30.
 */
public class VwsReqFeedback extends VwsReqCommon{
    private String machineID;
    private String title;
    private String content;
    private String phoneNumber;

    public String getMachineID() {
        return machineID;
    }

    public void setMachineID(String machineID) {
        this.machineID = machineID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public VwsEnumReqType getRequestType() {
        return VwsEnumReqType.myValueOf ( cmd );
    }

}
