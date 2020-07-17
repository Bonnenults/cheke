package com.autozi.cheke.util.web;


import com.autozi.common.utils.util1.MD5;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.web.WebAttributes;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 集成JCaptcha验证码的Filter.
 * 
 * @author Ropod
 */
public class CaptchaFilter implements Filter {

	public static final String PARAM_FAILURE_URL = "failureUrl";

	private String failureUrl;

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		if (validateCaptchaChallenge(request)) {
			chain.doFilter(request, response);
		} else {
			redirectFailureUrl(request, response);
		}
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		failureUrl = config.getInitParameter(PARAM_FAILURE_URL);
	}

	@Override
	public void destroy() {
	}
	
	/**
	 * 验证验证码.
	 */
	protected boolean validateCaptchaChallenge(final HttpServletRequest request) {
		HttpSession session = request.getSession();
		String captcha = request.getParameter("captcha");
        Object sessionCaptcha = session.getAttribute(CaptchaServlet.CAPTCHA_SESSION);
        if (sessionCaptcha == null) {
            return true;
        } else if (sessionCaptcha != null && captcha == null) {
            return false;
        } else {
            return MD5.getMD5(captcha.toUpperCase()).equals(session.getAttribute(CaptchaServlet.CAPTCHA_SESSION));
        }
	}

	
	/**
	 * 跳转到失败页面.
	 * 
	 * 可在子类进行扩展, 比如在session中放入SpringSecurity的Exception.
	 */
	protected void redirectFailureUrl( HttpServletRequest request,  HttpServletResponse response)
			throws IOException, ServletException {
		HttpSession session = request.getSession();
		session.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, new AuthenticationServiceException("您输入的验证码不正确！"));
		// UPDATE BY LIHF@Autozi.com 2017-8-3下午2:39:00 START 需求描述：升级到4.X，常亮定义没有了
//		session.setAttribute(WebAttributes.LAST_USERNAME,request.getParameter("j_username"));
		session.setAttribute("SPRING_SECURITY_LAST_USERNAME",request.getParameter("username"));
		// UPDATE BY LIHF@Autozi.com 2017-8-3下午2:39:00 END
		response.sendRedirect(request.getContextPath()+failureUrl);
	}
}
