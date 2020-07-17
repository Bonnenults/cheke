/**
 * 文件名称   : com.wms.base.dao.RunningNumber.java
 * 项目名称   : WMS
 * 创建日期   : 2013-03-25
 * 更新日期   :
 * 作       者   : haifeng.li@QeeGoo.com
 *
 * Copyright (C) 2013 LIHF 版权所有.
 */
package com.autozi.cheke.basic.dao;


import com.autozi.cheke.basic.mapper.RunningNumberMapper;
import com.autozi.cheke.basic.entity.RunningNumber;
import com.autozi.common.dal.mybatis.MyBatisDao;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * RunningNumberDao
 * 
 * @author lihf
 *
 */
@Component
public class RunningNumberDao extends MyBatisDao<RunningNumber> {
	
	public RunningNumber findByType(String type) {
		List<RunningNumber> list = getMapper(RunningNumberMapper.class).findByType(type);
		if (list == null || list.size() == 0) {
			return null;
		}
		
		if (list.size() > 1) {
			throw new RuntimeException("Not unique running number for type:"+type);
		}
		return list.get(0);
	}
	
}