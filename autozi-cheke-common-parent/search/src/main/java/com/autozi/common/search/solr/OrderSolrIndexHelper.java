package com.autozi.common.search.solr;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.autozi.common.utils.util2.DateTools;

/**
 * SOLR订单查询条件封装
 * @author zhiyun.chen
 *
 */
public class OrderSolrIndexHelper {

    /**
     * @Description: 订单头查询条件
     * @author: zhiyun.chen
     * 2016-11-18下午07:46:53
     */
    public static List<PropertiesFilter> initQueryParamForOrderHeader(List<PropertiesFilter> propertiesFiltersList, Map<String,Object> param) {
    	StringBuffer keyWord = new StringBuffer();
    	//关键词
		if (param.get("searchContext") != null && StringUtils.isNotBlank(param.get("searchContext").toString())) {//会员中心，搜索条件
			keyWord.append(param.get("searchContext").toString() + " ");
		}
		
		if (param.get("goodsName") != null) {//商品名称
			keyWord.append(param.get("goodsName").toString() + " ");
		}
		if (param.get("goodsStyle") != null) {//规则型号
			keyWord.append(param.get("goodsStyle").toString() + " ");
		}
		if (param.get("serialNumber") != null) {//出厂编码
			keyWord.append(param.get("serialNumber").toString() + " ");
		}
		if (param.get("goodsBrand") != null) {//品牌
			keyWord.append(param.get("goodsBrand").toString() + " ");
		}
		if (param.get("productName") != null) {//品类（最小分类）
			keyWord.append(param.get("productName").toString() + " ");
		}
		if (param.get("wareHouseName") != null) {//仓库名称
			keyWord.append(param.get("wareHouseName").toString() + " ");
		}
		if (param.get("buyerName") != null) {//购买汽修厂
			keyWord.append(param.get("buyerName").toString() + " ");
		}
		
		//关键字
		if (StringUtils.isNotBlank(keyWord.toString())) {
			propertiesFiltersList.add(setPropertiesFilter("keyWord",keyWord.toString().trim(), 0));
		}
    	
    	//时间
    	String sdate=(String)param.get("sdate");
    	String orderTimeStart=(String) param.get("orderingTimeStart");
    	String orderTimeEnd=(String) param.get("orderingTimeEnd");
		if (StringUtils.isNotBlank(sdate) && !StringUtils.isNotBlank(orderTimeStart) && !StringUtils.isNotBlank(orderTimeEnd)) {
			String[] time=conver(sdate);
    		try {
    			propertiesFiltersList.add(setPropertiesFilter("orderingTimeStart", time[0], 0));
        		propertiesFiltersList.add(setPropertiesFilter("orderingTimeEnd", time[1], 0));
    		} catch (Exception e) {
				e.printStackTrace();
			}
    	}else if (StringUtils.isNotBlank(orderTimeStart)||StringUtils.isNotBlank(orderTimeEnd)) {
			if (StringUtils.isNotBlank(orderTimeStart)) {
				propertiesFiltersList.add(setPropertiesFilter("orderingTimeStart", orderTimeStart.replaceAll("-", "") + "00000", 0));
			} 
			if (StringUtils.isNotBlank(orderTimeEnd)) {
				propertiesFiltersList.add(setPropertiesFilter("orderingTimeEnd", orderTimeEnd.replaceAll("-", "") + "235959", 0));
			} 
		}
		
		//购买人
		if (param.get("buyerId") != null) {
			PropertiesFilter filter = setPropertiesFilter("buyerId",String.valueOf(param.get("buyerId")), 0);
			propertiesFiltersList.add(filter);
		}
    	
		//删除标识
    	if(param.get("delFlag")!=null){
    		PropertiesFilter filter=setPropertiesFilter("delFlag", (String)param.get("delFlag"), 0);
    		propertiesFiltersList.add(filter);
    	}
    	
    	//订单状态
    	if(param.get("orderStatusEnd")!=null && param.get("orderStatusStart") != null){
			propertiesFiltersList.add(setPropertiesFilter("orderStatusStart", String.valueOf( param.get("orderStatusStart")), 0));
			propertiesFiltersList.add(setPropertiesFilter("orderStatusEnd", String.valueOf( param.get("orderStatusEnd")), 0));
    	} else if(param.get("orderStatusList")!=null){
    		List<Integer> orderStatusList = (List<Integer>)param.get("orderStatusList");
    		Integer orderStatusStart=orderStatusList.get(0);
    		Integer orderStatusEnd=orderStatusList.get(orderStatusList.size() - 1);
    		propertiesFiltersList.add(setPropertiesFilter("orderStatusStart", orderStatusStart, 0));
    		propertiesFiltersList.add(setPropertiesFilter("orderStatusEnd", orderStatusEnd, 0));
    	} else if (param.get("orderStatus") != null) {
    		propertiesFiltersList.add(setPropertiesFilter("orderStatusStart",String.valueOf(param.get("orderStatus")), 0));
			propertiesFiltersList.add(setPropertiesFilter("orderStatusEnd",String.valueOf(param.get("orderStatus")), 0));
		}
    	
    	//订单类型
		if (param.get("orderType") != null) {
			propertiesFiltersList.add(setPropertiesFilter("orderType",String.valueOf(param.get("orderType")), 0));
		}
		
		//订单子类型
		if (param.get("orderSubTypeList") != null) {
			propertiesFiltersList.add(setPropertiesFilter("orderSubTypeList",param.get("orderSubTypeList"), 0));
		}
		
		//订单子类型
		if (param.get("orderSubType") != null) {
			propertiesFiltersList.add(setPropertiesFilter("orderSubType",String.valueOf(param.get("orderSubType")), 0));
		}
		
		//可退数量
		if(param.get("availableReturnQuantity") != null){
    		propertiesFiltersList.add(setPropertiesFilter("hasCanReturnOrder","true", 0));
    	}
		
		//待付款
		if(param.get("hasNoPayOrder") != null){
    		propertiesFiltersList.add(setPropertiesFilter("hasNoPayOrder","true", 0));
    	}
		
		//订单头
		if (param.get("orderHeaderId") != null) {
			propertiesFiltersList.add(setPropertiesFilter("id",String.valueOf(param.get("orderHeaderId")), 0));
		}
		
		//汽修厂编码
		if (param.get("partyCode") != null) {
			propertiesFiltersList.add(setPropertiesFilter("buyerCode",String.valueOf(param.get("partyCode")), 0));
		}
		
		//结算方式
		if (param.get("settleTypeCaption") != null) {
			propertiesFiltersList.add(setPropertiesFilter("settleType",String.valueOf(param.get("settleTypeCaption")), 0));
		}
		
		//订单来源
		if (param.get("optClient") != null) {
			propertiesFiltersList.add(setPropertiesFilter("optClient",String.valueOf(param.get("optClient")), 0));
		}
		
		//销售单状态
		if (param.get("allOrderClosed") != null) {
			if (param.get("allOrderClosed").equals("1")) {
				propertiesFiltersList.add(setPropertiesFilter("allOrderClosed","true", 0));
			} else {
				propertiesFiltersList.add(setPropertiesFilter("allOrderClosed","false", 0));
			}
		}
		
		//仓库权限
		if (param.get("wareHouseIdList") != null) {
			propertiesFiltersList.add(setPropertiesFilter("wareHouseIdList",param.get("wareHouseIdList"), 0));
		}
		
		//品牌权限
		if (param.get("brandIdList") != null) {
			propertiesFiltersList.add(setPropertiesFilter("brandIdList",param.get("brandIdList"), 0));
		}
		
		//品类权限
		if (param.get("productIdList") != null) {
			propertiesFiltersList.add(setPropertiesFilter("productIdList",param.get("productIdList"), 0));
		}
		
    	//添加降序排序
    	PropertiesFilter filter=new PropertiesFilter();
    	filter.setName("orderingTime");
    	filter.setType(PropertiesFilter.Type.SORT_DESC);
    	propertiesFiltersList.add(filter);
    	return propertiesFiltersList;
    }

