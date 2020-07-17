/**
 * 文件名称   : com.autozi.lihf.es.service.AutoziPasscarDomainService.java
 * 项目名称   : 中驰车福-乘用车
 * 创建日期   : 2017-8-22
 * 更新日期   :
 * 作       者   : haifeng.li@autozi.com
 *
 * Copyright (C) 2016 中驰车福联合电子商务（北京）有限公司 版权所有.
 */
package com.autozi.common.search.es.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autozi.common.search.es.entity.AutoziPasscarDomain;
import com.autozi.common.search.es.repository.AutoziPasscarDomainRepository;

/**
 * <PRE>
 * 
 * 中文描述：
 * 
 * </PRE>
 * @version 1.0.0
 */
@Service
public class AutoziPasscarDomainService {
	
	@Autowired
	private AutoziPasscarDomainRepository domainRepository;
	
	public List<AutoziPasscarDomain> findBySourceIdAndDomainTypeIn(String sourceId,List<String> domainTypes){
		return domainRepository.findBySourceIdAndDomainTypeIn(sourceId,domainTypes);
	}
	

}
