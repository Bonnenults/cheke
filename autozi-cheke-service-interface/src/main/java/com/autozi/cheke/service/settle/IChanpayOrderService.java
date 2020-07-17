package com.autozi.cheke.service.settle;

import com.autozi.cheke.settle.entity.ChanpayOrder;
import com.autozi.cheke.settle.entity.ChanpayOrderBack;

import java.util.List;

/**
 * Created by lbm on 2018/1/10.
 */
public interface IChanpayOrderService {
    public Long insert(ChanpayOrder chanpayOrder);
    public void insertOrderBack(ChanpayOrderBack chanpayOrderBack);
    public Long update(ChanpayOrder chanpayOrder);
    public ChanpayOrder getChanpayOrderById(Long id);

    /**
     * 根据商品订单号查询所有支付记录
     * @param orderId
     * @return
     */
    public List<ChanpayOrder> listByOrderId(Long orderId);
    /**
     * 根据商品订单号查询最新的支付记录
     * @param orderId
     * @return
     */
    public ChanpayOrder getByOrderId(Long orderId);


}
