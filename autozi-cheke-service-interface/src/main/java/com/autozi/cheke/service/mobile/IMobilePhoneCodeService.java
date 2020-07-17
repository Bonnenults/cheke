package com.autozi.cheke.service.mobile;

import com.autozi.cheke.mobile.entity.MobilePhoneCode;

public interface IMobilePhoneCodeService {

	public MobilePhoneCode getByMobilePhone(String phone);

	public boolean checkMaxSendNumber(String mobilePhone);

	public String createMobilePhoneCode(String mobilePhone, String code, Integer type);

}
