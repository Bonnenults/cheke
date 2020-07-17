package com.autozi.cheke.service.settle;

import com.autozi.cheke.settle.dao.*;
import com.autozi.cheke.settle.entity.Account;
import com.autozi.cheke.settle.entity.AccountOrder;
import com.autozi.cheke.settle.entity.ChanpayOrder;
import com.autozi.cheke.settle.entity.ChanpayOrderBack;
import com.autozi.cheke.settle.type.IAccountConstant;
import com.autozi.cheke.settle.type.IAccountOrderConstant;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lbm on 2018/1/10.
 */
public class ChanpayOrderService implements IChanpayOrderService {

    @Autowired
    private ChanpayOrderDao chanpayOrderDao;
    @Autowired
    private ChanpayOrderBackDao chanpayOrderBackDao;
    @Autowired
    private AccountOrderDao accountOrderDao;
    @Autowired
    private DrawCashOrderDao drawCashOrderDao;
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private AccountLogDao accountLogDao;
    @Autowired
    private AccountService accountService;

    @Override
    public Long insert(ChanpayOrder chanpayOrder) {
        chanpayOrder.setCreateTime(new Date());
        chanpayOrderDao.insert(chanpayOrder);
        return chanpayOrder.getId();
    }

    @Override
    public void insertOrderBack(ChanpayOrderBack chanpayOrderBack) {
        chanpayOrderBack.setCreateTime(new Date());
        chanpayOrderBackDao.insert(chanpayOrderBack);
    }

    @Override
    public Long update(ChanpayOrder chanpayOrder) {
        chanpayOrder.setUpdateTime(new Date());
        chanpayOrderDao.update(chanpayOrder);
        return chanpayOrder.getId();
    }

    @Override
    public ChanpayOrder getChanpayOrderById(Long id) {
        return chanpayOrderDao.get(id);
    }

    @Override
    public List<ChanpayOrder> listByOrderId(Long orderId) {
        Map<String, Object> filters = new HashMap<>();
        filters.put("orderId", orderId);
        return chanpayOrderDao.findListForMap(filters);
    }

    @Override
    public ChanpayOrder getByOrderId(Long orderId) {
        Map<String, Object> filters = new HashMap<>();
        filters.put("orderId", orderId);
        List<ChanpayOrder> list = chanpayOrderDao.findListForMap(filters);
        if(list!=null && list.size()>0){
            return list.get(0);
        }
        return null;
    }



}
