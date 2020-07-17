package com.autozi.cheke.user.entity;

import com.autozi.common.dal.entity.IdEntity;

import java.util.Date;


public class ChekeComment extends IdEntity {
    private Long replyId; //被回复的ID，可以是文章ID、评论ID等
    private Long courseId; //若为课程相关评论，则需要课程ID
    private Long userId;//评论用户ID
    private String content;//评论内容
    private Integer isSub;//是否为子评论
    private Integer replyCount;//回复数
    private Date createTime;//评论时间
    private Date updateTime;//评论时间

    public Long getReplyId() {
        return replyId;
    }

    public void setReplyId(Long replyId) {
        this.replyId = replyId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getIsSub() {
        return isSub;
    }

    public void setIsSub(Integer isSub) {
        this.isSub = isSub;
    }

    public Integer getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
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
