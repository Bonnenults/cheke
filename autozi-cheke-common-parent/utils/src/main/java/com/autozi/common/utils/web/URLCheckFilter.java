package com.autozi.common.utils.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.autozi.common.utils.util1.MD5;

public class URLCheckFilter implements Filter {
	public final static String key = "a904ab681e0b95bd775ba7ae29ce5bb4";//公钥（"qeegoo.com.cn"经过MD5加密后的数据）
	public final static long timeOut = 15*60*1000 ;//过期时间为15分钟（考虑到服务器时间差）
	
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		if (checkUrl(request)) {
			chain.doFilter(request, response);
		} else {
			response.setContentType("text/html;charset=UTF-8");
	        response.getWriter().write("false");
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	
	/**
	  * @Description: URL有效性验证
	  * @user zhiyun.chen
	  * @dateTime 2013-7-24下午04:13:36
	 */
	public boolean checkUrl(HttpServletRequest request){
		String servletPath = request.getServletPath();
		try {
			if (servletPath.contains("/openapi")) {
				long nowTime = System.currentTimeMillis();
				long validTime = Long.parseLong(request.getParameter("validTime"));
				if (validTime + timeOut < nowTime) {//过期
					return false;
				}
				String sign = request.getParameter("sign");
				String real_key = MD5.getMD5(validTime + "|" + key);
				if (StringUtils.isNotBlank(sign) && sign.equals(real_key)) {
					return true;
				}
				return false;
			} else {
				long url_qeegooTime = Long.parseLong(request.getParameter("qeegooTime"));
				long nowTime = System.currentTimeMillis();
				if (!servletPath.contains("log.action")) {//登录、退出接口不验证过期时间
					if (url_qeegooTime*1000 + timeOut < nowTime) {//过期
						return false;
					}
				}
				StringBuffer url = request.getRequestURL();
				String urlParameter = request.getQueryString();
				String fullURL = url.toString()+"?"+urlParameter;
				//qeegooKey的加密规则："&qeegooKey"之前的url+公钥
				String urlKey = fullURL.substring(0, fullURL.indexOf("&qeegooKey"));
				String url_qeegooKey = request.getParameter("qeegooKey");
				String real_qeegooKey = MD5.getMD5(urlKey + key);
				if (url_qeegooKey.equals(real_qeegooKey)) {
					return true;
				} else {
					return false;
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static void main(String[] args) {
		String urlfdf = "http://localhost:8080/api/industry/party?23123&123=123&qeegooTime=123&qeegooKey=123232131231232";
		String urlKey = urlfdf.substring(0, urlfdf.indexOf("&qeegooKey"));
		System.out.println(urlKey);;
		System.out.println(MD5.getMD5("http://localhost:8080/api/industry/user/loginname/chx_qxc?qeegooTime=1374810005838" + key));
		System.out.println(System.currentTimeMillis());
	}
}
