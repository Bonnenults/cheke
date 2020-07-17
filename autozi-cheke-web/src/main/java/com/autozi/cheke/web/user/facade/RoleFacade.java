package com.autozi.cheke.web.user.facade;

import com.autozi.cheke.service.user.IRoleService;
import com.autozi.cheke.user.entity.Role;
import com.autozi.cheke.user.entity.User;
import com.autozi.common.core.page.Page;
import org.apache.commons.lang.text.StrBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RoleFacade {
	@Autowired
	private IRoleService roleService;
	
	 public Role getRole(Long id) {
	        return roleService.getRole(id);
	 }
	 
	 public List<Role> listRole(Map<String, Object> filters) {
			return roleService.listRole(filters);
	 }

	 public Page<Role> findPageForMap(Page<Role> page,Map<String,Object> filters){
		 return roleService. findPageForMap(page,filters);
	 };


	public void saveRole(String name, String description, String systemRoles, Long id, User user) {
		Role role = new Role();
		role.setId(id);
		role.setName(name);
		role.setDescription(description);
		role.setSystemRolesStr(systemRoles);
		role.setPartyId(user.getPartyId());
		roleService.saveRole(role);
	}

	public int deleteRole(long roleId) {
		return roleService.deleteRole(roleId);
	}

	public boolean checkRoleName(Long roleId, String roleName, User user){
		return roleService.checkRoleName(roleId,roleName,user);
	} ;
}
