package com.autozi.cheke.article.dao;
import com.autozi.cheke.article.mapper.ArticleOrderMapper;
import com.autozi.common.dal.mybatis.MyBatisDao;
import com.autozi.cheke.article.entity.ArticleOrder;
import org.springframework.stereotype.Component;
@Component
public class ArticleOrderDao extends MyBatisDao<ArticleOrder> {
    public Integer countAllClickNum(Long articleId) {
        return getMapper(ArticleOrderMapper.class).countAllClickNum(articleId);
    }
    public Integer getTaskCount(Long articleId) {
        return getMapper(ArticleOrderMapper.class).getTaskCount(articleId);
    }
}
