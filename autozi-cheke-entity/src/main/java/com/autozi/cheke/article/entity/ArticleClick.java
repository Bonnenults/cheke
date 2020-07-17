package com.autozi.cheke.article.entity;

import com.autozi.common.dal.entity.IdEntity;

import java.lang.ref.PhantomReference;
import java.util.Date;

/**
 * Created by wang-lei on 2017/11/23.
 * 用户点赞流水
 */
public class ArticleClick extends IdEntity{

    private Long articleId; //文章id
    private Date clickTime; //
    private String clickIp; //用户Ip地址
    private Long UserId; //分享用户的ID
    private Integer clickType; // 点击类型如：qq空间、微信朋友圈


    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Date getClickTime() {
        return clickTime;
    }

    public void setClickTime(Date clickTime) {
        this.clickTime = clickTime;
    }

    public String getClickIp() {
        return clickIp;
    }

    public void setClickIp(String clickIp) {
        this.clickIp = clickIp;
    }

    public Long getUserId() {
        return UserId;
    }

    public void setUserId(Long userId) {
        UserId = userId;
    }

    public Integer getClickType() {
        return clickType;
    }

    public void setClickType(Integer clickType) {
        this.clickType = clickType;
    }
}
