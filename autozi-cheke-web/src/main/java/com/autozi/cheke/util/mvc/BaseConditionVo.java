/**
 * 文件名称   : com.wms.web.mvc.BaseConditionVo.java
 * 项目名称   : 中驰汽配交易平台
 * 创建日期   : 2013-3-27
 * 更新日期   :
 * 作       者   : haifeng.li@qeegoo.com
 *
 * Copyright (C) 2013 启购时代 版权所有.
 */
package com.autozi.cheke.util.mvc;

/**
 * <PRE>
 * 
 * 中文描述：
 * 
 * </PRE>
 * @version 1.0.0
 */
public class BaseConditionVo {
	private String createTimeBegin;
	private String createTimeEnd;
	
	private String sortByItem;
	private String sortByType;
	
	public String getCreateTimeBegin() {
		return createTimeBegin;
	}
	public void setCreateTimeBegin(String createTimeBegin) {
		this.createTimeBegin = createTimeBegin;
	}
	public String getCreateTimeEnd() {
		return createTimeEnd;
	}
	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}
	public String getSortByItem() {
		return sortByItem;
	}
	public void setSortByItem(String sortByItem) {
		this.sortByItem = sortByItem;
	}
	public String getSortByType() {
		return sortByType;
	}
	public void setSortByType(String sortByType) {
		this.sortByType = sortByType;
	}
	
	
}
