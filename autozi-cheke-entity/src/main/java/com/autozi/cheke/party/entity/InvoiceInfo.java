package com.autozi.cheke.party.entity;

import com.autozi.common.dal.entity.IdEntity;

/**
 * User: 刘宇
 * Date: 12-8-22
 * Time: 下午1:35
 */
public class InvoiceInfo extends IdEntity {
    private static final long serialVersionUID = -3426606008582350946L;

    private Long partyId;       //所属主体ID
    private String title;       // 发票抬头
    private String code;        //纳税人识别号
    private String address;     //地址、电话
    private String account;     //开户行及账号

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;

    }

    public Long getPartyId() {
        return partyId;
    }

    public void setPartyId(Long partyId) {
        this.partyId = partyId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
