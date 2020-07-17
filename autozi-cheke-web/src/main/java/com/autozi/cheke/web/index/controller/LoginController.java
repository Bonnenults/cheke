/**
 * 
 */
package com.autozi.cheke.web.index.controller;

import com.autozi.cheke.service.user.IUserService;
import com.autozi.cheke.util.mvc.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author shixin.zhang
 *
 */
@Controller
public class LoginController extends BaseController {
	@Autowired
	private IUserService userService;
	/**
	 * 用户登陆
	 * @param request
	 * @param session
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/login.action")
	public String login(HttpServletRequest request, HttpSession session, ModelMap modelMap) {
		Object lastException = session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		if (session.getAttribute("CAPTCHA_NUMBER") == null) {
			session.setAttribute("CAPTCHA_NUMBER", 0);
		}
		if (lastException != null) {
			if (lastException instanceof BadCredentialsException) {
				modelMap.put("errorMessage", "用户名或密码不正确！");
			} else if (lastException instanceof AuthenticationServiceException) {
				AuthenticationServiceException ex = (AuthenticationServiceException) lastException;
				modelMap.put("errorMessage", ex.getMessage());
			}
			if (session.getAttribute("CAPTCHA_NUMBER") != null) {
				int number = Integer.valueOf(session.getAttribute("CAPTCHA_NUMBER").toString());
				number++;
				session.setAttribute("CAPTCHA_NUMBER", number);
			}
		}
		return "/index/login";
	}


}
