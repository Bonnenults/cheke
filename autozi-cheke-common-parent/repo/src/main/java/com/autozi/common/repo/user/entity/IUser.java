package com.autozi.common.repo.user.entity;

/**
 * 用户
 * 
 * @author lihf
 *
 */
public interface IUser {
	public Long getId();
	public String getLoginName();
	public String getPassword();
	public Long getPartyId();
	public Integer getUserType();
//	public int getPartyType();
}
