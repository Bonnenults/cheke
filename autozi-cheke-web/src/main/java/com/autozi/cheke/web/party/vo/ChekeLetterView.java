package com.autozi.cheke.web.party.vo;

import com.autozi.cheke.util.web.BaseView;

import java.util.Date;

/**
 * Created by lbm on 2017/12/15.
 */
public class ChekeLetterView extends BaseView {
    private Long toUserId; //接收者的ID 车客的ID要用partyId不是userId
    private Long fromUserId; //发送者ID
    private String content;//私信内容
    private Long relationId;//回复信的ID
    private String createTime;//发送时间
    private String updateTime;//回复时间

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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
