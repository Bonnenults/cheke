package com.autozi.cheke.mobile.filter;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.autozi.cheke.mobile.constant.MobileConstant;
import com.autozi.cheke.mobile.util.CheckKeyUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * 手机客户端请求过虑
 * @user zhiyun.chen
 *
 */
public class MobileApiFilter implements Filter{
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)arg0;
		HttpServletResponse response = (HttpServletResponse)arg1;
		String servletPath = request.getServletPath();
		if (!(servletPath.contains("editmyinfo.api")||servletPath.contains("uploadHeadImage.api")||servletPath.contains("saveIDCard.api"))) {//修改个人信息，不在此验证
			String time = request.getParameter("time");
			String timeCheckValue = request.getParameter("timeCheckValue");
			//检查time与timeCheckValue
			if(StringUtils.isBlank(time)||StringUtils.isBlank(timeCheckValue)||!CheckKeyUtils.validateTimeKey(time, timeCheckValue)){
				this.response(response, MobileConstant.Error._error_access, MobileConstant.Error._error_access_msg);
				return;
			}
		}
		filterChain.doFilter(arg0, arg1);
		
	}

	/**
	 * 应答信息的返回
	 * 
	 * @param response
	 * @throws IOException
	 */
	private void response(HttpServletResponse response, String code, String msg) throws IOException{
		JSONObject json = new JSONObject();
		JSONObject json1 = new JSONObject();
		json1.put("code", code);
		json1.put("msg", msg);
		json.put("status", json1);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.write(json.toString());
		out.flush();
		out.close();
	}
	
}
