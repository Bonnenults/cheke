package com.autozi.cheke.service.article;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autozi.cheke.article.dao.ArticleLeaveWordDao;
import com.autozi.cheke.article.entity.ArticleLeaveWord;
import com.autozi.cheke.article.type.ArticleClickType;
import com.autozi.common.core.page.Page;

/**
 * Created by wang-lei on 2017/12/18.
 */
@Service
public class ArticleLeaveWordService implements IArticleLeaveWordService{

    @Autowired
    private ArticleLeaveWordDao articleLeaveWordDao;

    @Override
    public int addLeaveWord(ArticleLeaveWord articleLeaveWord) {
        return articleLeaveWordDao.insert(articleLeaveWord);
    }

    @Override
    public ArticleLeaveWord getLeaveWordById(Long id) {
        return articleLeaveWordDao.get(id);
    }

    @Override
    public List<ArticleLeaveWord> getLeaveWordList(Map<String, Object> filter) {
        return articleLeaveWordDao.findListForMap(filter);
    }

    @Override
    public Page<ArticleLeaveWord> getLeaveWordPage(Page<ArticleLeaveWord> page, Map<String, Object> filter) {
        return articleLeaveWordDao.findPageForMap(page,filter);
    }


}
