package com.autozi.cheke.mobile.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * 验证标识
 * @user zhiyun.chen
 *
 */
public class CheckKeyUtils {
	
	public static char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
	
	/**
	 * 验证key
	 */
	public static boolean validateTokenKey(String sourceStr,String sourceKey){
		String certKey = MobileConfigUtils._token_key;
		String _newKey = createKey(sourceStr.getBytes(), certKey.getBytes());
		return sourceKey.equals(_newKey)?true:false;
	}
	
	/**
	  * @Description:验证时间戳
	  * @user zhiyun.chen
	  * @dateTime 2014-3-24下午05:32:36
	 */
	public static boolean validateTimeKey(String sourceStr,String sourceKey){
		String certKey = MobileConfigUtils._time_key;
		String _newKey = createKey(sourceStr.getBytes(), certKey.getBytes());
		return sourceKey.equals(_newKey)?true:false;
	}
	
	/**
	 * 
	 * 中文描述：生成Token
	 */
	public synchronized static String createToken(String clientIp,long userId) {
		byte id[] = String.valueOf(userId).getBytes();
//		byte ip[] = "qeegoo".getBytes();
		byte ip[] = clientIp.getBytes();
		return createKey(id,ip);
	}
	
	/**
	 * 
	 * 中文描述：生成Token
	 * 	wei.wei
	 */
	public synchronized static String createToken(String phone) {
		byte id[] = phone.getBytes();
//		byte ip[] = "qeegoo".getBytes();
//		byte ip[] = clientIp.getBytes();
		byte ip[] = MobileConfigUtils._time_key.getBytes();
		return createKey(id,ip);
	}
	
	/**
	 * 生成Key
	 */
	public static String createKey(byte str1[],byte str2[]){
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(str1);
			md.update(str2);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return toHex(md.digest());
	}

	private static String toHex(byte buffer[]) {
		StringBuffer sb = new StringBuffer(buffer.length * 2);
		for (int i = 0; i < buffer.length; i++) {
			sb.append(hexDigits[buffer[i]>>>4 & 0xf]);
			sb.append(hexDigits[buffer[i]& 0xf]);
		}
		return sb.toString();

	}

}
