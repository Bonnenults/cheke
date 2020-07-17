/**
 * 文件名称   : com.autozi.common.search.es.service.AutoziPasscarGoodsService.java
 * 项目名称   : 中驰车福-乘用车
 * 创建日期   : 2017-8-7
 * 更新日期   :
 * 作       者   : haifeng.li@autozi.com
 *
 * Copyright (C) 2016 中驰车福联合电子商务（北京）有限公司 版权所有.
 */
package com.autozi.common.search.es.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.DeleteQuery;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.stereotype.Service;

import com.autozi.common.search.es.entity.AutoziPasscarDomain;
import com.autozi.common.search.es.entity.AutoziPasscarGoods;
import com.autozi.common.search.es.repository.AutoziPasscarDomainRepository;
import com.autozi.common.search.es.repository.AutoziPasscarGoodsRepository;
import com.autozi.common.search.es.utils.QueryFilterHelper;

/**
 * <PRE>
 * 
 * 中文描述：
 * 
 * </PRE>
 * @version 1.0.0
 */
@Service
public class AutoziPasscarGoodsService {
	
	@Autowired
	private AutoziPasscarGoodsRepository goodsRepository;
	@Autowired
	private AutoziPasscarDomainRepository domainRepository;
	@Autowired
	private ElasticsearchTemplate template;
	
	/**
	 * 
	 * <PRE>
	 * 
	 * 中文描述：从Es返回商品数据
	 * 
	 * </PRE>
	 * @作者 lihf
	 * @日期 2017-9-17
	 * @param filters
	 * @return
	 */
	public List<Map<String, Object>> autocompleteGoods(Map<String, Object> filters){
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		// 基本信息
		Criteria criteria = QueryFilterHelper.convertGoodsFilterMapToCriteriaForAutoComplete(filters);
		if(criteria!=null){
			// 权限数据 -先去掉
			QueryFilterHelper.setDataScope(domainRepository, criteria, filters);
			CriteriaQuery query = new CriteriaQuery(criteria);
			// 排序信息
			if(filters.containsKey("goodsNameFirstLetter")||filters.containsKey("goodsName")){
				query.addSort(new Sort(Direction.ASC, "name"));
			}else if(filters.containsKey("goodsStyle")){
				query.addSort(new Sort(Direction.ASC, "goodsStyle"));
			}else if(filters.containsKey("serialNumber")){
				query.addSort(new Sort(Direction.ASC, "serialNumber"));
			}
			// 分页信息
			query.setPageable(new PageRequest(0, 10));
			Page<AutoziPasscarGoods> page = template.queryForPage(query, AutoziPasscarGoods.class);
			if(page.getContent().size()>0){
				List<AutoziPasscarGoods> _list = page.getContent();
				Set<String> _set = new HashSet<String>();
				for (AutoziPasscarGoods autoziPasscarGoods : _list) { // 去重
					if(filters.containsKey("goodsNameFirstLetter")||filters.containsKey("goodsName")){
						_set.add(autoziPasscarGoods.getName());
					}else if(filters.containsKey("goodsStyle")){
						_set.add(autoziPasscarGoods.getGoodsStyle());
					}else if(filters.containsKey("serialNumber")){
						_set.add(autoziPasscarGoods.getSerialNumber());
					}
				}
				for (String string : _set) {
					Map<String, Object> map = new HashMap<String,Object>();
					if(filters.containsKey("goodsNameFirstLetter")||filters.containsKey("goodsName")){
						map.put("NAME", string);
					}else if(filters.containsKey("goodsStyle")){
						map.put("GOODS_STYLE", string);
					}else if(filters.containsKey("serialNumber")){
						map.put("SERIAL_NUMBER", string);
					}
					result.add(map);
				}
			}
		}
		return result;
	}
	
	
	/**
	 * 
	 * <PRE>
	 * 
	 * 中文描述：商品综合查询-分页
	 * 
	 * </PRE>
	 * @作者 lihf
	 * @日期 2017-8-23
	 * @param filters
	 * @param pageNo
	 * @param size
	 * @return
	 */
	public Page<AutoziPasscarGoods> findGoodsPage(Map<String, Object> filters,int pageNo,int size) {
		// 基本信息
		Criteria criteria = QueryFilterHelper.convertGoodsFilterMapToCriteria(filters);
		if(criteria!=null){
			// 权限数据
			QueryFilterHelper.setDataScope(domainRepository, criteria, filters);
			CriteriaQuery query = new CriteriaQuery(criteria);
			// 排序信息
			if(filters.containsKey("sort")
					&&filters.containsKey("sortWay")){// 有指定排序
				query.addSort(new Sort(((String)filters.get("sortWay")).equals("0")?Direction.ASC:Direction.DESC, (String)filters.get("sort")));
			}else{// 默认排序
				// 1、品牌+品类+供应商组合排序
				boolean brand = Boolean.FALSE;
				if(filters.containsKey("goodsBrand")||filters.containsKey("brandName")){
					brand = Boolean.TRUE;
				}
				boolean product = Boolean.FALSE;
				if(filters.containsKey("productName")||filters.containsKey("productCode")){
					product = Boolean.TRUE;
				}
				boolean supplier = Boolean.FALSE;
				if(filters.containsKey("supplierName")){
					supplier = Boolean.TRUE;
				}
				boolean isSort = Boolean.FALSE;
				if(brand||product||supplier){
					if(brand&&product){
						query = query.addSort(new Sort(Direction.ASC, "brandName")).addSort(new Sort(Direction.ASC, "productName"));
						isSort = Boolean.TRUE;
					}else if(brand&&!product){
						query = query.addSort(new Sort(Direction.ASC, "brandName"));
						isSort = Boolean.TRUE;
					}else if(!brand&&product){
						query = query.addSort(new Sort(Direction.ASC, "productName"));
						isSort = Boolean.TRUE;
					}
					if(supplier){
						query = query.addSort(new Sort(Direction.ASC, "supplierName"));
						isSort = Boolean.TRUE;
					}
				}
				// 2、商品名称、规格型号、出厂编码
				if(!isSort&&filters.containsKey("goodsName")){
					query = query.addSort(new Sort(Direction.ASC, "name"));
					isSort = Boolean.TRUE;
				}
				// 3、默认按照时间排序
				if(!isSort){
					query.addSort(new Sort(Direction.DESC, "updateTime")).addSort(new Sort(Direction.ASC,"productId")).addSort(new Sort(Direction.ASC,"brandId"));
				}
			}
			// 分页信息
			pageNo = pageNo-1;
			if(pageNo<0){
				pageNo = 0;
			}
			query.setPageable(new PageRequest(pageNo, size));
			return template.queryForPage(query, AutoziPasscarGoods.class);
		}
		return null;
	}
	
