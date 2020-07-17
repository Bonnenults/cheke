package com.autozi.cheke.user.dao;

import com.autozi.cheke.user.entity.FeedBack;
import com.autozi.cheke.user.entity.SearchFeedBack;
import com.autozi.common.core.page.Page;
import com.autozi.common.dal.mybatis.MyBatisDao;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SearchFeedBackDao extends MyBatisDao<SearchFeedBack> {
        public Page<SearchFeedBack> findPage(Page<SearchFeedBack> page, Map<String,Object> filters){
            return findPageForMap(page,filters);
        }

}
