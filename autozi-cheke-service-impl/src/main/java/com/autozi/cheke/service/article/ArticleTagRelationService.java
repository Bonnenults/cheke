package com.autozi.cheke.service.article;

import com.autozi.cheke.article.dao.ArticleTagRelationDao;
import com.autozi.cheke.article.entity.ArticleTagRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by wang-lei on 2017/12/14.
 */
@Service
public class ArticleTagRelationService implements IArticleTagRelationService {

    @Autowired
    private ArticleTagRelationDao articleTagRelationDao;

    @Override
    public int addArticleTagRelation(ArticleTagRelation articleTagRelation) {
        return articleTagRelationDao.insert(articleTagRelation);
    }

    @Override
    public List<ArticleTagRelation> findRelationList(Map<String, Object> filter) {
        return articleTagRelationDao.findListForMap(filter);
    }

    @Override
    public int deleteRelationByArticleId(Long articleId) {
        return articleTagRelationDao.deleteRelationByArticleId(articleId);
    }

    @Override
    public boolean checkedIsRef(Long articleTagId) {
        int num = articleTagRelationDao.checkedIsRef(articleTagId);
        if(num > 0) {
            return true;
        }
        return false;
    }
}
