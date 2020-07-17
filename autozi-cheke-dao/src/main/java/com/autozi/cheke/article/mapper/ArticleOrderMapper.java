package com.autozi.cheke.article.mapper;
import com.autozi.common.dal.mapper.BaseMapper;
import com.autozi.cheke.article.entity.ArticleOrder;
import org.apache.ibatis.annotations.Param;

public interface ArticleOrderMapper extends BaseMapper<ArticleOrder> {
    Integer countAllClickNum(@Param("articleId") Long articleId);
    Integer getTaskCount(@Param("articleId") Long articleId);
}
