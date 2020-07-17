package com.autozi.cheke.settle.entity;

import com.autozi.common.dal.entity.IdEntity;

import java.util.Date;

/**
 * Created by lbm on 2018/1/9.
 */
public class ChanpayOrder extends IdEntity {

    private Long orderId;//提现或支付订单ID
    private String bankCommonName;//通用银行名称
    private String acctNo;//收款方银行卡或存折号码。使用平台公钥加密。
    private String acctName;//收款方银行卡或存折上的所有人姓名。使用平台公钥加密。
    private String transAmt;//交易金额（元）精确到小数点后两位

    private String codeUrl;//招码支付二维码
    private String reFlowNo;
    private Integer status;//订单状态 1=创建 2=支付中 3=成功 4=失败

    private Integer payType;//支付方式 1=代付 2=微信扫码 3=支付宝扫码 4=网银
    private Integer orderType;//1=充值 2=提现
    private Date createTime;
    private Date updateTime;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getBankCommonName() {
        return bankCommonName;
    }

    public void setBankCommonName(String bankCommonName) {
        this.bankCommonName = bankCommonName;
    }

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    public String getAcctName() {
        return acctName;
    }

    public void setAcctName(String acctName) {
        this.acctName = acctName;
    }

    public String getTransAmt() {
        return transAmt;
    }

    public void setTransAmt(String transAmt) {
        this.transAmt = transAmt;
    }

    public String getCodeUrl() {
        return codeUrl;
    }

    public void setCodeUrl(String codeUrl) {
        this.codeUrl = codeUrl;
    }

    public String getReFlowNo() {
        return reFlowNo;
    }

    public void setReFlowNo(String reFlowNo) {
        this.reFlowNo = reFlowNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
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
}
