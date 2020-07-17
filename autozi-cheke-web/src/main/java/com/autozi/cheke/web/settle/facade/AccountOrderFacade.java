package com.autozi.cheke.web.settle.facade;

import com.autozi.cheke.party.entity.Party;
import com.autozi.cheke.party.type.IPartyType;
import com.autozi.cheke.service.party.IPartyService;
import com.autozi.cheke.service.settle.IAccountOrderService;
import com.autozi.cheke.service.settle.IChanpayOrderService;
import com.autozi.cheke.service.settle.IChanpayService;
import com.autozi.cheke.settle.entity.AccountOrder;
import com.autozi.cheke.settle.entity.ChanpayOrder;
import com.autozi.cheke.settle.entity.ChanpayOrderBack;
import com.autozi.cheke.settle.type.IAccountOrderConstant;
import com.autozi.cheke.user.entity.User;
import com.autozi.cheke.web.settle.vo.AccountOrderVo;
import com.autozi.common.core.page.Page;
import com.autozi.common.utils.o2o.PageUtil;
import com.autozi.common.utils.util2.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User: long.jin
 * Date: 2017-12-04
 * Time: 15:18
 */
@Component
public class AccountOrderFacade {

    @Autowired
    private IAccountOrderService accountOrderService;
    @Autowired
    private IPartyService partyService;
    @Autowired
    private IChanpayOrderService chanpayOrderService;
    @Autowired
    private IChanpayService chanpayService;


    public Page<AccountOrderVo> findPageForMap(Page<AccountOrder> page, Map<String, Object> filters) {
        filters=DateUtils.String2Date(filters);
        page = accountOrderService.findPageForMap(page, filters);
        Page<AccountOrderVo> viewPage = new Page<>();
        List<AccountOrderVo> viewList = new ArrayList<>();
        for (AccountOrder accountOrder : page.getResult()) {
            AccountOrderVo accountVo = new AccountOrderVo();
            BeanUtils.copyProperties(accountOrder, accountVo);
//            accountVo.setCode(accountOrder.getId().toString());
            Double rate = accountVo.getRateFee()==null?0d:accountVo.getRateFee();
            Double slottingFee = accountVo.getSlottingFee()==null?0d:accountVo.getSlottingFee();
            accountVo.setRateFee(rate+slottingFee);
            viewList.add(accountVo);
        }
        PageUtil.convertPage(page, viewPage, viewList);
        return viewPage;
    }


    public Page<AccountOrderVo> findAdminPageForMap(Page<AccountOrder> page, Map<String, Object> filters) {
        filters=DateUtils.String2Date(filters);
        page = accountOrderService.findPageForMap(page, filters);
        Page<AccountOrderVo> viewPage = new Page<>();
        List<AccountOrderVo> viewList = new ArrayList<>();
        for (AccountOrder accountOrder : page.getResult()) {
            AccountOrderVo accountVo = new AccountOrderVo();
            BeanUtils.copyProperties(accountOrder, accountVo);
            Party party = partyService.getPartyById(accountOrder.getPartyId());
//            accountVo.setCode(accountOrder.getId().toString());
            accountVo.setPartyName(party.getName());
            String partyClassName = "";
            if(party.getPartyClass()!=null){
                partyClassName = IPartyType.partyClassMap.get(party.getPartyClass().toString());
            }
            accountVo.setPartyClassName(partyClassName);
            accountVo.setStatusName(IAccountOrderConstant.status.statusMap.get(accountOrder.getStatus().toString()));
            viewList.add(accountVo);
        }
        PageUtil.convertPage(page, viewPage, viewList);
        return viewPage;
    }

    /**
     * 提现订单
     *
     * @param page
     * @param filters
     * @return
     */
    public Page<AccountOrderVo> findAdminGetMoneyOrderPageForMap(Page<AccountOrder> page, Map<String, Object> filters) {
        DateUtils.String2Date(filters);
        page = accountOrderService.findPageForMap(page, filters);
        Page<AccountOrderVo> viewPage = new Page<>();
        List<AccountOrderVo> viewList = new ArrayList<>();
        for (AccountOrder accountOrder : page.getResult()) {
            AccountOrderVo accountVo = new AccountOrderVo();
            BeanUtils.copyProperties(accountOrder, accountVo);
            Party party = partyService.getPartyById(accountOrder.getPartyId());
            accountVo.setPartyName(party.getName());
            String partyClassName = "";
            if(party.getPartyClass()!=null){
                partyClassName = IPartyType.partyClassMap.get(party.getPartyClass().toString());
            }
            accountVo.setPartyClassName(partyClassName);
            accountVo.setStatusName(IAccountOrderConstant.status.statusMap.get(accountOrder.getStatus().toString()));
            viewList.add(accountVo);
        }
        PageUtil.convertPage(page, viewPage, viewList);
        return viewPage;
    }

    /**
     * 充值保存
     *
     * @param user
     * @param realMoney
     */
    public Long submitRechargeMoney(User user, Double realMoney) {
        return accountOrderService.submitRechargeMoney(user, realMoney);
    }

    public AccountOrder getAccountOrderById(Long id) {
        return accountOrderService.getAccountOrderById(id);
    }

    /**
     * 微信支付
     * @param user
     * @param orderId
     * @return
     */
    public String wxPay(User user,Long orderId) {
        AccountOrder order = accountOrderService.getAccountOrderById(orderId);
        return chanpayService.wxPay(order);
    }

    /**
     * 支付宝支付
     * @param user
     * @param orderId
     * @return
     */
    public String aliPay(User user,Long orderId) {
        AccountOrder order = accountOrderService.getAccountOrderById(orderId);
        return chanpayService.aliPay(order);
    }

    /**
     * 网银支付
     * @param user
     * @param orderId
     * @return
     */
    public String eBankPay(User user, Long orderId) {
        AccountOrder order = accountOrderService.getAccountOrderById(orderId);
        return chanpayService.eBankPay(order);
    }

    //调用银行回调
    public void eBankPayCallBack(Long orderId,String tradeStatus) {
        chanpayService.eBankPayCallBack(orderId,tradeStatus);
    }

    public void testCallBack(Long orderId) {
        chanpayService.testCallBack(orderId);
    }

    //代收付回调
    public void dsfPayCallBack(Long orderId,String tradeStatus) {
        chanpayService.dsfPayCallBack(orderId,tradeStatus);
    }

    //扫码回调
    public void scpPayCallBack(Long orderId,String tradeStatus) {
        chanpayService.scpPayCallBack(orderId,tradeStatus);
    }

    public ChanpayOrder getChanpayOrderById(Long id){
        return chanpayOrderService.getChanpayOrderById(id);
    }

    public void saveChanpayOrderBack(ChanpayOrderBack chanpayOrderBack){
        chanpayOrderService.insertOrderBack(chanpayOrderBack);
    }

    public Map<String,Object> getTotalMoney(Map<String,Object> map){
        return accountOrderService.getTotalMoney(map);
    }

}
