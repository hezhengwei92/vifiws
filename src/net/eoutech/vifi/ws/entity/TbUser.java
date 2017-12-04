package net.eoutech.vifi.ws.entity;

import java.util.Date;

/**
 * Created by wei on 2017/11/24.
 */
public class TbUser {
    private Integer ukKeyId;
    private String ukUserId;
    private String idxUserPhone;
    private String thirdUserId;
    private String thirdOpenId;
    private String userAvatar;
    private String userNickname;
    private Integer userLevelId;
    private String userLevelName;
    private Integer userLevelPoint;
    private String userType;
    private Integer userStatus;
    private Integer userCurrency;
    private Integer onlineStatus;
    private Date onlineTime;
    private Date createTime;

    public Integer getUkKeyId() {
        return ukKeyId;
    }

    public void setUkKeyId(Integer ukKeyId) {
        this.ukKeyId = ukKeyId;
    }

    public String getUkUserId() {
        return ukUserId;
    }

    public void setUkUserId(String ukUserId) {
        this.ukUserId = ukUserId;
    }

    public String getIdxUserPhone() {
        return idxUserPhone;
    }

    public void setIdxUserPhone(String idxUserPhone) {
        this.idxUserPhone = idxUserPhone;
    }

    public String getThirdUserId() {
        return thirdUserId;
    }

    public void setThirdUserId(String thirdUserId) {
        this.thirdUserId = thirdUserId;
    }

    public String getThirdOpenId() {
        return thirdOpenId;
    }

    public void setThirdOpenId(String thirdOpenId) {
        this.thirdOpenId = thirdOpenId;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public Integer getUserLevelId() {
        return userLevelId;
    }

    public void setUserLevelId(Integer userLevelId) {
        this.userLevelId = userLevelId;
    }

    public String getUserLevelName() {
        return userLevelName;
    }

    public void setUserLevelName(String userLevelName) {
        this.userLevelName = userLevelName;
    }

    public Integer getUserLevelPoint() {
        return userLevelPoint;
    }

    public void setUserLevelPoint(Integer userLevelPoint) {
        this.userLevelPoint = userLevelPoint;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public Integer getUserCurrency() {
        return userCurrency;
    }

    public void setUserCurrency(Integer userCurrency) {
        this.userCurrency = userCurrency;
    }

    public Integer getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(Integer onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public Date getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(Date onlineTime) {
        this.onlineTime = onlineTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
