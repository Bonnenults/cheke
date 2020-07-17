package com.autozi.cheke.web.settle.controller;

import com.autozi.cheke.article.entity.Article;
import com.autozi.cheke.article.type.ArticleStatus;
import com.autozi.cheke.party.type.IPartyType;
import com.autozi.cheke.settle.entity.Account;
import com.autozi.cheke.settle.entity.AccountLog;
import com.autozi.cheke.settle.entity.AccountOrder;
import com.autozi.cheke.settle.type.IAccountConstant;
import com.autozi.cheke.settle.type.IAccountOrderConstant;
import com.autozi.cheke.settle.type.IDrawCashOrderConstant;
import com.autozi.cheke.user.entity.User;
import com.autozi.cheke.util.mvc.BaseController;
import com.autozi.cheke.util.web.QRGenerator;
import com.autozi.cheke.web.article.vo.ArticleVo;
import com.autozi.cheke.web.settle.facade.AccountFacade;
import com.autozi.cheke.web.settle.facade.AccountOrderFacade;
import com.autozi.cheke.web.settle.vo.AccountLogVo;
import com.autozi.cheke.web.settle.vo.AccountOrderVo;
import com.autozi.cheke.web.settle.vo.AccountVo;
import com.autozi.common.core.page.Page;
import com.autozi.common.utils.util1.BigDecimalTools;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 资金账户控制类
 * User: long.jin
 * Date: 2017-12-04
 * Time: 14:44
 */
@Controller
@RequestMapping("/account")
public class AccountController extends BaseController {

    @Autowired
    private AccountFacade accountFacade;
    @Autowired
    private AccountOrderFacade accountOrderFacade;

