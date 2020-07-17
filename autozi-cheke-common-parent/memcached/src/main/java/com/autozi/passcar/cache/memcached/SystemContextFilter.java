package com.autozi.passcar.cache.memcached;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class SystemContextFilter implements Filter {

	@Override
	public void destroy() {}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		 ContextUtil.setRequest((HttpServletRequest)request);
		 ContextUtil.setResponse((HttpServletResponse)response);
	 	 chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {}

}
