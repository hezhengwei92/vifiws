package net.eoutech.vifi.ws.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by wei on 2017/11/24.
 */
public class TbConsumeRecord {
    private Integer ukKeyId;
    private String ukOrderId    ;
    private String idxUserId;
    private String idxMachineId;
    private Integer machineNumber;
    private String idxAgentId;
    private String machinePosition;
    private String positionId;
    private String userType;
    private Integer costCurrency;
    private Integer costStatus;//此次投币是否成功，0：失败 1：成功
//    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private String createBy;

    public Integer getUkKeyId() {
        return ukKeyId;
    }

    public void setUkKeyId(Integer ukKeyId) {
        this.ukKeyId = ukKeyId;
    }

    public String getUkOrderId() {
        return ukOrderId;
    }

    public void setUkOrderId(String ukOrderId) {
        this.ukOrderId = ukOrderId;
    }

    public String getIdxUserId() {
        return idxUserId;
    }

    public void setIdxUserId(String idxUserId) {
        this.idxUserId = idxUserId;
    }

    public String getIdxMachineId() {
        return idxMachineId;
    }

    public void setIdxMachineId(String idxMachineId) {
        this.idxMachineId = idxMachineId;
    }

    public Integer getMachineNumber() {
        return machineNumber;
    }

    public void setMachineNumber(Integer machineNumber) {
        this.machineNumber = machineNumber;
    }

    public String getIdxAgentId() {
        return idxAgentId;
    }

    public void setIdxAgentId(String idxAgentId) {
        this.idxAgentId = idxAgentId;
    }

    public String getMachinePosition() {
        return machinePosition;
    }

    public void setMachinePosition(String machinePosition) {
        this.machinePosition = machinePosition;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public Integer getCostCurrency() {
        return costCurrency;
    }

    public void setCostCurrency(Integer costCurrency) {
        this.costCurrency = costCurrency;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Integer getCostStatus() {
        return costStatus;
    }

    public void setCostStatus(Integer costStatus) {
        this.costStatus = costStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
}
