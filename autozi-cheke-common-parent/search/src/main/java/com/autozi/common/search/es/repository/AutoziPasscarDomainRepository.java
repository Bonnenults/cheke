/**
 * 文件名称   : com.autozi.common.search.es.repository.AutoziPasscarDomainRepository.java
 * 项目名称   : 中驰车福-乘用车
 * 创建日期   : 2017-8-7
 * 更新日期   :
 * 作       者   : haifeng.li@autozi.com
 *
 * Copyright (C) 2016 中驰车福联合电子商务（北京）有限公司 版权所有.
 */
package com.autozi.common.search.es.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.autozi.common.search.es.entity.AutoziPasscarDomain;

/**
 * <PRE>
 * 
 * 中文描述：
 * 
 * </PRE>
 * @version 1.0.0
 */
public interface AutoziPasscarDomainRepository extends PagingAndSortingRepository<AutoziPasscarDomain, Long> {
	
	/**
	 * 
	 * <PRE>
	 * 
	 * 中文描述：根据部门ID+数据类型
	 * 
	 * </PRE>
	 * @作者 lihf
	 * @日期 2017-8-22
	 * @param sourceId
	 * @param domainType
	 * @return
	 */
	List<AutoziPasscarDomain> findBySourceIdAndDomainTypeIn(String sourceId,
			List<String> domainTypes);
}
