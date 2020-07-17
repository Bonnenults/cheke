/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: Page.java 838 2010-01-06 13:47:36Z calvinxiu $
 */
package com.autozi.common.core.page;


/**
 * 与具体ORM实现无关的分页参数及查询结果封装.【用于Dpl平台】
 * 注意所有序号从1开始.
 * 
 * @param <T> Page中记录的类型.
 * 
 * @author calvin
 */
public class DplPage<T> extends Page<T> {
	private static final long serialVersionUID = -6838403542989373889L;

	public DplPage(){
		super(Page.SIZE); // 指定构造函数
	}
	
}
