package com.autozi.cheke.trigger.account;

import com.autozi.cheke.trigger.facade.AccountTaskFacade;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by lbm on 2018/1/29.
 */
public class AccountTrigger {
    @Autowired
    private AccountTaskFacade accountTaskFacade;

    public void syncAccountOrder(){
        accountTaskFacade.syncAccountOrder();
    }

}
