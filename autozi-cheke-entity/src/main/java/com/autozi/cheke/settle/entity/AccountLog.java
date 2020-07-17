package com.autozi.cheke.settle.entity;

import com.autozi.common.dal.entity.IdEntity;

import java.util.Date;

/**
 * User: long.jin
 * Date: 2017-11-24
 * Time: 15:06
 */
public class AccountLog extends IdEntity {
    private Long accountId; //账户Id
    private Double startAccount;  //开始金额
    private Double changeAccount; //变动金额
    private Double endAccount;  //变动后金额
    private Date createTime;
    private Integer sourceType;
    private Long sourceId;
    private String  note;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Double getStartAccount() {
        return startAccount;
    }

    public void setStartAccount(Double startAccount) {
        this.startAccount = startAccount;
    }

    public Double getChangeAccount() {
        return changeAccount;
    }

    public void setChangeAccount(Double changeAccount) {
        this.changeAccount = changeAccount;
    }

    public Double getEndAccount() {
        return endAccount;
    }

    public void setEndAccount(Double endAccount) {
        this.endAccount = endAccount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
