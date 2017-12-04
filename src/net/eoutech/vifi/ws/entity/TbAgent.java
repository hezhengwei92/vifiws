package net.eoutech.vifi.ws.entity;

import java.util.Date;

/**
 * Created by wei on 2017/11/24.
 */
public class TbAgent {
    private Integer ukKeyId;
    private String idxAgentId;
    private String ukAgentAccount;
    private String agentPassword;
    private String openId;
    private Integer isLogin;
    private Integer agentAuthority;
    private String agentAvatar;
    private String agentNickname;
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

    public String getUkAgentAccount() {
        return ukAgentAccount;
    }

    public void setUkAgentAccount(String ukAgentAccount) {
        this.ukAgentAccount = ukAgentAccount;
    }

    public String getAgentPassword() {
        return agentPassword;
    }

    public void setAgentPassword(String agentPassword) {
        this.agentPassword = agentPassword;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(Integer isLogin) {
        this.isLogin = isLogin;
    }

    public Integer getAgentAuthority() {
        return agentAuthority;
    }

    public void setAgentAuthority(Integer agentAuthority) {
        this.agentAuthority = agentAuthority;
    }

    public String getAgentAvatar() {
        return agentAvatar;
    }

    public void setAgentAvatar(String agentAvatar) {
        this.agentAvatar = agentAvatar;
    }

    public String getAgentNickname() {
        return agentNickname;
    }

    public void setAgentNickname(String agentNickname) {
        this.agentNickname = agentNickname;
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
