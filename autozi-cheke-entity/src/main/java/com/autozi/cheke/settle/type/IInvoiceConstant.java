package com.autozi.cheke.settle.type;

/**
 * Created by lbm on 2017/12/13.
 */
public interface IInvoiceConstant {
    public static final int STATUS_APPLY = 1;//申请中
    public static final int STATUS_COMPLETE = 2;//完成
    public static final int STATUS_REFUSE = 3;//

    public static final int ORDER_STATUS_DEFAULT = 0;//订单未申请
    public static final int ORDER_STATUS_APPLY = 1;//订单申请中
    public static final int ORDER_STATUS_COMPLETE = 2;//订单申请完成
}
