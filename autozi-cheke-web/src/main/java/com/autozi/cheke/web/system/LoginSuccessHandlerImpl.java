package com.autozi.cheke.web.system;


import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * 登陆成功处理跳转
 * @author shixin.zhang
 *
 */
public class LoginSuccessHandlerImpl extends SimpleUrlAuthenticationSuccessHandler {


    public LoginSuccessHandlerImpl() {
        setAlwaysUseDefaultTargetUrl(true);
        setDefaultTargetUrl("/index.action");
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response
            , Authentication authentication) throws IOException, ServletException {
        String loginName = authentication.getName();
        try {
        	//----------------------暂时写死后期从数据库获取－－－－－－－－－－－－－－
//        	 User user =new User();
//        	 user.setLoginName(loginName);
//        	 Party party = new Party();
//        	 if(user.getLoginName().equals("o2o_liuchao")){
//        		 party.setPartyType(IPartyConstant.TYPE_CHAIN);
//        	 }else{
//        		 party.setPartyType(IPartyConstant.TYPE_PLANT);
//        	 }
        	 //----------end－－－－－－－－－－－－－－－－－－－－－－－－－－－
        	 
//        	 if(party.getPartyType()==IPartyConstant.TYPE_CHAIN){
//        		 setDefaultTargetUrl("/admin/index.action");
//        	 }else{
        		 setDefaultTargetUrl("/index.action");
//        	 }
               
        } catch (Exception ex) {
            logger.error("更新登录时间失败", ex);
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }
}