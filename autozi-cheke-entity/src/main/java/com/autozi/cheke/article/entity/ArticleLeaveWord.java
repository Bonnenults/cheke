package com.autozi.cheke.article.entity;

import com.autozi.common.dal.entity.IdEntity;

import java.util.Date;

/**
 * Created by wang-lei on 2017/11/27.
 * 文章留言
 */
public class ArticleLeaveWord extends IdEntity {

    private Long articleId;


    private String leaveTitle; //留言标题
    private String leaveName;  //留言人名字
    private String leavePhone; //电话
    private String leaveMessage; //信息


    private Date createTime;
    private Date updateTime;

    private Long createUserId; //推客id
    private String createUserName; //推客姓名
    private String createUserPhone; //推客手机号


    private Integer channelType; //留言频道


    private Long chekeUserId; //车客id
    private Long chekePartyId; //车客partyId



    public Long getChekeUserId() {
        return chekeUserId;
    }

    public void setChekeUserId(Long chekeUserId) {
        this.chekeUserId = chekeUserId;
    }

    public Long getChekePartyId() {
        return chekePartyId;
    }

    public void setChekePartyId(Long chekePartyId) {
        this.chekePartyId = chekePartyId;
    }

    public Integer getChannelType() {
        return channelType;
    }

    public void setChannelType(Integer channelType) {
        this.channelType = channelType;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getCreateUserPhone() {
        return createUserPhone;
    }

    public void setCreateUserPhone(String createUserPhone) {
        this.createUserPhone = createUserPhone;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getLeaveTitle() {
        return leaveTitle;
    }

    public void setLeaveTitle(String leaveTitle) {
        this.leaveTitle = leaveTitle;
    }

    public String getLeaveName() {
        return leaveName;
    }

    public void setLeaveName(String leaveName) {
        this.leaveName = leaveName;
    }

    public String getLeavePhone() {
        return leavePhone;
    }

    public void setLeavePhone(String leavePhone) {
        this.leavePhone = leavePhone;
    }

    public String getLeaveMessage() {
        return leaveMessage;
    }

    public void setLeaveMessage(String leaveMessage) {
        this.leaveMessage = leaveMessage;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }
}
