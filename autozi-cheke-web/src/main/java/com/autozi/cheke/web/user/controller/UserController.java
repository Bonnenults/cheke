package com.autozi.cheke.web.user.controller;

import com.autozi.cheke.user.entity.FeedBack;
import com.autozi.cheke.user.entity.Role;
import com.autozi.cheke.user.entity.User;
import com.autozi.cheke.user.type.IUserType;
import com.autozi.cheke.util.mvc.BaseController;
import com.autozi.cheke.web.user.facade.FeedBackFacade;
import com.autozi.cheke.web.user.facade.UserFacade;
import com.autozi.cheke.web.user.vo.FeedBackView;
import com.autozi.cheke.web.user.vo.UserView;
import com.autozi.common.core.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by lbm on 2017/11/28.
 */
@RequestMapping("/user")
@Controller
public class UserController extends BaseController {
    @Autowired
    private UserFacade userFacade;

    @Autowired
    private FeedBackFacade feedBackFacade;

    @RequestMapping("/admin/list/listUser.action")
    public String listUser(HttpServletRequest request, Model uiModel, String ajax) {
        Page<User> page = this.buildPage(request);
        page.setPageSize(10);
        Map<String, Object> filters = buildFilter(request, uiModel);
        filters.put("status", IUserType.STATUS_NORMAL);
        filters.put("partyId", getCurrentUserPartyId());
        Page<UserView> userViewPage = userFacade.findForUserPage(page, filters);
        uiModel.addAttribute("page", userViewPage);
        this.pageInfoByMap(uiModel, userViewPage, filters);
        if (ajax != null) {
            return "/admin/user/listUser_ajax";
        }
        uiModel.addAttribute("statusMap",IUserType.userStatusMap);
        return "/admin/user/listUser.html";
    }

    /**
     * 锁定或者解锁用户
     * @param userId
     * @param status
     * @param response
     */
    @RequestMapping("/admin/list/lockOrUnLockUser.action")
    public void lockOrUnLockUser(Long userId, Integer status, HttpServletResponse response){
        String msg = "保存成功";
        try {
            userFacade.lockOrUnLockUser(userId,status,getCurrentUserPartyId());
        } catch (Exception e) {
            e.printStackTrace();
            msg = "出现错误";
        } finally {
            this.response(response, msg);
        }
    }


    @RequestMapping("/admin/list/savePassword.action")
    public void savePassword(Long userId,String password1,String password2, HttpServletResponse response) throws Exception {
        String msg = "ok";
        try {
            userFacade.savePassword(getCurrentUser(),userId,password1,password2);
        } catch (Exception e) {
            msg = "保存出错，请重试或者联系管理员！";
            e.printStackTrace();
        }
        this.response(response, msg);
    }


    /**
     * 保存用户密码
     * @param userId
     * @param uiModel
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/admin/list/editUser.action")
    public String editUser(Long userId, Model uiModel, HttpServletResponse response) throws Exception {
        if (userId != null) {
            User user = userFacade.getUserById(userId);
            uiModel.addAttribute("user", user);
            Role role =  userFacade.getUserRoleByUserId(userId);
            uiModel.addAttribute("roleId",role.getId());
            uiModel.addAttribute("roleName",role.getName());
        }
        uiModel.addAttribute("roleList",userFacade.findRoleByPartyId(getCurrentUserPartyId())) ;
        return "/admin/user/editUser_ajax";
    }


    /**
     * 验证用户名是否重复
     *
     * @param loginName
     * @param userId
     * @return
     */
    @RequestMapping("/admin/list/isAvailableLoginName.action")
    @ResponseBody
    public boolean isAvailableLoginName(@RequestParam String loginName, @RequestParam(required = false) Long userId) {
        return userFacade.checkLoginNameRepeat(loginName, userId);
    }

    /**
     * 保存用户信息
     * @param userId
     * @param uiModel
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/admin/list/saveUser.action")
    public void saveUser(Long userId, String password1, String password2, String loginName, String name, String phone, String email, Long roleId, Model uiModel, HttpServletResponse response) throws Exception {
        String msg = "ok";
        UserView userView=new UserView();
        userView.setLoginName(loginName);
        try {
            if(password1!=null && !password1.equals(password2)){
                msg ="两次输入不一致";
            }
            else if (userFacade.checkLoginName(userView)==true&&userId==null){
                msg ="用户已存在";
            }
            else{
                userFacade.saveUser(getCurrentUser(),userId,password1,password2,loginName,name,phone,email,roleId);
            }
        } catch (Exception e) {
            msg = "保存出错，请重试或者联系管理员！";
            e.printStackTrace();
        }
        this.response(response, msg);
    }

    @RequestMapping("/admin/list/listFeedBack.action")
    public String listFeedBack(HttpServletRequest request, Model uiModel, String ajax) {
        Page<FeedBack> page = this.buildPage(request);
        page.setPageSize(10);
        Map<String, Object> filters = buildFilter(request, uiModel);

        Page<FeedBackView> userViewPage = feedBackFacade.findForFeedBackPage(page, filters);
        uiModel.addAttribute("page", userViewPage);
        this.pageInfoByMap(uiModel, userViewPage, filters);
        if (ajax != null) {
            return "/admin/user/listUser_ajax";
        }
        uiModel.addAttribute("statusMap",IUserType.userStatusMap);
        return "/admin/user/listUser.html";
    }

}
