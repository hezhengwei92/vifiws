package net.eoutech.vifi.ws.entity;

import java.util.Date;

/**
 * Created by wei on 2017/11/24.
 */
public class TbRole {
    private Integer ukKeyId;
    private Integer ukRoleAuthority;
    private String roleName;
    private String roleAvatar;
    private Date createTime;
    private String createBy;

    public Integer getUkKeyId() {
        return ukKeyId;
    }

    public void setUkKeyId(Integer ukKeyId) {
        this.ukKeyId = ukKeyId;
    }

    public Integer getUkRoleAuthority() {
        return ukRoleAuthority;
    }

    public void setUkRoleAuthority(Integer ukRoleAuthority) {
        this.ukRoleAuthority = ukRoleAuthority;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleAvatar() {
        return roleAvatar;
    }

    public void setRoleAvatar(String roleAvatar) {
        this.roleAvatar = roleAvatar;
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
