/*
 * Copyright (c) 2010-2015 by HaoCheng
 * All rights reserved.
*/
package com.autozi.cheke.user.entity;

import com.autozi.common.dal.entity.IdEntity;

import java.util.Date;

/**
 * @author HaoCheng
 * @Version 0.6
 * Mar 3, 2011
 */
public class Domain extends IdEntity implements Comparable<Domain> {
    private static final long serialVersionUID = 6339398229111553889L;

    public static final int isPriority = 1;

    private String sourceType;
    private Long sourceId;
    private String domainType;
    private Long domainId;
    private Integer priority;
    
    private Date updateTime;

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public String getDomainType() {
        return domainType;
    }

    public void setDomainType(String domainType) {
        this.domainType = domainType;
    }

    public Long getDomainId() {
        return domainId;
    }

    public void setDomainId(Long domainId) {
        this.domainId = domainId;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public int compareTo(Domain domain) {
        return this.getDomainId().compareTo(domain.getDomainId());
    }

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
    
}
