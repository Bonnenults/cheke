package com.autozi.cheke.article.dao;
import com.autozi.cheke.article.mapper.ArticleShareMapper;
import com.autozi.common.dal.mybatis.MyBatisDao;
import com.autozi.cheke.article.entity.ArticleShare;
import org.springframework.stereotype.Component;
@Component
public class ArticleShareDao extends MyBatisDao<ArticleShare> {
    public Integer countAllShareClickNum(Long articleId) {
        return getMapper(ArticleShareMapper.class).countAllShareClickNum(articleId);
    }
}
