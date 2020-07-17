package com.autozi.cheke.party.mapper;
import com.autozi.common.dal.mapper.BaseMapper;
import com.autozi.cheke.party.entity.PartyLog;
public interface PartyLogMapper extends BaseMapper<PartyLog> {
    public PartyLog getPartyLogByPartyId(Long partyId);
}
