package net.eoutech.vifi.ws.msg.resp;

/**
 * Created by wei on 2017/11/30.
 */
public class VwsRespShow extends VwsRespCommon{
    private String machineType;//设备类型
    private String machineID;//设备ID
    private Integer machineNumber;//设备顺序编号
    private Integer machineStatus;//设备状态
    private String machineAgentId;//商户名
    private String machinePosition;//地址
    private String qrCode;//下载二维码URL

    public String getMachineType() {
        return machineType;
    }

    public void setMachineType(String machineType) {
        this.machineType = machineType;
    }

    public String getMachineID() {
        return machineID;
    }

    public void setMachineID(String machineID) {
        this.machineID = machineID;
    }

    public Integer getMachineNumber() {
        return machineNumber;
    }

    public void setMachineNumber(Integer machineNumber) {
        this.machineNumber = machineNumber;
    }

    public Integer getMachineStatus() {
        return machineStatus;
    }

    public void setMachineStatus(Integer machineStatus) {
        this.machineStatus = machineStatus;
    }

    public String getMachineAgentId() {
        return machineAgentId;
    }

    public void setMachineAgentId(String machineAgentId) {
        this.machineAgentId = machineAgentId;
    }

    public String getMachinePosition() {
        return machinePosition;
    }

    public void setMachinePosition(String machinePosition) {
        this.machinePosition = machinePosition;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder().append("{");
        sb.append( toIntJS( "code", code, "," ) ).append(toStrJS( "msg", msg, "," )).append(toStrJS( "machineType", machineType, "," ))
                .append(toStrJS( "machineID", machineID, "," )).append(toIntJS( "machineNumber", machineNumber, "," ))
                .append(toIntJS( "machineStatus", machineStatus, "," )).append(toStrJS( "machineAgentId", machineAgentId, "," ))
                .append(toStrJS( "machinePosition", machinePosition, "," )).append(toStrJS( "qrCode", qrCode, "}" ));
        return sb.toString();
    }

}
