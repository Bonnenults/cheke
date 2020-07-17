/**
 * 文件名称   : com.autozi.common.search.es.entity.AutoziPasscarDomain.java
 * 项目名称   : 中驰车福-乘用车
 * 创建日期   : 2017-8-18
 * 更新日期   :
 * 作       者   : haifeng.li@autozi.com
 *
 * Copyright (C) 2016 中驰车福联合电子商务（北京）有限公司 版权所有.
 */
package com.autozi.common.search.es.entity;


import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;

/**
 * <PRE>
 * 
 * 中文描述：
 * 
 * </PRE>
 * @version 1.0.0
 */
@Document(indexName="autozi_passcar",replicas=1,shards=6,type="domain")
public class AutoziPasscarDomain implements Serializable{
	
	private static final long serialVersionUID = -6570697327245251869L;
	@Id
	private long id;
	@Field(index=FieldIndex.not_analyzed)
    private String sourceType;
	@Field(index=FieldIndex.not_analyzed)
    private Long sourceId;
	@Field(index=FieldIndex.not_analyzed)
    private String domainType;
	@Field(index=FieldIndex.not_analyzed)
    private Long domainId;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
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
	
}
