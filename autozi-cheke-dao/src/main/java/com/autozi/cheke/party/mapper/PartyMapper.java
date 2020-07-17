package com.autozi.cheke.party.mapper;
import com.autozi.common.dal.mapper.BaseMapper;
import com.autozi.cheke.party.entity.Party;

import java.util.List;
import java.util.Map;

public interface PartyMapper extends BaseMapper<Party> {
    public List<Party> findList(Map<String,Object> filters);
}
