package com.autozi.cheke.party.entity;

import com.autozi.common.dal.entity.IdEntity;

import java.util.Date;

/**
 * Created by lbm on 2017/12/14.
 */
public class ChekeLetter extends IdEntity {
    private Long toUserId; //接收者的ID 车客的ID要用partyId不是userId
    private Long fromUserId; //发送者ID
    private String content;//私信内容
    private Long relationId;//回复信的ID
    private Date createTime;//发送时间
    private Date updateTime;//回复时间

    public Long getToUserId() {
        return toUserId;
    }

    public void setToUserId(Long toUserId) {
        this.toUserId = toUserId;
    }

    public Long getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Long fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long relationId) {
        this.relationId = relationId;
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
}
