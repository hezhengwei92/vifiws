package net.eoutech.vifi.ws.entity;

import java.util.Date;

/**
 * Created by wei on 2017/11/28.
 */
public class TbWithdrawals {
    private Integer ukKeyId;
    private String idxAgentId;
    private String idxWithdrawalsAccount;
    private String ukOrderId;
    private String ukTransactionId;
    private Integer withdrawalsMoney;
    private Integer withdrawalsGetMoney;
    private Integer withdrawalsPoundage;
    private Double withdrawalsRate;
    private String withdrawalsType;
    private Date createTime;
    private String createBy;

    public Integer getUkKeyId() {
        return ukKeyId;
    }

    public void setUkKeyId(Integer ukKeyId) {
        this.ukKeyId = ukKeyId;
    }

    public String getIdxAgentId() {
        return idxAgentId;
    }

    public void setIdxAgentId(String idxAgentId) {
        this.idxAgentId = idxAgentId;
    }

    public String getIdxWithdrawalsAccount() {
        return idxWithdrawalsAccount;
    }

    public void setIdxWithdrawalsAccount(String idxWithdrawalsAccount) {
        this.idxWithdrawalsAccount = idxWithdrawalsAccount;
    }

    public String getUkOrderId() {
        return ukOrderId;
    }

    public void setUkOrderId(String ukOrderId) {
        this.ukOrderId = ukOrderId;
    }

    public String getUkTransactionId() {
        return ukTransactionId;
    }

    public void setUkTransactionId(String ukTransactionId) {
        this.ukTransactionId = ukTransactionId;
    }

    public Integer getWithdrawalsMoney() {
        return withdrawalsMoney;
    }

    public void setWithdrawalsMoney(Integer withdrawalsMoney) {
        this.withdrawalsMoney = withdrawalsMoney;
    }

    public Integer getWithdrawalsGetMoney() {
        return withdrawalsGetMoney;
    }

    public void setWithdrawalsGetMoney(Integer withdrawalsGetMoney) {
        this.withdrawalsGetMoney = withdrawalsGetMoney;
    }

    public Integer getWithdrawalsPoundage() {
        return withdrawalsPoundage;
    }

    public void setWithdrawalsPoundage(Integer withdrawalsPoundage) {
        this.withdrawalsPoundage = withdrawalsPoundage;
    }

    public Double getWithdrawalsRate() {
        return withdrawalsRate;
    }

    public void setWithdrawalsRate(Double withdrawalsRate) {
        this.withdrawalsRate = withdrawalsRate;
    }

    public String getWithdrawalsType() {
        return withdrawalsType;
    }

    public void setWithdrawalsType(String withdrawalsType) {
        this.withdrawalsType = withdrawalsType;
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
