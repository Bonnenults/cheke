package com.autozi.cheke.trigger.facade;

import com.autozi.cheke.service.settle.IAccountOrderService;
import com.autozi.cheke.settle.entity.AccountOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by lbm on 2018/1/29.
 */
@Component
public class AccountTaskFacade {
    @Autowired
    private IAccountOrderService accountOrderService;

    public void syncAccountOrder(){
        accountOrderService.syncAccountOrder();
    }
}
