package com.autozi.cheke.course.entity;

import com.autozi.common.dal.entity.IdEntity;

import java.util.Date;

/**
 * Created by mingxin.li on 2018/5/21 10:38.
 */
public class CourseUserRelation extends IdEntity {

    private Long userId;

    private Long courseId;
    private Long articleId;
    private Integer status; //课程状态
    private String currentProgress;
    private Date beginTime; //开始学习时间
    private Date endTime; //完成学习时间

    private Integer ranking;//排名

    private Date createTime; //创建时间
    private Date updateTime; //修改时间


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Long getArticleId() {   return articleId; }

    public void setArticleId(Long articleId) {  this.articleId = articleId; }

    public Long getCourseId() {  return courseId; }

    public void setCourseId(Long courseId) {  this.courseId = courseId; }

    public String getCurrentProgress() {  return currentProgress; }

    public void setCurrentProgress(String currentProgress) {  this.currentProgress = currentProgress; }

    public Long getUserId() {  return userId; }

    public void setUserId(Long userId) {  this.userId = userId; }



}
