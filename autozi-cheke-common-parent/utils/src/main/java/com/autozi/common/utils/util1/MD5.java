package com.autozi.common.utils.util1;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	public static String getMD5(String arg) {
		byte[] source = arg.getBytes();
		String str = null;
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(source);
			byte tmp[] = md.digest(); 
			char chars[] = new char[16 * 2]; 
			int k = 0;
			for (int i = 0; i < 16; i++) { 
				
				byte byte0 = tmp[i];
				chars[k++] = hexDigits[byte0 >>> 4 & 0xf]; 
				chars[k++] = hexDigits[byte0 & 0xf];
			}
			str = new String(chars);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return str;
	}
}
