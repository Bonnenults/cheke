package com.autozi.cheke.party.dao;

import com.autozi.cheke.party.mapper.ChekeFansMapper;
import com.autozi.common.core.page.Page;
import com.autozi.common.dal.mybatis.MyBatisDao;
import com.autozi.cheke.party.entity.ChekeFans;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ChekeFansDao extends MyBatisDao<ChekeFans> {
    public void cancelFollow(ChekeFans chekeFans) {
        getMapper(ChekeFansMapper.class).cancelFollow(chekeFans);
    }

    public Integer getFansCountByPartyId(Long partyId) {
        return getMapper(ChekeFansMapper.class).getFansCountByPartyId(partyId);
    }
}
