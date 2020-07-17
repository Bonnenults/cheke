package com.autozi.cheke.article.dao;
import com.autozi.cheke.article.mapper.ArticleMapper;
import com.autozi.common.dal.mybatis.MyBatisDao;
import com.autozi.cheke.article.entity.Article;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ArticleDao extends MyBatisDao<Article> {
    public Integer countArticle(Map<String,Object> filter) {
        return getMapper(ArticleMapper.class).countArticle(filter);
    }
    public Integer updateNul(Article article) {
        return getMapper(ArticleMapper.class).updateNul(article);
    }

    public int countDayArticleNum(Integer channelType,Long createUserId,Long id) {
        return getMapper(ArticleMapper.class).countDayArticleNum(channelType,createUserId,id);
    }

    public List<Long> countDayArticleId(Integer channelType, Long createUserId) {
        return getMapper(ArticleMapper.class).countDayArticleId(channelType,createUserId);
    }

    public List<Article> findListForEndTimeTrigger() {
        return getMapper(ArticleMapper.class).findListForEndTimeTrigger();
    }

}
