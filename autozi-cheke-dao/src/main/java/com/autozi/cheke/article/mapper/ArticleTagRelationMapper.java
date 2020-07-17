package com.autozi.cheke.article.mapper;
import com.autozi.common.dal.mapper.BaseMapper;
import com.autozi.cheke.article.entity.ArticleTagRelation;
import org.apache.ibatis.annotations.Param;

public interface ArticleTagRelationMapper extends BaseMapper<ArticleTagRelation> {
    int deleteRelationByArticleId(@Param("articleId") Long articleId);
    int checkedIsRef(@Param("articleTagId") Long articleTagId);
}
