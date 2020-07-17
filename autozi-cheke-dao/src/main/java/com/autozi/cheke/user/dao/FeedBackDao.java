package com.autozi.cheke.user.dao;

import com.autozi.cheke.user.entity.FeedBack;
import com.autozi.cheke.user.entity.Role;
import com.autozi.cheke.user.mapper.RoleMapper;
import com.autozi.common.core.page.Page;
import com.autozi.common.dal.mybatis.MyBatisDao;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class FeedBackDao extends MyBatisDao<FeedBack> {
        public Page<FeedBack> findPage(Page<FeedBack> page, Map<String,Object> filters){
            return findPageForMap(page,filters);
        }

}
