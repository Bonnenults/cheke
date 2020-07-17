package com.autozi.passcar.cache.memcached;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ContextUtil {
	public static final String CURRENT_USERNAME="C_U";
	public static final String CURRENT_USERNAME_DOMAIN = PropertyUtils.getDomainValue("com.autozi.user.center.domain");
	
	public static HttpServletRequest getRequest() {
		return (HttpServletRequest)ThreadLocalUtil.get("threadHttpRequest");
	}
	public static void setRequest(HttpServletRequest request) {
		ThreadLocalUtil.set("threadHttpRequest", request);
	}
	public static void setResponse(HttpServletResponse response) {
		ThreadLocalUtil.set("threadHttpResponse", response);
	}
	public static HttpServletResponse getResponse() {
		return (HttpServletResponse)ThreadLocalUtil.get("threadHttpResponse");
	}
	public static void setUserNameToCookie(String username) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(username.getBytes());
			md.update(getRemoteIpAddr(getRequest()).getBytes());
			md.update(String.valueOf(System.currentTimeMillis()).getBytes());
			String token = toHex(md.digest());
			Cookie cookie = new Cookie(CURRENT_USERNAME,token);
			// ADD BY LIHF@QEEGOO 2016-3-15上午11:12:27 START BUG描述：作用域来自配置文件
			cookie.setPath("/");
			cookie.setDomain(CURRENT_USERNAME_DOMAIN);
			cookie.setMaxAge(-1);
			// ADD BY LIHF@QEEGOO 2016-3-15上午11:12:27 END   BUG描述：
			getResponse().addCookie(cookie);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
	}
	public static String getUserNameFromCookie() {
		Cookie[] cookies = getRequest().getCookies();
		if(cookies!=null) {
			for(Cookie cookie : cookies) {
				if(cookie.getName().equals(CURRENT_USERNAME)) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}
	/**
	 * 将一个字节数转换成十六进制得字符串
	 */
	public static String toHex(byte buffer[]) {
		StringBuffer sb = new StringBuffer(buffer.length * 2);
		for (int i = 0; i < buffer.length; i++) {
			sb.append(Character.forDigit((buffer[i] & 0x60) >> 4, 16));
			sb.append(Character.forDigit(buffer[i] & 0x0f, 16));
		}
		return sb.toString();

	}
	/**
	 * 获取客户端IP
	 */
	private static String getRemoteIpAddr(HttpServletRequest request) {
		
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		String remoteHost = request.getRemoteHost();
		return ip+remoteHost;
	}
}
