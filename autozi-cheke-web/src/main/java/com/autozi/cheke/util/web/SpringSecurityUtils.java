/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: SpringSecurityUtils.java 1062 2010-04-27 16:51:10Z calvinxiu $
 */
package com.autozi.cheke.util.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * SpringSecurity的工具类.
 * 
 * 注意. 本类只支持SpringSecurity 3.0.x.
 * 
 * @author calvin
 */
public class SpringSecurityUtils {

    private static ThreadLocal<String> currentUserName = new ThreadLocal<String>();

    public static void setCurrentUserName(String loginName) {
        currentUserName.set(loginName);
    }

	public static UserDetails getCurrentUserDetails() {
		Authentication authentication = getAuthentication();
		if (authentication != null && 
			authentication.getPrincipal() != null && 
			authentication.getPrincipal() instanceof UserDetails) {
			
			return (UserDetails) authentication.getPrincipal();
		} else {
			return null;
		}
	}

	/**
	 * 取得当前用户的登录名, 如果当前用户未登录则返回空字符串
     * @return 当前登录用户名.
	 */
	public static String getCurrentUserName() {
        String loginName = "";
		Authentication authentication = getAuthentication();
		if (authentication != null && authentication.getPrincipal() != null) {
			loginName = authentication.getName();
		}
        if (loginName.equals("anonymousUser")) {
            loginName = currentUserName.get();
        }
        if (loginName != null) {
            return loginName;
        } else {
            return "";
        }

	}

    /**
     * 获得当前登录用户拥有的系统内置角色
     * @return 系统内置角色列表
     */
    public static List<String> getCurrentUserRoles() {
        List<String> roles = new ArrayList<String>();
        Authentication authentication = getAuthentication();
        for (GrantedAuthority ga : authentication.getAuthorities()) {
            roles.add(ga.getAuthority());
        }
        return roles;
    }

	/**
	 * 将UserDetails保存到Security Context.
	 * 
	 * @param userDetails 已初始化好的用户信息.
	 * @param request 用于获取用户IP地址信息.
	 */
	public static void saveUserDetailsToContext(UserDetails userDetails, HttpServletRequest request) {
		PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(userDetails,
				userDetails.getPassword(), userDetails.getAuthorities());

		authentication.setDetails(new WebAuthenticationDetails(request));

		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	/**
	 * 取得Authentication, 如当前SecurityContext为空时返回null.
     * @return 当前登录用户的Authentication
	 */
	private static Authentication getAuthentication() {
		SecurityContext context = SecurityContextHolder.getContext();
		if (context != null) {
			return context.getAuthentication();
		}
		return null;
	}
}
