package com.autozi.cheke.service.settle;

import com.autozi.cheke.settle.entity.AccountOrder;
import com.autozi.cheke.settle.entity.ChanpayOrder;
import com.autozi.cheke.settle.entity.DrawCashOrder;

/**
 * Created by lbm on 2018/1/9.
 */
public interface IChanpayService {
    public String dsfPay(Long drawCashOrderId);
    public String wxPay(AccountOrder accountOrder);
    public String aliPay(AccountOrder accountOrder);
    public String eBankPay(AccountOrder accountOrder);
    public void update(ChanpayOrder chanpayOrder);

    /**
     * 根据充值订单ID获取支付订单
     * @param orderId
     * @return
     */
    public ChanpayOrder getChanpayOrderByOrderId(Long orderId);
    /**
     * 银行回调处理
     * @param orderId
     * @param tradeStatus
     * @return
     */
    public void eBankPayCallBack(Long orderId,String tradeStatus);

    /**
     * 扫码回调处理
     * @param orderId
     * @param tradeStatus
     * @return
     */
    public void scpPayCallBack(Long orderId,String tradeStatus);

    /**
     * 代收付回调处理
     * @param orderId
     * @param tradeStatus
     * @return
     */
    public void dsfPayCallBack(Long orderId,String tradeStatus);

    public void testCallBack(Long orderId);

    public void testDsfCallBack(Long orderId);
}
