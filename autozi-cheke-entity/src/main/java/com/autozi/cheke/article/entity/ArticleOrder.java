package com.autozi.cheke.article.entity;

import com.autozi.common.dal.entity.IdEntity;

import java.util.Date;

/**
 * Created by wang-lei on 2017/11/23.
 * 任务分享，点击次数
 */
public class ArticleOrder  extends IdEntity{

    private Long articleId;
    private Long userId;

    private Date createTime;

    private Integer clickNum;//点击次数
    private Integer maxClickNum;//最大点击次数
    private Date  updateTime;

    private Integer status;  //0正常未完成  1已满额已完成任务

    private Integer type; //分享类型（此字段没用）

    private Integer txStatus; //是否已提现，0未提现，1,已提现

    private double myselfMoney; //自己获取的钱
    private double outMoney; //上一级推客获取的佣金

    private Integer hasParentTwr; //是否有上级推客  0没有，1有

    private double syncMyselfMoney; //已同步自己获取的钱

    private double syncOutMoney; //已同步上级推客获取的钱

    private Long topTuikeId; //上级推客用户ID


    public Long getTopTuikeId() {
        return topTuikeId;
    }

    public void setTopTuikeId(Long topTuikeId) {
        this.topTuikeId = topTuikeId;
    }

    public double getSyncMyselfMoney() {
        return syncMyselfMoney;
    }

    public void setSyncMyselfMoney(double syncMyselfMoney) {
        this.syncMyselfMoney = syncMyselfMoney;
    }

    public double getSyncOutMoney() {
        return syncOutMoney;
    }

    public void setSyncOutMoney(double syncOutMoney) {
        this.syncOutMoney = syncOutMoney;
    }

    public Integer getHasParentTwr() {
        return hasParentTwr;
    }

    public void setHasParentTwr(Integer hasParentTwr) {
        this.hasParentTwr = hasParentTwr;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public double getMyselfMoney() {
        return myselfMoney;
    }

    public void setMyselfMoney(double myselfMoney) {
        this.myselfMoney = myselfMoney;
    }

    public double getOutMoney() {
        return outMoney;
    }

    public void setOutMoney(double outMoney) {
        this.outMoney = outMoney;
    }

    public Integer getTxStatus() {
        return txStatus;
    }

    public void setTxStatus(Integer txStatus) {
        this.txStatus = txStatus;
    }
    public Integer getClickNum() {
        return clickNum;
    }

    public void setClickNum(Integer clickNum) {
        this.clickNum = clickNum;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getMaxClickNum() {
        return maxClickNum;
    }

    public void setMaxClickNum(Integer maxClickNum) {
        this.maxClickNum = maxClickNum;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
