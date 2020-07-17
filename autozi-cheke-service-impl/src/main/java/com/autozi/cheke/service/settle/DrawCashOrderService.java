package com.autozi.cheke.service.settle;

import com.autozi.cheke.service.basic.RunningNumberHelper;
import com.autozi.cheke.settle.dao.AccountDao;
import com.autozi.cheke.settle.dao.AccountLogDao;
import com.autozi.cheke.settle.dao.BankCardDao;
import com.autozi.cheke.settle.dao.ChanpayOrderDao;
import com.autozi.cheke.settle.dao.DrawCashOrderDao;
import com.autozi.cheke.settle.entity.Account;
import com.autozi.cheke.settle.entity.AccountLog;
import com.autozi.cheke.settle.entity.DrawCashOrder;
import com.autozi.cheke.settle.type.IAccountConstant;
import com.autozi.cheke.settle.type.IDrawCashOrderConstant;
import com.autozi.common.core.page.Page;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Map;

/**
 * User: long.jin
 * Date: 2017-12-13
 * Time: 13:55
 */
public class DrawCashOrderService implements IDrawCashOrderService {
    @Autowired
    private DrawCashOrderDao drawCashOrderDao;
    @Autowired
    private BankCardDao bankCardDao;
    @Autowired
    private ChanpayOrderDao chanpayOrderDao;
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private AccountLogDao accountLogDao;
    @Autowired
    private AccountService accountService;

    @Override
    public Page<DrawCashOrder> findPageForMap(Page<DrawCashOrder> page, Map<String, Object> filters) {
        return drawCashOrderDao.findPageForMap(page,filters);
    }

    @Override
    public int applyDrawCash(Long userId,Double money,Long bankCardId) {
        //1.生成提现订单
        DrawCashOrder drawCashOrder = new DrawCashOrder();
        drawCashOrder.setAccountMoney(money);
        drawCashOrder.setMoney(money - IDrawCashOrderConstant.RATE_FEE);
        drawCashOrder.setSlottingFee(IDrawCashOrderConstant.RATE_FEE);
        drawCashOrder.setRateFee(0d);
        drawCashOrder.setStatus(IDrawCashOrderConstant.status.STATUS_CREATE);
        drawCashOrder.setUserId(userId);
        drawCashOrder.setCreateTime(new Date());
        drawCashOrder.setBankCardId(bankCardId);
        drawCashOrder.setCode(RunningNumberHelper.getDrawCashOrderCode());
        drawCashOrderDao.insert(drawCashOrder);

        //2.账户变动
        Account account = accountService.accountByOwnerIdAndType(userId, IAccountConstant.type.TYPE_TK);
        Double remainAccount = account.getAccount();
        Double newAccount = remainAccount-money;
        account.setAccount(newAccount);
        account.setUpdateTime(new Date());
        accountDao.update(account);
        //添加变更记录
        AccountLog accountLog = new AccountLog();
        accountLog.setAccountId(account.getId());
        accountLog.setSourceType(IAccountConstant.AccountLogSourceType.TYPE_CK_GET_MONEY);
        accountLog.setSourceId(drawCashOrder.getId());
        accountLog.setChangeAccount(money);
        accountLog.setStartAccount(remainAccount);
        accountLog.setEndAccount(newAccount);
        accountLog.setCreateTime(new Date());
        accountLogDao.insert(accountLog);

        return IAccountConstant.msg.SUCCESS;
    }

    @Override
    public Map<String, Object> getTotalMoney(Map<String, Object> map) {
        return drawCashOrderDao.getTotalMoney(map);
    }
}
