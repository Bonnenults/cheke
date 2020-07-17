package com.autozi.cheke.service.article;

import com.autozi.cheke.article.dao.ArticleTagDao;
import com.autozi.cheke.article.entity.ArticleTag;
import com.autozi.common.core.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by wang-lei on 2017/12/13.
 */
@Service
public class ArticleTagService implements IArticleTagService{

    @Autowired
    private ArticleTagDao articleTagDao;

    @Override
    public int addArticleTag(ArticleTag articleTag) {
        return articleTagDao.insert(articleTag);
    }

    @Override
    public ArticleTag getArticleTagById(Long id) {
        return articleTagDao.get(id);
    }

    @Override
    public int deleteArticleTagById(Long id) {
        return articleTagDao.delete(id);
    }

    @Override
    public int updateArticleTag(ArticleTag articleTag) {
        return articleTagDao.update(articleTag);
    }

    @Override
    public ArticleTag getArticleTagByFilter(Map<String, Object> filter) {
        ArticleTag articleTag = null;
        List<ArticleTag> list = articleTagDao.findListForMap(filter);

        if(list != null && list.size() > 0) {
            articleTag = list.get(0);
        }

        return articleTag;
    }

    @Override
    public Page<ArticleTag> findArticleTagPage(Page<ArticleTag> page, Map<String, Object> filter) {
        return articleTagDao.findPageForMap(page,filter);
    }

    @Override
    public List<ArticleTag> findArticleTagList(Map<String, Object> filter) {
        return articleTagDao.findListForMap(filter);
    }

}
