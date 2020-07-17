package com.autozi.cheke.party.dao;
import com.autozi.cheke.party.mapper.ChekeLetterRelationMapper;
import com.autozi.common.core.page.Page;
import com.autozi.common.dal.mybatis.MyBatisDao;
import com.autozi.cheke.party.entity.ChekeLetterRelation;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ChekeLetterRelationDao extends MyBatisDao<ChekeLetterRelation> {
    public Page<Map<String,Object>> findPartyLetterList(Page<Map<String,Object>> page, Map<String, Object> map){
        return this.findPage(page, "findPartyLetterList", map);
    }

    public Integer getLetterCount(Map<String,Object> map){
        return getMapper(ChekeLetterRelationMapper.class).getLetterCount(map);
    }
}
