package com.autozi.cheke.party.entity;

import com.autozi.common.dal.entity.IdEntity;


public class Department extends IdEntity {
    private static final long serialVersionUID = -7062544160220384497L;

    public static final int STATUS_VALID = 1;   //可用
    public static final int STATUS_INVALID = -1;  //不可用
    private Long partyId;
    private String name;
    private String code;

    private Integer status;    //状态：1 正常 -1 删除

    public Long getPartyId() {
        return partyId;
    }

    public void setPartyId(Long partyId) {
        this.partyId = partyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
