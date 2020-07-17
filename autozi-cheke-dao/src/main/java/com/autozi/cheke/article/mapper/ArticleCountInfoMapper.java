package com.autozi.cheke.article.mapper;

import com.autozi.common.dal.mapper.BaseMapper;
import com.autozi.cheke.article.entity.ArticleCountInfo;

import java.util.Map;

public interface ArticleCountInfoMapper extends BaseMapper<ArticleCountInfo> {
    int updateNul(ArticleCountInfo articleCountInfo);

    Double countTotalCostForParty(Map<String,Object> map);

    Double countCostForParty(Map<String,Object> map);
}
