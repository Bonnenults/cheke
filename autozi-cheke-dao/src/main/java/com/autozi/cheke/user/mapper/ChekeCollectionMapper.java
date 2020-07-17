package com.autozi.cheke.user.mapper;

import com.autozi.cheke.user.entity.ChekeCollection;
import com.autozi.common.dal.mapper.BaseMapper;


public interface ChekeCollectionMapper extends BaseMapper<ChekeCollection> {
    public void cancelCollect(ChekeCollection chekeCollection);
}
