package com.autozi.cheke.article.mapper;
import com.autozi.common.dal.mapper.BaseMapper;
import com.autozi.cheke.article.entity.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ArticleMapper extends BaseMapper<Article> {
    Integer countArticle(Map<String,Object> filter);
    int updateNul(Article article);

    int countDayArticleNum(@Param("channelType")Integer channelType,@Param("createUserId") Long createUserId,@Param("id") Long id);

    List<Long> countDayArticleId(@Param("channelType")Integer channelType,@Param("createUserId") Long createUserId);

    List<Article> findListForEndTimeTrigger();
}
