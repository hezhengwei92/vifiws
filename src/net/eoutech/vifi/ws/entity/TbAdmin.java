package net.eoutech.vifi.ws.entity;

import java.util.Date;

/**
 * Created by wei on 2017/11/24.
 */
public class TbAdmin {
    private Integer ukKeyId;
    private String ukAdminAccount;
    private String adminPassword;
    private Integer isLogin;
    private Integer adminAuthority;
    private String adminAvatar;
    private String adminNickname;
    private Date createTime;
    private String createBy;

    public Integer getUkKeyId() {
        return ukKeyId;
    }

    public void setUkKeyId(Integer ukKeyId) {
        this.ukKeyId = ukKeyId;
    }

    public String getUkAdminAccount() {
        return ukAdminAccount;
    }

    public void setUkAdminAccount(String ukAdminAccount) {
        this.ukAdminAccount = ukAdminAccount;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public Integer getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(Integer isLogin) {
        this.isLogin = isLogin;
    }

    public Integer getAdminAuthority() {
        return adminAuthority;
    }

    public void setAdminAuthority(Integer adminAuthority) {
        this.adminAuthority = adminAuthority;
    }

    public String getAdminAvatar() {
        return adminAvatar;
    }

    public void setAdminAvatar(String adminAvatar) {
        this.adminAvatar = adminAvatar;
    }

    public String getAdminNickname() {
        return adminNickname;
    }

    public void setAdminNickname(String adminNickname) {
        this.adminNickname = adminNickname;
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