	/**
	 * 
	 * <PRE>
	 * 
	 * 中文描述：商品综合查询-不分页
	 * 
	 * </PRE>
	 * @作者 lihf
	 * @日期 2017-8-23
	 * @param filters
	 * @param pageNo
	 * @param size
	 * @return
	 */
	public List<AutoziPasscarGoods> findGoods(Map<String, Object> filters) {
		// 基本信息
		Criteria criteria = QueryFilterHelper.convertGoodsFilterMapToCriteria(filters);
		if(criteria!=null){
			// 权限数据
			QueryFilterHelper.setDataScope(domainRepository, criteria, filters);
			CriteriaQuery query = new CriteriaQuery(criteria);
			// 排序信息
			if(filters.containsKey("sort")
					&&filters.containsKey("sortWay")){
				query.addSort(new Sort(((String)filters.get("sortWay")).equals("0")?Direction.ASC:Direction.DESC, (String)filters.get("sort")));
			}else{// 默认排序
				query.addSort(new Sort(Direction.DESC, "id")).addSort(new Sort(Direction.ASC,"productId")).addSort(new Sort(Direction.ASC,"brandId"));
			}
			return template.queryForList(query, AutoziPasscarGoods.class);
		}
		return null;
	}
	
	/**
	 * 
	 * <PRE>
	 * 
	 * 中文描述：
	 * 
	 * </PRE>
	 * @作者 lihf
	 * @日期 2017-8-7
	 * @param name
	 * @return
	 */
	public List<AutoziPasscarGoods> findByName(String name){
		return goodsRepository.findByName(name);
	}
	
	/**
	 * 
	 * <PRE>
	 * 
	 * 中文描述：
	 * 
	 * </PRE>
	 * @作者 lihf
	 * @日期 2017-8-7
	 * @param standardName
	 * @return
	 */
	public List<AutoziPasscarGoods> findByStandardName(String standardName){
		return goodsRepository.findByStandardName(standardName);
	}
	
	/**
	 * 
	 * <PRE>
	 * 
	 * 中文描述：查询在指定品牌范围内的商品列表
	 * 
	 * </PRE>
	 * @作者 lihf
	 * @日期 2017-8-7
	 * @param brandIds
	 * @return
	 */
	public List<AutoziPasscarGoods> findByBrandIdIn(List<Long> brandIds){
		return goodsRepository.findByBrandIdIn(brandIds);
	}
	
	/**
	 * 
	 * <PRE>
	 * 
	 * 中文描述：根据商品名称查询，并且分页
	 * 
	 * </PRE>
	 * @作者 lihf
	 * @日期 2017-8-7
	 * @param name
	 * @param pageable
	 * @return
	 */
	public Page<AutoziPasscarGoods> findByName(String name,Pageable pageable){
		return goodsRepository.findByName(name, pageable);
	}
	
