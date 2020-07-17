package com.autozi.cheke.service.user;

import com.autozi.cheke.user.entity.FeedBack;
import com.autozi.cheke.user.entity.SearchFeedBack;
import com.autozi.common.core.page.Page;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Auther: Administrator
 * @Date: 2018/6/15 17:51
 * @Description:
 */

@Component
public interface IFeedBackService {

    public void addFeedBack(FeedBack feedBack);

    void addSearchFeedBack(SearchFeedBack searchfeedBack);

    Page<FeedBack> findFeedBackPage(Page<FeedBack> page, Map<String, Object> filters);

    Page<SearchFeedBack> findFeedBackForSearchPage(Page<SearchFeedBack> page, Map<String, Object> filters);
}