    private static String[] conver(String sdate){
    	String[] time=new String[2];
    	String orderingTimeEnd=null;
		String orderingTimeStart=null;
    	if("s001".equals(sdate))
		{
    		orderingTimeStart = DateTools.getDiffMonth(new Date(),-3).replaceAll("-", "");
    		orderingTimeEnd = DateTools.getShortDateStr(new Date()).replaceAll("-", "");
		}else if("s002".equals(sdate))
		{
			Calendar localTime= Calendar.getInstance();   // 当前日期
			int x = localTime.get(Calendar.YEAR);
			orderingTimeStart=x+"01"+"01" ;
			orderingTimeEnd=DateTools.ROLL_DATE.format(new Date());
		}else if ("s003".equals(sdate)) {
			Calendar localTime= Calendar.getInstance();   // 当前日期
			int x = localTime.get(Calendar.YEAR)-1;
			orderingTimeStart=x+"01"+"01" ;
			orderingTimeEnd=x+"12"+"31" ;
		}else if ("s004".equals(sdate)) {
			Calendar localTime= Calendar.getInstance();   // 当前日期
			int x = localTime.get(Calendar.YEAR)-2;
			orderingTimeStart=x+"01"+"01" ;
			orderingTimeEnd=x+"12"+"31" ;
		}else if ("s005".equals(sdate)) {
			Calendar localTime= Calendar.getInstance();   // 当前日期
			int x = localTime.get(Calendar.YEAR)-3;
			orderingTimeStart="2000" + "01" + "01";
			orderingTimeEnd=x+"12"+"31" ;
		}
    	time[0]= orderingTimeStart + "000000";
    	time[1]=orderingTimeEnd + "235959";
    	return time;
    }
    
