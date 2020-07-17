package com.autozi.common.web.session.manager;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieCheck {

	/**
	 * 设置SSO认证标识(把用户名,密码 写入客户端浏览器的cookie),关闭浏览器后，cookie立即失效
	 * 
	 * @param response
	 *            HttpServletResponse
	 * @param userName
	 *            用户名
	 * @param password
	 *            密码
	 */
	public static void setReffer(final HttpServletResponse response, final String userName, final String password){
		final String sSession = password + userName;  //密码是SHA1加密,长度为40个字符(160位)
		Cookie oItem;
		// 因为Cookie 中不允许保存特殊字符, 所以采用 BASE64 编码，CookieUtil.encode()是BASE64编码方法,略..
		oItem = new Cookie("SSO", CookieUtil.encode(sSession));
		oItem.setDomain("b2bex.com");  //请用自己的域
		oItem.setMaxAge(-1); //关闭浏览器后，cookie立即失效		
		oItem.setPath("/");
		response.addCookie(oItem);
	}
	
	/**
	 * 认证SSO标识（从客户端浏览器读入cookie, 并取得用户名、密码，不能取得时返回null）
	 * 
	 * @param request
	 * @return 返回从cookie中取得的用户名、密码，不能取得时返回null.String[0]中保存用户名,String[1]中保存密码
	 */
	public static String[] checkReffer(final HttpServletRequest request){
		final Cookie[] oCookies = request.getCookies();
		if (oCookies != null)
		{
			for (final Cookie oItem : oCookies)
			{
				final String sName = oItem.getName();

				if (sName.equals("SSO"))
				{
					final String sSession = CookieUtil.decode(oItem.getValue()); //解码 CookieUtil.decode()是BASE64解码方法,略..
					if (sSession.length() > 40)
					{
						// 获得存储在 Cookies 中的用户名称和密码
						final String sUser = sSession.substring(40);
						final String sPass = sSession.substring(0, 40);
						final String[] strArr =
						{ sUser, sPass };
						return strArr; //返回用户名,密码
					}
				}
			}
		}
		return null;
	}

}
