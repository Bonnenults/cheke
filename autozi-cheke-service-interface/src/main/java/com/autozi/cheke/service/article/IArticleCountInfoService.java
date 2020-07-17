package com.autozi.cheke.service.article;

import com.autozi.cheke.article.entity.ArticleCountInfo;
import com.autozi.cheke.article.type.ArticleClickType;

import java.util.Map;

/**
 * Created by wang-lei on 2017/11/28.
 */
public interface IArticleCountInfoService {

    int addCountInfo(ArticleCountInfo articleCountInfo);

    ArticleCountInfo getCountInfoByArticleId(Long articleId);

    int updateCountInfo(ArticleCountInfo articleCountInfo);

    int updateCountInfoNul(ArticleCountInfo articleCountInfo);

    int updateNum(ArticleClickType clickType);

    Double countTotalCostForParty(Map<String,Object> map);

    public Double countCostForParty(Map<String, Object> map);
    
}
