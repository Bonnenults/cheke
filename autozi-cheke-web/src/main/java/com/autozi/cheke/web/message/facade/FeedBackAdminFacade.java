package com.autozi.cheke.web.message.facade;

import com.autozi.cheke.article.entity.ArticleLeaveWord;
import com.autozi.cheke.service.message.ILeaveWordService;
import com.autozi.cheke.service.user.IFeedBackService;
import com.autozi.cheke.user.entity.FeedBack;
import com.autozi.cheke.user.entity.SearchFeedBack;
import com.autozi.common.core.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by wang-lei on 2017/12/11.
 */
@Component
public class FeedBackAdminFacade {

    @Autowired
    private IFeedBackService feedBackService;


    public Page<FeedBack> findFeedBackPage(Page<FeedBack> page, Map<String,Object> filter) {
        return feedBackService.findFeedBackPage(page,filter);
    }

    public Page<SearchFeedBack> findFeedBackForSearchPage(Page<SearchFeedBack> page, Map<String,Object> filter) {
        return feedBackService.findFeedBackForSearchPage(page,filter);
    }

}
