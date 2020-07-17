package com.autozi.cheke.user.mapper;
import com.autozi.common.dal.mapper.BaseMapper;
import com.autozi.cheke.user.entity.Role;

import java.util.List;
import java.util.Map;

public interface RoleMapper extends BaseMapper<Role> {
    List<Role> checkRoleUnique(Map<String, Object> filter);

    Role findRoleByUserId(Long user_id);

    	List<Role> findRoleByPartyId(Map<String, Object> filter);

    	List<Long> findUserIdsByRole(Long roleId);

    	Long getRoleIdByName(String name);
}
