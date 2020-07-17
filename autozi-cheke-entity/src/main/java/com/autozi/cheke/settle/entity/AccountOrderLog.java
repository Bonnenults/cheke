package com.autozi.cheke.settle.entity;

import com.autozi.common.dal.entity.IdEntity;

import java.util.Date;

/**
 * 账户变动单
 * User: long.jin
 * Date: 2017-11-24
 * Time: 13:49
 */
public class AccountOrderLog extends IdEntity {
    private Long accountOrderId;
    private Integer oldStatus;
    private Integer oldPayStatus;
    private Integer status;
    private Integer payStatus;
    private Date createTime;
    private String note; //备注


}
