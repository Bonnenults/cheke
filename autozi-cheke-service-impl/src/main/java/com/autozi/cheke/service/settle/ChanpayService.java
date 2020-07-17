package com.autozi.cheke.service.settle;

import com.autozi.cheke.settle.dao.*;
import com.autozi.cheke.settle.entity.AccountOrder;
import com.autozi.cheke.settle.entity.BankCard;
import com.autozi.cheke.settle.entity.ChanpayOrder;
import com.autozi.cheke.settle.entity.DrawCashOrder;
import com.autozi.cheke.settle.type.IAccountOrderConstant;
import com.autozi.cheke.settle.type.IChanpayOrderConstant;
import com.autozi.cheke.settle.type.IDrawCashOrderConstant;
import com.autozi.common.utils.chanpay.util.ChanPayConstant;
import com.autozi.common.utils.chanpay.util.ChanPayUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lbm on 2018/1/9.
 */
@Service
public class ChanpayService implements IChanpayService {
    @Autowired
    private ChanpayOrderDao chanpayOrderDao;
    @Autowired
    private ChanpayOrderService chanpayOrderService;
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
    @Autowired
    private BankCardDao bankCardDao;

    @Override
    public String dsfPay(Long drawCashOrderId) {
        ChanpayOrder chanpayOrder = saveDrawCashChanpayOrder(drawCashOrderId);
        if(chanpayOrder==null){
            return "未找到支付订单";
        }
        // 设用支付接口
        Map<String, String> map = ChanPayUtil.getBaseParameter("cjt_dsf");
        map.put("TransCode", "T10000"); // 交易码
        map.put("OutTradeNo", chanpayOrder.getId().toString()); // 商户网站唯一订单号
        map.put("BusinessType", "0"); // 业务类型
        map.put("BankCommonName", chanpayOrder.getBankCommonName()); // 通用银行名称
        map.put("AcctNo", ChanPayUtil.encrypt(chanpayOrder.getAcctNo(), ChanPayConstant.MERCHANT_PUBLIC_KEY, ChanPayConstant.CHARSET)); // 对手人账号
        map.put("AcctName", ChanPayUtil.encrypt(chanpayOrder.getAcctName(), ChanPayConstant.MERCHANT_PUBLIC_KEY, ChanPayConstant.CHARSET)); // 对手人账户名称
        map.put("TransAmt", chanpayOrder.getTransAmt());
        map.put("CorpPushUrl", ChanPayConstant.dsfUrl);
        String resultStr = "";
        try{
            resultStr = ChanPayUtil.sendPost(map, ChanPayConstant.CHARSET, ChanPayConstant.MERCHANT_PRIVATE_KEY);
            System.out.println(resultStr);
            JSONObject reObj = JSONObject.fromObject(resultStr);
            String acceptStatus = reObj.getString("AcceptStatus");
            if("S".equals(acceptStatus)){
                String flowNo = reObj.getString("FlowNo");
                chanpayOrder.setReFlowNo(flowNo);
                chanpayOrder.setUpdateTime(new Date());
                chanpayOrderDao.update(chanpayOrder);

                DrawCashOrder drawCashOrder = drawCashOrderDao.get(drawCashOrderId);
                drawCashOrder.setStatus(IDrawCashOrderConstant.status.STATUS_PAYING);
                drawCashOrder.setUpdateTime(new Date());
                drawCashOrderDao.update(drawCashOrder);
            }
        }catch (Exception e){
            e.printStackTrace();
            return "系统异常";
        }
        return "申请成功";
    }



    @Override
    public String wxPay(AccountOrder accountOrder) {
        return postPay(accountOrder,IChanpayOrderConstant.payType.PAY_TYPE_WX);
    }

    @Override
    public String aliPay(AccountOrder accountOrder) {
        return postPay(accountOrder,IChanpayOrderConstant.payType.PAY_TYPE_ALI);
    }

