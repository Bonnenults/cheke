package com.autozi.common.utils.util2;

import com.autozi.common.utils.util2.PropertyUtil;



public class MobileConfigUtils {
	public static final int _user_expire_time = PropertyUtil.getIntValue("mobileConfig.properties", "user.expire.time", 30);
	public static final String _time_key = PropertyUtil.getStrValue("mobileConfig.properties", "time.key", "");
	public static final String _token_key = PropertyUtil.getStrValue("mobileConfig.properties", "token.key", "");
	public static final String _www_mobile = PropertyUtil.getStrValue("mobileConfig.properties", "www.mobile", "");
	public static final int _mobilePhoneCode_expire_time = PropertyUtil.getIntValue("mobileConfig.properties", "mobilePhoneCode.expire.time", 10);
	public static final int _mobilePhoneCode_send_number = PropertyUtil.getIntValue("mobileConfig.properties", "mobilePhoneCode.send.number", 10);
	public static final int _default_distance = PropertyUtil.getIntValue("mobileConfig.properties", "default.distance", 50);
}
