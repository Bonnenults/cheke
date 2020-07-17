package com.autozi.cheke.settle.mapper;
import com.autozi.common.dal.mapper.BaseMapper;
import com.autozi.cheke.settle.entity.AccountLog;

import java.util.Map;

public interface AccountLogMapper extends BaseMapper<AccountLog> {
    public Double getTotalTaskMoney(Map<String,Object> filters);
}
