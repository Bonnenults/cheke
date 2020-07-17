package com.autozi.common.repo.entity;

import java.io.Serializable;



/**
 * 应用程序创建的实体对象Version 从10开始，version 小于10的是系统初始化的数据，
 * 这些数据不能从应用程序删除。
 * @author haocheng
 *
 */
public abstract class IdEntity implements Serializable{
	private static final long serialVersionUID = 8094833045795401482L;
	protected Long id;
	protected Integer version = 10;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}
