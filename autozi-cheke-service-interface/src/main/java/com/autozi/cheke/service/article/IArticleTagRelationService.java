package com.autozi.cheke.service.article;

import com.autozi.cheke.article.entity.ArticleTagRelation;

import java.util.List;
import java.util.Map;

/**
 * Created by wang-lei on 2017/12/14.
 */
public interface IArticleTagRelationService {

    int addArticleTagRelation(ArticleTagRelation articleTagRelation);

    List<ArticleTagRelation> findRelationList(Map<String,Object> filter);

    int deleteRelationByArticleId(Long articleId);

    boolean checkedIsRef(Long articleTagId);
}
