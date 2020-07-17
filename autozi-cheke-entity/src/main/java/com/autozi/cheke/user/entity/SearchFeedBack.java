package com.autozi.cheke.user.entity;

import com.autozi.common.dal.entity.IdEntity;

import java.util.Date;

public class SearchFeedBack extends IdEntity {
    private String content;
    private Long userId; //创建人ID
    private Date createTime;//  创建时间

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
