package com.autozi.cheke.basic.entity;

import com.autozi.common.dal.entity.IdEntity;

import java.util.Date;

/**
 * Created by mingxin.li on 2018/5/31 15:55.
 */
public class Properties extends IdEntity {
    private String popKey;
    private String value;
    private Long createUserId;
    private Long updateUserId;
    private Date createTime; //创建时间
    private Date updateTime; //修改时间

    public String gePoptKey() {
        return popKey;
    }

    public void setPopKey(String key) {
        this.popKey = popKey;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }
}
