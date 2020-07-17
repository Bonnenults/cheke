package com.autozi.cheke.web.system;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 退时，清除与b2c相关的信息（session、cookie）
 * 
 * @author shixin.zhang
 *
 */
public class LogoutHandlerImpl implements LogoutHandler {
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		try {
			request.getSession().setAttribute("LOGOUT_FLAG", true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
