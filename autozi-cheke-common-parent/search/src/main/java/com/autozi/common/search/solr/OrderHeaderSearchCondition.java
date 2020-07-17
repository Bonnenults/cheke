package com.autozi.common.search.solr;

import java.util.List;

public class OrderHeaderSearchCondition {
	private Long buyerId;
	private String searchContext;      //商品名称/规格型号/订单号
	private Integer orderType;
	private Integer orderSubType;
	private Long orderStatus; 			//订单状态
	private String sdate;
	private String orderBy; // 排序
	private List<Integer> orderSubTypeList;
	private String orderingTimeStart;	//订单提交时间开始
	private String orderingTimeEnd;		//订单提交时间结束
	private String haveReturnOrder;  //是否有可退的订单("true":有，"false"：无)
	private Integer delFlag; //删除标记  1为删除
	
	public final static String sdate_s001 = "s001";//最近三个月
	public final static String sdate_s002 = "s002";//今年内
	public final static String sdate_s003 = "s003";//去年
	public final static String sdate_s004 = "s004";//前年
	public final static String sdate_s005 = "s005";//前年以前
	
	public Long getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}
	public String getSearchContext() {
		return searchContext;
	}
	public void setSearchContext(String searchContext) {
		this.searchContext = searchContext;
	}
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	public Integer getOrderSubType() {
		return orderSubType;
	}
	public void setOrderSubType(Integer orderSubType) {
		this.orderSubType = orderSubType;
	}
	public Long getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Long orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getSdate() {
		return sdate;
	}
	public void setSdate(String sdate) {
		this.sdate = sdate;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public List<Integer> getOrderSubTypeList() {
		return orderSubTypeList;
	}
	public void setOrderSubTypeList(List<Integer> orderSubTypeList) {
		this.orderSubTypeList = orderSubTypeList;
	}
	public String getOrderingTimeStart() {
		return orderingTimeStart;
	}
	public void setOrderingTimeStart(String orderingTimeStart) {
		this.orderingTimeStart = orderingTimeStart;
	}
	public String getOrderingTimeEnd() {
		return orderingTimeEnd;
	}
	public void setOrderingTimeEnd(String orderingTimeEnd) {
		this.orderingTimeEnd = orderingTimeEnd;
	}
	public String getHaveReturnOrder() {
		return haveReturnOrder;
	}
	public void setHaveReturnOrder(String haveReturnOrder) {
		this.haveReturnOrder = haveReturnOrder;
	}
	public Integer getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	
	
}
