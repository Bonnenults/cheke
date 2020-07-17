package com.autozi.cheke.service.article;

import com.autozi.cheke.article.entity.Article;
import com.autozi.common.core.page.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by wang-lei on 2017/11/28.
 */
public interface IArticleService {
    int addArticle(Article article);

    int updateArticle(Article article);

    Article getArticleById(Long id);

    Article getArticleByFilter(Map<String,Object> filter);

    Page<Article> findArticlePage(Page<Article> page,Map<String,Object> filter) throws Exception;
    
    List<Article> findListForMap(Map<String,Object> filter);

    Integer countArticleByCurrentUserId(Long userId);

    /**
     * 批量发布资讯文章
     *
     * @param articleIds
     */
    public void batchPublish(List<String> articleIds);

    /**
     * 批量取消发布资讯文章
     * @param articleIds
     */
    public void batchCancel(List<String> articleIds);

    /**
     * 批量下线
     *
     * @param articleIds
     */
    void batchOffline(List<String> articleIds);

    int publishArticle(Long articleId);

    int cancelPublish(Long articleId);

    int offLineArticle(Long articleId);

    int addArticleReturnId(Article article, List<String> tagIdList, Double allCost, Double onceCost, Double mostCost);

    int updateArticleNotPublic(Article article, List<String> tagIdList, Double allCost, Double onceCost, Double mostCost);

    int copyArticle(Long articleId);

    int countDayArticleNum(Integer channelType,Long createUserId,Long articleId);

    List<Long> countDayArticleId(Integer channelType,Long createUserId);

    List<Article> findListForEndTimeTrigger();
}
