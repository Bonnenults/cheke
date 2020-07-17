package com.autozi.cheke.settle.dao;
import com.autozi.cheke.settle.mapper.AccountLogMapper;
import com.autozi.common.dal.mybatis.MyBatisDao;
import com.autozi.cheke.settle.entity.AccountLog;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AccountLogDao extends MyBatisDao<AccountLog> {
    public Double getTotalTaskMoney(Map<String,Object> filters){
        return getMapper(AccountLogMapper.class).getTotalTaskMoney(filters);
    }
}
