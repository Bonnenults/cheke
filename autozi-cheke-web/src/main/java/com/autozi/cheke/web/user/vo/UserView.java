package com.autozi.cheke.web.user.vo;

import com.autozi.cheke.util.web.BaseView;

import java.util.Date;

/**
 * Created by lbm on 2017/11/28.
 */
public class UserView extends BaseView {
    private String name;          //推客：昵称
    private String loginName;     //车客：登录名；推客：推客号
    private String password;      //密码
    private Integer userType;   //用户类型[1=后台管理员;2=车客;3=推客]
    private String phone;         //车客：联系电话 推客：登录手机号
    private String phonePassword;      //推客：手机登录密码
    private String email;         //电子邮箱 保留扩展用
    private String imageUrl;      //头像
    private Long partyId;           //主体ID
    private Integer userStatus;     //用户状态：1=正常 2=停用
    private String gender;          //推客：性别
    private String inviteCode;     //推客：别人邀请码
    private String inviteCodeMine;     //推客：本人邀请码
    private String companyName;     //推客：公司名称
    private String jobName;     //推客：职位名称
    private String description;     //推客：个人简介
    private String address;     //推客：个人地址
    private String validateCode; //验证码
    private String validatePassword;//确认密码
    private String partyName;
    private String userStatusCN;
    private Integer admin;
    private String identifyImgA;    //推客：身份证正面
    private String identifyImgB;    //推客：身份证反面
    private String money;//总佣金
    private Integer idCardStatus ; //身份证状态
    private Integer sourceType;//汽修厂初始 。自助注册

    private Date loginTime;
    private Date lastLoginTime;    // 最后一次登录时间
    private Date updateTime;
    private Date createTime;//  创建时间
    private Long createUserId; //创建人ID

    private String refuseReason;
    private String userCount;//下级推客数

    private String roleName;//角色名称
    private String realName;

    private Integer loginFailedCount; //创建人ID
    private Integer verifyFlag; //是否审核通过过

    public Integer getVerifyFlag() {
        return verifyFlag;
    }

    public void setVerifyFlag(Integer verifyFlag) {
        this.verifyFlag = verifyFlag;
    }

    public Integer getLoginFailedCount() {
        return loginFailedCount;
    }

    public void setLoginFailedCount(Integer loginFailedCount) {
        this.loginFailedCount = loginFailedCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhonePassword() {
        return phonePassword;
    }

    public void setPhonePassword(String phonePassword) {
        this.phonePassword = phonePassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getPartyId() {
        return partyId;
    }

    public void setPartyId(Long partyId) {
        this.partyId = partyId;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getInviteCodeMine() {
        return inviteCodeMine;
    }

    public void setInviteCodeMine(String inviteCodeMine) {
        this.inviteCodeMine = inviteCodeMine;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    public String getValidatePassword() {
        return validatePassword;
    }

    public void setValidatePassword(String validatePassword) {
        this.validatePassword = validatePassword;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getUserStatusCN() {
        return userStatusCN;
    }

    public void setUserStatusCN(String userStatusCN) {
        this.userStatusCN = userStatusCN;
    }

    public Integer getAdmin() {
        return admin;
    }

    public void setAdmin(Integer admin) {
        this.admin = admin;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getIdentifyImgA() {
        return identifyImgA;
    }

    public void setIdentifyImgA(String identifyImgA) {
        this.identifyImgA = identifyImgA;
    }

    public String getIdentifyImgB() {
        return identifyImgB;
    }

    public void setIdentifyImgB(String identifyImgB) {
        this.identifyImgB = identifyImgB;
    }

    public Integer getIdCardStatus() {
        return idCardStatus;
    }

    public void setIdCardStatus(Integer idCardStatus) {
        this.idCardStatus = idCardStatus;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public String getRefuseReason() {
        return refuseReason;
    }

    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason;
    }

    public String getUserCount() {
        return userCount;
    }

    public void setUserCount(String userCount) {
        this.userCount = userCount;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
