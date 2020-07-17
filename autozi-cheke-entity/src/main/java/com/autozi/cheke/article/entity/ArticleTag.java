package com.autozi.cheke.article.entity;

import com.autozi.common.dal.entity.IdEntity;

import java.util.Date;

/**
 * 信息分类
 * Created by wang-lei on 2017/12/11.
 */
public class ArticleTag extends IdEntity {

    private Integer code;
    private String name;

    private Long createUserId;

    private Date createTime;
    private Date updateTime;

    private Integer channelType;


    public Integer getChannelType() {
        return channelType;
    }

    public void setChannelType(Integer channelType) {
        this.channelType = channelType;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
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
