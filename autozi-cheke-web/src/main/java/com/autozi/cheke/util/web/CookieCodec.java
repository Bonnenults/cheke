package com.autozi.cheke.util.web;

import com.autozi.common.utils.util2.ApplicationProperties;
import com.autozi.common.utils.util2.Base64;
import org.springframework.security.crypto.codec.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class CookieCodec {
	public static final int MAX_AGE = 60*60*24*30;// 设置该COOKIE的有效期,单位为秒   
	private static final String DELIMITER = ":";
    public static final int TWO_WEEKS_S = 1209600;


	protected static String makeTokenSignature(long tokenExpiryTime, String username,
			String password) {
		String data = username + ":" + tokenExpiryTime + ":" + password+ ":" + getKey();
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("No MD5 algorithm available!");
		}

		return new String(Hex.encode(digest.digest(data.getBytes())));
	}

	private static String getKey() {
		return ApplicationProperties.getValue("b2cDomainKey");//获取b2c单点登录的key
	}

	protected static String encodeCookie(String[] cookieTokens) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < cookieTokens.length; i++) {
			sb.append(cookieTokens[i]);

			if (i < cookieTokens.length - 1) {
				sb.append(DELIMITER);
			}
		}

		String value = sb.toString();

		sb = new StringBuilder(new String(Base64.encode(value.getBytes())));

		while (sb.charAt(sb.length() - 1) == '=') {
			sb.deleteCharAt(sb.length() - 1);
		}

		return sb.toString();
	}

	public static String springSecurityKey(String username, String password,long tokenExpiryTime) {
		long expiryTime = System.currentTimeMillis();
		tokenExpiryTime+=expiryTime;
		String signatureValue = makeTokenSignature(tokenExpiryTime, username, password);
		String cookieValue=encodeCookie(new String[] {username, Long.toString(tokenExpiryTime), signatureValue});
		return cookieValue;
	}
	
	/**
	  * @Description: 获取全局域名（正式环境为：autozi.com）
	  * @user zhiyun.chen
	  * @dateTime 2013-3-29上午09:56:22
	 */
	public static String getGlobalDomain(){
		return ApplicationProperties.getValue("globalDomain");
	}
}
