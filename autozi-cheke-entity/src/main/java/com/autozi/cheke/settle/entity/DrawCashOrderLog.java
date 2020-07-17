package com.autozi.cheke.settle.entity;

import com.autozi.common.dal.entity.IdEntity;

import java.util.Date;

/**
 * 提现变动流水
 * User: long.jin
 * Date: 2017-11-24
 * Time: 13:49
 */
public class DrawCashOrderLog extends IdEntity {
    private Long drawCashOrderId;
    private Integer oldStatus;
    private Integer status;
    private Date createTime;
    private String note; //备注

    public Long getDrawCashOrderId() {
        return drawCashOrderId;
    }

    public void setDrawCashOrderId(Long drawCashOrderId) {
        this.drawCashOrderId = drawCashOrderId;
    }

    public Integer getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(Integer oldStatus) {
        this.oldStatus = oldStatus;
    }



    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }



    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
