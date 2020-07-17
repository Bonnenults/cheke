package com.autozi.cheke.user.dao;
import com.autozi.cheke.user.mapper.PersonalPartyMapper;
import com.autozi.common.dal.mybatis.MyBatisDao;
import com.autozi.cheke.user.entity.PersonalParty;
import org.springframework.stereotype.Component;
@Component
public class PersonalPartyDao extends MyBatisDao<PersonalParty> {
    public Integer getInviteCount(String inviteCode){
        return getMapper(PersonalPartyMapper.class).getInviteCount(inviteCode);
    }
}
