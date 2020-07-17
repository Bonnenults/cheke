package com.autozi.cheke.party.dao;
import com.autozi.cheke.party.mapper.PartyMapper;
import com.autozi.common.core.page.Page;
import com.autozi.common.dal.mybatis.MyBatisDao;
import com.autozi.cheke.party.entity.Party;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class PartyDao extends MyBatisDao<Party> {
    public List<Party> findList(Map<String,Object> filters){
        return getMapper(PartyMapper.class).findList(filters);
    }
    public Page<Map<String,Object>> listParty(Page<Map<String,Object>> page, Map<String, Object> map){
        return this.findPage(page, "listParty", map);
    }
}
