package com.autozi.cheke.service.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autozi.cheke.article.dao.ArticleClickDao;
import com.autozi.cheke.article.entity.ArticleClick;

import java.util.List;
import java.util.Map;

@Service
public class ArticleClickService implements IArticleClickService {

	@Autowired
	private ArticleClickDao  articleClickDao;

	@Override
	public void addArticleClick(ArticleClick articleClick,Integer action) {
		if(action != null){
			articleClickDao.insert(articleClick);
		}
	}

    @Override
    public void updataArticleClick(ArticleClick articleClick,Integer action) {
        if(action != null){
            articleClickDao.update(articleClick);
        }
    }

	@Override
	public ArticleClick getArticleClickbyFilter(Map<String, Object> filter){
		ArticleClick articleClick = null;
        List<ArticleClick> list = articleClickDao.findListForMap(filter);
        if (list != null && list.size() > 0) {
            articleClick = list.get(0);
        }
        return articleClick;
	}

}
