package com.autozi.cheke.basic.entity;

import com.autozi.common.dal.entity.IdEntity;

import java.util.Date;


public class Area extends IdEntity {
    private static final long serialVersionUID = -3427106008582350946L;
    public static Integer LEVEL_ONE = 1; //1代表一级地区
    public static Integer LEVEL_TWO = 2;  //2,代表2级地区
    public static Integer LEVEL_THREE = 3;//3，代表3级地区
    public static Integer STATUS_VALID = 1;//1可用
    public static Integer STATUS_INVALID = -1;//2不可用,逻辑删 除
    public final int initLevel = 3;
    public final int initDigit = 2;
    public final int intiData = 100;

    private String name;
    private String areaCode;
    private Integer status; //状态0:可用1:不可用
    private Long parentId;
    private Integer areaLevel;  //IAreaLevel

    private Date updateTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getAreaLevel() {
        return areaLevel;
    }

    public void setAreaLevel(Integer areaLevel) {
        this.areaLevel = areaLevel;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
