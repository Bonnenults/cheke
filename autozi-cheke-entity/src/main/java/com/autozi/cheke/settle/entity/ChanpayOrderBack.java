package com.autozi.cheke.settle.entity;

import com.autozi.common.dal.entity.IdEntity;

import java.util.Date;

/**
 * Created by lbm on 2018/1/9.
 */
public class ChanpayOrderBack extends IdEntity {
    private Long chanpayOrderId;//交易订单号
    private String innerTradeNo;//畅捷订单号
    private String tradeStatus;//交易状态
    private String tradeAmount;//交易金额
    private String gmtCreate;//交易创建时间
    private Date createTime;

    public Long getChanpayOrderId() {
        return chanpayOrderId;
    }

    public void setChanpayOrderId(Long chanpayOrderId) {
        this.chanpayOrderId = chanpayOrderId;
    }

    public String getInnerTradeNo() {
        return innerTradeNo;
    }

    public void setInnerTradeNo(String innerTradeNo) {
        this.innerTradeNo = innerTradeNo;
    }

    public String getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public String getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(String tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
