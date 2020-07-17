package com.autozi.cheke.web.index.facade;

import com.autozi.cheke.service.user.IUserService;
import com.autozi.cheke.user.entity.Role;
import com.autozi.cheke.user.entity.User;
import com.autozi.cheke.user.type.IUserType;
import com.autozi.cheke.web.index.vo.Menu;
import com.autozi.cheke.web.index.vo.SystemMenus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class MenuFacade {

    @Autowired
    private IUserService userService;

    /**
     * 获得Menu对象
     *
     * @param id MenuID
     * @return Menu
     */
    public Menu getMenu(Integer id) {
        return SystemMenus.getMenu(id);
    }

    public Map<String, List<Menu>> findAuthority(User user) {
        List<Menu> menuList;
        if (user.getPartyId().equals(IUserType.AdminUserId)) {
            menuList = SystemMenus.getAllMenus(user);
        } else {
            menuList = filterFoSecurity(SystemMenus.getAllMenus(user), user);
        }
        Menu tmp = null;
        for (Menu menu : menuList) {
            if (menu.getId() == 10) {
                tmp = menu;
            }
        }
        menuList.remove(tmp);
        Map<String, List<Menu>> menus = new HashMap<String, List<Menu>>();
        for (Menu menu : menuList) {
            if (menus.containsKey(String.valueOf(menu.getParentId()))) {
                menus.get(String.valueOf(menu.getParentId())).add(menu);
            } else {
                List<Menu> children = new ArrayList<Menu>();
                children.add(menu);
                menus.put(String.valueOf(menu.getParentId()), children);
            }
        }
        return menus;
    }

    /**
     * 根据当前登录用户，对菜单进行过滤
     *
     * @param original 未进行过滤的菜单
     * @return 过滤后的菜单
     */
    public List<Menu> filterFoSecurity(List<Menu> original, User user) {
        Set<String> systemRoles = new HashSet<String>();
        Role role = userService.getRoleByUserId(user.getId());
        systemRoles.addAll(Arrays.asList(role.getSystemRoles()));
        List<Menu> filtered = new ArrayList<>();
        for (Menu menu : original) {
            if (menu.getId() == 10 || systemRoles.contains(menu.getSystemRole())) {
                filtered.add(menu);
            }
        }
        return filtered; //所有菜单
    }


    public List<Menu> getAvailableNavMenus(User user) {
        List<Menu> allAvailableMenus = filterFoSecurity(SystemMenus.getAllMenus(user), user); //所有菜单
        for (Menu menu : allAvailableMenus) {
            for (Menu another : allAvailableMenus) {
                if (menu.getId() == another.getParentId()) {
                    menu.getItems().add(another);
                }
            }
        }
        List<Menu> topMenus = new ArrayList<Menu>();
        for (Menu menu : allAvailableMenus) {
            if (menu.getParentId() == 0 && menu.getId() != 25) {
                topMenus.add(menu);
            }
        }
        return topMenus;
    }


}
