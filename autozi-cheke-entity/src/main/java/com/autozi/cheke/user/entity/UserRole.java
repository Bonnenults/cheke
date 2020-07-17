/**
 * 文件名称   : com.wms.base.entity.UserRole.java
 * 项目名称   : 中驰汽配交易平台
 * 创建日期   : 2013-3-25
 * 更新日期   :
 * 作       者   : haifeng.li@qeegoo.com
 *
 * Copyright (C) 2013 启购时代 版权所有.
 */
package com.autozi.cheke.user.entity;


import com.autozi.common.dal.entity.IdEntity;

import java.util.Date;

/**
 * <PRE>
 * 
 * 中文描述：
 * 
 * </PRE>
 * @version 1.0.0
 */
public class UserRole extends IdEntity {

	private static final long serialVersionUID = 8216097619045465046L;
	
	private long userId;
	private long roleId;
	private Date createTime;//  创建时间
    private Long createUserId; //创建人ID
    
    
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

}