    /**
     * @Description: 订单查询条件
     * @author: zhiyun.chen
     * 2016-11-18下午07:47:17
     */
    public static List<PropertiesFilter> initQueryParamForOrder(Map<String,Object> param) {
    	List<PropertiesFilter> propertiesFiltersList = new ArrayList<PropertiesFilter>();
    	//订单头
    	if(param.get("orderHeaderIds")!=null){
    		propertiesFiltersList.add(setPropertiesFilter("orderHeaderIds", param.get("orderHeaderIds"), 0));
    	}
    	
    	//订单状态
    	if(param.get("orderStatusEnd")!=null&&param.get("orderStatusStart") != null){
    		propertiesFiltersList.add(setPropertiesFilter("orderStatusStart", String.valueOf( param.get("orderStatusStart")), 0));
    		propertiesFiltersList.add(setPropertiesFilter("orderStatusEnd", String.valueOf( param.get("orderStatusEnd")), 0));
    	} else if(param.get("orderStatusList")!=null){
    		List<Integer> orderStatusList = (List<Integer>)param.get("orderStatusList");
    		Integer orderStatusStart=orderStatusList.get(0);
    		Integer orderStatusEnd=orderStatusList.get(orderStatusList.size() - 1);
    		propertiesFiltersList.add(setPropertiesFilter("orderStatusStart", orderStatusStart, 0));
    		propertiesFiltersList.add(setPropertiesFilter("orderStatusEnd", orderStatusEnd, 0));
    	} else if (param.get("orderStatus") != null) {
    		propertiesFiltersList.add(setPropertiesFilter("orderStatusStart",String.valueOf(param.get("orderStatus")), 0));
			propertiesFiltersList.add(setPropertiesFilter("orderStatusEnd",String.valueOf(param.get("orderStatus")), 0));
		}
    	
    	//可退数量
    	if(param.get("availableReturnQuantity") != null){
    		propertiesFiltersList.add(setPropertiesFilter("hasCanReturnOrder","true", 0));
    	}
    	
    	//待付款
		if(param.get("hasNoPayOrder") != null){
    		propertiesFiltersList.add(setPropertiesFilter("hasNoPayOrder","true", 0));
    	}
		
		//订单类型
    	if(param.get("orderType")!=null){
    		propertiesFiltersList.add(setPropertiesFilter("orderType", param.get("orderType"), 0));
    	}
    	
    	//订单类型
    	if(param.get("buyerId")!=null){
    		propertiesFiltersList.add(setPropertiesFilter("buyerId", param.get("buyerId"), 0));
    	}
    	
    	//添加降序排序
    	PropertiesFilter filter=new PropertiesFilter();
    	filter.setName("id");
    	filter.setType(PropertiesFilter.Type.SORT_ASC);
    	propertiesFiltersList.add(filter);
    	return propertiesFiltersList;
    }

	private static PropertiesFilter setPropertiesFilter(String name,Object value, int i) {
		PropertiesFilter filter = new PropertiesFilter();
		filter.setName(name);
		if (value != null) {
			filter.setValue(value);
		}
		if (i == 0) {
			filter.setType(PropertiesFilter.Type.EQUAL);
		} else if (i == 1) {
			filter.setType(PropertiesFilter.Type.SORT_DESC);
		}
		return filter;
	}
}
