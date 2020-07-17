package com.autozi.common.utils.util1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 随机生成手机验证码
 * @user zhiyun.chen
 *
 */
public class MobilePhoneUtils {
	public static final boolean NUMBER_FLAG_TRUE = true;
	public static final boolean NUMBER_FLAG_FALSE = false;
	
	/**
	  * @Description: 随机生成手机验证码
	  * @param numberFlag true:纯数字，false:数据+英文字母
	  * @user zhiyun.chen
	  * @dateTime 2014-3-20上午11:13:59
	 */
	public static String createRandom(boolean numberFlag, int length) {
		String retStr = "";
		String strTable = numberFlag ? "1234567890": "1234567890abcdefghijkmnpqrstuvwxyz";
		int len = strTable.length();
		boolean bDone = true;
		do {
			retStr = "";
			int count = 0;
			for (int i = 0; i < length; i++) {
				double dblR = Math.random() * len;
				int intR = (int) Math.floor(dblR);
				char c = strTable.charAt(intR);
				if (('0' <= c) && (c <= '9')) {
					count++;
				}
				retStr += strTable.charAt(intR);
			}
			if (count >= 2) {
				bDone = false;
			}
		} while (bDone);
		return retStr;
	}
	
	/**
	  * @Description: 手机号码格式验证
	  * @user zhiyun.chen
	  * @dateTime 2014-3-20下午04:42:05
	 */
	public static boolean checkPhone(String phone){
        Pattern pattern = Pattern.compile("^0?(1[34578])[0-9]{9}$");
        Matcher matcher = pattern.matcher(phone);
        if (matcher.matches()){
            return true;
        }
        return false;
    }

}
