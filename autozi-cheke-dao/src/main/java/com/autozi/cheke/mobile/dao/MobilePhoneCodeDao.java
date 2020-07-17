package com.autozi.cheke.mobile.dao;

import java.util.HashMap;
import java.util.Map;

import com.autozi.cheke.mobile.entity.MobilePhoneCode;
import com.autozi.cheke.mobile.mapper.MobilePhoneCodeMapper;
import com.autozi.common.dal.mybatis.MyBatisDao;
import org.springframework.stereotype.Component;

@Component
public class MobilePhoneCodeDao extends MyBatisDao<MobilePhoneCode> {

	
	public MobilePhoneCode getByMobilePhone(String mobilePhone){
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("mobilePhone", mobilePhone);
		return this.getMapper(MobilePhoneCodeMapper.class).getByMobilePhone(filter);
	}
	
}
