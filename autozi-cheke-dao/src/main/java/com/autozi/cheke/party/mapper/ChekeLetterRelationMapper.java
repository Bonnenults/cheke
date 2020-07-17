package com.autozi.cheke.party.mapper;
import com.autozi.common.dal.mapper.BaseMapper;
import com.autozi.cheke.party.entity.ChekeLetterRelation;

import java.util.Map;

public interface ChekeLetterRelationMapper extends BaseMapper<ChekeLetterRelation> {
    public Integer getLetterCount(Map<String,Object> map);
}
