package com.autozi.cheke.settle.mapper;
import com.autozi.common.dal.mapper.BaseMapper;
import com.autozi.cheke.settle.entity.AccountOrder;

import java.util.Map;

public interface AccountOrderMapper extends BaseMapper<AccountOrder> {
    public Map<String,Object> getTotalMoney(Map<String,Object> map);

    /**
     * 同步充值订单，超时的设为已取消状态
     * @param accountOrder
     * @return
     */
    public void syncAccountOrder(AccountOrder accountOrder);
}
