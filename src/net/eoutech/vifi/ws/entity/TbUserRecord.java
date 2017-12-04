package net.eoutech.vifi.ws.entity;

import java.util.Date;

/**
 * Created by wei on 2017/11/24.
 */
public class TbUserRecord {
    private Integer ukKeyId;
    private String ukOrderId;
    private String ukTransactionId;
    private String idxUserId;
    private String buyIp;
    private String buyType;
    private String buyPackageId;
    private Integer buyPackageMoney;
    private Integer buyPackageCurrency;
    private Date createTime;
    private Integer isPay;

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

    public String getUkTransactionId() {
        return ukTransactionId;
    }

    public void setUkTransactionId(String ukTransactionId) {
        this.ukTransactionId = ukTransactionId;
    }

    public String getIdxUserId() {
        return idxUserId;
    }

    public void setIdxUserId(String idxUserId) {
        this.idxUserId = idxUserId;
    }

    public String getBuyIp() {
        return buyIp;
    }

    public void setBuyIp(String buyIp) {
        this.buyIp = buyIp;
    }

    public String getBuyType() {
        return buyType;
    }

    public void setBuyType(String buyType) {
        this.buyType = buyType;
    }

    public String getBuyPackageId() {
        return buyPackageId;
    }

    public void setBuyPackageId(String buyPackageId) {
        this.buyPackageId = buyPackageId;
    }

    public Integer getBuyPackageMoney() {
        return buyPackageMoney;
    }

    public void setBuyPackageMoney(Integer buyPackageMoney) {
        this.buyPackageMoney = buyPackageMoney;
    }

    public Integer getBuyPackageCurrency() {
        return buyPackageCurrency;
    }

    public void setBuyPackageCurrency(Integer buyPackageCurrency) {
        this.buyPackageCurrency = buyPackageCurrency;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getIsPay() {
        return isPay;
    }

    public void setIsPay(Integer isPay) {
        this.isPay = isPay;
    }
}
