package com.autozi.cheke.user.entity;

import com.autozi.common.dal.entity.IdEntity;

import java.util.Date;
/**
 * 密码找回参数
 */
public class FindPwdLog extends IdEntity {
	private static final long serialVersionUID = -2838521017430620549L;

    private long userId;		//用户Id
    private String key;			//随机密码
    private Date overdueTime;		//过期时间
    
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Date getOverdueTime() {
		return overdueTime;
	}
	public void setOverdue(Date overdueTime) {
		this.overdueTime = overdueTime;
	}
}
