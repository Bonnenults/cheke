package com.autozi.common.utils.des;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;


/**
 * account token utils
 * 
 * @author zhaicl
 * @date 2017年2月4日 下午2:05:12
 */
public class AccountCookieB2BUtils {

	public final static String TOKEN_NAME = "_tokenb2b";

	private static String PATH = "/";

	private static int MAXAGE = 0;

	private static String DOMAIN = ".autozi.com";
	
	private final static String keyDes="(hT#p://wwW.Auto%_%zi.CoM)";//加解密密钥
	
	/**
	 * 往cookie添加token
	 * @param response
	 * @param token
	 * @param domain
	 * @author zhaicl
	 * @date 2017年2月4日 下午2:22:20
	 */
	public static void addToken(HttpServletResponse response,String token,String domain) {
		Cookie cookie = new Cookie(TOKEN_NAME, token);
		cookie.setPath(PATH);
		if (MAXAGE > 0) {
			cookie.setMaxAge(MAXAGE);
		}
		if(StringUtils.isNotBlank(domain)){
			cookie.setDomain(domain);			
		}
		response.addCookie(cookie);
	}
	
	/**
	 * 获得token值
	 * @param request
	 * @param token
	 * @return
	 * @author zhaicl
	 * @date 2017年2月4日 下午2:22:39
	 */
	public static String getToken(HttpServletRequest request) {
		String token="";
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				if (TOKEN_NAME.equalsIgnoreCase(cookie.getName())) {
					token=cookie.getValue();
					break;
				}
			}
		}
		if(StringUtils.isBlank(token)){
			token=request.getParameter(TOKEN_NAME);
		}
		return token;
	}
	
	/**
	 * 删除token
	 * @param response
	 * @author zhaicl
	 * @date 2017年2月4日 下午2:22:52
	 */
	public static void deleteToken(HttpServletResponse response,String domain) {
		Cookie cookie = new Cookie(TOKEN_NAME, null);
		cookie.setMaxAge(0);
		if(StringUtils.isNotBlank(domain)){
			cookie.setDomain(domain);			
		}
		cookie.setPath(PATH);
		response.addCookie(cookie);
	}
	
	/**
	 * 是否存在token
	 * @param request
	 * @return
	 * @author zhaicl
	 * @date 2017年2月4日 下午2:41:12
	 */
	public static boolean existToken(HttpServletRequest request){
		String token=getToken(request);
		return StringUtils.isNotBlank(token);
	}
	
	/**
	 * 根据token获得用户登录名
	 * @param request
	 * @return
	 * @author zhaicl
	 * @date 2017年2月4日 下午2:41:26
	 */
	public static String getLoginName(HttpServletRequest request){
		String token=getToken(request);
		return getLoginName(token);
	}
	
	/**
	 * 根据token获得用户登录名
	 * @param request
	 * @return
	 * @author zhaicl
	 * @date 2017年2月4日 下午2:41:26
	 */
	public static String getLoginName(String token){
		if(token==null){
			return null;
		}
		try {
			DESUtil desUtil=new DESUtil(keyDes);
			String data=desUtil.decryptMode(token);
			if(data.contains("||")){
				return data.split("\\|\\|")[0];
			}
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 根据用户信息生成token
	 * @param userBean
	 * @return
	 * @author zhaicl
	 * @date 2017年2月4日 下午2:41:37
	 */
	public static String generateToken(String loginName){
		String data=loginName+"||"+new Date().getTime();
		DESUtil desUtil=new DESUtil(keyDes);		
		String token="";
		try {
			token = desUtil.encryptMode(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return token;
	}
	
	public static void main(String[] args) {
		String data="zhaicl||123123";
		if(data.contains("||")){
			System.out.println(data.split("\\|\\|")[0]);
		}
	}
}
