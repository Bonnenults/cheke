package com.autozi.cheke.article.dao;
import com.autozi.cheke.article.mapper.ArticleCountInfoMapper;
import com.autozi.common.dal.mybatis.MyBatisDao;
import com.autozi.cheke.article.entity.ArticleCountInfo;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ArticleCountInfoDao extends MyBatisDao<ArticleCountInfo> {
    public Integer updateNul(ArticleCountInfo articleCountInfo) {
        return getMapper(ArticleCountInfoMapper.class).updateNul(articleCountInfo);
    }

    public Double countTotalCostForParty(Map<String,Object> map){
        return getMapper(ArticleCountInfoMapper.class).countTotalCostForParty(map);
    }

    public Double countCostForParty(Map<String,Object> map){
        return getMapper(ArticleCountInfoMapper.class).countCostForParty(map);
    }
}
