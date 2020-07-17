/**
 * 文件名称   : com.autozi.common.search.es.utils.QueryFilterHelper.java
 * 项目名称   : 中驰车福-乘用车
 * 创建日期   : 2017-8-23
 * 更新日期   :
 * 作       者   : haifeng.li@autozi.com
 *
 * Copyright (C) 2016 中驰车福联合电子商务（北京）有限公司 版权所有.
 */
package com.autozi.common.search.es.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;

import com.autozi.common.search.es.entity.AutoziPasscarDomain;
import com.autozi.common.search.es.repository.AutoziPasscarDomainRepository;
import com.autozi.passcar.cache.memcached.MemcacheKeyConstants;
import com.autozi.passcar.cache.memcached.client.B2bGoodsMemClient;

/**
 * <PRE>
 * 
 * 中文描述：
 * 
 * </PRE>
 * @version 1.0.0
 */
public class QueryFilterHelper {
	
	/**
	 * 
	 * <PRE>
	 * 
	 * 中文描述：商品基本信息查询-条件转换-AutoComplete
	 * 
	 * </PRE>
	 * @作者 lihf
	 * @日期 2017-8-23
	 * @param filters
	 * @return
	 */
	public static Criteria convertGoodsFilterMapToCriteriaForAutoComplete(Map<String, Object> filters){
		Criteria criteria = null;
		Iterator<String> ite = filters.keySet().iterator();
		while(ite.hasNext()){
			String key = ite.next();
			Object value = filters.get(key);
			if(key.equals("statusList")){
				@SuppressWarnings("unchecked")
				List<Integer> statusList = (List<Integer>)value;
				if(criteria!=null){
					criteria = criteria.and("status").in(statusList);
				}else{
					criteria = Criteria.where("status").in(statusList);
				}
				continue;
			}
			if(key.equals("onShoppingStatusList")){
				@SuppressWarnings("unchecked")
				List<Integer> onShoppingStatusList = (List<Integer>)value;
				if(criteria!=null){
					criteria = criteria.and("onShopping").in(onShoppingStatusList);
				}else{
					criteria = Criteria.where("onShopping").in(onShoppingStatusList);
				}
				continue;
			}
			if(StringUtils.isEmpty(String.valueOf(value))){
				continue;
			}
			String[] searchList = {"\\","+","-","!","(",")","（","）",":","^","[","]","\"","{","}","~","*","?","|","&",";","/"," "};
			String[] replacementList = {"","","","","","","","","","","","","","","","","","","","","","",""};
			String _value = StringUtils.replaceEach(String.valueOf(value), searchList, replacementList);
			if(key.equals("status")){
				if(criteria!=null){
					criteria = criteria.and("status").is(_value);
				}else{
					criteria = Criteria.where("status").is(_value);
				}
				continue;
			}
			if(key.equals("goodsNameFirstLetter")){
				if(criteria!=null){
					criteria = criteria.and("nameFirstLetter").startsWith(_value);
				}else{
					criteria = Criteria.where("nameFirstLetter").startsWith(_value);
				}
				continue;
			}
			if(key.equals("goodsName")){
				if(criteria!=null){
					criteria = criteria.and("nameFS").contains(_value);
				}else{
					criteria = Criteria.where("nameFS").contains(_value);
				}
				continue;
			}
			if(key.equals("goodsStyle")){
				if(criteria!=null){
					criteria = criteria.and("goodsStyleFS").startsWith(_value);
				}else{
					criteria = Criteria.where("goodsStyleFS").startsWith(_value);
				}
				continue;
			}
			if(key.equals("serialNumber")){
				if(criteria!=null){
					criteria = criteria.and("serialNumberFS").startsWith(_value);
				}else{
					criteria = Criteria.where("serialNumberFS").startsWith(_value);
				}
				continue;
			}
			if(key.equals("onShopping")){
				if(criteria!=null){
					criteria = criteria.and("onShopping").is(_value);
				}else{
					criteria = Criteria.where("onShopping").is(_value);
				}
				continue;
			}
			if(key.equals("onPurchaseStatus")){
				if(criteria!=null){
					criteria = criteria.and("onPurchaseStatus").is(_value);
				}else{
					criteria = Criteria.where("onPurchaseStatus").is(_value);
				}
				continue;
			}
			if(key.equals("supplierId")){
				if(criteria!=null){
					criteria = criteria.and("supplierId").is(_value);
				}else{
					criteria = Criteria.where("supplierId").is(_value);
				}
				continue;
			}
			// other conditions
		}
		return criteria;
	}
	
