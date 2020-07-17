package com.autozi.cheke.mobile.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import net.sf.json.JSONObject;

import com.autozi.cheke.mobile.constant.MobileConstant;
import com.autozi.cheke.mobile.util.CheckKeyUtils;
import com.autozi.cheke.mobile.util.MobileConfigUtils;
import com.autozi.cheke.party.entity.Party;
import com.autozi.cheke.service.user.IUserLoginStatusService;
import com.autozi.cheke.service.user.IUserService;
import com.autozi.cheke.user.entity.User;
import com.autozi.cheke.user.entity.UserLoginStatus;
import com.autozi.cheke.user.type.IUserType;
import com.autozi.common.utils.util2.DateTools;

public class BaseApiController {

	@Autowired
	private IUserLoginStatusService userLoginStatusService;
	@Autowired
	private IUserService userService;
	
	protected User getUser(HttpServletRequest request){
		UserLoginStatus userLoginStatus = userLoginStatusService.getUserLoginStatus(request.getParameter("token"));
		if(userLoginStatus==null){
			return null;
		}
		return userService.getUserById(userLoginStatus.getId());
	}

	protected User getUser(Long userId) {
		User user = null;
		try {
			user = userService.getUserById(userId);
		} catch (Exception e) {
		}
		return user;
	}
	
	protected Party getParty(HttpServletRequest request){
		
		return null;
	}
	
	
	/**
	 * 应答信息的返回
	 * 
	 * @param response
	 * @param msg
	 * @throws IOException
	 */
	protected void response(HttpServletResponse response, String code, String msg,String data){
		JSONObject json = new JSONObject();
		JSONObject json1 = new JSONObject();
		json1.put("code", code);
		json1.put("msg", msg);
		json.put("status", json1);
		json.put("data", data);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.write(json.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 应答信息的返回(可以跨域)
	 * 
	 * @param request
	 * @param response
	 * @param msg
	 * @throws IOException
	 */
	protected void response(HttpServletRequest request,HttpServletResponse response, String code, String msg,String data){
		JSONObject json = new JSONObject();
		JSONObject json1 = new JSONObject();
		json1.put("code", code);
		json1.put("msg", msg);
		json.put("status", json1);
		json.put("data", data);
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
		PrintWriter out;
		try {
			out = response.getWriter();
			out.write(json.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 应答信息的返回
	 * 
	 * @param response
	 * @param msg
	 * @throws IOException
	 */
	protected void response(HttpServletResponse response, String code, String msg){
		JSONObject json = new JSONObject();
		JSONObject json1 = new JSONObject();
		json1.put("code", code);
		json1.put("msg", msg);
		json.put("status", json1);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.write(json.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 此方法应对H5开发 跨域请求 在response中添加header
	 * 应答信息的返回
	 * @param  request
	 * @param response
	 * @param msg
	 * @throws IOException
	 */
	protected void response(HttpServletRequest request,HttpServletResponse response, String code, String msg){
		JSONObject json = new JSONObject();
		JSONObject json1 = new JSONObject();
		json1.put("code", code);
		json1.put("msg", msg);
		json.put("status", json1);
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
		PrintWriter out;
		try {
			out = response.getWriter();
			out.write(json.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	  * @Description: 验证token
	  * @user zhiyun.chen
	  * @dateTime 2014-3-24下午05:17:07
	 */
	protected void checkToken(HttpServletRequest request,HttpServletResponse response){
		String token = request.getParameter("token");
//		String tokenCheckValue = request.getParameter("tokenCheckValue");
		if(StringUtils.isBlank(token)){
			this.response(response, MobileConstant.Error._error_access, MobileConstant.Error._error_access_msg);
			return;
		}
		UserLoginStatus userLoginStatus = userLoginStatusService.getUserLoginStatus(token);
		if(userLoginStatus==null){
			this.response(response, MobileConstant.Error._errorToken, MobileConstant.Error._errorToken_msg);
			return;
		}
		User user = userService.getUserById(userLoginStatus.getId());

		// 用户状态有效性检查
        if (IUserType.STATUS_NORMAL != user.getUserStatus()) {
            this.response(request,response, MobileConstant.Error._userNuEnabled, MobileConstant.Error._userNuEnabled_msg);
            return;
        }
		// 检查用户session是否有效-一个月之内免登录
		long lastTime = userLoginStatus.getLastLoginTime().getTime();
		long curTime = DateTools.getCurrentDate().getTime();
		int expireTime = MobileConfigUtils._user_expire_time;
		long userExpireTime = expireTime*24*60*60*1000l;
		if((lastTime+userExpireTime)<curTime){// 过期
			this.response(response, MobileConstant.Error._tokenIsExpire, MobileConstant.Error._tokenIsExpire_msg);
			return;
		}
		// 更新登录时间【一天一次】
		String cur_yyyymmdd = DateTools.formatDateForMore(DateTools.getCurrentDate(), "yyyyMMdd");
		String last_yyyymmdd = DateTools.formatDateForMore(userLoginStatus.getLastLoginTime(), "yyyyMMdd");
		if(!last_yyyymmdd.equals(cur_yyyymmdd)){
			userLoginStatus.setLastLoginTime(DateTools.getCurrentDate());
			userLoginStatusService.updateUserLoginStatusForDate(userLoginStatus);
		}
	}
	
}
