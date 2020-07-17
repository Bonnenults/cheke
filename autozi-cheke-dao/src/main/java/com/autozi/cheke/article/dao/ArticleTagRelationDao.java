package com.autozi.cheke.article.dao;
import com.autozi.cheke.article.mapper.ArticleTagRelationMapper;
import com.autozi.common.dal.mybatis.MyBatisDao;
import com.autozi.cheke.article.entity.ArticleTagRelation;
import org.springframework.stereotype.Component;
@Component
public class ArticleTagRelationDao extends MyBatisDao<ArticleTagRelation> {

    public int deleteRelationByArticleId(Long articleId) {
        return getMapper(ArticleTagRelationMapper.class).deleteRelationByArticleId(articleId);
    }

    public int checkedIsRef(Long articleTagId) {
        return getMapper(ArticleTagRelationMapper.class).checkedIsRef(articleTagId);
    }

}
