package com.autozi.cheke.service.user;

import org.springframework.stereotype.Component;

import com.autozi.cheke.user.entity.UserLoginStatus;

@Component
public interface IUserLoginStatusService {

	public UserLoginStatus getUserLoginStatus(String token);
	
	public UserLoginStatus getUserLoginStatus(Long id);

	public void createUserLoginStatus(UserLoginStatus userLoginStatus);

	public void updateUserLoginStatus(UserLoginStatus userLoginStatus);

	public void updateUserLoginStatusForDate(UserLoginStatus userLoginStatus);
}
