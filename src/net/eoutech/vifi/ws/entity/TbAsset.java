package net.eoutech.vifi.ws.entity;

import java.util.Date;

/**
 * Created by wei on 2017/11/28.
 * 资产表
 */
public class TbAsset {
    private Integer ukKeyId;
    private String idxAgentId;
    private String alipayAccount;
    private Integer alipayMoney;
    private String wechatpayAccount;
    private Integer wechatpayMoney;
    private Integer accountAuthority;
    private Date modifyTime;
    private String modifyBy;
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

    public String getAlipayAccount() {
        return alipayAccount;
    }

    public void setAlipayAccount(String alipayAccount) {
        this.alipayAccount = alipayAccount;
    }

    public Integer getAlipayMoney() {
        return alipayMoney;
    }

    public void setAlipayMoney(Integer alipayMoney) {
        this.alipayMoney = alipayMoney;
    }

    public String getWechatpayAccount() {
        return wechatpayAccount;
    }

    public void setWechatpayAccount(String wechatpayAccount) {
        this.wechatpayAccount = wechatpayAccount;
    }

    public Integer getWechatpayMoney() {
        return wechatpayMoney;
    }

    public void setWechatpayMoney(Integer wechatpayMoney) {
        this.wechatpayMoney = wechatpayMoney;
    }

    public Integer getAccountAuthority() {
        return accountAuthority;
    }

    public void setAccountAuthority(Integer accountAuthority) {
        this.accountAuthority = accountAuthority;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy;
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
