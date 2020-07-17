package com.autozi.cheke.web.settle.controller;

import com.autozi.cheke.settle.entity.AccountOrder;
import com.autozi.cheke.settle.entity.ChanpayOrder;
import com.autozi.cheke.settle.entity.ChanpayOrderBack;
import com.autozi.cheke.user.entity.User;
import com.autozi.cheke.util.mvc.BaseController;
import com.autozi.cheke.web.settle.facade.AccountFacade;
import com.autozi.cheke.web.settle.facade.AccountOrderFacade;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lbm on 2018/1/15.
 */
@Controller
@RequestMapping("/chanpay")
public class ChanPayController extends BaseController {
    @Autowired
    private AccountFacade accountFacade;
    @Autowired
    private AccountOrderFacade accountOrderFacade;

    @RequestMapping("/eBankPayCallBack.action")
    public void eBankPayCallBack(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("**************************接到畅捷通知************************************");
        String notifyId = request.getParameter("notify_id");//通知ID
        String notifyType = request.getParameter("notify_type");//通知类型
        String notifyTime = request.getParameter("notify_time");//通知时间
        String inputCharset = request.getParameter("_input_charset");//参数字符集编码
        String sign = request.getParameter("sign");//签名
        String signType = request.getParameter("sign_type");//签名方式
        String version = request.getParameter("version");//版本号
        String outerTradeNo = request.getParameter("outer_trade_no");//畅捷支付合作商户网站唯一订单号
        String innerTradeNo = request.getParameter("inner_trade_no");//畅捷平台订单号
        String tradeStatus = request.getParameter("trade_status");//通知交易状态
        String tradeAmount = request.getParameter("trade_amount");
        String gmtCreate = request.getParameter("gmt_create");
        String gmtPayment = request.getParameter("gmt_payment");
        String gmtClose = request.getParameter("gmt_close");
        String extension = request.getParameter("extension");

        try {
            //根据订单号获取支付的订单
            Long orderId = Long.parseLong(outerTradeNo);
            ChanpayOrderBack orderBack = new ChanpayOrderBack();
            orderBack.setChanpayOrderId(orderId);
            orderBack.setInnerTradeNo(innerTradeNo);
            orderBack.setTradeStatus(tradeStatus);
            orderBack.setTradeAmount(tradeAmount);
            orderBack.setGmtCreate(gmtCreate);
            accountOrderFacade.saveChanpayOrderBack(orderBack);

            //更新订单状态
            accountOrderFacade.eBankPayCallBack(orderId,tradeStatus);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.response(response, "success");
        }
        this.response(response, "success");
    }

    @RequestMapping("/scpPayCallBack.action")
    public void scpPayCallBack(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("**************************接到畅捷通知************************************");
        String notifyId = request.getParameter("notify_id");//通知ID
        String notifyType = request.getParameter("notify_type");//通知类型
        String notifyTime = request.getParameter("notify_time");//通知时间
        String inputCharset = request.getParameter("_input_charset");//参数字符集编码
        String sign = request.getParameter("sign");//签名
        String signType = request.getParameter("sign_type");//签名方式
        String version = request.getParameter("version");//版本号
        String outerTradeNo = request.getParameter("outer_trade_no");//畅捷支付合作商户网站唯一订单号
        String innerTradeNo = request.getParameter("inner_trade_no");//畅捷平台订单号
        String tradeStatus = request.getParameter("trade_status");//通知交易状态
        String tradeAmount = request.getParameter("trade_amount");
        String gmtCreate = request.getParameter("gmt_create");
        String gmtPayment = request.getParameter("gmt_payment");
        String gmtClose = request.getParameter("gmt_close");
        String extension = request.getParameter("extension");

        try {
            //根据订单号获取支付的订单
            Long orderId = Long.parseLong(outerTradeNo);
            ChanpayOrderBack orderBack = new ChanpayOrderBack();
            orderBack.setChanpayOrderId(orderId);
            orderBack.setInnerTradeNo(innerTradeNo);
            orderBack.setTradeStatus(tradeStatus);
            orderBack.setTradeAmount(tradeAmount);
            orderBack.setGmtCreate(gmtCreate);
            accountOrderFacade.saveChanpayOrderBack(orderBack);

            //更新订单状态
            accountOrderFacade.scpPayCallBack(orderId,tradeStatus);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.response(response, "success");
        }
        this.response(response, "success");
    }

    @RequestMapping("/dsfPayCallBack.action")
    public void dsfPayCallBack(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("**************************接到畅捷通知************************************");
        String notifyId = request.getParameter("notify_id");//通知ID
        String notifyType = request.getParameter("notify_type");//通知类型
        String notifyTime = request.getParameter("notify_time");//通知时间
        String inputCharset = request.getParameter("_input_charset");//参数字符集编码
        String sign = request.getParameter("sign");//签名
        String signType = request.getParameter("sign_type");//签名方式
        String version = request.getParameter("version");//版本号

        String outerTradeNo = request.getParameter("outer_trade_no");//畅捷支付合作商户网站唯一订单号
        String innerTradeNo = request.getParameter("inner_trade_no");//畅捷平台订单号
        String tradeStatus = request.getParameter("withdrawal_status");//通知交易状态
        String tradeAmount = request.getParameter("withdrawal_amount");
        String gmtCreate = request.getParameter("gmt_create");
        String gmtPayment = request.getParameter("gmt_payment");
        String gmtClose = request.getParameter("gmt_close");
        String extension = request.getParameter("extension");

        try {
            //根据订单号获取支付的订单
            Long orderId = Long.parseLong(outerTradeNo);
            ChanpayOrderBack orderBack = new ChanpayOrderBack();
            orderBack.setChanpayOrderId(orderId);
            orderBack.setInnerTradeNo(innerTradeNo);
            orderBack.setTradeStatus(tradeStatus);
            orderBack.setTradeAmount(tradeAmount);
            orderBack.setGmtCreate(gmtCreate);
            accountOrderFacade.saveChanpayOrderBack(orderBack);

            //更新订单状态
            accountOrderFacade.dsfPayCallBack(orderId,tradeStatus);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.response(response, "success");
        }
        this.response(response, "success");
    }
}
