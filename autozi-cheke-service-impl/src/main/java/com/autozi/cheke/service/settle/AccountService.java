package com.autozi.cheke.service.settle;

import com.autozi.cheke.settle.dao.AccountDao;
import com.autozi.cheke.settle.dao.AccountLogDao;
import com.autozi.cheke.settle.dao.BankCardDao;
import com.autozi.cheke.settle.entity.Account;
import com.autozi.cheke.settle.entity.AccountLog;
import com.autozi.cheke.settle.entity.BankCard;
import com.autozi.cheke.settle.type.IAccountConstant;
import com.autozi.cheke.user.dao.UserDao;
import com.autozi.cheke.user.entity.User;
import com.autozi.common.core.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: long.jin
 * Date: 2017-12-04
 * Time: 14:38
 */
@Service
public class AccountService implements IAccountService {

    @Autowired
    private AccountDao accountDao;
    @Autowired
    private AccountLogDao accountLogDao;
    @Autowired
    private BankCardDao bankCardDao;
    @Autowired
    private UserDao userDao;


    @Override
    public Long insertAccount(Account account) {
        account.setCreateTime(new Date());
        accountDao.insert(account);
        return account.getId();
    }

    /**
     * 通过拥有者和类型 获取账户信息
     * @param ownerId
     * @param type
     * @return
     */
    @Override
    public Account accountByOwnerIdAndType(Long ownerId, Integer type) {
        Map<String,Object> filters  = new HashMap<>();
        filters.put("ownerId",ownerId);
        filters.put("type",type);
        filters.put("limit1","true");
        List<Account> accountList = accountDao.findListForMap(filters);
        Account account =null;
        //如果未取到，则新添账户
        if(accountList.size()==0){
            account = new Account();
            account.setOwnerId(ownerId);
            account.setType(type);
            account.setAccount(0d);
            account.setCreateTime(new Date());
            accountDao.insert(account);
        }else{
            account = accountList.get(0);
        }
        return account;
    }

    @Override
    public Page<AccountLog> findAccountLogPageForMap(Page<AccountLog> page, Map<String, Object> filters) {
        return accountLogDao.findPageForMap(page,filters);
    }

    @Override
    public Page<Account> findAccountPage(Page<Account> page, Map<String, Object> filters) {
        return accountDao.findPageForMap(page,filters);
    }

    @Override
    public Double getTotalTaskMoney(Long userId, Integer type) {
        Map<String,Object> filters = new HashMap();
        filters.put("ownerId",userId);
        filters.put("sourceType",type);
        return accountLogDao.getTotalTaskMoney(filters);
    }

    @Override
    public Double getTaskMoney(Map<String, Object> filters) {
        return accountLogDao.getTotalTaskMoney(filters);
    }

    @Override
    public void saveTaskRewardMoney(Long userId, Double money, Long articleOrderId) {
        updateAccount(userId,money,articleOrderId,IAccountConstant.type.TYPE_TK,IAccountConstant.AccountLogSourceType.TYPE_CK_ORDER_MONEY);
    }

    @Override
    public void saveTaskPercentMoney(Long userId, Double money, Long articleOrderId) {
        updateAccount(userId,money,articleOrderId,IAccountConstant.type.TYPE_TK,IAccountConstant.AccountLogSourceType.TYPE_TK_SHARE_MONEY);
    }

    @Override
    public boolean isFull(Long userId, Double money) {
        if(money==null){
            return false;
        }
        User user = userDao.get(userId);
        Long partyId = user.getPartyId();
        //根据userId获取账户
        Account account = accountByOwnerIdAndType(partyId, IAccountConstant.type.TYPE_CK);
        if(money.doubleValue()>account.getAccount().doubleValue()){
            return false;
        }
        return true;
    }

