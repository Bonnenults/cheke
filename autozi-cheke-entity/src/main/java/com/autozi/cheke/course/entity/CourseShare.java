package com.autozi.cheke.course.entity;

import com.autozi.common.dal.entity.IdEntity;

import java.util.Date;

/**
 * Created by mingxin.li on 2018/5/22 15:35.
 */
public class CourseShare extends IdEntity {

    private Long courseId;
    private Long userId;
    private Date shareTime; //分享时间
    private Integer type; //分享类型
    private Integer clickNum; //点击次数

    private String shareIp;

    public String getShareIp() {
        return shareIp;
    }

    public void setShareIp(String shareIp) {
        this.shareIp = shareIp;
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

    public Date getShareTime() {
        return shareTime;
    }

    public void setShareTime(Date shareTime) {
        this.shareTime = shareTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getClickNum() {
        return clickNum;
    }

    public void setClickNum(Integer clickNum) {
        this.clickNum = clickNum;
    }
}
