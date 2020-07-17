/**
 * 文件名称   : com.wms.base.mapper.RunningNumber.java
 * 项目名称   : WMS
 * 创建日期   : 2013-03-25
 * 更新日期   :
 * 作       者   : haifeng.li@QeeGoo.com
 *
 * Copyright (C) 2013 LIHF 版权所有.
 */
package com.autozi.cheke.basic.mapper;


import com.autozi.cheke.basic.entity.RunningNumber;
import com.autozi.common.dal.mapper.BaseMapper;

import java.util.List;

/**
 * RunningNumberMapper
 */
public interface RunningNumberMapper extends BaseMapper<RunningNumber> {

	List<RunningNumber> findByType(String type);
	
}