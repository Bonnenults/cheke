package com.autozi.common.search.solr;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.autozi.common.core.page.Page;

/**
 * 类描述:
 * 创建人: lyw
 * 创建时间: 16-8-3 上午09:33:16
 */
public class SalesOrderHeaderSolrClientHelper {
	
	private static Logger logger = LoggerFactory.getLogger(SalesOrderHeaderSolrClientHelper.class);
	private static Logger _logger_solr = LoggerFactory.getLogger("SOLR");

	/**
	 * @Description: 搜索
	 * @author: lyw
	 * 2016-8-3上午09:33:16
	 */
    public static SolrQuery initSolrQuery(Page<SalesOrderHeaderEntity> page, List<PropertiesFilter> propertiesFilterList) {
        SolrQuery solrQuery = new SolrQuery();
        StringBuilder queryStr = new StringBuilder();
        String orderingTimeStart = "";
        String orderingTimeEnd = "";
        String orderStatusStart=null;
        String orderStatusEnd = null;
        //设置大搜索框查询条件
        queryStr.append("keyWord:*");//为了方便后续的条件都用AND
        // UPDATE BY LIHF@QEEGOO 2015-6-1下午8:12:33 START BUG描述：
        if(propertiesFilterList==null
        		||propertiesFilterList.size()<=0){
        	return null;
        }
        for (PropertiesFilter properties : propertiesFilterList) {
            PropertiesFilter.Type type = properties.getType();
            if(PropertiesFilter.Type.EQUAL == type) {
                if(properties.getName().equals("keyWord")){
                	Pattern pattern=Pattern.compile("[\\u4e00-\\u9fa5]+");//判断是否含有中文，有中文不模糊查询  
            		Matcher matcher=pattern.matcher(properties.getValue().toString().replaceAll(IndexEntity.excludeRegex,"").replaceAll(" ", ""));  
                    queryStr.append(" AND ").append(properties.getName()).append(":");
                    if(matcher.find()){
                        if(properties.getValue().toString().length()==1){
                            queryStr.append("*"+properties.getValue().toString().toUpperCase().replaceAll(IndexEntity.excludeRegex,"").replaceAll(" ", "")+"*");
                        }else{
                            queryStr.append(properties.getValue().toString().toUpperCase().replaceAll(IndexEntity.excludeRegex,"").replaceAll(" ", ""));
                        }
                    }else{
                    	queryStr.append("*"+properties.getValue().toString().toUpperCase().replaceAll(IndexEntity.excludeRegex,"").replaceAll(" ", "")+"*");
                    }
//                	if(properties.getValue().toString().toUpperCase().replaceAll(IndexEntity.excludeRegex,"").length()>0){
//	                	queryStr.append(" AND (").append(properties.getName()).append(":");
//	                	queryStr.append("*"+properties.getValue().toString().toUpperCase().replaceAll(IndexEntity.excludeRegex,"").replaceAll(" ", "")+"*");
//	                	queryStr.append("  OR  ").append(properties.getName()).append(":");
//	                	queryStr.append(properties.getValue().toString().toUpperCase().replaceAll(IndexEntity.excludeRegex,"").replaceAll(" ", ""));
//	                	queryStr.append("  OR  ").append(properties.getName()).append(":");
//	                	queryStr.append("*"+properties.getValue().toString().toUpperCase().replaceAll(IndexEntity.excludeRegex,"").replaceAll(" ", ""));
//	                	queryStr.append("  OR  ").append(properties.getName()).append(":");
//	                	queryStr.append(properties.getValue().toString().toUpperCase().replaceAll(IndexEntity.excludeRegex,"").replaceAll(" ", "")+"*)");
//                	}else{
//                		queryStr.append(" AND ").append(properties.getName()).append(":000000000000");
//                	}
                }else if(properties.getName().equals("orderingTimeStart")){
                	orderingTimeStart=properties.getValue().toString();
                }else if(properties.getName().equals("orderingTimeEnd")){
                	orderingTimeEnd=properties.getValue().toString();
                }else if(properties.getName().equals("orderStatusStart")){
                	orderStatusStart=properties.getValue().toString();
                }else if(properties.getName().equals("orderStatusEnd")){
                	orderStatusEnd=properties.getValue().toString();
                }else if(properties.getName().equals("orderSubTypeList")){
                	List<Integer> orderSubTypeList = (List)properties.getValue();
                	queryStr.append(" AND ");
                    queryStr.append("(");
                    for (int i=0; i<orderSubTypeList.size(); i++) {
                         if (i > 0) {
                             queryStr.append(" OR ");
                         }
                         queryStr.append("orderSubType").append(":");
                         queryStr.append(orderSubTypeList.get(i));
                    }
                    queryStr.append(")");
                }else{
                	queryStr.append(" AND ").append(properties.getName()).append(":");
                	queryStr.append(properties.getValue());
                }
            }else if(PropertiesFilter.Type.SORT_ASC == properties.getType()){
            	if(properties.getName()=="orderingTime"){
            		solrQuery.addSort("createDate", SolrQuery.ORDER.asc);
            	}else{
            		solrQuery.addSort(properties.getName(), SolrQuery.ORDER.asc);
            	}
            }else if(PropertiesFilter.Type.SORT_DESC == properties.getType()){
            	if(properties.getName()=="orderingTime"){
            		solrQuery.addSort("createDate", SolrQuery.ORDER.desc);
            	}else{
            		solrQuery.addSort(properties.getName(), SolrQuery.ORDER.desc);
            	}
            }else if(PropertiesFilter.Type.FACET == properties.getType()){
            	
            }
        }
        if (StringUtils.isNotBlank(orderingTimeStart) || StringUtils.isNotBlank(orderingTimeEnd)) {
        	queryStr.append(" AND ").append("createDate").append(":[");
        	queryStr.append(orderingTimeStart==""?"*":orderingTimeStart).append(" TO ").append(orderingTimeEnd==""?"*":orderingTimeEnd).append("]");
        }
        if(orderStatusStart!=null){
     	   queryStr.append(" AND ").append("orderStatus").append(":[");
     	   queryStr.append(orderStatusStart).append(" TO ").append(orderStatusEnd).append("]");
        }
        String solrQueryStr = queryStr.toString();
        if(solrQueryStr.equals("keyWord:*")){
            logger.info("本次查询条件为："+solrQueryStr+";直接返回");
            _logger_solr.info("本次查询条件为："+solrQueryStr+";直接返回");
        	return null;
        }
        solrQueryStr = solrQueryStr.substring(14, solrQueryStr.length());
        logger.info("本次查询条件为："+solrQueryStr);
        _logger_solr.info("本次查询条件为："+solrQueryStr);
        solrQuery.setQuery(solrQueryStr);
//        //设置搜索结果返回的字段
//        solrQuery.setFields("id","orderHeaderDate","delivery","deliveryName","totalMoney","totalAmount","settleType","settleTypeName",
//        		"orderHeaderStatus","coupons","attendPromotion","couponIds","applNo","contNo","instuName","payStatus","payFlag","loanFlag",
//        		"isAvailable","freeLoandays","ConfirmType","notRepayExplain","isFreeflag","orderSubType","frontPayStatus","finalPayStatus","frontTotalMoney","deliverTime",
//        		"prePayEndTime","suitQuantity","buyerID","delFlag");
        solrQuery.setStart((page.getPageNo() - 1) * page.getPageSize());
        solrQuery.setRows(page.getPageSize());
        return solrQuery;
    }   
}
