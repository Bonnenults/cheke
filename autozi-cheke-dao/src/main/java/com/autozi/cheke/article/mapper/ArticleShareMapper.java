package com.autozi.cheke.article.mapper;
import com.autozi.common.dal.mapper.BaseMapper;
import com.autozi.cheke.article.entity.ArticleShare;
import org.apache.ibatis.annotations.Param;

public interface ArticleShareMapper extends BaseMapper<ArticleShare> {
    Integer countAllShareClickNum(@Param("articleId") Long articleId);
}
