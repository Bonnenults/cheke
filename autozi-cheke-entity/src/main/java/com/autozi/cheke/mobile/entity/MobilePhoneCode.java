package com.autozi.cheke.mobile.entity;


import java.util.Date;

import com.autozi.common.dal.entity.IdEntity;


/**
 * 手机验证码
 * @user zhiyun.chen
 *
 */
public class MobilePhoneCode extends IdEntity {
	private static final long serialVersionUID = -6180449201952716105L;
	
	private String mobilePhone; //手机号码
	private String code; //手机验证码
	private Integer totalSendNumber; //总发送次数
	private Integer sendNumber; //一天内发送次数（如：最多发送5次）
	private Integer status; //状态，0：未验证，1：验证通过
	private Date createTime; //创建时间
	private Date lastSendTime; //最后一次发送时间
	private Date updateTime; //更新时间
	
	public static final int STATUS_NOT = 0;//未验证
	public static final int STATUS_SUCCESS = 1;//验证通过
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getTotalSendNumber() {
		return totalSendNumber;
	}
	public void setTotalSendNumber(Integer totalSendNumber) {
		this.totalSendNumber = totalSendNumber;
	}
	public Integer getSendNumber() {
		return sendNumber;
	}
	public void setSendNumber(Integer sendNumber) {
		this.sendNumber = sendNumber;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLastSendTime() {
		return lastSendTime;
	}
	public void setLastSendTime(Date lastSendTime) {
		this.lastSendTime = lastSendTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}
