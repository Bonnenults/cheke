package com.autozi.cheke.web.settle.vo;

import com.autozi.cheke.util.web.BaseView;

import java.util.Date;

/**
 * User: long.jin
 * Date: 2017-12-04
 * Time: 15:17
 */
public class DrawCashOrderVo extends BaseView {
    private String code;
    private Long userId;  //用户Id
    private Double accountMoney; //账户变动金额
    private Double slottingFee; //通道费
    private Double money; //实际金额
    private Date createTime;
    private Date updateTime;
    private Date payTime;//成功支付时间
    private Integer status;
    private String note;

    private String tkName;
    private String tkPhone;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Double getSlottingFee() {
        return slottingFee;
    }

    public void setSlottingFee(Double slottingFee) {
        this.slottingFee = slottingFee;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
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

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
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

    public String getTkName() {
        return tkName;
    }

    public void setTkName(String tkName) {
        this.tkName = tkName;
    }

    public String getTkPhone() {
        return tkPhone;
    }

    public void setTkPhone(String tkPhone) {
        this.tkPhone = tkPhone;
    }
}
