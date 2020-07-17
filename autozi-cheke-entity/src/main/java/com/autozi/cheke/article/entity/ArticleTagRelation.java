package com.autozi.cheke.article.entity;

import com.autozi.common.dal.entity.IdEntity;

/**
 * Created by wang-lei on 2017/12/14.
 */
public class ArticleTagRelation extends IdEntity {

    private Long articleId;
    private Long articleTagId;

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Long getArticleTagId() {
        return articleTagId;
    }

    public void setArticleTagId(Long articleTagId) {
        this.articleTagId = articleTagId;
    }
}
