package com.autozi.cheke.party.mapper;
import com.autozi.common.dal.mapper.BaseMapper;
import com.autozi.cheke.party.entity.ChekeFans;
public interface ChekeFansMapper extends BaseMapper<ChekeFans> {
    public void cancelFollow(ChekeFans chekeFans);
    public Integer getFansCountByPartyId(Long partyId);
}
