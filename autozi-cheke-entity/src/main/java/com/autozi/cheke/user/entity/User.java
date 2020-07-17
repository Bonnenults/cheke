package com.autozi.cheke.user.entity;

import com.autozi.common.dal.entity.IdEntity;
import com.autozi.common.dal.user.entity.IUser;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import java.util.Date;

/**
 * 用户.
 */
public class User extends IdEntity implements IUser {

    private String name;          //推客：昵称
    private String realName;          //真实姓名
    private String loginName;     //车客：登录名；推客：推客号
    private String password;      //密码
    private Integer userType;   //用户类型[1=后台管理员;2=车客;3=推客]
    private String phone;         //车客：联系电话 推客：登录手机号
    private String phonePassword;      //推客：手机登录密码
    private String email;         //电子邮箱 保留扩展用
    private Long partyId;           //主体ID
    private Integer userStatus;     //用户状态：1=正常 2=停用
    private String gender;          //推客：性别
    private Integer admin;          //车客：是否是主体的管理员 1=是 2=不是

    private Date loginTime;
    private Date lastLoginTime;    // 最后一次登录时间
    private Date updateTime;
    private Date createTime;//  创建时间
    private Long createUserId; //创建人ID
    private Integer loginFailedCount; //登录失败次数
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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    @Override
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
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

    @Override
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

    public Integer getAdmin() {
        return admin;
    }

    public void setAdmin(Integer admin) {
        this.admin = admin;
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

    public static void main(String[] args) {
        System.out.println(new Md5PasswordEncoder().encodePassword("12345", "admin"));
    }
}