package com.autozi.common.utils.util2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormatUtils {
	/**
	  * @Description: 手机号码格式验证
	  * @user jinpeng.wang
	  * @dateTime 2014-10-23
	 */
	public static boolean checkPhone(String phone){
		Pattern Phone_Pattern = Pattern.compile("^0?(1[345789])[0-9]{9}$");
       Matcher matcher = Phone_Pattern.matcher(phone);
       if (matcher.matches()){
           return true;
       }
       return false;
   }
	/**
	 * @Description: email格式验证
	 * @user jinpeng.wang
	 * @dateTime 2014-10-23
	 */
	public static boolean checkEmail(String email){
		Pattern Email_Pattern = Pattern.compile("^([a-zA-Z0-9]+[\\_|\\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[\\_|\\.]?)*[a-zA-Z0-9]+.[a-zA-Z]{2,3}$");
		Matcher matcher = Email_Pattern.matcher(email);
		if (matcher.matches()){
			return true;
		}
		return false;
	}
	
	/**
	 * @Description: 密码格式验证
	 * @author: zhiyun.chen
	 * @param loginName
	 * 2017-6-21下午03:04:53
	 */
	public static boolean checkPassword(String password){
		Pattern Password_Pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-zA-Z])(.{8,20})$");
		Matcher matcher = Password_Pattern.matcher(password);
		if (matcher.matches()) {
			return true;
		}
		return false;
   }
}
