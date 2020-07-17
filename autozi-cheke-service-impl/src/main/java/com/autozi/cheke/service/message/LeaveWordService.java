package com.autozi.cheke.service.message;

import com.autozi.cheke.article.dao.ArticleLeaveWordDao;
import com.autozi.cheke.article.entity.ArticleLeaveWord;
import com.autozi.common.core.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by wang-lei on 2017/12/11.
 */
@Service
public class LeaveWordService implements ILeaveWordService{

    @Autowired
    private ArticleLeaveWordDao articleLeaveWordDao;

    @Override
    public int addLeaveWord(ArticleLeaveWord leaveWord) {
        return articleLeaveWordDao.insert(leaveWord);
    }

    @Override
    public Page<ArticleLeaveWord> findLeaveWordPage(Page<ArticleLeaveWord> page, Map<String, Object> filter) {
        return articleLeaveWordDao.findPageForMap(page,filter);
    }
}
