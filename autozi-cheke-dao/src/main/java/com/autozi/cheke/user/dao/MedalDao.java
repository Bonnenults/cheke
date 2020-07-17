package com.autozi.cheke.user.dao;


import com.autozi.cheke.user.entity.Medal;
import com.autozi.cheke.user.mapper.MedalMapper;
import com.autozi.common.dal.mybatis.MyBatisDao;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


@Component
public class MedalDao extends MyBatisDao<Medal> {
    public List<Medal> findList(Map<String,Object> filters){
        return getMapper(MedalMapper.class).findList(filters);
    }
}
