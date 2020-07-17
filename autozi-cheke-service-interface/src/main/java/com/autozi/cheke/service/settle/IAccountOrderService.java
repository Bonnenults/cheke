package com.autozi.cheke.service.settle;

import com.autozi.cheke.settle.entity.AccountOrder;
import com.autozi.cheke.user.entity.User;
import com.autozi.common.core.page.Page;

import java.util.Map;

/**
 * User: long.jin
 * Date: 2017-12-04
 * Time: 15:13
 */
public interface IAccountOrderService {

    public Page<AccountOrder> findPageForMap(Page<AccountOrder> page,Map<String,Object> filters);

    public AccountOrder getAccountOrderById(Long id);

    public void update(AccountOrder accountOrder);

    Long submitRechargeMoney(User user, Double realMoney);

    public Map<String,Object> getTotalMoney(Map<String,Object> map);

    /**
     * 同步超时订单，将状态置为已取消
     */
    public void syncAccountOrder();
}
