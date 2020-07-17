package com.autozi.cheke.service.article;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autozi.cheke.article.dao.ArticleShareDao;
import com.autozi.cheke.article.entity.ArticleShare;

/**
 * Created by wang-lei on 2017/12/18.
 */
@Service
public class ArticleShareService implements IArticleShareService{

    @Autowired
    private ArticleShareDao articleShareDao;

    @Override
    public int addArticleShare(ArticleShare articleShare) {
        return articleShareDao.insert(articleShare);
    }

    @Override
    public int updateArticleShare(ArticleShare articleShare) {
        return articleShareDao.update(articleShare);
    }

    @Override
    public ArticleShare getArticleShareById(Long id) {
        return articleShareDao.get(id);
    }

    @Override
    public List<ArticleShare> getArticleShareList(Map<String, Object> filter) {
        return articleShareDao.findListForMap(filter);
    }

    @Override
    public ArticleShare getArticleShareByFilter(Map<String, Object> filter) {
        ArticleShare articleShare = null;
        List<ArticleShare> list = articleShareDao.findListForMap(filter);
        if(list != null && list.size() > 0) {
            articleShare = list.get(0);
        }
        return articleShare;
    }

    @Override
    public Integer countAllShareClickNum(Long articleId) {
        return articleShareDao.countAllShareClickNum(articleId);
    }

	@Override
	public Long addArticleShareAndUpdataCountInfo(ArticleShare articleShare) {
		articleShareDao.insert(articleShare);
        return articleShare.getId();
	}

}
