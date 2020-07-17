package com.autozi.cheke.user.mapper;

import com.autozi.cheke.user.entity.UserLoginStatus;
import com.autozi.common.dal.mapper.BaseMapper;

public interface UserLoginStatusMapper extends BaseMapper<UserLoginStatus> {
	
	public UserLoginStatus getByToken(String token);

}
