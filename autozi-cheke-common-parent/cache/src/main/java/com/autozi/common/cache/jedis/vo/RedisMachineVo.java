package com.autozi.common.cache.jedis.vo;

import java.io.Serializable;

import com.autozi.common.dal.entity.IdEntity;

public class RedisMachineVo extends IdEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1559818919447564087L;

	private String hostName;
	private Integer port;
	
	private int timeout = 5000;
	private String password = null;
	private String name = null;

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return hostName + ":" + port;
	}
}
