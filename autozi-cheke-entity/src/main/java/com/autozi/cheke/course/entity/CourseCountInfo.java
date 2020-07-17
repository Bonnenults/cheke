package com.autozi.cheke.course.entity;

import com.autozi.common.dal.entity.IdEntity;

/**
 * Created by mingxin.li on 2018/5/21 10:36.
 */
public class CourseCountInfo extends IdEntity {

    private Long courseId;
    private Integer twitter; //推客
    private Integer leaveWords; //留言
    private Integer likes; //点赞
    private Integer dislikes; //点low

    private Integer stuNum;//学习中
    private Integer completedNum;//已结业

    private Integer pageView; //浏览量
    private Integer shareAmount; //分享量


    private Integer wxFriendsCircle; //朋友圈
    private Integer wxFriends; //微信好友

    private Integer qqZone; //QQ空间
    private Integer qqFriends; //QQ好友

    private Integer sina; //新浪微博

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Integer getDislikes() {
        return dislikes;
    }

    public void setDislikes(Integer dislikes) {
        this.dislikes = dislikes;
    }

    public Integer getLeaveWords() {
        return leaveWords;
    }

    public void setLeaveWords(Integer leaveWords) {
        this.leaveWords = leaveWords;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getPageView() {
        return pageView;
    }

    public void setPageView(Integer pageView) {
        this.pageView = pageView;
    }

    public Integer getQqFriends() {
        return qqFriends;
    }

    public void setQqFriends(Integer qqFriends) {
        this.qqFriends = qqFriends;
    }

    public Integer getQqZone() {
        return qqZone;
    }

    public void setQqZone(Integer qqZone) {
        this.qqZone = qqZone;
    }

    public Integer getShareAmount() {
        return shareAmount;
    }

    public void setShareAmount(Integer shareAmount) {
        this.shareAmount = shareAmount;
    }

    public Integer getSina() {
        return sina;
    }

    public void setSina(Integer sina) {
        this.sina = sina;
    }

    public Integer getTwitter() {
        return twitter;
    }

    public void setTwitter(Integer twitter) {
        this.twitter = twitter;
    }

    public Integer getWxFriends() {
        return wxFriends;
    }

    public void setWxFriends(Integer wxFriends) {
        this.wxFriends = wxFriends;
    }

    public Integer getWxFriendsCircle() {
        return wxFriendsCircle;
    }

    public void setWxFriendsCircle(Integer wxFriendsCircle) {
        this.wxFriendsCircle = wxFriendsCircle;
    }

    public Integer getStuNum() {  return stuNum; }

    public void setStuNum(Integer stuNum) { this.stuNum = stuNum; }

    public Integer getCompletedNum() {  return completedNum; }

    public void setCompletedNum(Integer completedNum) {  this.completedNum = completedNum; }





}
