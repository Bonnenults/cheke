package com.autozi.cheke.service.article;

import com.autozi.cheke.article.entity.ArticleLeaveWord;
import com.autozi.common.core.page.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by wang-lei on 2017/12/18.
 */
public interface IArticleLeaveWordService {

    int addLeaveWord(ArticleLeaveWord articleLeaveWord);

    ArticleLeaveWord getLeaveWordById(Long id);

    List<ArticleLeaveWord> getLeaveWordList(Map<String,Object> filter);

    Page<ArticleLeaveWord> getLeaveWordPage(Page<ArticleLeaveWord> page, Map<String,Object> filter);

}
