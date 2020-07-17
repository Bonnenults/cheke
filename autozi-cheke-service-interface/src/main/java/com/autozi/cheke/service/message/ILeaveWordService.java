package com.autozi.cheke.service.message;

import com.autozi.cheke.article.entity.ArticleLeaveWord;
import com.autozi.common.core.page.Page;

import java.util.Map;

/**
 * Created by wang-lei on 2017/12/11.
 */
public interface ILeaveWordService {

    int addLeaveWord(ArticleLeaveWord leaveWord);

    Page<ArticleLeaveWord> findLeaveWordPage(Page<ArticleLeaveWord> page, Map<String,Object> filter);

}
