package com.autozi.common.search.solr;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.common.params.CommonParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.autozi.common.core.page.Page;
import com.autozi.common.utils.util2.ApplicationPropertiesUtils;

/**
 * 类描述:
 * 创建人: yourun.liu
 * 创建时间: 13-3-19 下午3:56
 */
public class SalesOrderSolrClientHelper {
	
	private static Logger logger = LoggerFactory.getLogger(SalesOrderSolrClientHelper.class);
	private static Logger _logger_solr = LoggerFactory.getLogger("SOLR");

	/**
	 * @Description: 搜索
	 * @author: zhiyun.chen
	 * 2015-6-6上午09:33:16
	 */
    public static SolrQuery SolrQuery(Page<SalesOrderEntity> page, List<PropertiesFilter> propertiesFilterList) {
        SolrQuery solrQuery = new SolrQuery();
        StringBuilder queryStr = new StringBuilder();
        String orderStatusStart=null;
        String orderStatusEnd = null;
        //设置大搜索框查询条件
        queryStr.append("keyWord:*");//为了方便后续的条件都用AND
        if(propertiesFilterList==null || propertiesFilterList.size()<=0){
        	return null;
        }
        for (PropertiesFilter properties : propertiesFilterList) {
            PropertiesFilter.Type type = properties.getType();
            if(PropertiesFilter.Type.EQUAL == type) {
                if(properties.getName().equals("keyWord")){
//                	if(properties.getValue().toString().toUpperCase().replaceAll(IndexEntity.excludeRegex,"").length()>0){
//                		queryStr.append(" AND (").append(properties.getName()).append(":");
//                		queryStr.append("*"+properties.getValue().toString().toUpperCase().replaceAll(IndexEntity.excludeRegex,"").replaceAll(" ", "")+"*");
//                		queryStr.append("  OR  ").append(properties.getName()).append(":");
//                		queryStr.append(properties.getValue().toString().toUpperCase().replaceAll(IndexEntity.excludeRegex,"").replaceAll(" ", ""));
//                		queryStr.append("  OR  ").append(properties.getName()).append(":");
//                		queryStr.append("*"+properties.getValue().toString().toUpperCase().replaceAll(IndexEntity.excludeRegex,"").replaceAll(" ", ""));
//                		queryStr.append("  OR  ").append(properties.getName()).append(":");
//                		queryStr.append(properties.getValue().toString().toUpperCase().replaceAll(IndexEntity.excludeRegex,"").replaceAll(" ", "")+"*)");
//                	}else{
//                		queryStr.append(" AND ").append(properties.getName()).append(":000000000000");
//                	}
                	Pattern pattern=Pattern.compile("[\\u4e00-\\u9fa5]+");//判断是否含有中文，有中文不模糊查询  
            		Matcher matcher=pattern.matcher(properties.getValue().toString().replaceAll(IndexEntity.excludeRegex,"").replaceAll(" ", ""));  
                    queryStr.append(" AND ").append(properties.getName()).append(":");
                    if(matcher.find()){
                        if(properties.getValue().toString().length()==1){
                            queryStr.append("*"+properties.getValue().toString().replaceAll(IndexEntity.excludeRegex,"").replaceAll(" ", "")+"*");
                        }else{
                            queryStr.append(properties.getValue().toString().replaceAll(IndexEntity.excludeRegex,"").replaceAll(" ", ""));
                        }
                    }else{
                    	queryStr.append("*"+properties.getValue().toString().replaceAll(IndexEntity.excludeRegex,"").replaceAll(" ", "")+"*");
                    }
                }else if(properties.getName().equals("orderStatusStart")){
                	orderStatusStart=properties.getValue().toString();
                }else if(properties.getName().equals("orderStatusEnd")){
                	orderStatusEnd=properties.getValue().toString();
                }else if(properties.getName().equals("orderHeaderIds")){
                	String[] orderHeaderIds = properties.getValue().toString().split(",");
                	queryStr.append(" AND ");
                    queryStr.append("(");
                    for (int i = 0; i < orderHeaderIds.length; i++) {
                         if (i > 0) {
                             queryStr.append(" OR ");
                         }
                         queryStr.append("orderHeaderId").append(":");
                         queryStr.append(orderHeaderIds[i]);
                    }
                    queryStr.append(")");
                }else {
                	queryStr.append(" AND ").append(properties.getName()).append(":");
                	queryStr.append(properties.getValue());
				}	
            }else if(PropertiesFilter.Type.SORT_ASC == properties.getType()){
        		solrQuery.addSort(properties.getName(), SolrQuery.ORDER.asc);
            }else if(PropertiesFilter.Type.SORT_DESC == properties.getType()){
        		solrQuery.addSort(properties.getName(), SolrQuery.ORDER.desc);
            }else if(PropertiesFilter.Type.FACET == properties.getType()){
            	
            }
        }
        if(orderStatusStart!=null && orderStatusEnd != null){
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
//        solrQuery.setFields("id","goodsName","orderHeaderId","orderStatus","unitPrice","orderingTime","promotions","realUnitPrice",
//        		"discountAmount","totalOrderSum","totalDiscountAmount","delivery","orderingQuantity","payFlag","coupons","goodsId","goodsInfo","goodsStyle",
//        		"goodsName","goodsImageUrl","b2bCode","serialNumber","brandId","onShopping","brandName","buyerID",
//        		"sellerStockChangeQuantity","billState","confirmType","availableReturnQuantity","orderSubType","orderType");
        solrQuery.setStart((page.getPageNo() - 1) * page.getPageSize());
        solrQuery.setRows(page.getPageSize());
        return solrQuery;
    }
} 
  
    

