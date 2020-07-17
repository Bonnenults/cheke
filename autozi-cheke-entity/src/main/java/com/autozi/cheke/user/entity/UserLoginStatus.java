package com.autozi.cheke.user.entity;


import java.util.Date;

import com.autozi.common.dal.entity.IdEntity;

/**
 *  手机用户登录状态
 * @user zhiyun.chen
 *
 */
public class UserLoginStatus extends IdEntity {

	private static final long serialVersionUID = -197213943832006676L;

	private String token;      // 登录标志
	private Date lastLoginTime;// 上次登录时间
	private Date createTime;   // 第一次登录时间

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
