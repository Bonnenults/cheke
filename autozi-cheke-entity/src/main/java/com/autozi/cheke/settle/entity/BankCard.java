package com.autozi.cheke.settle.entity;

import com.autozi.common.dal.entity.IdEntity;

import java.util.Date;

/**
 * Created by lbm on 2018/1/8.
 */
public class BankCard extends IdEntity {
    private Long accountId;
    private String name;//持卡人姓名
    private String bankName;//卡所在银行名称
    private String cardNumber;//卡号
    private String bankCode;//银行代号
    private Date createTime;
    private Date updateTime;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
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

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }
}
