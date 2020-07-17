package com.autozi.cheke.mobile.entity;


import java.util.Date;

import com.autozi.common.dal.entity.IdEntity;

public class MobilePhoneMessageEntry extends IdEntity {
	private static final long serialVersionUID = 1022867035876688090L;

	private String mobilePhone;	//手机号码
	private String content;	//短信内容
	private Integer sendResult;	//发送结果
	private Integer checkResult; //验证结果
	private Integer type; //发送类型
	private Date createTime; //发送时间
	private String code; //手机验证码
	
	public static final int SEND_RESULT_NOT = 0; //发送结果失败
	public static final int SEND_RESULT_SUCCESS = 1; //发送结果成功
	public static final int CHECK_RESULT_NOT = 0; //验证未通过
	public static final int CHECK_RESULT_SUCCESS = 1; //验证通过
	public static final int TYPE_REGISTER = 1; //类型-注册
	public static final int TYPE_FIND_PASSWORD = 2; //类型-找回密码
	public static final int TYPE_UPDATE_MOBILE = 3; //类型-修改手机号码
	public static final int TYPE_QUICK_LOGIN = 4; //类型-快速登录
	public static final int TYPE_REPAIR_REGISTER = 5; //类型-维修登记
	public static final int TYPE_RESCUE_REGISTER = 6; //类型-救援登记
	public static final int TYPE_ASSESSMENT_RELEASE = 7; //类型-定损发布
	public static final int TYPE_QUICK_LOGIN_REGISTER = 8; //类型-快速登录-注册用户
	public static final int TYPE_RESERVE_REPAIR_REGISTER = 9; //类型-快速登录-注册用户
	public static final int TYPE_HOME_MAINTENANCE_REGISTER = 10; //类型-上门保养-注册用户
	public static final int TYPE_SALES_REGISTER = 11; //类型-销售出库-注册用户
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getSendResult() {
		return sendResult;
	}
	public void setSendResult(Integer sendResult) {
		this.sendResult = sendResult;
	}
	public Integer getCheckResult() {
		return checkResult;
	}
	public void setCheckResult(Integer checkResult) {
		this.checkResult = checkResult;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

}
