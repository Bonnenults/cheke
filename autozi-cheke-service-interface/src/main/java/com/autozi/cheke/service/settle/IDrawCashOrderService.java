package com.autozi.cheke.service.settle;

import com.autozi.cheke.settle.entity.DrawCashOrder;
import com.autozi.common.core.page.Page;

import java.util.Map;

/**
 * User: long.jin
 * Date: 2017-12-13
 * Time: 13:54
 */
public interface IDrawCashOrderService {

    public Page<DrawCashOrder> findPageForMap(Page<DrawCashOrder>page,Map<String,Object> filters);

    public int applyDrawCash(Long userId,Double money,Long bankCardId);

    public Map<String,Object> getTotalMoney(Map<String,Object> map);
}
