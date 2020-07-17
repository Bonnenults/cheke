package com.autozi.cheke.user.entity;

import com.autozi.common.dal.entity.IdEntity;

import java.util.Date;

/**
 * Created by lbm on 2017/12/19.
 */
public class PersonalParty extends IdEntity {

    private Integer idCardStatus ; //身份证状态
    private String inviteCode;     //推客：别人邀请码
    private String inviteCodeMine;     //推客：本人邀请码
    private String companyName;     //推客：公司名称
    private String jobName;         //推客：职位名称
    private String description;     //推客：个人简介
    private String address;         //推客：个人地址
    private String identifyImgA;    //推客：身份证正面
    private String identifyImgB;    //推客：身份证反面
    private Date updateTime;
    private Date createTime;//  创建时间
    private Integer sourceType;//汽修厂初始 。自助注册
    private String imageUrl;      //头像
    private String refuseReason;    //拒绝原因
    private String idCardNumber;

    public Integer getIdCardStatus() {
        return idCardStatus;
    }

    public void setIdCardStatus(Integer idCardStatus) {
        this.idCardStatus = idCardStatus;
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

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
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

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRefuseReason() {
        return refuseReason;
    }

    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }
}
