package net.eoutech.vifi.ws.entity;

import java.util.Date;

/**
 * Created by wei on 2017/11/24.
 */
public class TbPackage {
    private Integer ukKeyId;
    private String ukPackageId;
    private String idxAgentId;
    private String packageDescription;
    private Integer packagePrice;
    private Integer packageCurrency;
    private Integer packageStatus;
    private Date createTime;
    private String createBy;

    public Integer getUkKeyId() {
        return ukKeyId;
    }

    public void setUkKeyId(Integer ukKeyId) {
        this.ukKeyId = ukKeyId;
    }

    public String getUkPackageId() {
        return ukPackageId;
    }

    public void setUkPackageId(String ukPackageId) {
        this.ukPackageId = ukPackageId;
    }

    public String getIdxAgentId() {
        return idxAgentId;
    }

    public void setIdxAgentId(String idxAgentId) {
        this.idxAgentId = idxAgentId;
    }

    public String getPackageDescription() {
        return packageDescription;
    }

    public void setPackageDescription(String packageDescription) {
        this.packageDescription = packageDescription;
    }

    public Integer getPackagePrice() {
        return packagePrice;
    }

    public void setPackagePrice(Integer packagePrice) {
        this.packagePrice = packagePrice;
    }

    public Integer getPackageCurrency() {
        return packageCurrency;
    }

    public void setPackageCurrency(Integer packageCurrency) {
        this.packageCurrency = packageCurrency;
    }

    public Integer getPackageStatus() {
        return packageStatus;
    }

    public void setPackageStatus(Integer packageStatus) {
        this.packageStatus = packageStatus;
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
