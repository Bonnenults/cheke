/**
 * 文件名称 : SceneDefinition.java
 * 项目名称 : B2BV2.0
 * 创建日期 : 2012-10-23
 * 作    者 : LITONGKE
 *
 * Copyright (C) 2012 启购时代 版权所有.
 */
package com.autozi.common.utils.util3;

/** 
 * <PRE> 
 *  
 * 中文描述: 结算场景定义
 *  
 * </PRE> 
 * @version 1.0.0, 2012-10-23 
 * 
 */
public interface SceneDefinition {
	
	public static String DETAIL_FLAG = "999";				//单据明细页面
	
	/*账单管理涉及的场景*/
	public static String RECON_CREATE_EXPENSE = "110";		//费用账单登记
	public static String RECON_QUERY_WAIT_VERIFY = "120";	//待审核账单查询
	public static String RECON_QUERY_HAS_REJECTED = "130";	//已驳回账单查询
	public static String RECON_MODIFY_EXPENSE = "140";		//已驳回账单修改
	public static String RECON_QUERY_HAS_REVOKED = "150";	//已撤消账单查询
	public static String RECON_QUERY_WAIT_PAY = "160";		//待支付账单查询
	public static String RECON_QUERY_PAYING = "170";		//支付中账单查询
	public static String RECON_QUERY_HAS_PAID = "180";		//已支付账单查询
	public static String RECON_QUERY_ALL = "190";			//全状态账单查询
	
	/*支付管理（账单支付）*/
	public static String RECON_PAY = RECON_QUERY_WAIT_PAY;	//账单支付
	public static String RECON_CASHREGISTER = "161";		//收款登记账单查询
	
	/*实体结算*/
	public static String ACTUAL_RECON_PAY_DIRECTSHIP = "162";		       		 //直运结算
	public static String ACTUAL_RECON_PAY_CENTRALPURCHASE = "163";	//集采结算
	public static String ACTUAL_RECON_PAY_REPLACEPURCHASE = "164";	//代采结算
	public static String ACTUAL_RECON_PAY_CONSIGNMENT = "165";		    //寄售结算
	
	/*支付管理（支付查询、收款查询及相应操作）*/
	public static String PAYMENT_QUERY_WAITCHECK = RECON_PAY;	//待审批的支付单查询
	public static String PAYMENT_QUERY_PAYING = "210";		//支付中的支付单查询
	public static String PAYMENT_QUERY_SUCCEED = "220";		//支付完成的支付单查询
	public static String PAYMENT_QUERY_FAILED = "230";		//支付失败的支付单查询
	public static String PAYMENT_QUERY_CANCEL = "240";		//支付核销的支付单查询
	public static String PAYMENT_QUERY_SUCCEED_ONLINE = "250";      //支付完成且仍在在线表的支付单查询(分批支付的支付单)

	public static String REFUND_RETURN_ORDER_VERIFY = "410";		//退货订单的退款审批
	public static String REFUND_BILL_WAIT_REFUND_LESSRECEIVE = "420";	//【订单收货差异】待退款退款单查询及退款执行
	public static String REFUND_BILL_WAIT_REFUND_RETURN = "421";	//【退货】待退款退款单查询及退款执行
	public static String REFUND_BILL_WAIT_REFUND_REPEAT = "422";	//重复支付待退款退款单查询及退款执行
	public static String REFUND_BILL_WAIT_REFUND = "423";	//待退款退款单查询及退款执行
	public static String REFUND_BILL_REFUNDING = "430";		//退款处理中
	public static String REFUND_BILL_HASREFUND = "440";		//已退款完成的退款单查询
	public static String REFUND_BILL_HASFAILED = "450";		//已退款失败的退款单查询
	public static String REFUND_BILL_CANCEL = "460";		//已退款核销的退款单查询


    /*服务费结算场景*/
    public static String REBATE_BILL_ALL = "910";			//应付费用统计
    public static String REBATE_BILL_WAIT_CHECK = "920";	//待对账
    public static String CHECK_BILL_WAIT_WRITEOFF = "930";	//待核销
    public static String WRITEOFF_BILL_WAIT_PAY = "940";	//待付款
    public static String WRITEOFF_BILL_HAS_PAID = "950";	//已付款

	/*结算设置*/
	public static String BASE_SETTING = "310";				//基础设置
	public static String COMMISSION_RATE = "320";			//佣金设置
	
	/*平台费生成*/
	public static String COMMISSION_CREATE = "320";			//平台费生成
	
	public static String CUSTOM_RECHARGE = "700";			//客户充值
	public static String CUSTOM_PARTY_ACCOUNT = "810";		//账期设置
	public static String CUSTOM_INVOICE = "830";			//客户发票查看
	
	
	
	public static String CHAIN_SUPPLIER_FLAG = "1020";		//连锁供应结算
    public static String CHAIN_B2C_FLAG = "2020";			//连锁销售结算
    public static String CHAIN_CDC_FLAG = "3020";			//连锁CDC结算
    public static String CHAIN_SHIPPERS_FLAG = "4020";		//连锁承运商结算
    public static String CHAIN_COURIER_FLAG = "5020";		//连锁配送员结算
    public static String CHAIN_BRANCHCOMPANY_FLAG = "6020"; //子公司结算
    public static String CHAIN_SUBSIDIARY_SALES_FLAG = "7020";//连锁外部调拨销售结算-销售方
    public static String CHAIN_SUBSIDIARY_PURCHASE_FLAG = "7070";//连锁外部调拨供应结算-采购方
    public static String CHAIN_B2R_SALES_FLAG = "8040";		//B2R销售结算
    public static String CHAIN_QEEGOO_FLAG = "9920";		//连锁平台费结算
    public static String SUPPLIER_FLAG = "1010";			//供应商销售结算
    public static String CDC_FLAG = "3030";					//CDC结算
    public static String SHIPPERS_FLAG = "4050";			//承运商结算
    public static String COURIER_FLAG = "5060";				//配送员结算
    public static String QEEGOO_FLAG = "9900";				//平台结算
    public static String B2C = "2040";
    public static String BRANCHCOMPANY_FLAG = "6090";		//子公司销售结算
    public static String CHAIN_RETAILER_FLAG = "9080";		//服务费结算

	
	public static String REQUEST_ERROR_PAGE = "request_error";
	public static String REQUEST_NOT_ACCESSIBLE_PAGE = "request_not_accessible";
	public static String REQUEST_NOT_ACCESSIBLE_FLAG = "not_accessible";
	public static String OK = "OK";
	public static String ERROR = "ERROR";
	public static String PAYMENT_SELECTED_RECONIDS = "payment_selected_reconids";
	public static String PAYMENT_SELECTED_RECONIDS_SEP = "@@";//分割标识
	public static String PAYMENT_SEARCH_CONDITION = "payment_search_condition";
	
}
