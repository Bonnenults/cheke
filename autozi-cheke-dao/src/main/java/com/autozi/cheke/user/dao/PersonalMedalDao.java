package com.autozi.cheke.user.dao;

import com.autozi.cheke.user.entity.PersonalMedal;
import com.autozi.cheke.user.mapper.PersonalMedalMapper;
import com.autozi.common.dal.mybatis.MyBatisDao;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


@Component
public class PersonalMedalDao extends MyBatisDao<PersonalMedal> {
    public List<PersonalMedal> findList(Map<String,Object> filters){
        return getMapper(PersonalMedalMapper.class).findList(filters);
    }
}
