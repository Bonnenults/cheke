package com.autozi.cheke.service.settle;

import com.autozi.cheke.settle.dao.AccountOrderDao;
import com.autozi.cheke.settle.dao.InvoiceDao;
import com.autozi.cheke.settle.dao.InvoiceOrderDao;
import com.autozi.cheke.settle.entity.AccountOrder;
import com.autozi.cheke.settle.entity.Invoice;
import com.autozi.cheke.settle.entity.InvoiceOrder;
import com.autozi.cheke.settle.type.IInvoiceConstant;
import com.autozi.common.core.page.Page;
import com.autozi.common.utils.util1.UID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by lbm on 2017/12/11.
 */
@Service
public class InvoiceService implements IInvoiceService{
    @Autowired
    private InvoiceDao invoiceDao;
    @Autowired
    private AccountOrderDao accountOrderDao;
    @Autowired
    private InvoiceOrderDao invoiceOrderDao;

    @Override
    public Page<Invoice> findPageForMap(Page<Invoice> page, Map<String, Object> filters) {
        return invoiceDao.findPageForMap(page,filters);
    }

    @Override
    public List<Invoice> findList(Map<String, Object> filters) {
        return invoiceDao.findListForMap(filters);
    }

    @Override
    public Invoice getInvoiceById(Long id) {
        return invoiceDao.get(id);
    }

    @Override
    public List<AccountOrder> getOrderList(Long invoiceId) {
        Invoice invoice = invoiceDao.get(invoiceId);
        String orderIds = invoice.getOrderIds();
        List<AccountOrder> list = new ArrayList<AccountOrder>();
        if(orderIds==null || "".equals(orderIds.trim())){
            return list;
        }
        String[] orderIdArr = orderIds.split(",");
        for (int i = 0; i < orderIdArr.length; i++) {
            AccountOrder accountOrder =accountOrderDao.get(Long.parseLong(orderIdArr[i]));
            if(accountOrder!=null){
                list.add(accountOrder);
            }
        }
        return list;
    }

    @Override
    public void save(Invoice invoice) {
        invoice.setCreateTime(new Date());
        invoice.setUpdateTime(new Date());
        invoiceDao.insert(invoice);
    }

    @Override
    public void update(Invoice invoice) {
        invoice.setUpdateTime(new Date());
        invoiceDao.update(invoice);
    }

    @Override
    public void applyInvoice(Invoice invoice) {
        String orderIds = invoice.getOrderIds();
        String[] orderIdArr = orderIds.split(",");
        Long invoiceId = UID.next();
        Double money = 0.0;
        for (int i = 0; i < orderIdArr.length; i++) {
            AccountOrder accountOrder = accountOrderDao.get(Long.parseLong(orderIdArr[i]));
            accountOrder.setInvoiceStatus(IInvoiceConstant.ORDER_STATUS_APPLY);
            Double realMoney = accountOrder.getRealMoney();
            if(realMoney!=null){
                money+=realMoney;
            }
            accountOrderDao.update(accountOrder);//更新订单状态
            //插入关联表中
            InvoiceOrder invoiceOrder = new InvoiceOrder();
            invoiceOrder.setOrderId(Long.parseLong(orderIdArr[i]));
            invoiceOrder.setInvoiceId(invoiceId);
            invoiceOrderDao.insert(invoiceOrder);
        }
        invoice.setStatus(IInvoiceConstant.ORDER_STATUS_APPLY);
        invoice.setMoney(money);
        invoice.setCreateTime(new Date());
        invoice.setUpdateTime(new Date());
        invoiceDao.insert(invoice);
    }

    @Override
    public InvoiceOrder getInvoiceOrder(Long orderId) {
        Map<String,Object> filters = new HashMap<>();
        filters.put("orderId",orderId);
        List<InvoiceOrder> list = invoiceOrderDao.findListForMap(filters);
        InvoiceOrder invoiceOrder= null;
        if(list!=null && list.size()>0){
            invoiceOrder = list.get(0);
        }
        return invoiceOrder;
    }

    @Override
    public void confirmInvoice(Long invoiceId) {
        Invoice invoice = new Invoice();
        invoice.setId(invoiceId);
        invoice.setStatus(IInvoiceConstant.STATUS_COMPLETE);
        invoiceDao.update(invoice);
        List<AccountOrder> list = getOrderList(invoiceId);
        for(AccountOrder accountOrder:list){
            accountOrder.setInvoiceStatus(IInvoiceConstant.STATUS_COMPLETE);
            accountOrder.setInvoiceTime(new Date());
            try{
                accountOrderDao.update(accountOrder);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