	/**
	 * 
	 * <PRE>
	 * 
	 * 中文描述：商品基本信息查询-条件转换
	 * 
	 * </PRE>
	 * @作者 lihf
	 * @日期 2017-8-23
	 * @param filters
	 * @return
	 */
	public static Criteria convertGoodsFilterMapToCriteria(Map<String, Object> filters){
		Criteria criteria = null;
		Iterator<String> ite = filters.keySet().iterator();
		while(ite.hasNext()){
			String key = ite.next();
			Object value = filters.get(key);
			if(key.equals("statusList")){
				@SuppressWarnings("unchecked")
				List<Integer> statusList = (List<Integer>)value;
				if(criteria!=null){
					criteria = criteria.and("status").in(statusList);
				}else{
					criteria = Criteria.where("status").in(statusList);
				}
				continue;
			}
			if(key.equals("onShoppingStatusList")){
				@SuppressWarnings("unchecked")
				List<Integer> onShoppingStatusList = (List<Integer>)value;
				if(criteria!=null){
					criteria = criteria.and("onShopping").in(onShoppingStatusList);
				}else{
					criteria = Criteria.where("onShopping").in(onShoppingStatusList);
				}
				continue;
			}
			if(StringUtils.isEmpty(String.valueOf(value))){
				continue;
			}
			String[] searchList = {"\\","+","-","!","(",")","（","）",":","^","[","]","\"","{","}","~","*","?","|","&",";","/"," "};
			String[] replacementList = {"","","","","","","","","","","","","","","","","","","","","","",""};
			String _value = StringUtils.replaceEach(String.valueOf(value), searchList, replacementList);
			if(key.equals("status")){
				if(criteria!=null){
					criteria = criteria.and("status").is(_value);
				}else{
					criteria = Criteria.where("status").is(_value);
				}
				continue;
			}
			if(key.equals("onShopping")){
				if(criteria!=null){
					criteria = criteria.and("onShopping").is(_value);
				}else{
					criteria = Criteria.where("onShopping").is(_value);
				}
				continue;
			}
			if(key.equals("goodsCode")||key.equals("b2bCode")){
				if(criteria!=null){
					criteria = criteria.and("b2bCode").is(_value);
				}else{
					criteria = Criteria.where("b2bCode").is(_value);
				}
				continue;
			}
			if(key.equals("supplierName")){
				if(criteria!=null){
					criteria = criteria.and("supplierNameFS").is(_value);
				}else{
					criteria = Criteria.where("supplierNameFS").is(_value);
				}
				continue;
			}
			if(key.equals("goodsBrand")||key.equals("brandName")){
				if(criteria!=null){
					criteria = criteria.and("brandNameFS").is(_value);
				}else{
					criteria = Criteria.where("brandNameFS").is(_value);
				}
				continue;
			}
			if(key.equals("goodsName")){
				if(criteria!=null){
					criteria = criteria.and("nameFS").is(_value);
				}else{
					criteria = Criteria.where("nameFS").is(_value);
				}
				continue;
			}
			if(key.equals("goodsStyle")){
				if(criteria!=null){
					criteria = criteria.and("goodsStyleFS").contains(_value);
				}else{
					criteria = Criteria.where("goodsStyleFS").contains(_value);
				}
				continue;
			}
			if(key.equals("serialNumber")){
				if(criteria!=null){
					criteria = criteria.and("serialNumberFS").contains(_value);
				}else{
					criteria = Criteria.where("serialNumberFS").contains(_value);
				}
				continue;
			}
			if(key.equals("oemAlias")){
				if(criteria!=null){
					criteria = criteria.and("oemAliasFS").contains(_value);
				}else{
					criteria = Criteria.where("oemAliasFS").contains(_value);
				}
				continue;
			}
			if(key.equals("productCode")){
				if(criteria!=null){
					criteria = criteria.and("productCode").is(_value);
				}else{
					criteria = Criteria.where("productCode").is(_value);
				}
				continue;
			}
			if(key.equals("productName")){
				if(criteria!=null){
					criteria = criteria.and("productNameFS").is(_value);
				}else{
					criteria = Criteria.where("productNameFS").is(_value);
				}
				continue;
			}
			if(key.equals("shoppingPrice")){
				if(criteria!=null){
					criteria = criteria.and("shoppingPrice").between(new Double(0.00d), new Double(_value));
				}else{
					criteria = Criteria.where("shoppingPrice").between(new Double(0.00d), new Double(_value));
				}
				continue;
			}
			if(key.equals("onPurchaseStatus")){
				if(criteria!=null){
					criteria = criteria.and("onPurchaseStatus").is(_value);
				}else{
					criteria = Criteria.where("onPurchaseStatus").is(_value);
				}
				continue;
			}
			if(key.equals("supplierId")){
				if(criteria!=null){
					criteria = criteria.and("supplierId").is(_value);
				}else{
					criteria = Criteria.where("supplierId").is(_value);
				}
				continue;
			}
			if(key.equals("subPartyId")){
				_value = _value.substring(0, _value.length()-1);
				if(criteria!=null){
					criteria = criteria.and("subChainList").contains(_value);
				}else{
					criteria = Criteria.where("subChainList").contains(_value);
				}
				continue;
			}
			// other conditions
		}
		return criteria;
	}
	