    /**
     * 车客端查找用户列表
     *
     * @param request
     * @param uiModel
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/ck/list/showAccount.action")
    public String showAccount(HttpServletRequest request, Model uiModel) throws Exception {
        User user = getCurrentUser();
        AccountVo accountVo = accountFacade.getAccountVoByPartyId(user.getPartyId());
        uiModel.addAttribute("account", accountVo);
        Map<String, Object> filters = new HashMap<>();
        filters.put("partyId", user.getPartyId());
        filters.put("status", IAccountOrderConstant.status.STATUS_CREATE);
        Page<AccountOrder> page = buildPage(request);
        Page<AccountOrderVo> orderPage = accountOrderFacade.findPageForMap(page, filters);
        uiModel.addAttribute("orderPage", orderPage);
        //获取昨日消费
        Map<String, Object> filters1 = new HashMap<>();
        filters1.put("partyId",user.getPartyId());
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        String date1 = format.format(cal.getTime());
        filters1.put("createTimeStart", (Date)format.parse(date1));
        String dateEnd = format.format(new Date());
        filters1.put("createTimeEnd",(Date)format.parse(dateEnd));
        Double todayCost = accountFacade.getTotalMoney(filters1);
        uiModel.addAttribute("todayCost", todayCost);
        //获取本周花费金额
        Calendar calWeek = Calendar.getInstance();
        calWeek.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calWeek.set(Calendar.HOUR_OF_DAY, 0);
        calWeek.set(Calendar.MINUTE, 0);
        calWeek.set(Calendar.SECOND, 0);
        filters1.put("createTimeStart", calWeek.getTime());
        filters1.put("createTimeEnd",new Date());
        Double weekCost = accountFacade.getTotalMoney(filters1);
        uiModel.addAttribute("weekCost", weekCost);
        this.pageInfoByMap(uiModel, "/account/ck/list/listAccountOrderAjax.action", orderPage, "changeId", filters);
        return "/settle/account/showAccount.html";
    }

    /**
     * 车客端进入充值页面
     * @param request
     * @param uiModel
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/ck/list/rechargeAccount.action")
    public String rechargeAccount(HttpServletRequest request, Model uiModel) throws UnsupportedEncodingException {
        User user = getCurrentUser();
        AccountVo accountVo = accountFacade.getAccountVoByPartyId(user.getPartyId());
        uiModel.addAttribute("partyLoginName", user.getLoginName());
        uiModel.addAttribute("account", accountVo);
        uiModel.addAttribute("rateFee",IAccountOrderConstant.rateFee);
        uiModel.addAttribute("slottingFee",IAccountOrderConstant.slottingFee);
        uiModel.addAttribute("allFee", BigDecimalTools.add(IAccountOrderConstant.rateFee,IAccountOrderConstant.slottingFee));
        return "/settle/account/rechargeAccount.html";
    }

    /**
     * 车客提交充值订单
     *
     * @param request
     * @param uiModel
     * @param  realMoney 充值金额
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/ck/list/submitRechargeMoney.action")
    public String submitRechargeMoney(HttpServletRequest request,HttpServletResponse response, Model uiModel,Double realMoney) throws UnsupportedEncodingException {
        User user = getCurrentUser();
        Long orderId = accountOrderFacade.submitRechargeMoney(user,realMoney);
        AccountOrder accountOrder = accountOrderFacade.getAccountOrderById(orderId);
        uiModel.addAttribute("orderId", orderId);
        uiModel.addAttribute("code", accountOrder.getCode());
        uiModel.addAttribute("realMoney", realMoney);
        return "/settle/account/payAccountOrder.html";
    }

    @RequestMapping("/ck/list/toPay.action")
    public String toPay(HttpServletRequest request,Model uiModel,HttpServletResponse response, Long accountOrderId) throws UnsupportedEncodingException {
        User user = getCurrentUser();
        AccountOrder accountOrder = accountOrderFacade.getAccountOrderById(accountOrderId);
        uiModel.addAttribute("accountOrder", accountOrder);
        uiModel.addAttribute("orderId", accountOrder.getId());
        uiModel.addAttribute("code", accountOrder.getCode());
        uiModel.addAttribute("realMoney", accountOrder.getRealMoney());
        return "/settle/account/payAccountOrder.html";
    }

    @RequestMapping("/ck/list/toWxPay.action")
    public String toWxPay(HttpServletRequest request, Model uiModel,Long orderId) throws UnsupportedEncodingException {
        User user = getCurrentUser();
        uiModel.addAttribute("orderId", orderId);
        AccountOrder accountOrder = accountOrderFacade.getAccountOrderById(orderId);
        uiModel.addAttribute("accountOrder", accountOrder);
        uiModel.addAttribute("code", accountOrder.getCode());
        uiModel.addAttribute("payMoney", accountOrder.getRealMoney());
        return "/settle/account/wxPay.html";
    }

    @RequestMapping("/ck/list/wxPay.action")
    public void wxPay(HttpServletRequest request,HttpServletResponse response, Model uiModel,Long orderId) throws Exception {
        User user = getCurrentUser();
        String codeUrl = accountOrderFacade.wxPay(user,orderId);
        if(codeUrl!=null){
            response.setContentType("image/png");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            QRGenerator generator = new QRGenerator(codeUrl);
            generator.generateQRToOutputStream(response.getOutputStream());
        }
    }

    @RequestMapping("/ck/list/toAliPay.action")
    public String toAliPay(HttpServletRequest request, Model uiModel,Long orderId) throws UnsupportedEncodingException {
        User user = getCurrentUser();
        uiModel.addAttribute("orderId", orderId);
        AccountOrder accountOrder = accountOrderFacade.getAccountOrderById(orderId);
        uiModel.addAttribute("accountOrder", accountOrder);
        uiModel.addAttribute("code", accountOrder.getCode());
        uiModel.addAttribute("payMoney", accountOrder.getRealMoney());
        return "/settle/account/aliPay.html";
    }

    @RequestMapping("/ck/list/aliPay.action")
    public void aliPay(HttpServletRequest request, HttpServletResponse response,Model uiModel, Long orderId) throws Exception {
        User user = getCurrentUser();
        String codeUrl = accountOrderFacade.aliPay(user,orderId);
        if(codeUrl!=null){
            response.setContentType("image/png");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);

            QRGenerator generator = new QRGenerator(codeUrl);
            generator.generateQRToOutputStream(response.getOutputStream());
        }
    }

    @RequestMapping("/ck/list/eBankPay.action")
    public void eBankPay(HttpServletRequest request,HttpServletResponse response, Long orderId) throws Exception {
        User user = getCurrentUser();
        String url = accountOrderFacade.eBankPay(user,orderId);
        JSONObject obj = new JSONObject();
        obj.put("url", url);
        this.response(response, obj.toString());
    }

    @RequestMapping("/ck/list/checkPaySuccess.action")
    public void checkPaySuccess(HttpServletRequest request, HttpServletResponse response,Long orderId) throws UnsupportedEncodingException {
        JSONObject jsonObject = new JSONObject();
        boolean msg=false;
        try{
            AccountOrder accountOrder = accountOrderFacade.getAccountOrderById(orderId);
            if(accountOrder.getStatus()==IAccountOrderConstant.status.STATUS_END){
                msg=true;
            }else{
                msg=false;
            }

        }catch (Exception e){
            msg=false;
        }
        jsonObject.put("msg", msg);
        this.response(response, jsonObject.toString());
    }

    @RequestMapping("/ck/list/toPaySuccess.action")
    public String toPaySuccess(HttpServletRequest request,Model uiModel, Long accountOrderId) throws UnsupportedEncodingException {
        AccountOrder accountOrder = accountOrderFacade.getAccountOrderById(accountOrderId);
        uiModel.addAttribute("accountOrder", accountOrder);
        return "/settle/account/paySuccess.html";
    }



    /**
     * 车客端查找用户列表
     *
     * @param request
     * @param uiModel
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/ck/list/listAccountOrderAjax.action")
    public String listAccountOrderAjax(HttpServletRequest request, Model uiModel) throws UnsupportedEncodingException {
        Map<String, Object> filters = buildFilter(request, uiModel);
        User user = getCurrentUser();
        filters.put("partyId", user.getPartyId());
        Page<AccountOrder> page = buildPage(request);
        Page<AccountOrderVo> orderPage = accountOrderFacade.findPageForMap(page, filters);
        uiModel.addAttribute("orderPage", orderPage);
        this.pageInfoByMap(uiModel, "/account/ck/list/listAccountOrderAjax.action", orderPage, "changeId", filters);
        Object statusStr = filters.get("status");
        if(statusStr==null){
            statusStr = "10";
        }
        Integer status = Integer.parseInt(statusStr.toString());
        if (status.equals(IAccountOrderConstant.status.STATUS_END)) { //已完成
            return "/settle/account/listAccountOrder_end_ajax";
        } else if (status.equals(IAccountOrderConstant.status.STATUS_CANCEL)) { //已取消
            return "/settle/account/listAccountOrder_cancel_ajax";
        }
        return "/settle/account/listAccountOrder_ajax";
    }


    /**
     * 车客端账户交易明细
     *
     * @param request
     * @param uiModel
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/ck/accountLog/listAccountLog.action")
    public String listAccountLog(HttpServletRequest request, Model uiModel, String ajax) throws UnsupportedEncodingException {
        Map<String, Object> filter = buildFilter(request, uiModel);
        User user = getCurrentUser();
        Long partyId = user.getPartyId();
        AccountVo accountVo = accountFacade.getAccountVoByPartyId(partyId);
        filter.put("accountId", accountVo.getId());
        Page<AccountLog> page = buildPage(request);
        Page<AccountLogVo> voPage = accountFacade.findAccountLogPage(page, filter);
        this.pageInfoByMap(uiModel, voPage, filter);
        uiModel.addAttribute("page", voPage);
        uiModel.addAttribute("logTypeMap", IAccountConstant.AccountLogSourceType.typeMap);
        if (ajax != null) {
            return "/settle/account/log/listAccountLog_ajax";
        }
        return "/settle/account/log/listAccountLog.html";
    }


    /**
     * 运营平台 查看账户信息
     *
     * @param request
     * @param uiModel
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/admin/listAccount/listAccountForAdmin.action")
    public String listAccountForAdmin(HttpServletRequest request, Model uiModel, String ajax) throws UnsupportedEncodingException {
        Map<String, Object> filter = buildFilter(request, uiModel);
        Page<Account> page = buildPage(request);
        filter.put("type", IAccountConstant.type.TYPE_CK);
        Page<AccountVo> voPage = accountFacade.findAccountPage(page, filter);
        this.pageInfoByMap(uiModel, voPage, filter);
        uiModel.addAttribute("page", voPage);
        if (ajax != null) {
            return "/admin/settle/account/listAccount_ajax";
        }
        return "/admin/settle/account/listAccount.html";
    }


    /**
         * 平台查看某一车客充值订单列表
         *
         * @param request
         * @param uiModel
         * @return
         * @throws UnsupportedEncodingException
         */
        @RequestMapping("/admin/listAccount/listAccountOrderByParty.action")
        public String listAccountOrderByParty(HttpServletRequest request, Model uiModel, String ajax, Long partyId) throws UnsupportedEncodingException {
            Map<String, Object> filters = buildFilter(request, uiModel);
            User user = getCurrentUser();
            Page<AccountOrder> page = buildPage(request);
            filters.put("type", IAccountOrderConstant.type.TYPE_RECHARGE);//充值订单
            filters.put("status", IAccountOrderConstant.status.STATUS_END);//只查充值成功的订单
            Page<AccountOrderVo> orderPage = accountOrderFacade.findAdminPageForMap(page, filters);

            if(partyId==null){
                partyId =0l;
            }
            Map<String, Object> map = new HashMap<>();
            map.put("status", IDrawCashOrderConstant.status.STATUS_END);
            map.put("partyId",partyId);
            Map<String,Object> result = accountOrderFacade.getTotalMoney(map);
            //AccountVo accountVo = accountFacade.getAccountVoByPartyId(partyId);
            uiModel.addAttribute("totalMoney",result.get("totalMoney").toString());
            uiModel.addAttribute("rateFee",result.get("rateFee").toString());
            uiModel.addAttribute("slottingFee",result.get("slottingFee").toString());
            uiModel.addAttribute("orderCount",result.get("orderCount").toString());
            uiModel.addAttribute("accountMoney", result.get("accountMoney").toString());

            uiModel.addAttribute("orderPage", orderPage);
            uiModel.addAttribute("statusMap", IAccountOrderConstant.status.ckStatusMap);
            uiModel.addAttribute("partyClassMap", IPartyType.partyClassMap);
            uiModel.addAttribute("partyId", partyId);

            this.pageInfoByMap(uiModel, orderPage, filters);
            if (ajax != null) {
                return "/admin/settle/account/listAccountOrderByParty_ajax";
            }
            return "/admin/settle/account/listAccountOrderByParty.html";
        }


    /**
     * 消费记录
     *
     * @param request
     * @param uiModel
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/admin/listAccount/listCostByParty.action")
    public String listCostByParty(HttpServletRequest request, Model uiModel, String ajax, Long partyId) throws Exception {
        Map<String, Object> filters = buildFilter(request, uiModel);
        Page<Article> page1 = buildPage(request);
        filters.put("aIsTask", 1);//任务
        filters.put("onlineAndEndStatus", ArticleStatus.OFFLINE.getType());//已完成的任务
        filters.put("createPartyId", partyId);//车客ID
        Page<ArticleVo> page = accountFacade.findArticlePage(page1, filters);
        Double totalCost = accountFacade.getAllCost(partyId);
        uiModel.addAttribute("partyId", partyId);
        uiModel.addAttribute("totalCost", totalCost);
        uiModel.addAttribute("page", page);
        this.pageInfoByMap(uiModel, page, filters);
        if (ajax != null) {
            return "/admin/settle/account/listCostByParty_ajax";
        }
        return "/admin/settle/account/listCostByParty.html";
    }

    public static void main(String[] args) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date1 = format.format(cal.getTime());
        System.out.println(date1);
    }

}
