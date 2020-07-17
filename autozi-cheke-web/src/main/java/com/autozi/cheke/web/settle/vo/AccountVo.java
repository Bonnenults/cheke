package com.autozi.cheke.web.settle.vo;

import com.autozi.cheke.util.web.BaseView;

import java.util.Date;

/**
 * User: long.jin
 * Date: 2017-12-04
 * Time: 14:47
 */
public class AccountVo extends BaseView {

    private Long ownerId;  //拥有者ID   如果是车客账户 则对应partyId  如果是 推客账户 则对应userId r
    private Integer type;  //账户类型
    private Double account; //余额
    private Double lockAccount;//锁定金额
    private Date createTime;
    private Date updateTime;
    private String partyName;
    private String partyClassName;
    private String companyName;

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

    public Double getLockAccount() {
        return lockAccount;
    }

    public void setLockAccount(Double lockAccount) {
        this.lockAccount = lockAccount;
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

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getPartyClassName() {
        return partyClassName;
    }

    public void setPartyClassName(String partyClassName) {
        this.partyClassName = partyClassName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
