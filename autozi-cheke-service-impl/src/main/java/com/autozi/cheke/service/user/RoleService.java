package com.autozi.cheke.service.user;

import com.autozi.cheke.user.dao.RoleDao;
import com.autozi.cheke.user.dao.UserRoleDao;
import com.autozi.cheke.user.entity.Role;
import com.autozi.cheke.user.entity.User;
import com.autozi.common.core.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 安全相关实体的管理类, 包括用户,角色,资源与授权类.
 * 默认将类中的所有函数纳入事务管理.
 *
 * @author haijun
 */
@Component
public class RoleService  implements IRoleService{
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleDao userRoleDao;

    //-- Role Manager --//
    public Role getRole(Long id) {
        return roleDao.get(id);
    }

    public List<Role> listRole(Map<String, Object> filter) {
        return roleDao.findListForMap(filter);
    }

    public int deleteRole(Long id) {
        List<Long> userIds = roleDao.findUserIdsByRole(id);
        int result;
        if (userIds == null || userIds.isEmpty()) {
            Role role = roleDao.get(id);
            if (role.getStatus() != Role.STATUS_DEL) {
                role.setStatus(Role.STATUS_DEL);
            }
            result = roleDao.update(role);
        } else {
            result = 0;
        }
        return result;
    }

    public Role findRolesByUserId(Long userId) {
        return roleDao.findRoleByUserId(userId);
    }


    public Set<String> findSystemRoles(Long userId) {
        Set<String> systemRoles = new HashSet<String>();
        Role role = findRolesByUserId(userId);
        systemRoles.addAll(Arrays.asList(role.getSystemRoles()));
        return systemRoles;
    }


    public void saveRole(Role role) {
        if (role.getId() == null) {
            role.setStatus(1);
            roleDao.insert(role);
        } else {
            Role updateRole = roleDao.get(role.getId());
            updateRole.setName(role.getName());
            updateRole.setDescription(role.getDescription());
            updateRole.setSystemRoles(role.getSystemRoles());
            roleDao.update(updateRole);
        }
    }

    public boolean checkRoleName(Long roleId, String roleName, User user) {
        Map<String, Object> filter = new HashMap<String, Object>();
        filter.put("roleName", roleName);
        filter.put("status", Role.STATUS_NORMAL);
        filter.put("partyId", user.getPartyId());
        List<Role> list = roleDao.checkRoleUnique(filter);
        if (list.size() == 0) {
            return true;
        } else {
            Role role = list.get(0);
            return role.getId().equals(roleId);
        }
    }

    public Page<Role> findPageForMap(Page<Role> page, Map<String, Object> filters) {
        return roleDao.findPage(page, filters);
    }

    public Long getRoleIdByName(String name) {
        return roleDao.getRoleIdByName(name);
    }


}
