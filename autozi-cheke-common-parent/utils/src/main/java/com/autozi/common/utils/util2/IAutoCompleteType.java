package com.autozi.common.utils.util2;

/**
 * User: 刘宇
 * Date: 11-8-22
 * Time: 下午2:25
 */
public interface IAutoCompleteType {
    public static final int CHAIN_LIST = 0;//零售商商品列表/零售商按商品设置供应关系列表/零售商终端价格设置列表/终端商品上架列表
    public static final int CHAIN_ON_SHELF = 1;//零售商商品上架列表
    public static final int CHAIN_OFF_SHELF = 2;//零售商商品下架列表
    public static final int CHAIN_SHOP_LIST = 3;//零售商商城商品列表
    public static final int CHAIN_SHOP_ON_SHELF = 4;//零售商商城发布列表
    public static final int CHAIN_SHOP_OFF_SHELF = 5;//零售商商城下架列表
    public static final int SUPPLIER_LIST = 6;//供应商商品列表/零售商供应关系按供应商设置列表/供应商供应价格查看列表/供应商编码映射列表
    public static final int SUPPLIER_OFF_SHELF = 7;//供应商停止供应商品查看列表
    public static final int STORE_LIST = 8;//终端商品列表/终端价格查询列表
    public static final int STORE_OFF_SHELF = 9;//终端下架列表
    public static final int CHAIN_SUPPLIER_PRICE = 10;//零售商供应价格列表
    public static final int CHAIN_CONFIRM_GOODS = 11;//零售商商品审核列表/供应商商品审核查看列表
    //--------------------------------------订单模块---------------------------
    //终端
    public static final int STORE_ORDER_STOCK = 20;	//我要订货、我要退货
    public static final int STORE_ORDER_RECEIVE_DELIVERY = 30;	//我要收货、我要交货
    public static final int STORE_ORDER_TRACK = 40;	//订货单跟踪、退货单跟踪
    public static final int STORE_ORDER_DELIVERY = 50;	//交货单打印
    //销售管理
    public static final int CHAIN_ORDER_CHECK = 60;	//订货单审核、退货单审核
    public static final int CHAIN_ORDER_IN_OUT_GOODS = 70;//发货出库、收取退货
    public static final int CHAIN_ORDER_INVOICES_PRINT = 80;//发货单打印
    public static final int CHAIN_ORDER_CLOSE = 90;//订货单关闭、退货单关闭
    public static final int CHAIN_ORDER_TRACK = 100;//订货单跟踪、退货单跟踪
    public static final int CHAIN_ORDER_IN_SINGLE = 110;//按单订货、按单退货
    public static final int CHAIN_ORDER_REPLENISHMENT_GOODS = 120;//补库订货、库存退货
    public static final int CHAIN_ORDER_STORAGE = 130;//收货入库/退货交货
    public static final int CHAIN_ORDER_TRACK_ORDER = 140;//订货跟踪/退货跟踪
    public static final int CHAIN_ORDER_DELIVERY_PRINT = 150;//交货单打印
    
    //供应商
    public static final int SUPPLIER_ORDER_PURCHASE_CHECK = 160;//订货单审核、退货单审核
    public static final int SUPPLIER_ORDER_PURCHASE_IN_OUT_STOCK = 170;//发货出库、退货入库
    public static final int SUPPLIER_ORDER_PURCHASE_CLOSE = 180;//订货单关闭、退货单关闭
    public static final int SUPPLIER_ORDER_PURCHASE_TRACK = 190;//订货单跟踪、退货单跟踪
    public static final int SUPPLIER_ORDER_INVOICE_INPUT = 200;//发货单打印
    
    
    
    
    
    
    
    
    
    
    /**终端订单表*/
    public static final int STORE_ORDER = 1;
    
    /**终端库存表+商品表*/
    public static final int STORE_STOCK_CATEGORY = 2;
    
    /**连锁订单表*/
    public static final int CHAIN_ORDER = 4;
    
    /**商品表——sql中不关联库存表，只查询商品表*/
    public static final int CHAIN_STOCK_CATEGORY = 8;
}
