package com.autozi.common.utils.util1;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TokenProcessor {
	private long privious;// 上次生成表单标识号得时间值
	private static TokenProcessor instance = new TokenProcessor();
	public static String FORM_TOKEN_KEY = "FORM_TOKEN_KEY";
	private static final Logger APILIMITLOGGER = LoggerFactory.getLogger("apiLimit");

	private TokenProcessor() {

	}

	public static TokenProcessor getInstance() {
		return instance;
	}

	/**
	 * 验证请求中得标识号是否有效，如果请求中的表单标识与当前用户session中的相同，返回结果true=
	 */
	public synchronized boolean isTokenValid(HttpServletRequest request) {
		// 为避免session对象不存在时候创建Session对象
		HttpSession session = request.getSession(false);
		if (session == null) {
			return false;
		}
		String saved = (String) session.getAttribute(FORM_TOKEN_KEY);
		if (saved == null) {
			APILIMITLOGGER.info("验证token时，session中未获取到token");
			return false;
		}
		String token = (String) request.getParameter(FORM_TOKEN_KEY);
		if (token == null) {
			APILIMITLOGGER.info("验证token时，request中未获取到token");
			return false;
		}
		boolean result = saved.equals(token);
		if (!result) {
			APILIMITLOGGER.info("验证token时，token不一致，seesion中的token为:" + saved + "，request中的token为：" + token);//token异常时，加日志 zhiyun.chen 2017-06-28 （临时）
		}
		return result;
	}
	
	/**
	 * 验证请求中得标识号是否有效，如果请求中的表单标识与当前用户session中的相同，返回结果true=
	 */
	public synchronized boolean isTokenValid(HttpServletRequest request,String token) {
		// 未避免session对象不存在时候创建Session对象
		HttpSession session = request.getSession(false);
		if (session == null) {
			return false;
		}
		String saved = (String) session.getAttribute(FORM_TOKEN_KEY);
		if (saved == null) {
			return false;
		}
		if (token == null) {
			return false;
		}
		return saved.equals(token);
	}
	
	

	/**
	 * 清除存储在当前用户session中的表单标识号
	 */
	public synchronized void reset(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return;
		}
		session.removeAttribute(FORM_TOKEN_KEY);
	}

	/**
	 * 产生表单标识号并将之保存在当前用户得session中
	 */

	public synchronized void saveToken(HttpServletRequest request) {
		HttpSession session = request.getSession();
		try {
			byte id[] = session.getId().getBytes();
			long current = System.currentTimeMillis();
			if (current == privious) {
				current++;
			}
			privious = current;
			byte now[] = String.valueOf(current).getBytes();
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(id);
			md.update(now);
			String token = toHex(md.digest());
			session.setAttribute(FORM_TOKEN_KEY, token);
		} catch (NoSuchAlgorithmException e) {

		}
	}

	/**
	 * 将一个字节数转换成十六进制得字符串
	 */
	public String toHex(byte buffer[]) {
		StringBuffer sb = new StringBuffer(buffer.length * 2);
		for (int i = 0; i < buffer.length; i++) {
			sb.append(Character.forDigit((buffer[i] & 0x60) >> 4, 16));
			sb.append(Character.forDigit(buffer[i] & 0x0f, 16));
		}
		return sb.toString();

	}
}
