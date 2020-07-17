package com.autozi.cheke.settle.mapper;
import com.autozi.common.dal.mapper.BaseMapper;
import com.autozi.cheke.settle.entity.DrawCashOrder;

import java.util.Map;

public interface DrawCashOrderMapper extends BaseMapper<DrawCashOrder> {
    public Map<String,Object> getTotalMoney(Map<String,Object> map);
}
