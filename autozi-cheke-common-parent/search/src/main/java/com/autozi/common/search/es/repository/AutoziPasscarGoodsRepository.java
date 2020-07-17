/**
 * 文件名称   : com.autozi.common.search.es.repository.AutoziPasscarGoodsRepository.java
 * 项目名称   : 中驰车福-乘用车
 * 创建日期   : 2017-8-7
 * 更新日期   :
 * 作       者   : haifeng.li@autozi.com
 *
 * Copyright (C) 2016 中驰车福联合电子商务（北京）有限公司 版权所有.
 */
package com.autozi.common.search.es.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.autozi.common.search.es.entity.AutoziPasscarGoods;

/**
 * <PRE>
 * 
 * 中文描述：
 * 
 * </PRE>
 * @version 1.0.0
 */
public interface AutoziPasscarGoodsRepository extends PagingAndSortingRepository<AutoziPasscarGoods, Long> {
	
	/**
	 * 
	 * <PRE>
	 * 
	 * 中文描述：根据商品名称查询
	 * 
	 * </PRE>
	 * @作者 lihf
	 * @日期 2017-8-7
	 * @param name
	 * @return
	 */
	public List<AutoziPasscarGoods> findByName(String name);
	/**
	 * 
	 * <PRE>
	 * 
	 * 中文描述：根据商品标准名称查询
	 * 
	 * </PRE>
	 * @作者 lihf
	 * @日期 2017-8-7
	 * @param standardName
	 * @return
	 */
	public List<AutoziPasscarGoods> findByStandardName(String standardName);
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
	public List<AutoziPasscarGoods> findByBrandIdIn(List<Long> brandIds);
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
	public Page<AutoziPasscarGoods> findByName(String name,Pageable pageable);
	
}
