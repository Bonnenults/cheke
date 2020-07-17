package com.autozi.cheke.article.entity;

import com.autozi.common.dal.entity.IdEntity;

import java.util.Date;

/**
 * Created by wang-lei on 2017/11/23.
 * 普通分享
 */
public class ArticleShare extends IdEntity{

    private Long articleId;
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
    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
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
