package com.autozi.cheke.user.dao;

import com.autozi.cheke.user.entity.Role;
import com.autozi.cheke.user.mapper.RoleMapper;
import com.autozi.common.core.page.Page;
import com.autozi.common.dal.mybatis.MyBatisDao;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class RoleDao extends MyBatisDao<Role> {
    /**
    	 * 重载函数,因为Role中没有建立与User的关联,因此需要以较低效率的方式进行删除User与Role的多对多中间表.
    	 */
    	@Override
    	public int delete(Long id) {
    		Role role = get(id);
    		//查询出拥有该角色的用户,并删除该用户的角色.业务逻辑错误
    		//getMapper(RoleMapper.class).deleteFromUser(id);
    		//查询出拥有该角色的用户,并删除该角色的所有权限.
    		return super.delete(role);
    	}

    	public List<Role> checkRoleUnique(Map<String, Object> filter){
    		return getMapper(RoleMapper.class).checkRoleUnique(filter);
    	}

    	public Role findRoleByUserId(Long user_id){
    		return getMapper(RoleMapper.class).findRoleByUserId(user_id);
    	}

    	public List<Role> findRoleByPartyId(Map<String, Object> filter){
    		return getMapper(RoleMapper.class).findRoleByPartyId(filter);
    	}

    	public List<Long> findUserIdsByRole(Long roleId){
    		return getMapper(RoleMapper.class).findUserIdsByRole(roleId);
    	}

        public Page<Role> findPage(Page<Role> page, Map<String,Object> filters){
            return findPageForMap(page,filters);
        }
        public Long getRoleIdByName(String name){
            return getMapper(RoleMapper.class).getRoleIdByName(name);
        }
}
