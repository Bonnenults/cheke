package com.autozi.cheke.web.user.controller;

import com.autozi.cheke.util.mvc.BaseController;
import com.autozi.cheke.util.web.CaptchaServlet;
import com.autozi.cheke.web.party.facade.PartyFacade;
import com.autozi.cheke.web.user.facade.UserFacade;
import com.autozi.cheke.web.user.vo.UserView;
import com.autozi.common.utils.util1.MD5;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lbm on 2017/11/28.
 */
@RequestMapping("/cheke/register/")
@Controller
public class RegisterController extends BaseController {
    @Autowired
    private UserFacade userFacade;
    @Autowired
    private PartyFacade partyFacade;

    @RequestMapping("/toRegister.action")
    public String toRegister() throws Exception {
        return "/index/register";
    }

    @RequestMapping("/register.action")
    public void register(UserView userView, HttpServletRequest request, HttpServletResponse response) throws Exception {
        JSONObject jsonObject = new JSONObject();
        String msg="ok";
        //验证码
        String validateCode = MD5.getMD5(userView.getValidateCode().toString().toUpperCase());
        String sessionCaptcha = (String)request.getSession().getAttribute(CaptchaServlet.CAPTCHA_SESSION);
        if(!sessionCaptcha.equals(validateCode)){
            msg = "验证码不正确";
            jsonObject.put("msg",msg);
            this.response(response,jsonObject.toString());
            return;
        }
        boolean isExistUser = userFacade.checkLoginName(userView);
        if(isExistUser){
            msg = "用户名已经存在";
            jsonObject.put("msg",msg);
            this.response(response,jsonObject.toString());
            return;
        }
        boolean isExistParty = partyFacade.checkChekeName(userView.getPartyName());
        if(isExistParty){
            msg = "车客名称已经存在";
            jsonObject.put("msg",msg);
            this.response(response,jsonObject.toString());
            return;
        }
        userFacade.register(userView);
        jsonObject.put("msg",msg);
        this.response(response,jsonObject.toString());
    }

    @RequestMapping("/register/checkLoginName.action")
    public void checkLoginName(UserView userView, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String msg="ok";
        boolean exist = userFacade.checkLoginName(userView);
        if(exist){
            msg = "用户名重复";
            this.response(response,msg);
            return;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg",msg);
        this.response(response,jsonObject.toString());
    }

    @RequestMapping("/register/checkChekeName.action")
    public void checkChekeName(UserView userView, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String msg="ok";
        boolean exist = partyFacade.checkChekeName(userView.getName());
        if(exist){
            msg = "车客名重复";
            this.response(response,msg);
            return;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg",msg);
        this.response(response,jsonObject.toString());
    }
}
