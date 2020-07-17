package com.autozi.cheke.service.user;

import com.autozi.cheke.user.dao.FeedBackDao;
import com.autozi.cheke.user.dao.SearchFeedBackDao;
import com.autozi.cheke.user.entity.FeedBack;
import com.autozi.cheke.user.entity.SearchFeedBack;
import com.autozi.common.core.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Auther: Administrator
 * @Date: 2018/6/15 17:51
 * @Description:
 */
@Service
public class FeedBackService implements IFeedBackService{
    @Autowired
    private FeedBackDao feedBackDao;
    @Autowired
    private SearchFeedBackDao searchFeedBackDao;

    @Override
    public void addFeedBack(FeedBack feedBack){
        feedBackDao.insert(feedBack);
    }

    @Override
    public void addSearchFeedBack(SearchFeedBack searchfeedBack){
        searchFeedBackDao.insert(searchfeedBack);
    }

    @Override
    public Page<FeedBack> findFeedBackPage(Page<FeedBack> page, Map<String, Object> filters) {
        return feedBackDao.findPageForMap(page,filters);
    }

    @Override
    public Page<SearchFeedBack> findFeedBackForSearchPage(Page<SearchFeedBack> page, Map<String, Object> filters) {
        return searchFeedBackDao.findPageForMap(page,filters);
    }
}
