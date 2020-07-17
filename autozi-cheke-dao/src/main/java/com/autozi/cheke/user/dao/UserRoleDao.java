/**
 * 文件名称   : com.qeegoo.jxc.user.dao.UserRole.java
 * 项目名称   : 中驰汽配交易平台
 * 创建日期   : 2014-02-17
 * 更新日期   :
 * 作       者   : haifeng.li@QeeGoo.com
 *
 * Copyright (C) 2013 启购时代 版权所有.
 */
package com.autozi.cheke.user.dao;

import com.autozi.cheke.user.entity.UserRole;
import com.autozi.cheke.user.mapper.UserRoleMapper;
import com.autozi.common.dal.mybatis.MyBatisDao;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * UserRoleDao
 * 
 * @author lihf
 *
 */
@Component
public class UserRoleDao extends MyBatisDao<UserRole> {

    public UserRole getByMap(Long userId,Long roleId){
        Map<String ,Object> map =new HashMap<String, Object>();
        map.put("userId",userId);
        map.put("roleId",roleId);
        List<UserRole>userRoleList=findListForMap(map);
        if(userRoleList.size()>0){
            return userRoleList.get(0);
        }
        return null;
    }

    public void clearUserRole(Long  userId){
        getMapper(UserRoleMapper.class).deleteUserRole(userId);
    }
}