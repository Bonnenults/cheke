package com.autozi.cheke.user.entity;

import com.autozi.common.dal.entity.IdEntity;

import java.util.Date;


public class ChekeCollection extends IdEntity {
    private Long collectionId; //被收藏的ID
    private Long userId;//评论用户ID
    private Integer isCourse;//是否为课程
    private Date createTime;//评论时间
    private Date updateTime;//评论时间

    public Long getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Long collectionId) {
        this.collectionId = collectionId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getIsCourse() {
        return isCourse;
    }

    public void setIsCourse(Integer isCourse) {
        this.isCourse = isCourse;
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
