package com.autozi.cheke.user.mapper;

import com.autozi.cheke.user.entity.Medal;
import com.autozi.common.dal.mapper.BaseMapper;

import java.util.List;
import java.util.Map;


public interface MedalMapper extends BaseMapper<Medal> {
    public List<Medal> findList(Map<String,Object> filters);
}
