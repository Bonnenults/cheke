package com.autozi.cheke.web.settle.controller;

import com.autozi.cheke.party.type.IPartyType;
import com.autozi.cheke.settle.entity.AccountLog;
import com.autozi.cheke.settle.entity.AccountOrder;
import com.autozi.cheke.settle.type.IAccountConstant;
import com.autozi.cheke.settle.type.IAccountOrderConstant;
import com.autozi.cheke.settle.type.IDrawCashOrderConstant;
import com.autozi.cheke.user.entity.User;
import com.autozi.cheke.util.mvc.BaseController;
import com.autozi.cheke.web.settle.facade.AccountFacade;
import com.autozi.cheke.web.settle.facade.AccountOrderFacade;
import com.autozi.cheke.web.settle.vo.AccountLogVo;
import com.autozi.cheke.web.settle.vo.AccountOrderVo;
import com.autozi.common.core.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * User: long.jin
 * Date: 2017-12-12
 * Time: 10:42
 */
@Controller
@RequestMapping("/accountOrder")
public class AccountOrderController extends BaseController {
    @Autowired
    private AccountOrderFacade accountOrderFacade;
    @Autowired
    private AccountFacade accountFacade;

    /**
     * 平台查看充值订单列表
     *
     * @param request
     * @param uiModel
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/admin/list/listAccountOrder.action")
    public String listAccountOrder(HttpServletRequest request, Model uiModel, String ajax) throws UnsupportedEncodingException {
        Page<AccountOrder> page = buildPage(request);
        Map<String, Object> filters = buildFilter(request, uiModel);
        //filters.put("type", IAccountOrderConstant.type.TYPE_RECHARGE);//充值订单
        Page<AccountOrderVo> orderPage = accountOrderFacade.findAdminPageForMap(page, filters);
        Map<String, Object> map = new HashMap<>();
        map.put("status", IDrawCashOrderConstant.status.STATUS_END);
        Map<String,Object> result = accountOrderFacade.getTotalMoney(map);
        uiModel.addAttribute("totalMoney", result.get("totalMoney").toString());
        uiModel.addAttribute("page", orderPage);
        uiModel.addAttribute("statusMap",IAccountOrderConstant.status.ckStatusMap);
        uiModel.addAttribute("partyClassMap", IPartyType.partyClassMap);
        this.pageInfoByMap(uiModel, orderPage, filters);
        if (ajax != null) {
            return "/admin/settle/accountOrder/listAccountOrder_ajax";
        }
        return "/admin/settle/accountOrder/listAccountOrder.html";
    }


    /**
     * 车客端账户交易明细
     *
     * @param request
     * @param uiModel
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/admin/accountLog/listAccountLog.action")
    public String listAdminAccountLog(HttpServletRequest request, Model uiModel, String ajax) throws UnsupportedEncodingException {
        Map<String, Object> filter = buildFilter(request, uiModel);
        Page<AccountLog> page = buildPage(request);
        filter.put("accountType", IAccountConstant.type.TYPE_CK);
        Page<AccountLogVo> voPage = accountFacade.findAccountLogPageForAdmin(page, filter);
        this.pageInfoByMap(uiModel, voPage, filter);
        uiModel.addAttribute("page", voPage);
        uiModel.addAttribute("logTypeMap", IAccountConstant.AccountLogSourceType.typeMap);
        if (ajax != null) {
            return "/admin/settle/account/log/listAccountLog_ajax";
        }
        return "/admin/settle/account/log/listAccountLog.html";
    }


    /**
     * 平台查看体现订单
     *
     * @param request
     * @param uiModel
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/admin/listGetMoney/listGetMoneyAccountOrder.action")
    public String listGetMoneyAccountOrder(HttpServletRequest request, Model uiModel, String ajax) throws UnsupportedEncodingException {
        Map<String, Object> filters = buildFilter(request, uiModel);
        User user = getCurrentUser();
        Page<AccountOrder> page = new Page<>();
        filters.put("type", IAccountOrderConstant.type.TYPE_GETMONEY);//提现订单
        Page<AccountOrderVo> orderPage = accountOrderFacade.findAdminPageForMap(page, filters);
        uiModel.addAttribute("orderPage", orderPage);
        this.pageInfoByMap(uiModel, orderPage, filters);
        if (ajax != null) {
            return "/admin/settle/accountOrder/listGetMoneyAccountOrder_ajax";
        }
        uiModel.addAttribute("statusMap", IAccountOrderConstant.status.ckStatusMap);
        uiModel.addAttribute("partyClassMap", IPartyType.partyClassMap);
        return "/admin/settle/accountOrder/listGetMoneyAccountOrder.html";
    }


}
