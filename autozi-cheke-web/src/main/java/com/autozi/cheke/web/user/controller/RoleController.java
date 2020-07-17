package com.autozi.cheke.web.user.controller;

import com.autozi.cheke.user.entity.Role;
import com.autozi.cheke.user.entity.User;
import com.autozi.cheke.util.mvc.BaseController;
import com.autozi.cheke.web.index.facade.MenuFacade;
import com.autozi.cheke.web.index.vo.Menu;
import com.autozi.cheke.web.user.facade.RoleFacade;
import com.autozi.common.core.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: kai.liu
 * Date: 12-1-4
 * Time: 下午1:16
 */
@RequestMapping("/role")
@Controller
public class RoleController extends BaseController {


    @Autowired
    private MenuFacade menuFacade;

    @Autowired
    private RoleFacade roleFacade;


    /**
     * @param uiModel
     * @param request
     * @param ajax
     * @return
     * @Description: 平台的
     */
    @RequestMapping("/admin/list/listRole.action")
    public String listRole(Model uiModel, HttpServletRequest request, String ajax) {
        HashMap<String, Object> filters = buildFilter(request, uiModel);
        Page<Role> page = buildPage(request);
        page.setPageSize(10);
        filters.put("partyId", getCurrentUserPartyId());
        filters.put("status",Role.STATUS_NORMAL);
        Page<Role> viewPage = roleFacade.findPageForMap(page, filters);
        uiModel.addAttribute("page", viewPage);
        this.pageInfoByMap(uiModel, viewPage, filters);
        if (ajax != null) {
            return "/admin/role/listRole_ajax";
        }
        return "/admin/role/listRole.html";
    }

    /**
     * @param roleId
     * @param uiModel
     * @return
     * @Description: 平台的
     */
    @RequestMapping("/admin/list/editRole.action")
    public String editRole(@RequestParam(required = false) Long roleId, Model uiModel) {
        Map<String, List<Menu>> authorities = menuFacade.findAuthority(this.getCurrentUser());
        uiModel.addAttribute("authorities", authorities);
        if (roleId != null) {
            Role role = roleFacade.getRole(roleId);
            Set<String> systemRoles = new HashSet<String>();
            systemRoles.addAll(Arrays.asList(role.getSystemRoles()));

            uiModel.addAttribute("role", role);
            uiModel.addAttribute("systemRoles", systemRoles);
        }
        uiModel.addAttribute("userPartyId", this.getCurrentUserPartyId());
        return "/admin/role/editRole.html";
    }

    /**
     * @return
     * @throws Exception
     * @Description: 平台的
     * @author chuanwen.li
     * @date 2017年4月11日 下午5:36:31
     */
    @RequestMapping("/admin/list/save.action")
    public void save(String name, String description, String systemRoles, Long id, HttpServletResponse response) throws Exception {
        Map<String, String> result = new HashMap<>();
        String msg = "true";
        try {
            User user = getCurrentUser();
            if (roleFacade.checkRoleName(id, name, user)) {  //名字不重复
                roleFacade.saveRole(name, description, systemRoles, id, user);
            } else {
                msg = "doubleName";
            }
        } catch (Exception e) {
            e.printStackTrace();
            msg = "error";
        } finally {
            this.responseJson(response,"200",msg);
        }


    }

    @RequestMapping("/admin/list/deleteRole.action")
    public void delete(@RequestParam long roleId, HttpServletResponse response) throws Exception {
        int result = roleFacade.deleteRole(roleId);
        String msg;
        if (result > 0) {
            msg = "删除成功！";
        } else {
            msg = "此角色已经被分配，不能删除！";
        }
        this.responseJson(response,"200",msg);
    }

}
