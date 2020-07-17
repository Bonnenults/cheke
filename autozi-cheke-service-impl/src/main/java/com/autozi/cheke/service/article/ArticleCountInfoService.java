package com.autozi.cheke.service.article;

import com.autozi.cheke.article.dao.ArticleCountInfoDao;
import com.autozi.cheke.article.entity.ArticleCountInfo;
import com.autozi.cheke.article.type.ArticleClickType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wang-lei on 2017/11/28.
 */
@Service
public class ArticleCountInfoService implements IArticleCountInfoService{

    @Autowired
    private ArticleCountInfoDao articleCountInfoDao;

    @Override
    public int addCountInfo(ArticleCountInfo articleCountInfo) {
        return articleCountInfoDao.insert(articleCountInfo);
    }

    @Override
    public ArticleCountInfo getCountInfoByArticleId(Long articleId) {
        Map<String,Object> filter = new HashMap<>();
        filter.put("articleId",articleId);
        ArticleCountInfo articleCountInfo = null;
        List<ArticleCountInfo> list = articleCountInfoDao.findListForMap(filter);
        if(list != null && list.size() > 0) {
            articleCountInfo = list.get(0);
        }
        return articleCountInfo;
    }

    @Override
    public int updateCountInfo(ArticleCountInfo articleCountInfo) {
        if(articleCountInfo == null){
            System.out.println("======= articleCountInfo 为空！！！ =======");
        }
        return articleCountInfoDao.update(articleCountInfo);
    }

    @Override
    public int updateCountInfoNul(ArticleCountInfo articleCountInfo) {
        return articleCountInfoDao.updateNul(articleCountInfo);
    }

    @Override
    public int updateNum(ArticleClickType clickType) {
        return 0;
    }

    @Override
    public Double countTotalCostForParty(Map<String,Object> map){
        return articleCountInfoDao.countTotalCostForParty(map);
    }

    @Override
    public Double countCostForParty(Map<String,Object> map){
        return articleCountInfoDao.countCostForParty(map);
    }
    
}
