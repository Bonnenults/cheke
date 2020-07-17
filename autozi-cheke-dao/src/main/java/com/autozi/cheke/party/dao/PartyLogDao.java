package com.autozi.cheke.party.dao;
import com.autozi.cheke.party.mapper.PartyLogMapper;
import com.autozi.common.dal.mybatis.MyBatisDao;
import com.autozi.cheke.party.entity.PartyLog;
import org.springframework.stereotype.Component;
@Component
public class PartyLogDao extends MyBatisDao<PartyLog> {
    public PartyLog getPartyLogByPartyId(Long partyId){
        return getMapper(PartyLogMapper.class).getPartyLogByPartyId(partyId);
    }
}