    @Override
    public Integer refundCk(Long userId, Double money, Long articleId) {
        User user = userDao.get(userId);
        Long partyId = user.getPartyId();
        //根据userId获取账户
        Account account = accountByOwnerIdAndType(partyId, IAccountConstant.type.TYPE_CK);
        //更新账户余额
        Double remainAccount = account.getAccount();
        Double newAccount = remainAccount+money;
        account.setAccount(newAccount);
        account.setUpdateTime(new Date());
        accountDao.update(account);
        //插入账户变更记录
        Long accountId = account.getId();
        AccountLog accountLog = new AccountLog();
        accountLog.setAccountId(accountId);
        accountLog.setChangeAccount(money);//变更钱
        accountLog.setStartAccount(remainAccount);//变更前余额
        accountLog.setEndAccount(newAccount);//变更后余额
        accountLog.setSourceType(IAccountConstant.AccountLogSourceType.TYPE_SPREAD_RETURN);
        accountLog.setSourceId(articleId);
        accountLog.setCreateTime(new Date());
        accountLogDao.insert(accountLog);
        return 1;
    }

    @Override
    public Integer payTask(Long userId, Double money, Long articleId) {
        if(money==null || money==0){
            return 0;
        }
        User user = userDao.get(userId);
        Long partyId = user.getPartyId();
        //根据userId获取账户
        Account account = accountByOwnerIdAndType(partyId, IAccountConstant.type.TYPE_CK);
        if(money.doubleValue()>account.getAccount().doubleValue()){
            return 2;
        }
        //更新账户余额
        Double remainAccount = account.getAccount();
        Double newAccount = remainAccount-money;
        account.setAccount(newAccount);
        account.setUpdateTime(new Date());
        accountDao.update(account);
        //插入账户变更记录
        Long accountId = account.getId();
        AccountLog accountLog = new AccountLog();
        accountLog.setAccountId(accountId);
        accountLog.setChangeAccount(money);//变更钱
        accountLog.setStartAccount(remainAccount);//变更前余额
        accountLog.setEndAccount(newAccount);//变更后余额
        accountLog.setSourceType(IAccountConstant.AccountLogSourceType.TYPE_SPREAD_COST);
        accountLog.setSourceId(articleId);
        accountLog.setCreateTime(new Date());
        accountLogDao.insert(accountLog);
        return 1;
    }

    @Override
    public void recharge(Long partyId, Double money,Long orderId) {
        Account account = accountByOwnerIdAndType(partyId, IAccountConstant.type.TYPE_CK);
        Double remainAccount = account.getAccount();
        Long accountId = account.getId();
        AccountLog accountLog = new AccountLog();
        accountLog.setAccountId(accountId);
        accountLog.setChangeAccount(money);//变更钱
        accountLog.setStartAccount(remainAccount);//变更前余额
        accountLog.setEndAccount(remainAccount+money);//变更后余额
        accountLog.setSourceType(IAccountConstant.AccountLogSourceType.TYPE_SPREAD_RECHARGE);
        accountLog.setSourceId(orderId);
        accountLog.setCreateTime(new Date());
        accountLogDao.insert(accountLog);

        account.setAccount(remainAccount+money);
        accountDao.update(account);
    }

    @Override
    public void insertAccountLog(AccountLog accountLog) {
        accountLog.setCreateTime(new Date());
        accountLogDao.insert(accountLog);
    }

    @Override
    public List<BankCard> getBankCardByAccountId(Long accountId) {
        Map<String,Object> filters = new HashMap<>();
        filters.put("accountId",accountId);
        return bankCardDao.findListForMap(filters);
    }

    @Override
    public Long insertBankCard(BankCard bankCard) {
        bankCard.setCreateTime(new Date());
        bankCardDao.insert(bankCard);
        return bankCard.getId();
    }

    private void updateAccount(Long userId, Double money, Long articleOrderId,Integer userType,Integer sourceType){
        if(money==null || money==0){
            return;
        }
        //根据userId获取账户
        Account account = accountByOwnerIdAndType(userId, userType);
        //如果为空，则创建新账户
        Double remainAccount = account.getAccount();
        Double newAccount = money+remainAccount;
        account.setAccount(newAccount);
        account.setUpdateTime(new Date());
        accountDao.update(account);

        //插入账户变更记录
        Long accountId = account.getId();
        AccountLog accountLog = new AccountLog();
        accountLog.setAccountId(accountId);
        accountLog.setChangeAccount(money);//变更钱
        accountLog.setStartAccount(remainAccount);//变更前余额
        accountLog.setEndAccount(newAccount);//变更后余额
        accountLog.setSourceType(sourceType);
        accountLog.setSourceId(articleOrderId);
        accountLog.setCreateTime(new Date());
        accountLogDao.insert(accountLog);
    }
}
