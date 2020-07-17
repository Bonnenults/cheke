package com.autozi.cheke.service.user;

import com.autozi.cheke.user.dao.UserLoginStatusDao;
import com.autozi.cheke.user.entity.UserLoginStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserLoginStatusServiceImpl implements IUserLoginStatusService {

	@Autowired
	private UserLoginStatusDao userLoginStatusDao;
	
	@Override
	public UserLoginStatus getUserLoginStatus(String token) {
		return userLoginStatusDao.getUserLoginStatus(token);
	}

	@Override
	public void createUserLoginStatus(UserLoginStatus userLoginStatus) {
		userLoginStatusDao.insert(userLoginStatus);
	}

	@Override
	public UserLoginStatus getUserLoginStatus(Long id) {
		return userLoginStatusDao.get(id);
	}

	@Override
	public void updateUserLoginStatus(UserLoginStatus userLoginStatus) {
		userLoginStatusDao.update(userLoginStatus);
	}

	@Override
	public void updateUserLoginStatusForDate(UserLoginStatus userLoginStatus) {
		userLoginStatusDao.update(userLoginStatus);
	}

}