	/**
	 * 
	 * <PRE>
	 * 
	 * 中文描述：设置数据权限
	 * 
	 * </PRE>
	 * @作者 lihf
	 * @日期 2017-8-23
	 * @param domainRepository
	 * @param criteria
	 * @param filters
	 */
	public static void setDataScope(AutoziPasscarDomainRepository domainRepository,Criteria criteria
			,Map<String, Object> filters){
		Set<String> productIds = new HashSet<String>();
		Set<String> brandIds = new HashSet<String>();
		if(filters.containsKey("sourceId")){
			String domainId = String.valueOf((Long)filters.get("sourceId"));
			Criteria dataQuery = Criteria.where("sourceId").is(domainId);
			CriteriaQuery query = new CriteriaQuery(dataQuery);
			query.addIndices();
			
			List<String> domainTypes = new ArrayList<String>();
			domainTypes.add("product");
			domainTypes.add("brand");
			// 先查询缓存
			String key=MemcacheKeyConstants.DataScope._部门数据权限+String.valueOf((Long)filters.get("sourceId"));
			@SuppressWarnings("unchecked")
			List<AutoziPasscarDomain> domains = (List<AutoziPasscarDomain>)B2bGoodsMemClient.getInstance().get(key);
			if(domains==null){
				domains = domainRepository.findBySourceIdAndDomainTypeIn(String.valueOf((Long)filters.get("sourceId"))
						, domainTypes);
				B2bGoodsMemClient.getInstance().set(key,MemcacheKeyConstants.DataScope._部门数据权限_过期时间, (List<AutoziPasscarDomain>)domains);
			}
			for (AutoziPasscarDomain autoziPasscarDomain : domains) {
				if(autoziPasscarDomain.getDomainType().equals("product")){
					productIds.add(String.valueOf(autoziPasscarDomain.getDomainId()));
				}else if(autoziPasscarDomain.getDomainType().equals("brand")){
					brandIds.add(String.valueOf(autoziPasscarDomain.getDomainId()));
				}
			}
		}
		if(productIds!=null&&productIds.size()>0){
			criteria = criteria.and("productId").in(productIds);
		}
		if(brandIds!=null&&brandIds.size()>0){
			criteria = criteria.and("brandId").in(brandIds);
		}
	}

}
