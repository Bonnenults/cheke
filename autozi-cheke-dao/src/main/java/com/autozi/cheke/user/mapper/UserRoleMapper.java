/**
 * 文件名称   : com.qeegoo.jxc.user.mapper.UserRole.java
 * 项目名称   : 中驰汽配交易平台
 * 创建日期   : 2014-02-17
 * 更新日期   :
 * 作       者   : haifeng.li@QeeGoo.com
 *
 * Copyright (C) 2013 启购时代 版权所有.
 */
package com.autozi.cheke.user.mapper;


import com.autozi.cheke.user.entity.UserRole;
import com.autozi.common.dal.mapper.BaseMapper;

/**
 * UserRoleMapper
 * 
 * @author lihf
 *
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {

    void deleteUserRole(Long userId);
	
}