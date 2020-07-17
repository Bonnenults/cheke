package com.autozi.common.dal.format;

import java.io.Serializable;

public abstract class IdEntity implements Serializable{
	@Column(ColumnType=persistence.TEMPORARY)
	private static final long serialVersionUID = -5489876511792281292L;
	
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
