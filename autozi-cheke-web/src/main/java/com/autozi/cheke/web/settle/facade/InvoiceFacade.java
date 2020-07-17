package com.autozi.cheke.web.settle.facade;

import com.autozi.cheke.service.settle.IAccountOrderService;
import com.autozi.cheke.service.settle.IInvoiceService;
import com.autozi.cheke.settle.entity.AccountOrder;
import com.autozi.cheke.settle.entity.Invoice;
import com.autozi.cheke.settle.entity.InvoiceOrder;
import com.autozi.cheke.settle.type.IInvoiceConstant;
import com.autozi.cheke.web.settle.vo.AccountOrderVo;
import com.autozi.cheke.web.settle.vo.InvoiceVo;
import com.autozi.common.core.page.Page;
import com.autozi.common.utils.o2o.PageUtil;
import com.autozi.common.utils.util2.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lbm on 2017/12/11.
 */
@Component
public class InvoiceFacade {
    @Autowired
    private IInvoiceService invoiceService;
    @Autowired
    private IAccountOrderService accountOrderService;

    public Page<InvoiceVo> findForInvoicePage(Page<Invoice> page, Map<String, Object> filters) {
        page = invoiceService.findPageForMap(page, filters);
        Page<InvoiceVo> viewPage = new Page<>();
        List<InvoiceVo> viewList = new ArrayList<>();
        for (Invoice obj : page.getResult()) {
            if (obj == null || obj.getId() == null) {
                continue;
            }
            InvoiceVo view = new InvoiceVo();
            BeanUtils.copyProperties(obj,view);
            viewList.add(view);
        }
        PageUtil.convertPage(page, viewPage, viewList);
        return viewPage;
    }

    public List<Invoice> findList(Map<String, Object> filters) {
        return invoiceService.findList(filters);
    }

    public void save(InvoiceVo invoiceVo){
        String orderIds = invoiceVo.getOrderIds();
        if(orderIds==null || "".equals(orderIds)){
            return;
        }
        Invoice invoice = new Invoice();
        BeanUtils.copyProperties(invoiceVo,invoice);
        invoiceService.applyInvoice(invoice);
    }

    public Invoice getInvoiceById(Long id){
        return invoiceService.getInvoiceById(id);
    }

    public void confirmInvoice(Long invoiceId){
        invoiceService.confirmInvoice(invoiceId);
    }

    public List<AccountOrder> listInvoiceOrder(Long invoiceId){
        return invoiceService.getOrderList(invoiceId);
    }

    public InvoiceOrder getInvoiceOrder(Long orderId){
        return invoiceService.getInvoiceOrder(orderId);
    }

    public Page<AccountOrderVo> listAccountOrder(Page<AccountOrder> page, Map<String,Object> filters){
        filters=DateUtils.String2Date(filters);
        page = accountOrderService.findPageForMap(page,filters);
        Page<AccountOrderVo> viewPage = new Page<>();
        List<AccountOrderVo> viewList = new ArrayList<>();
        for(AccountOrder accountOrder:page.getResult()){
            AccountOrderVo accountVo = new AccountOrderVo();
            BeanUtils.copyProperties(accountOrder,accountVo);
//            if(accountOrder.getInvoiceStatus()==IInvoiceConstant.ORDER_STATUS_COMPLETE){
//                //如果出现异常，继续执行
//                try{
//                    InvoiceOrder invoiceOrder = invoiceService.getInvoiceOrder(accountOrder.getId());
//                    if(invoiceOrder!=null){
//                        Invoice invoice = invoiceService.getInvoiceById(invoiceOrder.getInvoiceId());
//                        accountVo.setInvoiceTime(invoice.getUpdateTime());
//                    }
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
            viewList.add(accountVo);
        }
        PageUtil.convertPage(page,viewPage,viewList);
        return viewPage;
    }
}
