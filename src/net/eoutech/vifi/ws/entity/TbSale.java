package net.eoutech.vifi.ws.entity;

import java.util.Date;

/**
 * Created by wei on 2017/12/1.
 */
public class TbSale {
    private Integer ukKeyId;
    private String idxSaleAccount;
    private String idxMachineId;
    private String idxAgentAccount;
    private Date activeTime;
    private Integer isActive;
    private Date createTime;
    private String createBy;

    public Integer getUkKeyId() {
        return ukKeyId;
    }

    public void setUkKeyId(Integer ukKeyId) {
        this.ukKeyId = ukKeyId;
    }

    public String getIdxSaleAccount() {
        return idxSaleAccount;
    }

    public void setIdxSaleAccount(String idxSaleAccount) {
        this.idxSaleAccount = idxSaleAccount;
    }

    public String getIdxMachineId() {
        return idxMachineId;
    }

    public void setIdxMachineId(String idxMachineId) {
        this.idxMachineId = idxMachineId;
    }

    public String getIdxAgentAccount() {
        return idxAgentAccount;
    }

    public void setIdxAgentAccount(String idxAgentAccount) {
        this.idxAgentAccount = idxAgentAccount;
    }

    public Date getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(Date activeTime) {
        this.activeTime = activeTime;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
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
