package com.autozi.cheke.party.entity;

import com.autozi.common.dal.entity.IdEntity;

import java.util.Date;


public class Message extends IdEntity {
	
	private static final long serialVersionUID = -3427106708582350676L;
	private Date createTime;    //创建时间
	private Integer priority;	//优先级
	private String sender;  	//发送者
	private Long senderId;  	//发送者ID
    private Integer senderType; //发送主体类型
	private String subject;		//内容
    private String target;   	//接收者
    private Long targetId;   	//接收者ID
    private Integer targetType; //接收主体类型
    private Integer status; 	//状态
    private Integer type;		//消息类型
    
    private String reMark;   	//备注信息

    public static final int TYPE_SUPPLIER_PRICE_CHANGE = 1;
    public static final String TYPE_SUPPLIER_PRICE_CHANGE_CAPTION = "供应商价格修改";

    public static final int TYPE_SUPPLIER_GOODS_PURCHASE=2;
    public static final String TYPE_SUPPLIER_GOODS_PURCHASE_CAPTION="供货商补货提醒";

    public static final int TYPE_ORDER_REMARK=3;
    public static final String TYPE_ORDER_REMARK_CAPTION="审核提醒";//审核订单备注，订单备注提醒
    
    public static final int TYPE_ONLINE_GOODS_REFUND=4;
    public static final String TYPE_ONLINE_GOODS_REFUND_CAPTION="退款提醒";//在线支付退款提醒


    public static final int TYPE_ORDER_CONFIRMFH=5;
    public static final String TYPE_ORDER_CONFIRMFH_CAPTION="发货提醒";

    public static final int TYPE_NOT_USE_TIME_OUT=6;
    public static final String TYPE_NOT_USE_TIME_OUT_CAPTION="优惠券快到期提醒";//优惠券快到期提醒

    public static final int TYPE_GET_TICKET=7;
    public static final String TYPE_GET_TICKET_CAPTION="得到优惠券提醒";//得到优惠券提醒

    public static final int TYPE_GET_USER_MESSAGE=8;
    public static final String TYPE_GET_USER_MESSAGE_CAPTION="发送给临时用户登录信息";//发送给临时用户登录信息


    public static final int STATUS_NEW = 0; //新的消息
    public static final int STATUS_READ = 1; //已经读取
    public static final int STATUS_DELETED = -1; //删除
    
//    public static final int SENDER_ID_TWO = 2; //平台审核


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Integer getSenderType() {
        return senderType;
    }

    public void setSenderType(Integer senderType) {
        this.senderType = senderType;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public Integer getTargetType() {
        return targetType;
    }

    public void setTargetType(Integer targetType) {
        this.targetType = targetType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

	public String getReMark() {
		return reMark;
	}

	public void setReMark(String reMark) {
		this.reMark = reMark;
	}
}
