package com.autozi.cheke.user.dao;

import com.autozi.cheke.user.entity.ChekeCollection;
import com.autozi.cheke.user.mapper.ChekeCollectionMapper;
import com.autozi.common.dal.mybatis.MyBatisDao;
import org.springframework.stereotype.Component;


@Component
public class ChekeCollectionDao extends MyBatisDao<ChekeCollection> {
    public void cancelCollect(ChekeCollection chekeCollection) {
        getMapper(ChekeCollectionMapper.class).cancelCollect(chekeCollection);
    }
}
