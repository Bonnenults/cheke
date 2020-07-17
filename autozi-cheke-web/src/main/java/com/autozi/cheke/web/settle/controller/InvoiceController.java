package com.autozi.cheke.web.settle.controller;

import com.autozi.cheke.party.entity.Party;
import com.autozi.cheke.settle.entity.AccountOrder;
import com.autozi.cheke.settle.entity.Invoice;
import com.autozi.cheke.settle.type.IAccountOrderConstant;
import com.autozi.cheke.user.entity.User;
import com.autozi.cheke.util.mvc.BaseController;
import com.autozi.cheke.web.party.facade.PartyFacade;
import com.autozi.cheke.web.settle.facade.AccountOrderFacade;
import com.autozi.cheke.web.settle.facade.InvoiceFacade;
import com.autozi.cheke.web.settle.vo.AccountOrderVo;
import com.autozi.cheke.web.settle.vo.InvoiceVo;
import com.autozi.common.core.page.Page;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lbm on 2017/12/11.
 */
@RequestMapping("/invoice")
@Controller
public class InvoiceController extends BaseController {
    @Autowired
    private AccountOrderFacade accountOrderFacade;
    @Autowired
    private PartyFacade partyFacade;
    @Autowired
    private InvoiceFacade invoiceFacade;

    @RequestMapping("/cheke/list/listAccountOrder.action")
    public String listAccountOrder(HttpServletRequest request, Model uiModel, String ajax) throws UnsupportedEncodingException {
        Page<AccountOrder> page = buildPage(request);
        HashMap<String, Object> filters = buildFilter(request, uiModel);
        User user = getCurrentUser();
        String invoiceStatus = request.getParameter("invoiceStatus");
        if(invoiceStatus==null){
            invoiceStatus = "0";
        }
        Party party = partyFacade.getPartyById(user.getPartyId());
        filters.put("invoiceStatus",invoiceStatus);
        filters.put("partyId",user.getPartyId());
        filters.put("status",IAccountOrderConstant.status.STATUS_END);

        Page<AccountOrderVo> viewPage = invoiceFacade.listAccountOrder(page,filters);
        uiModel.addAttribute("page", viewPage);
        uiModel.addAttribute("partyId", user.getPartyId());
        uiModel.addAttribute("partyClass", party.getPartyClass());
        uiModel.addAttribute("invoiceStatus",invoiceStatus);
        this.pageInfoByMap(uiModel, viewPage,"data_table_list;pagingDiv", filters);

        if (ajax != null) {
            return "/settle/invoice/listAccountOrder_ajax";
        }
        return "/settle/invoice/listAccountOrder.html";
    }

    @RequestMapping("/cheke/list/applyInvoice.action")
    public void applyInvoice(InvoiceVo invoiceVo, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        JSONObject jsonObject = new JSONObject();
        String msg="ok";
        User user = getCurrentUser();
        Long partyId = user.getPartyId();
        if(partyId==null){
            jsonObject.put("msg", "当前车客为空");
            this.response(response, jsonObject.toString());
            return;
        }
        HashMap<String, Object> filters = new HashMap<>();
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);
        filters.put("createTimeStart",new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(c.getTime()));
        filters.put("createTimeEnd", new Date());
        filters.put("partyId",partyId);
        List<Invoice> list = invoiceFacade.findList(filters);
        if(list!=null&& list.size()>0){
            Invoice invoice= list.get(0);
            jsonObject.put("msg", "您已在本月"+new SimpleDateFormat("yyyy-MM-dd HH:mm").format(invoice.getCreateTime())+"号申请过一次发票了，每个月只能申请一次");
            this.response(response, jsonObject.toString());
            return;
        }
        invoiceVo.setPartyId(user.getPartyId());
        invoiceFacade.save(invoiceVo);
        jsonObject.put("msg", msg);
        this.response(response, jsonObject.toString());
    }

    @RequestMapping("/cheke/list/getInvoiceInfo.action")
    public void getInvoiceInfo(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        User user = getCurrentUser();
        Long partyId = user.getPartyId();
        Party party = partyFacade.getPartyById(partyId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("party", party);
        this.response(response, jsonObject.toString());
    }

    @RequestMapping("/admin/list/listInvoice.action")
    public String listInvoice(HttpServletRequest request, Model uiModel, String ajax) throws UnsupportedEncodingException {
        Page<Invoice> page = buildPage(request);
        HashMap<String, Object> filters = buildFilter(request, uiModel);
        String status = request.getParameter("status");
        if(status==null){
            status = "1";
        }
        filters.put("status",status);
        Page<InvoiceVo> viewPage = invoiceFacade.findForInvoicePage(page,filters);
        uiModel.addAttribute("page", viewPage);
        uiModel.addAttribute("status", status);
        this.pageInfoByMap(uiModel, viewPage, filters);

        if (ajax != null) {
            return "/admin/settle/invoice/listInvoice_ajax";
        }
        return "/admin/settle/invoice/listInvoice.html";
    }

    @RequestMapping("/admin/list/listInvoiceOrder.action")
    public String listInvoiceOrder(Long invoiceId,HttpServletRequest request, Model uiModel, String ajax) throws UnsupportedEncodingException {
        Invoice invoice = invoiceFacade.getInvoiceById(invoiceId);
        Party party = partyFacade.getPartyById(invoice.getPartyId());
        List<AccountOrder> orderList = invoiceFacade.listInvoiceOrder(invoiceId);
        uiModel.addAttribute("orderList", orderList);
        uiModel.addAttribute("invoice",invoice);
        uiModel.addAttribute("party",party);
        return "/admin/settle/invoice/listInvoiceOrder.html";
    }

    @RequestMapping("/admin/list/getInvoice.action")
    public void getInvoice(Long invoiceId,HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        JSONObject jsonObject = new JSONObject();
        Invoice invoice = invoiceFacade.getInvoiceById(invoiceId);
        jsonObject.put("invoice",invoice);
        this.response(response, jsonObject.toString());
    }

    @RequestMapping("/admin/list/confirmInvoice.action")
    public void confirmInvoice(Long invoiceId,HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        JSONObject jsonObject = new JSONObject();
        String msg = "ok";
        try{
            invoiceFacade.confirmInvoice(invoiceId);
        }catch (Exception e){
            msg="操作失败";
        }
        jsonObject.put("msg",msg);
        this.response(response, jsonObject.toString());
    }

    public static void main(String[] args) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);
        System.out.println(new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(c.getTime()));
    }
}
