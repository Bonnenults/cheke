package com.autozi.cheke.web.user.controller;

import com.autozi.cheke.basic.entity.Area;
import com.autozi.cheke.party.entity.PartyLog;
import com.autozi.cheke.party.type.IPartyType;
import com.autozi.cheke.user.entity.User;
import com.autozi.cheke.user.type.IUserType;
import com.autozi.cheke.util.mvc.BaseController;
import com.autozi.cheke.web.party.vo.PartyView;
import com.autozi.cheke.web.user.facade.UserFacade;
import com.autozi.cheke.web.user.vo.UserView;
import com.autozi.common.core.page.Page;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lbm on 2017/11/28.
 */
@RequestMapping("/tuike/user")
@Controller
public class TuikeUserController extends BaseController {
    @Autowired
    private UserFacade userFacade;

    @RequestMapping("/list/listTuikeUser.action")
    public String listTuikeUser(HttpServletRequest request, Model uiModel, String ajax) throws UnsupportedEncodingException {
        Page<User> page = buildPage(request);
        HashMap<String, Object> filters = buildFilter(request, uiModel);
        filters.put("userType", IUserType.USER_TYPE_TUIKE);

        Page<UserView> viewPage = userFacade.listTuikeUser(page,filters);
        uiModel.addAttribute("page", viewPage);
        this.pageInfoByMap(uiModel, viewPage, filters);
        if (ajax != null) {
            return "/admin/user/listTuikeUser_ajax";
        }
        return "/admin/user/listTuikeUser.html";
    }

    @RequestMapping("/list/listSubTuikeUser.action")
    public String listSubTuikeUser(HttpServletRequest request, Model uiModel, String ajax) throws UnsupportedEncodingException {
        Page<User> page = buildPage(request);
        HashMap<String, Object> filters = buildFilter(request, uiModel);
        filters.put("userType", IUserType.USER_TYPE_TUIKE);

        Page<UserView> viewPage = userFacade.listTuikeUser(page,filters);
        uiModel.addAttribute("page", viewPage);
        this.pageInfoByMap(uiModel, viewPage, filters);
        if (ajax != null) {
            return "/admin/user/listSubTuikeUser_ajax";
        }
        return "/admin/user/listSubTuikeUser.html";
    }

    @RequestMapping("/list/audit.action")
    public String audit(Model uiModel,Long id) throws UnsupportedEncodingException {
        UserView userView = userFacade.getTuikeUser(id);
        uiModel.addAttribute("user", userView);
        return "/admin/user/verifyTuikeUser.html";
    }

    /**
     * 新增审核通过
     * @param id
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/list/passVerify.action")
    public void passVerify(Long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String msg="ok";
        try{
            userFacade.passVerify(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg",msg);
        this.response(response,jsonObject.toString());
    }

    /**
     *
     * @param view
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/list/refuseVerify.action")
    public void refuseVerify(UserView view, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String msg="ok";
        try{
            userFacade.refuseVerify(view.getId(),view.getRefuseReason());
        }catch (Exception e){
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg",msg);
        this.response(response,jsonObject.toString());
    }

    /**
     * 锁定或者解锁用户
     * @param userId
     * @param status
     * @param response
     */
    @RequestMapping("/list/lockOrUnLockTuikeUser.action")
    public void lockOrUnLockTuikeUser(Long userId, Integer status, HttpServletResponse response){

        String msg = "锁定成功";
        if(status!=null&&status==1){
            msg = "解锁成功";
        }
        try {
            userFacade.lockOrUnLockTuikeUser(userId,status);
        } catch (Exception e) {
            e.printStackTrace();
            msg = "出现错误";
        } finally {
            this.response(response, msg);
        }
    }

}
