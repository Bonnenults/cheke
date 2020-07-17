package com.autozi.cheke.service.settle;

import com.autozi.cheke.service.basic.RunningNumberHelper;
import com.autozi.cheke.settle.dao.AccountOrderDao;
import com.autozi.cheke.settle.entity.AccountOrder;
import com.autozi.cheke.settle.type.IAccountOrderConstant;
import com.autozi.cheke.user.entity.User;
import com.autozi.common.core.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * User: long.jin
 * Date: 2017-12-04
 * Time: 15:15
 */
@Service
public class AccountOrderService implements IAccountOrderService {

    @Autowired
    private AccountOrderDao accountOrderDao;

    @Override
    public Page<AccountOrder> findPageForMap(Page<AccountOrder> page, Map<String, Object> filters) {
        return accountOrderDao.findPageForMap(page,filters);
    }

    @Override
    public AccountOrder getAccountOrderById(Long id) {
        return accountOrderDao.get(id);
    }

    @Override
    public void update(AccountOrder accountOrder) {
        accountOrderDao.update(accountOrder);
    }

    @Override
    public Long submitRechargeMoney(User user, Double realMoney) {
        AccountOrder order = buildAccountOrder(user,realMoney);
        accountOrderDao.insert(order);
        return order.getId();
    }

    private AccountOrder buildAccountOrder(User user, Double realMoney){
        AccountOrder order = new AccountOrder();
        order.setCode(RunningNumberHelper.getAccountOrderCode());
        order.setPartyId(user.getPartyId());
        order.setUserId(user.getId());
        Date now = new Date();
        order.setUpdateTime(now);
        order.setCreateTime(now);
        order.setType(IAccountOrderConstant.type.TYPE_RECHARGE);
        order.setStatus(IAccountOrderConstant.status.STATUS_CREATE);
        order.setInvoiceStatus(0);//未开票
        double rateMoney = mul(realMoney,IAccountOrderConstant.rateFee);//向上取整，保留2位小数
        double slottingMoney = mul(realMoney,IAccountOrderConstant.slottingFee);//向上取整，保留2位小数
        order.setRateFee(rateMoney);
        order.setSlottingFee(slottingMoney);
        order.setRealMoney(realMoney);
        double totalFee = add(rateMoney,slottingMoney);
        order.setAccountMoney(sub(realMoney,totalFee));//向下取整，保留2位小数
        return order;
    }


    /**
     * <PRE>
     * <p/>
     * 中文描述：用于指定精度的乘法 采用 进位  不是四舍五入
     * <p/>
     * </PRE>
     *
     * @param v1
     * @param v2
     * @return
     */
    public static double mul(double v1, double v2) {
        BigDecimal a1 = new BigDecimal(Double.toString(v1));
        BigDecimal a2 = new BigDecimal(Double.toString(v2));
        return a1.multiply(a2).setScale(2, BigDecimal.ROUND_UP).doubleValue();
    }

    /**
     * 两个String类型的数值相加
     *
     * @param v1
     * @param v2
     * @return
     */
    public static double add(double v1, double v2) {
        BigDecimal a1 = new BigDecimal(v1);
        BigDecimal a2 = new BigDecimal(v2);
        return a1.add(a2).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    /**
     * 两个String类型的数值相减
     *
     * @param v1
     * @param v2
     * @return 两个参数的差
     */
    public static double sub(double v1, double v2) {
        BigDecimal a1 = new BigDecimal(v1);
        BigDecimal a2 = new BigDecimal(v2);
        return a1.subtract(a2).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
    }

    @Override
    public Map<String, Object> getTotalMoney(Map<String, Object> map) {
        return accountOrderDao.getTotalMoney(map);
    }

    @Override
    public void syncAccountOrder(){
        Calendar now= Calendar.getInstance();
        now.add(Calendar.DATE,-1);
        AccountOrder accountOrder = new AccountOrder();
        accountOrder.setCreateTime(now.getTime());
        accountOrderDao.syncAccountOrder(accountOrder);
    }

    public static void main(String[] args) {
        Calendar now= Calendar.getInstance();
        now.add(Calendar.DATE,-1);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        System.out.println(format.format(now.getTime()));
    }

}
