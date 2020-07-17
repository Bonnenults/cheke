package com.autozi.cheke.service.article;

import com.autozi.cheke.article.entity.ArticleClick;

import java.util.Map;

public interface IArticleClickService {

    void addArticleClick(ArticleClick articleClick, Integer action);

    void updataArticleClick(ArticleClick articleClick, Integer action);

    ArticleClick getArticleClickbyFilter(Map<String, Object> filter);
}
