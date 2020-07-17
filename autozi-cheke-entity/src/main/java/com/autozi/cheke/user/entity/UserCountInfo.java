package com.autozi.cheke.user.entity;

import com.autozi.common.dal.entity.IdEntity;

import java.util.Date;

public class UserCountInfo extends IdEntity {
    private Integer loginCount; //登录天数
    private Integer continuousLoginCount; //连续登录天数
    private Date lastLoginTime;
    private Integer studyDaysCount; //学习天数
    private Date lastStudyTime;
    private Integer studyChapterCount; //学习章节数
    private Date updateTime;
    private Date createTime;

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    public Integer getContinuousLoginCount() {
        return continuousLoginCount;
    }

    public void setContinuousLoginCount(Integer continuousLoginCount) {
        this.continuousLoginCount = continuousLoginCount;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getStudyDaysCount() {
        return studyDaysCount;
    }

    public void setStudyDaysCount(Integer studyDaysCount) {
        this.studyDaysCount = studyDaysCount;
    }

    public Date getLastStudyTime() {
        return lastStudyTime;
    }

    public void setLastStudyTime(Date lastStudyTime) {
        this.lastStudyTime = lastStudyTime;
    }

    public Integer getStudyChapterCount() {
        return studyChapterCount;
    }

    public void setStudyChapterCount(Integer studyChapterCount) {
        this.studyChapterCount = studyChapterCount;
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
}
