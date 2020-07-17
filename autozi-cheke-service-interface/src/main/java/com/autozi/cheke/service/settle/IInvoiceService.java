package com.autozi.cheke.service.settle;

import com.autozi.cheke.settle.entity.AccountOrder;
import com.autozi.cheke.settle.entity.Invoice;
import com.autozi.cheke.settle.entity.InvoiceOrder;
import com.autozi.common.core.page.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by lbm on 2017/12/11.
 */
public interface IInvoiceService {
    public Page<Invoice> findPageForMap(Page<Invoice> page, Map<String, Object> filters);

    public List<Invoice> findList(Map<String, Object> filters);

    public Invoice getInvoiceById(Long id);

    public List<AccountOrder> getOrderList(Long invoiceId);

    public void save(Invoice invoice);

    public void update(Invoice invoice);

    public void applyInvoice(Invoice invoice);

    public InvoiceOrder getInvoiceOrder(Long orderId);

    public void confirmInvoice(Long invoiceId);
}
