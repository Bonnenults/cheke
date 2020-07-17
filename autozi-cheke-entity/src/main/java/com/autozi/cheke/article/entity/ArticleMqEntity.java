package com.autozi.cheke.article.entity;

import com.autozi.common.dal.entity.IdEntity;

/**
 * User: long.jin
 * Date: 2017-12-21
 * Time: 15:34
 */
public class ArticleMqEntity extends IdEntity {

    private Long userId;
    private Long articleId; //文章ID
    private Integer shareType; //分享类型
    private String pkId;//articleUserId@userId@shareType

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Integer getShareType() {
        return shareType;
    }

    public void setShareType(Integer shareType) {
        this.shareType = shareType;
    }

    public String getPkId() {
        return pkId;
    }

    public void setPkId(String pkId) {
        this.pkId = pkId;
    }
}
