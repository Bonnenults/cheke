package com.autozi.cheke.settle.entity;

import com.autozi.common.dal.entity.IdEntity;

import java.util.Date;

/**
 * 虚拟账户
 * User: long.jin
 * Date: 2017-11-24
 * Time: 13:37
 */
public class Account extends IdEntity {
    private Long ownerId;  //拥有者ID   如果是车客账户 则对应partyId  如果是 推客账户 则对应userId r
    private Integer type;  //账户类型
    private Double account; //余额
    private Double lockAccount;//锁定金额
    private Date createTime;
    private Date updateTime;

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Double getAccount() {
        return account;
    }

    public void setAccount(Double account) {
        this.account = account;
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

    public Double getLockAccount() {
        return lockAccount;
    }

    public void setLockAccount(Double lockAccount) {
        this.lockAccount = lockAccount;
    }
}
