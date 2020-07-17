package com.autozi.cheke.service.user;

import com.autozi.cheke.user.entity.Role;
import com.autozi.cheke.user.entity.User;
import com.autozi.common.core.page.Page;

import java.util.List;
import java.util.Map;

/**
 * User: long.jin
 * Date: 2017-12-15
 * Time: 11:33
 */
public interface IRoleService {

    Role getRole(Long id);

    List<Role> listRole(Map<String,Object> filters);

    void saveRole(Role role);

    int deleteRole(Long roleId);

    Page<Role> findPageForMap(Page<Role> page, Map<String, Object> filters);


    public boolean checkRoleName(Long roleId, String roleName, User user) ;
}
