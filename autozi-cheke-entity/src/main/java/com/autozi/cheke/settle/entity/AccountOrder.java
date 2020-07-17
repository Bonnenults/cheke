package com.autozi.cheke.settle.entity;

import com.autozi.common.dal.entity.IdEntity;

import java.util.Date;

/**
 * 账户变动单
 * User: long.jin
 * Date: 2017-11-24
 * Time: 13:49
 */
public class AccountOrder extends IdEntity {

    private String code;
    private Long partyId; //主体Id
    private Long userId;  //用户Id
    private Double accountMoney; //账户变动金额
    private Double rateFee; //扣减税费
    private Double slottingFee; //通道费
    private Double realMoney; //实际金额
    private Date createTime;
    private Date updateTime;
    private Date payTime;
    private Integer type;  //类型 状态
    private Integer status;
    private String note;
    private Integer invoiceStatus;//0初始状态 1开票中 2已开票
    private Date invoiceTime;//开发票时间

    public Date getInvoiceTime() {
        return invoiceTime;
    }

    public void setInvoiceTime(Date invoiceTime) {
        this.invoiceTime = invoiceTime;
    }

    public Long getPartyId() {
        return partyId;
    }

    public void setPartyId(Long partyId) {
        this.partyId = partyId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getAccountMoney() {
        return accountMoney;
    }

    public void setAccountMoney(Double accountMoney) {
        this.accountMoney = accountMoney;
    }

    public Double getRealMoney() {
        return realMoney;
    }

    public void setRealMoney(Double realMoney) {
        this.realMoney = realMoney;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getRateFee() {
        return rateFee;
    }

    public void setRateFee(Double rateFee) {
        this.rateFee = rateFee;
    }

    public Double getSlottingFee() {
        return slottingFee;
    }

    public void setSlottingFee(Double slottingFee) {
        this.slottingFee = slottingFee;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Integer getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(Integer invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }
}
