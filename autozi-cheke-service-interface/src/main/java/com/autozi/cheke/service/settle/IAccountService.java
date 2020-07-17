package com.autozi.cheke.service.settle;

import com.autozi.cheke.settle.entity.Account;
import com.autozi.cheke.settle.entity.AccountLog;
import com.autozi.cheke.settle.entity.BankCard;
import com.autozi.common.core.page.Page;

import java.util.List;
import java.util.Map;

/**
 * User: long.jin
 * Date: 2017-12-04
 * Time: 14:37
 */
public interface IAccountService {

    public Long insertAccount(Account account);

    public Account accountByOwnerIdAndType(Long ownerId, Integer type);

    public Page<AccountLog> findAccountLogPageForMap(Page<AccountLog> page, Map<String, Object> filters);

    Page<Account> findAccountPage(Page<Account> page, Map<String, Object> filters);

    public Double getTotalTaskMoney(Long userId,Integer type);

    public Double getTaskMoney(Map<String, Object> filters);

    /**
     * 获得奖励金
     * @param userId
     * @param money
     * @param articleOrderId
     */
    public void saveTaskRewardMoney(Long userId,Double money,Long articleOrderId);

    /**
     * 获得推广分成
     * @param userId
     * @param money
     * @param articleOrderId
     */
    public void saveTaskPercentMoney(Long userId,Double money,Long articleOrderId);

    /**
     *
     * @param userId
     * @param money
     * @return
     */
    public boolean isFull(Long userId,Double money);

    /**
     * 任务退款
     * @param userId
     * @param money
     * @param articleId
     * @return
     */
    public Integer refundCk(Long userId,Double money,Long articleId);

    /**
     * 为推广任务付钱
     * @param userId
     * @param money
     * @param articleId
     * @return
     */
    public Integer payTask(Long userId,Double money,Long articleId);

    public void recharge(Long userId, Double money,Long orderId);

    public void insertAccountLog(AccountLog accountLog);

    public List<BankCard> getBankCardByAccountId(Long accountId);

    public Long insertBankCard(BankCard bankCard);

}
