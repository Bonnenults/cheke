package com.autozi.cheke.user.entity;

import com.autozi.common.dal.entity.IdEntity;
import java.util.Date;

public class Medal extends IdEntity {
    private String medalName;
    private String image;
    private String imageActive;
    private String intro;
    private Integer status;
    private Integer obtainNum;//获得人数
    private Integer adornNum;//佩戴人数
    private Long createUserId;
    private Long updateUserId;
    private Date updateTime;
    private Date createTime;
    private Date publishTime;
    private Date offlineTime;
    private Integer type;


    public String getMedalName() {
        return medalName;
    }

    public void setMedalName(String medalName) {
        this.medalName = medalName;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}

