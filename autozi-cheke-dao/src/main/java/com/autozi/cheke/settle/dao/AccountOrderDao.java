package com.autozi.cheke.settle.dao;

import com.autozi.cheke.settle.entity.AccountOrder;
import com.autozi.cheke.settle.mapper.AccountOrderMapper;
import com.autozi.common.dal.mybatis.MyBatisDao;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AccountOrderDao extends MyBatisDao<AccountOrder> {
    public Map<String,Object> getTotalMoney(Map<String,Object> map){
        return getMapper(AccountOrderMapper.class).getTotalMoney(map);
    }

    public void syncAccountOrder(AccountOrder accountOrder){
        getMapper(AccountOrderMapper.class).syncAccountOrder(accountOrder);
    }
}
