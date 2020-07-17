package com.autozi.cheke.service.article;

import com.autozi.cheke.article.entity.ArticleShare;

import java.util.List;
import java.util.Map;

/**
 * Created by wang-lei on 2017/12/18.
 */
public interface IArticleShareService {
    int addArticleShare(ArticleShare articleShare);

    int updateArticleShare(ArticleShare articleShare);

    ArticleShare getArticleShareById(Long id);

    List<ArticleShare> getArticleShareList(Map<String,Object> filter);

    ArticleShare getArticleShareByFilter(Map<String,Object> filter);

    Integer countAllShareClickNum(Long articleId);
    
    Long addArticleShareAndUpdataCountInfo(ArticleShare articleShare);
}
