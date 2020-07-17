package com.autozi.cheke.basic.entity;

import com.autozi.common.dal.entity.IdEntity;

import java.util.Date;


/**
 * 用来保存最大流水号
 * 
 * @author bochen
 *
 */
public class RunningNumber extends IdEntity {
	private static final long serialVersionUID = -2199355391397536998L;
	public final static String SD = "SD";
	public final static String DD = "DD";
	
	private Date runningTime;
	private String type;
	private String runningNumber;
	private Long partyId;

	public Date getRunningTime() {
		return runningTime;
	}

	public void setRunningTime(Date runningTime) {
		this.runningTime = runningTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRunningNumber() {
		return runningNumber;
	}

	public void setRunningNumber(String runningNumber) {
		this.runningNumber = runningNumber;
	}

	public Long getPartyId() {
		return partyId;
	}

	public void setPartyId(Long partyId) {
		this.partyId = partyId;
	}
}
