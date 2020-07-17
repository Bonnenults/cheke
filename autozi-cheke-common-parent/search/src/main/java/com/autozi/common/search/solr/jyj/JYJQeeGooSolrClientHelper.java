package com.autozi.common.search.solr.jyj;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.autozi.common.core.page.Page;
import com.autozi.common.search.solr.IndexEntity;
import com.autozi.common.search.solr.PropertiesFilter;

/**
 * 类描述:
 * 创建人: binbin.lee
 * 创建时间: 20170213 下午3:56
 */
public class JYJQeeGooSolrClientHelper {
	private static Logger logger = LoggerFactory.getLogger(JYJQeeGooSolrClientHelper.class);
	private static Logger _logger_solr = LoggerFactory.getLogger("SOLR");

	
	/**
	 * @Description: 搜索
	 * @author: binbin.lee
	 * 2015-6-6上午09:33:16
	 */
    public static SolrQuery initSolrQuery(Page<IndexEntity> page, List<PropertiesFilter> propertiesFilterList, String ownerPartyId) {
        SolrQuery solrQuery = new SolrQuery();
        StringBuilder queryStr = new StringBuilder();
        /*为了方便后续的条件都用AND(在所有sql拼接结束候，会去掉前缀保证sql正常) | 并 搜索JYJ商品（积压件数据来源）*/
        queryStr.append("keyWord:* AND goodsSource:2 ");//为了方便后续的条件都用AND
        if(propertiesFilterList == null || propertiesFilterList.size() <= 0){
        	return null;
        }
        /*提前取出非查询条件的filter*/
        boolean hasLogin = false;//是否登录
        if (!hasLogin) {//未登录，搜索不到特定品牌下的商品
        	/*queryStr.append(" AND ").append("brandId:(* NOT 1509241604421139 NOT 1509241604271138 NOT 1509241604171137 NOT 1402201548086417 NOT 595 NOT 1409242210460000)");*/
        }
        for (PropertiesFilter properties : propertiesFilterList) {
        	String proName = properties.getName();
        	if(StringUtils.isBlank(proName)){
        		continue; // 保证无错误数据
        	}
        	Object proValue = properties.getValue();
        	PropertiesFilter.Type type = properties.getType();
            if(PropertiesFilter.Type.EQUAL == type) {
            	if(proValue == null){
            		continue;// 保证无错误数据
            	}
            	if(proName.trim().equals("keyWord")){
                	Pattern pattern=Pattern.compile("[\\u4e00-\\u9fa5]+");//判断是否含有中文，有中文不模糊查询  
            		Matcher matcher=pattern.matcher(proValue.toString().replaceAll(IndexEntity.excludeRegex,"").replaceAll(" ", ""));  
                    queryStr.append(" AND ").append(proName + ":");
                    if(matcher.find()){
                        if(proValue.toString().length()==1){
                            queryStr.append("*"+proValue.toString().replaceAll(IndexEntity.excludeRegex,"").replaceAll(" ", "")+"*");
                        }else{
                            queryStr.append(proValue.toString().replaceAll(IndexEntity.excludeRegex,"").replaceAll(" ", ""));
                        }
                    }else{
                    	queryStr.append("*"+proValue.toString().replaceAll(IndexEntity.excludeRegex,"").replaceAll(" ", "")+"*");
                    }
                    queryStr.append(" ");	/*结尾加入空格*/
                } else if(proName.trim().equals("ids")){
                    queryStr.append(" AND ").append(proValue.toString()).append(" ");
                } else if(proName.trim().equals("carLogoId")|| proName.equals("carBrandId") || proName.equals("carModelId") || proName.equals("carSeriesId")){
                	queryStr.append(" AND ").append(proName + ":" + proValue).append(" ");
                } else if(proName.trim().equals("oem")){
                    queryStr.append(" AND ").append(proName + ":" + proValue).append(" ");
                } else if(proName.equals("oemStr")){
                	queryStr.append(" AND ").append(proValue.toString()).append(" ");
                } else if(proName.trim().equals("jyjPriceRange")) { 
                	/*jyjShoppingPrice组合查询	没有值使用“*”代替 helper工具类直接拼接*/
                	queryStr.append(" AND ").append(" ( " + "jyjShoppingPrice" + ":" + proValue.toString() + ")").append(" ");
                } else if(proName.trim().equals("beginPrice") || proName.trim().equals("endPrice")) { // jyjShoppingPrice组合查询	没有值使用“*”代替
                	// 这两个字段不适用（至空）
                } else {
                	queryStr.append(" AND ").append(proName.trim() + ":" + proValue.toString()).append(" ");
                }
            }else if(PropertiesFilter.Type.SORT_ASC == type){
                solrQuery.addSort(proName, SolrQuery.ORDER.asc);
            }else if(PropertiesFilter.Type.SORT_DESC == type){
                solrQuery.addSort(proName, SolrQuery.ORDER.desc);
            }else if(PropertiesFilter.Type.FACET == type){
            	if (proName.equals("goodsName") || proName.equals("goodsBrandNameAndGoodsName") || proName.equals("goodsBrandNameAndCategory2Name")) {
            		solrQuery.addFacetField(proName).setFacetMinCount(1).setFacetLimit(30);
            	} else {
            		solrQuery.addFacetField(proName).setFacetMinCount(1).setFacetLimit(1000);
            	}
            }
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
        /*
         * 设置搜索结果返回的字段  <br>
         * 新增
         * "jyjAreaId","jyjAreaName","jyjProductType","jyjGeneral","jyjApplicableModel", "jyjShoppingPrice","goodsSource","jyjGoodsNameInfo"
         * "ccjCategory1Id","ccjCategory2Id","ccjCategory3Id"
         */
        solrQuery.setFields("id","goodsName","brandId","brandName","goodsStyle","serialNumber",
        		"smallImagePath","middleImagePath","largeImagePath","b2bCode",
        		"oem","oemAlias","goodsNameDisplay","category1Id","category2Id","goodsStyle","productId",
        		"jyjAreaId","jyjAreaName","jyjProductType","jyjGeneral","jyjApplicableModel",
        		"jyjShoppingPrice","goodsSource","jyjGoodsNameInfo","supplierId","jyjSettlePrice",
        		"jyjGoodsType","jyjInvoiceTag","jyjShowTag","jyjTradePrice","ccjCategory1Id",
        		"ccjCategory2Id","ccjCategory3Id","ccjProductQuality"
        		);	
        solrQuery.setStart((page.getPageNo() - 1) * page.getPageSize());
        solrQuery.setRows(page.getPageSize());
        return solrQuery;
    }
    
    /**
     * @Description: 关键字提示
     * @author: binbin.lee
     * 2015-6-6上午09:33:00
     */
    public static SolrQuery initSolrFacetQuery(List<PropertiesFilter> propertiesFilterList) {
        SolrQuery solrQuery = new SolrQuery();
        StringBuilder queryStr = new StringBuilder();
        if(propertiesFilterList==null||propertiesFilterList.size()<=0){
        	return solrQuery;
        }
        /*为了方便后续的条件都用AND(在所有sql拼接结束候，会去掉前缀保证sql正常) | 并 搜索JYJ商品（积压件数据来源）*/
        queryStr.append("keyWord:* AND goodsSource:2 ");//为了方便后续的条件都用AND
        /*提前取出非查询条件的filter*/
        boolean hasLogin = false;//是否登录
        if (!hasLogin) {//未登录，搜索不到特定品牌下的商品
        	/*queryStr.append(" AND ").append("brandId:(* NOT 1509241604421139 NOT 1509241604271138 NOT 1509241604171137 NOT 1402201548086417 NOT 595 NOT 1409242210460000)");*/
        }
        for (PropertiesFilter properties : propertiesFilterList) {
            String proName = properties.getName();
            if(StringUtils.isBlank(proName)){
            	continue;// 保证无错误数据
            }
            Object proValue = properties.getValue();
            PropertiesFilter.Type type = properties.getType();
            if(PropertiesFilter.Type.EQUAL == type) {
            	if(proValue == null){
            		continue;// 保证无错误数据
            	}
                if(proName.equals("keyWord")){
                	Pattern pattern=Pattern.compile("[\\u4e00-\\u9fa5]+");//判断是否含有中文，有中文不模糊查询  
            		Matcher matcher=pattern.matcher(proValue.toString().replaceAll(IndexEntity.excludeRegex,"").replaceAll(" ", ""));  
                    queryStr.append(" AND " + proName + ":");
                    if(matcher.find()){
                        if(proValue.toString().length()==1){
                            queryStr.append(" *"+proValue.toString().replaceAll(IndexEntity.excludeRegex,"").replaceAll(" ", "")+"* ");
                        }else{
                            queryStr.append(proValue.toString().replaceAll(IndexEntity.excludeRegex,"").replaceAll(" ", ""));
                        }
                    }else{
                    	queryStr.append(proValue.toString().replaceAll(IndexEntity.excludeRegex,"").replaceAll(" ", ""));
                    }
                    queryStr.append(" ");/*结尾加入空格*/
                } else {
                	queryStr.append(" AND ").append(proName + ":" + proValue).append(" ");
                }
            } else if (PropertiesFilter.Type.FACET == type){
                solrQuery.addFacetField(proName).setFacetMinCount(1).setFacetLimit(10);//每个类，只取相关度最高的10条
            }
        }
        String solrQueryStr = queryStr.toString();
        if(solrQueryStr.equals("keyWord:*")){
            logger.info("本次关键字搜索提示查询条件为："+solrQueryStr+";直接返回");
            _logger_solr.info("本次关键字搜索提示查询条件为："+solrQueryStr+";直接返回");
        	return solrQuery;
        }
        solrQueryStr = solrQueryStr.substring(14, solrQueryStr.length());
        logger.info("本次关键字搜索提示查询条件为：" + solrQueryStr);
        _logger_solr.info("本次关键字搜索提示查询条件为：" + solrQueryStr);
        solrQuery.setQuery(solrQueryStr);
        //设置搜索结果返回的字段
        solrQuery.setFields("id");
        solrQuery.setStart(0);
        solrQuery.setRows(0);
        return solrQuery;
    }
    
}
