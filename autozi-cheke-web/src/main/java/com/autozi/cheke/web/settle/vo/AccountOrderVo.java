package com.autozi.cheke.web.settle.vo;

import com.autozi.cheke.util.web.BaseView;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;

/**
 * User: long.jin
 * Date: 2017-12-04
 * Time: 15:17
 */
public class AccountOrderVo extends BaseView {
    private String code;
    private Long partyId; //主体Id
    private Long userId;  //用户Id
    private Double accountMoney; //账户变动金额
    private Double rateFee; //扣减税费
    private Double slottingFee; //通道费
    private Double realMoney; //实际金额
    private Date createTime;
    private Date updateTime;
    private Integer type;  //类型 状态
    private Integer status;
    private String note;
    private Date payTime;
    private String partyClassName; //车客号 or 推客号
    private String partyName;
    private String statusName;
    private String tkPhone; //推客电话
    private Integer invoiceStatus;//订单是否开发票状态
    private Date invoiceTime;//开发票时间

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

    public String getPartyClassName() {
        return partyClassName;
    }

    public void setPartyClassName(String partyClassName) {
        this.partyClassName = partyClassName;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getTkPhone() {
        return tkPhone;
    }

    public void setTkPhone(String tkPhone) {
        this.tkPhone = tkPhone;
    }

    public Integer getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(Integer invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public Date getInvoiceTime() {
        return invoiceTime;
    }

    public void setInvoiceTime(Date invoiceTime) {
        this.invoiceTime = invoiceTime;
    }
}