    /**
     * 请求接口返回二维码
     * @param accountOrder
     * @param payType
     * @return
     */
    private String postPay(AccountOrder accountOrder,int payType){
        //生成支付订单
        ChanpayOrder chanpayOrder = saveChanpayOrder(accountOrder,payType);

        Map<String, String> map = ChanPayUtil.getBaseParameter("mag_init_code_pay");
        String bankCode = ChanPayConstant.WXPAY;
        if(payType==IChanpayOrderConstant.payType.PAY_TYPE_ALI){
            bankCode = ChanPayConstant.ALIPAY;
        }
        //map.put("ReturnUrl", ChanPayConstant.RETURN_URL);// 前台跳转url
        map.put("OutTradeNo", chanpayOrder.getId().toString());//订单号
        map.put("MchId", ChanPayConstant.MCH_ID);//商户标识
        map.put("TradeType", ChanPayConstant.TRADE_TYPE);//交易类型11=即时 12=担保
        map.put("BankCode", bankCode);//WXPAY:微信渠道 ALIPAY:支付宝渠道
        map.put("TradeAmount", chanpayOrder.getTransAmt());
        map.put("GoodsName", "推广充值");//商品名称
        map.put("Subject", "推广充值订单");
        map.put("OrderStartTime",ChanPayUtil.getNowDateFormat("yyyyMMddHHmmss"));
        map.put("NotifyUrl", ChanPayConstant.scpUrl);
        map.put("SpbillCreateIp", ChanPayConstant.SpbillCreateIp);
        try{
            String resultStr = ChanPayUtil.sendPost(map, ChanPayConstant.CHARSET, ChanPayConstant.MERCHANT_PRIVATE_KEY);
            JSONObject reObj = JSONObject.fromObject(resultStr);
            String acceptStatus = reObj.getString("AcceptStatus");
            if("S".equals(acceptStatus)){
                String codeUrl = reObj.getString("CodeUrl");
//                String innerTradeNo = reObj.getString("InnerTradeNo");
//                chanpayOrder.setReFlowNo(innerTradeNo);
//                chanpayOrderDao.update(chanpayOrder);
                return codeUrl;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private ChanpayOrder saveChanpayOrder(AccountOrder accountOrder,int payType){
        ChanpayOrder chanpayOrder = new ChanpayOrder();
        chanpayOrder.setOrderId(accountOrder.getId());
        chanpayOrder.setOrderType(IChanpayOrderConstant.orderType.ORDER_TYPE_RECHARGE);
        chanpayOrder.setPayType(payType);
        chanpayOrder.setCreateTime(new Date());
        chanpayOrder.setStatus(IAccountOrderConstant.status.STATUS_CREATE);
        chanpayOrder.setTransAmt(accountOrder.getRealMoney().toString());
        chanpayOrderDao.insert(chanpayOrder);
        return chanpayOrder;
    }

    private ChanpayOrder saveDrawCashChanpayOrder(Long drawCashOrderId){
        DrawCashOrder drawCashOrder =drawCashOrderDao.get(drawCashOrderId);
        //生成畅捷支付订单
        //获取银行卡信息
        BankCard bankCard = bankCardDao.get(drawCashOrder.getBankCardId());
        String bankName = bankCard.getBankName();
        String cardNumber = bankCard.getCardNumber();
        //插入支付订单
        ChanpayOrder chanpayOrder = new ChanpayOrder();
        chanpayOrder.setOrderId(drawCashOrder.getId());
        chanpayOrder.setBankCommonName(bankName);
        chanpayOrder.setAcctNo(cardNumber);
        chanpayOrder.setStatus(IAccountOrderConstant.status.STATUS_CREATE);
        chanpayOrder.setAcctName(bankCard.getName());
        chanpayOrder.setTransAmt(drawCashOrder.getMoney().toString());
        chanpayOrder.setPayType(IChanpayOrderConstant.payType.PAY_TYPE_DSF);//支付方式
        chanpayOrder.setOrderType(IChanpayOrderConstant.orderType.ORDER_TYPE_WITHDRAW);//订单类型
        chanpayOrder.setCreateTime(new Date());
        chanpayOrder.setUpdateTime(new Date());
        chanpayOrderDao.insert(chanpayOrder);
        return chanpayOrder;
    }

    public ChanpayOrder getChanpayOrderByOrderId(Long orderId){
        if(orderId==null){
            return null;
        }
        Map<String, Object> filters = new HashMap<>();
        filters.put("orderId",orderId);
        List<ChanpayOrder> list = chanpayOrderDao.findListForMap(filters);
        if(list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public String eBankPay(AccountOrder accountOrder) {
        ChanpayOrder chanpayOrder = saveChanpayOrder(accountOrder,IChanpayOrderConstant.payType.PAY_TYPE_EBANK);

        Map<String, String> map = ChanPayUtil.getBaseParameter("nmg_ebank_pay");
        //网银支付 api 业务参数
        map.put("OutTradeNo",chanpayOrder.getId().toString());
        map.put("MchId", ChanPayConstant.MCH_ID);
        map.put("ChannelType", "02");
        map.put("BizType", "01");
        map.put("CardFlag", "01");
        map.put("PayFlag", "01");
        map.put("ServiceType", "01");
        map.put("TradeType", "00");
        map.put("GoodsType", "00");
        map.put("GoodsName", "推广充值");
        map.put("Currency", "00");
        map.put("OrderStartTime", ChanPayUtil.getNowDateFormat("yyyyMMddHHmmss"));
        map.put("ExpiredTime", "1d");
        map.put("OrderAmt", chanpayOrder.getTransAmt());
        map.put("NotifyUrl",ChanPayConstant.bankNotifyUrl);
        map.put("UserIp", ChanPayConstant.SpbillCreateIp);
        try{
            return ChanPayUtil.sendEBankPost(map, ChanPayConstant.CHARSET, ChanPayConstant.MERCHANT_PRIVATE_KEY);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(ChanpayOrder chanpayOrder) {
        chanpayOrder.setUpdateTime(new Date());
        chanpayOrderDao.update(chanpayOrder);
    }

    @Override
    public void eBankPayCallBack(Long orderId, String tradeStatus) {
        //获取支付订单
        ChanpayOrder chanpayOrder = chanpayOrderDao.get(orderId);
        //获取充值订单
        Long accountOrderId = chanpayOrder.getOrderId();
        AccountOrder accountOrder = accountOrderDao.get(accountOrderId);
        //更新订单状态
        if("TRADE_SUCCESS".equals(tradeStatus)){
            chanpayOrder.setStatus(IAccountOrderConstant.status.STATUS_END);
            accountOrder.setStatus(IAccountOrderConstant.status.STATUS_END);
            //只有成功才更新账户的钱
            accountService.recharge(accountOrder.getPartyId(),accountOrder.getAccountMoney(),accountOrderId);
        }else if("TRADE_CLOSED".equals(tradeStatus)){
            chanpayOrder.setStatus(IAccountOrderConstant.status.STATUS_CANCEL);
            accountOrder.setStatus(IAccountOrderConstant.status.STATUS_CANCEL);
            accountOrder.setNote("支付失败");
        }

        chanpayOrder.setUpdateTime(new Date());
        chanpayOrderDao.update(chanpayOrder);
        accountOrder.setPayTime(new Date());
        accountOrder.setUpdateTime(new Date());
        accountOrderDao.update(accountOrder);
    }

    @Override
    public void scpPayCallBack(Long orderId, String tradeStatus) {
        //获取支付订单
        ChanpayOrder chanpayOrder = chanpayOrderDao.get(orderId);
        //获取充值订单
        Long accountOrderId = chanpayOrder.getOrderId();
        AccountOrder accountOrder = accountOrderDao.get(accountOrderId);
        //更新订单状态
        if("TRADE_SUCCESS".equals(tradeStatus)){
            chanpayOrder.setStatus(IAccountOrderConstant.status.STATUS_END);
            accountOrder.setStatus(IAccountOrderConstant.status.STATUS_END);
            //更新账户的钱
            accountService.recharge(accountOrder.getPartyId(),accountOrder.getAccountMoney(),accountOrderId);
        }else if("TRADE_CLOSED".equals(tradeStatus)){
            chanpayOrder.setStatus(IAccountOrderConstant.status.STATUS_CANCEL);
            accountOrder.setStatus(IAccountOrderConstant.status.STATUS_CANCEL);
            accountOrder.setNote("支付失败");
        }
        chanpayOrder.setUpdateTime(new Date());
        chanpayOrderDao.update(chanpayOrder);
        accountOrder.setUpdateTime(new Date());
        accountOrder.setPayTime(new Date());
        accountOrderDao.update(accountOrder);
    }

    @Override
    public void dsfPayCallBack(Long orderId, String tradeStatus) {
        //获取支付订单
        ChanpayOrder chanpayOrder = chanpayOrderDao.get(orderId);
        //获取充值订单
        Long drawCashOrderId = chanpayOrder.getOrderId();
        DrawCashOrder drawCashOrder = drawCashOrderDao.get(drawCashOrderId);
        //更新订单状态
        if("WITHDRAWAL_SUCCESS".equals(tradeStatus)){
            chanpayOrder.setStatus(IDrawCashOrderConstant.status.STATUS_END);
            chanpayOrder.setUpdateTime(new Date());
            drawCashOrder.setStatus(IDrawCashOrderConstant.status.STATUS_END);
            drawCashOrder.setPayTime(new Date());
            drawCashOrder.setUpdateTime(new Date());
        }
        chanpayOrderDao.update(chanpayOrder);
        drawCashOrderDao.update(drawCashOrder);
    }

    @Override
    public void testCallBack(Long orderId) {
        AccountOrder accountOrder = accountOrderDao.get(orderId);
        //更新订单状态
        accountOrder.setStatus(IAccountOrderConstant.status.STATUS_END);
        accountOrder.setPayTime(new Date());
        accountOrderDao.update(accountOrder);
        //更新账户的钱
        accountService.recharge(accountOrder.getPartyId(),accountOrder.getAccountMoney(),orderId);
    }

    @Override
    public void testDsfCallBack(Long orderId) {
        DrawCashOrder drawCashOrder = drawCashOrderDao.get(orderId);
        drawCashOrder.setStatus(IAccountOrderConstant.status.STATUS_END);
        drawCashOrder.setUpdateTime(new Date());
        drawCashOrder.setPayTime(new Date());
        drawCashOrderDao.update(drawCashOrder);
    }
}