	/**
	 * 
	 * <PRE>
	 * 
	 * 中文描述：单个建立索引
	 * 
	 * </PRE>
	 * @作者 lihf
	 * @日期 2017-8-7
	 * @param goods
	 * @return
	 */
	public int indexGoods(AutoziPasscarGoods goods){
		try{
			goodsRepository.save(goods);
			return 1;
		}catch(Exception ex){
			ex.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 
	 * <PRE>
	 * 
	 * 中文描述：单个建立索引
	 * 
	 * </PRE>
	 * @作者 lihf
	 * @日期 2017-8-7
	 * @param goods
	 * @return
	 */
	public int indexDomain(AutoziPasscarDomain domain){
		try{
			domainRepository.save(domain);
			return 1;
		}catch(Exception ex){
			ex.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 
	 * <PRE>
	 * 
	 * 中文描述：批量建立商品索引
	 * 
	 * </PRE>
	 * @作者 lihf
	 * @日期 2017-8-7
	 * @param passcarGoods
	 * @return
	 */
	public int bulkIndexGoods(List<AutoziPasscarGoods> passcarGoods){
		try{
			goodsRepository.save(passcarGoods);
			return 1;
		}catch(Exception ex){
			ex.printStackTrace();
			return 0;
		}
	}
	
	
	/**
	 * 
	 * <PRE>
	 * 
	 * 中文描述：根据部门ID和数据权限类型删除全部数据
	 * 
	 * </PRE>
	 * @作者 lihf
	 * @日期 2017-9-13
	 * @param sourceId
	 * @param domainType
	 * @return
	 */
	public int deleteDomainBySourceIdAndDomainType(String sourceId,String domainType){
		DeleteQuery deleteQuery = new DeleteQuery();
		deleteQuery.setPageSize(5000); // 最大5000条记录
		deleteQuery.setQuery(QueryBuilders.boolQuery()
				.must(QueryBuilders.termQuery("sourceId", sourceId))
				.must(QueryBuilders.termQuery("domainType", domainType)));
		try{
			template.delete(deleteQuery, AutoziPasscarDomain.class);
			return 1;
		}catch(Exception ex){
			ex.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 
	 * <PRE>
	 * 
	 * 中文描述：批量建立数据权限索引
	 * 
	 * </PRE>
	 * @作者 lihf
	 * @日期 2017-8-7
	 * @param passcarGoods
	 * @return
	 */
	public int bulkIndexDomain(List<AutoziPasscarDomain> passcarDomain){
		try{
			domainRepository.save(passcarDomain);
			return 1;
		}catch(Exception ex){
			ex.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 
	 * <PRE>
	 * 
	 * 中文描述：更新商品索引信息
	 * 
	 * </PRE>
	 * @作者 lihf
	 * @日期 2017-8-7
	 * @param goods
	 * @return
	 */
	public int updateGoods(AutoziPasscarGoods goods){
		return indexGoods(goods);
	}
	
	/**
	 * 
	 * <PRE>
	 * 
	 * 中文描述：批量更新商品索引
	 * 
	 * </PRE>
	 * @作者 lihf
	 * @日期 2017-8-7
	 * @param passcarGoods
	 * @return
	 */
	public int bulkUpdateGoods(List<AutoziPasscarGoods> passcarGoods){
		return bulkIndexGoods(passcarGoods);
	}
	
	/**
	 * 
	 * <PRE>
	 * 
	 * 中文描述：
	 * 
	 * </PRE>
	 * @作者 lihf
	 * @日期 2017-8-7
	 * @param id
	 * @return
	 */
	public int deleteGoods(Long id){
		try{
			goodsRepository.delete(id);
			return 1;
		}catch(Exception ex){
			ex.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 
	 * <PRE>
	 * 
	 * 中文描述：
	 * 
	 * </PRE>
	 * @作者 lihf
	 * @日期 2017-8-7
	 * @param goods
	 * @return
	 */
	public int deleteGoods(AutoziPasscarGoods goods){
		try{
			goodsRepository.delete(goods);
			return 1;
		}catch(Exception ex){
			ex.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 
	 * <PRE>
	 * 
	 * 中文描述：
	 * 
	 * </PRE>
	 * @作者 lihf
	 * @日期 2017-8-7
	 * @param goods
	 * @return
	 */
	public int deleteDomain(long id){
		try{
			domainRepository.delete(id);
			return 1;
		}catch(Exception ex){
			ex.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 
	 * <PRE>
	 * 
	 * 中文描述：建立Domain索引
	 * 
	 * </PRE>
	 * @作者 lihf
	 * @日期 2017-9-12
	 * @param indexName
	 * @param queries
	 * @return
	 */
	@Deprecated
	public int bulkDomain(String indexName,List<IndexQuery> queries){
		try{
			if(!template.indexExists(indexName)){// 不存在则先创建索引
				Map<String, String> settings = new HashMap<String, String>();
				settings.put("index.number_of_shards", String.valueOf(6));// 设置索引的分片数
				settings.put("index.number_of_replicas", String.valueOf(1));// 设置索引的副本数
				template.createIndex(indexName, settings);
			}
			template.bulkIndex(queries);
			return 1;
		}catch(Exception ex){
			ex.printStackTrace();
			return 0;
		}
	}

}
