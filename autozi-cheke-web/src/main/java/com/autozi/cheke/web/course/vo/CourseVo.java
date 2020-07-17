package com.autozi.cheke.web.course.vo;

import com.autozi.cheke.util.web.BaseView;

import java.util.Date;

/**
 * Created by wang-lei on 2017/11/22.
 * 文章
 */
public class CourseVo extends BaseView {

    private String courseName;//课程名.
    private String image;//课程封面.
    private String source; //来源

    private Integer type; //课程类型

    private Integer status; //课程状态

    private Integer stuStatus;//课程学习状态-100,"全部" ；0,"未开始"；1,"学习中"；2,"已结业"；-1,"已删除";

    private Long createUserId;
    private Long updateUserId;
    private Date createTime; //创建时间
    private Date updateTime; //修改时间

    private Date publishTime; //下线时间
    private Date offlineTime; //下线时间

    private Date beginTime;
    private Date endTime;

    private String intro; //简介

    private Integer stuNum;//课程学习人数
    private Integer completedNum;//课程学习人数

    private String typeName; //项目类型名称
    private String statusName; //状态名称

    private String beginTimeStr;//任务开始时间
    private String publishTimeStr; //任务上线时间
    private String tag;


    public String getCourseName() { return courseName; }

    public void setCourseName(String courseName) { this.courseName = courseName;  }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getCompletedNum() {  return completedNum; }

    public void setCompletedNum(Integer completedNum) {  this.completedNum = completedNum; }

    public Integer getStuNum() {  return stuNum; }

    public void setStuNum(Integer stuNum) {  this.stuNum = stuNum; }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStatus() {  return status; }

    public void setStatus(Integer status) {  this.status = status; }

    public Integer getStuStatus() {  return stuStatus; }

    public void setStuStatus(Integer stuStatus) {  this.stuStatus = stuStatus; }

    public Long getCreateUserId() {  return createUserId; }

    public void setCreateUserId(Long createUserId) {  this.createUserId = createUserId; }

    public Long getUpdateUserId() {  return updateUserId; }

    public void setUpdateUserId(Long updateUserId) {  this.updateUserId = updateUserId; }

    public Date getOfflineTime() {  return offlineTime; }

    public void setOfflineTime(Date offlineTime) {  this.offlineTime = offlineTime; }

    public Date getPublishTime() {  return publishTime; }

    public void setPublishTime(Date publishTime) {  this.publishTime = publishTime; }


    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getPublishTimeStr() {
        return publishTimeStr;
    }

    public void setPublishTimeStr(String publishTimeStr) {
        this.publishTimeStr = publishTimeStr;
    }

    public String getBeginTimeStr() {
        return beginTimeStr;
    }

    public void setBeginTimeStr(String beginTimeStr) {
        this.beginTimeStr = beginTimeStr;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
