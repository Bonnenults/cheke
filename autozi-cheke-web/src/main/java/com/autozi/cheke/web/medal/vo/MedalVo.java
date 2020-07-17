package com.autozi.cheke.web.medal.vo;

import com.autozi.cheke.util.web.BaseView;

import java.util.Date;

/**
 * Created by wang-lei on 2017/11/22.
 * 文章
 */
public class MedalVo extends BaseView {

    private String MedalName;//勋章名.
    private String image;//勋章未激活.
    private String imageActive;//勋章已激活.

    private String intro; //简介
    private Integer status; //状态
    private String statusCN;
    private Integer obtainNum;
    private Integer adornNum;
    private Long createUserId;
    private Long updateUserId;
    private Date createTime; //创建时间
    private Date publishTime; //上线时间
    private Date offlineTime; //下线时间
    private Date updateTime; //修改时间
    private Integer type; //类型

    public String getMedalName() {
        return MedalName;
    }

    public void setMedalName(String medalName) {
        MedalName = medalName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageActive() {
        return imageActive;
    }

    public void setImageActive(String imageActive) {
        this.imageActive = imageActive;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusCN() {
        return statusCN;
    }

    public void setStatusCN(String statusCN) {
        this.statusCN = statusCN;
    }

    public Integer getObtainNum() {
        return obtainNum;
    }

    public void setObtainNum(Integer obtainNum) {
        this.obtainNum = obtainNum;
    }

    public Integer getAdornNum() {
        return adornNum;
    }

    public void setAdornNum(Integer adornNum) {
        this.adornNum = adornNum;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Date getOfflineTime() {
        return offlineTime;
    }

    public void setOfflineTime(Date offlineTime) {
        this.offlineTime = offlineTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
