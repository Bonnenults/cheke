package com.autozi.cheke.service.article;

import java.util.List;
import java.util.Map;

import com.autozi.cheke.article.entity.ArticleOrder;
import com.autozi.common.core.page.Page;

/**
 * Created by wang-lei on 2017/11/28.
 */
public interface IArticleOrderService {
    Long addArticleOrder(ArticleOrder articleOrder);

    int updateArticleOrder(ArticleOrder articleOrder);

    ArticleOrder getOrderById(Long id);
    
    ArticleOrder getOrderByArticleIdAndUserId(Long articleId, Long userId);

    List<ArticleOrder> getOrderByUserId(Long userId);

    Page<ArticleOrder> findPageForMap(Page<ArticleOrder> page,Map<String,Object> filter);

    Integer countAllClickNum(Long articleId);
    
    void updataOrderAndCountInfo(ArticleOrder articleOrder);

    Integer getTaskCount(Long articleId);

    List<ArticleOrder> getOrderByArticleId(Long articleId);

    List<ArticleOrder> getOrderByUserIdAndStatus(Long userId);

}
