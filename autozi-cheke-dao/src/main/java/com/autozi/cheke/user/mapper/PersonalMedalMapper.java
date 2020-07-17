package com.autozi.cheke.user.mapper;

import com.autozi.cheke.user.entity.PersonalMedal;
import com.autozi.common.dal.mapper.BaseMapper;

import java.util.List;
import java.util.Map;


public interface PersonalMedalMapper extends BaseMapper<PersonalMedal> {

    public List<PersonalMedal> findList(Map<String, Object> filters);
}
