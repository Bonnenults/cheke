package com.autozi.cheke.service.article;

import com.autozi.cheke.article.entity.ArticleTag;
import com.autozi.common.core.page.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by wang-lei on 2017/12/13.
 */
public interface IArticleTagService {


    int addArticleTag(ArticleTag articleTag);

    ArticleTag getArticleTagById(Long id);

    int deleteArticleTagById(Long id);

    int updateArticleTag(ArticleTag articleTag);

    ArticleTag getArticleTagByFilter(Map<String,Object> filter);

    Page<ArticleTag> findArticleTagPage(Page<ArticleTag> page,Map<String,Object> filter);

    List<ArticleTag> findArticleTagList(Map<String,Object> filter);


}
