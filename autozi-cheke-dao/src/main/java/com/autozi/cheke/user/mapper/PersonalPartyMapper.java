package com.autozi.cheke.user.mapper;
import com.autozi.common.dal.mapper.BaseMapper;
import com.autozi.cheke.user.entity.PersonalParty;

public interface PersonalPartyMapper extends BaseMapper<PersonalParty> {
    public Integer getInviteCount(String inviteCode);
}
