package net.eoutech.vifi.ws.entity;

import net.eoutech.vifi.ws.machine.service.IndexHomeService;

import java.util.Date;

/**
 * Created by wei on 2017/11/27.
 */
public class TbCoin {
    private Integer ukKeyId;
    private String idxMachineId;
    private Integer machineCoinNumber;
    private String machineCoinStatus;
    private Date createTime;

    public Integer getUkKeyId() {
        return ukKeyId;
    }

    public void setUkKeyId(Integer ukKeyId) {
        this.ukKeyId = ukKeyId;
    }

    public String getIdxMachineId() {
        return idxMachineId;
    }

    public void setIdxMachineId(String idxMachineId) {
        this.idxMachineId = idxMachineId;
    }

    public Integer getMachineCoinNumber() {
        return machineCoinNumber;
    }

    public void setMachineCoinNumber(Integer machineCoinNumber) {
        this.machineCoinNumber = machineCoinNumber;
    }

    public String getMachineCoinStatus() {
        return machineCoinStatus;
    }

    public void setMachineCoinStatus(String machineCoinStatus) {
        this.machineCoinStatus = machineCoinStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
