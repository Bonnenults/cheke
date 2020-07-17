package com.autozi.cheke.mobile.mapper;

import java.util.Map;

import com.autozi.cheke.mobile.entity.MobilePhoneCode;
import com.autozi.common.dal.mapper.BaseMapper;

public interface MobilePhoneCodeMapper extends BaseMapper<MobilePhoneCode> {

	public MobilePhoneCode getByMobilePhone(Map<String, Object> filters);
	
}
