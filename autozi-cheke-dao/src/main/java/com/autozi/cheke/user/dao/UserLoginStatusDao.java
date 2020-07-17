package com.autozi.cheke.user.dao;

import org.springframework.stereotype.Component;

import com.autozi.cheke.user.entity.UserLoginStatus;
import com.autozi.cheke.user.mapper.UserLoginStatusMapper;
import com.autozi.common.dal.mybatis.MyBatisDao;

@Component
public class UserLoginStatusDao extends MyBatisDao<UserLoginStatus> {

	public UserLoginStatus getUserLoginStatus(String token) {
		return getMapper(UserLoginStatusMapper.class).getByToken(token);
	}

}
