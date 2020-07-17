/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: Page.java 838 2010-01-06 13:47:36Z calvinxiu $
 */
package com.autozi.common.core.page;

import com.autozi.common.core.utils.ApplicationContextProvider;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 与具体ORM实现无关的分页参数及查询结果封装.
 * 注意所有序号从1开始.
 * 
 * @param <T> Page中记录的类型.
 * 
 * @author calvin
 */
public class Page<T> implements Serializable {
	private static final long serialVersionUID = -6838403542989373889L;
	
	private static final int PAGE_FETCH_NUMBER = 3;		// 每次读取的页数
	private static final int PAGE_CACHE_NUMBER = 10;		// 可以cache时每次读取的页数

	public static final int MSITE_ORDER_PAGE_SIZE = 5;		// M站订单中心里面显示页数 20160505 by　QiHaiYang
	
	//-- 公共变量 --//
	public static final String ASC = "asc";
	public static final String DESC = "desc";

	// default page size
	public static final int SIZE = 15;
	
	//-- 分页参数 --//
	protected int pageNo = 1;
	protected int pageSize = SIZE;

	protected int cachePageNo = 1;	// 
	protected List<T> cacheResult = new ArrayList<T>();
	
	protected long totalCount = 0;
    protected long b2rTotalCount = 0;
	protected String orderBy = null;
	protected String order = null;
	protected boolean autoCount = true;
	protected boolean maybeHasMore = true;
	protected boolean cachable = false;

	//-- 返回结果 --//
	protected List<T> result = new ArrayList<T>();

	//-- 构造函数 --//
	public Page() {
		IDataRows dataRowsService =  ApplicationContextProvider.getBean("dataRowsService");
		if(dataRowsService!=null){
	        this.pageSize = dataRowsService.getUserDataRows();
		}else{
			this.pageSize = SIZE;
		}
	}

	public Page(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public Integer getFirstResult() {

		return pageNo * pageSize - pageSize;
	}

	//-- 访问查询参数函数 --//
	/**
	 * 获得当前页的页号,序号从1开始,默认为1.
	 */
	public int getPageNo() {
		return pageNo;
	}

	/**
	 * 设置当前页的页号,序号从1开始,低于1时自动调整为1.
	 */
	public void setPageNo(final int pageNo) {
		this.pageNo = pageNo;

		if (pageNo < 1) {
			this.pageNo = 1;
		}
	}

	/**
	 * 获得每页的记录数量,默认为1.
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置每页的记录数量,低于1时自动调整为1.
	 */
	public void setPageSize(final int pageSize) {
		this.pageSize = pageSize;

		if (pageSize < 1) {
			this.pageSize = 1;
		}
	}

	public int getCachePageNo() {
		return cachePageNo;
	}

	public void setCachePageNo(int cachePageNo) {
		this.cachePageNo = cachePageNo;
	}

	public List<T> getCacheResult() {
		return cacheResult;
	}

	public void setCacheResult(List<T> cacheResult) {
		this.cacheResult = cacheResult;
	}

	public boolean isMaybeHasMore() {
		return maybeHasMore;
	}

	public void setMaybeHasMore(boolean maybeHasMore) {
		this.maybeHasMore = maybeHasMore;
	}

	public boolean isCachable() {
		return cachable;
	}

	public void setCachable(boolean cachable) {
		this.cachable = cachable;
	}

	/**
	 * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从1开始.
	 */
	public int getFirst() {
		return ((pageNo - 1) * pageSize) + 1;
	}

	/**
	 * 获得排序字段,无默认值.多个排序字段时用','分隔.
	 */
	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * 设置排序字段,多个排序字段时用','分隔.
	 */
	public void setOrderBy(final String orderBy) {
		this.orderBy = orderBy;
	}

	/**
	 * 获得排序方向.
	 */
	public String getOrder() {
		return order;
	}

	/**
	 * 设置排序方式向.
	 * 
	 * @param order 可选值为desc或asc,多个排序字段时用','分隔.
	 */
	public void setOrder(final String order) {
		//检查order字符串的合法值
		String[] orders = StringUtils.split(StringUtils.lowerCase(order), ',');
		for (String orderStr : orders) {
			if (!StringUtils.equals(DESC, orderStr) && !StringUtils.equals(ASC, orderStr)) {
				throw new IllegalArgumentException("排序方向" + orderStr + "不是合法值");
			}
		}

		this.order = StringUtils.lowerCase(order);
	}

	/**
	 * 是否已设置排序字段,无默认值.
	 */
	public boolean isOrderBySetted() {
		return (StringUtils.isNotBlank(orderBy) && StringUtils.isNotBlank(order));
	}

	/**
	 * 查询对象时是否自动另外执行count查询获取总记录数, 默认为false.
	 */
	public boolean isAutoCount() {
		return autoCount;
	}

	/**
	 * 查询对象时是否自动另外执行count查询获取总记录数.
	 */
	public void setAutoCount(final boolean autoCount) {
		this.autoCount = autoCount;
	}

	//-- 访问查询结果函数 --//

	/**
	 * 取得页内的记录列表.
	 */
	public List<T> getResult() {
		return result;
	}

	/**
	 * 设置页内的记录列表.
	 */
	public void setResult(final List<T> result) {
		this.result = result;
	}

	/**
	 * 取得总记录数, 默认值为-1.
	 */
	public long getTotalCount() {
		return totalCount;
	}

	/**
	 * 设置总记录数.
	 */
	public void setTotalCount(final long totalCount) {
		this.totalCount = totalCount;
	}

    /**
     * B2R-portal取得总记录数, 默认值为0.
     */
    public long getB2rTotalCount() {
        return b2rTotalCount;
    }

    /**
     * B2R-portal设置总记录数.
     */
    public void setB2rTotalCount(final long b2rTotalCount) {
        this.b2rTotalCount = b2rTotalCount;
    }

    /**
	 * 根据pageSize与totalCount计算总页数, 默认值为-1.
	 */
	public long getTotalPages() {
		if (totalCount < 0) {
			return -1;
		}

		long count = totalCount / pageSize;
		if (totalCount % pageSize > 0) {
			count++;
		}
		return count;
	}
	/**
	 * 根据pageSize与totalCount计算总页数, 默认值为0.
	 */
	public long getSumPages() {
		if (totalCount < 0) {
			return 0;
		}

		long count = totalCount / pageSize;
		if (totalCount % pageSize > 0) {
			count++;
		}
		return count;
	}

    /**
     * B2R-portal根据pageSize与totalCount计算总页数, 默认值为0.
     */
    public long getB2RSumPages() {
        if (b2rTotalCount < 0) {
            return 0;
        }

        long count = b2rTotalCount / pageSize;
        if (b2rTotalCount % pageSize > 0) {
            count++;
        }
        return count;
    }

    /**
	 * 是否还有下一页.
	 */
	public boolean isHasNext() {
		return (pageNo + 1 <= getTotalPages());
	}

	/**
	 * 取得下页的页号, 序号从1开始.
	 * 当前页为尾页时仍返回尾页序号.
	 */
	public int getNextPage() {
		if (isHasNext()) {
			return pageNo + 1;
		} else {
			return pageNo;
		}
	}

	/**
	 * 是否还有上一页.
	 */
	public boolean isHasPre() {
		return (pageNo - 1 >= 1);
	}

	/**
	 * 取得上页的页号, 序号从1开始.
	 * 当前页为首页时返回首页序号.
	 */
	public int getPrePage() {
		if (isHasPre()) {
			return pageNo - 1;
		} else {
			return pageNo;
		}
	}
	
	public RowBounds getRowBounds() {
		if (isCachable()) {
			// get PAGE_CACHE_NUMBER pages
			return new RowBounds(getFirst() - 1, getFirst() + getPageSize() * PAGE_CACHE_NUMBER);
		} else {
			// get PAGE_FETCH_NUMBER pages
			return new RowBounds(getFirst() - 1, getPageSize() * PAGE_FETCH_NUMBER);
		}
	}
	
	public int getPageFetchumber(){
		return PAGE_FETCH_NUMBER * getPageSize();
	}
	
	public int getPageCacheNumber(){
		return PAGE_CACHE_NUMBER* getPageSize();
	}

	public <X> boolean hasNextTwoPage(Page<X> cache, Page<X> page) {
		return page.getPageNo() >= cache.getCachePageNo() + PAGE_CACHE_NUMBER - PAGE_FETCH_NUMBER - 1;
	}

    public int getFirstElementIndex() {
        return (pageNo - 1) * pageSize + 1;
    }

    /**
     * 是否是首页（第一页），第一页页码为1
     * @return 首页标识
     */
	public boolean isFirstPage() {
		return pageNo == 1;
	}

    /**
     * 是否是最后一页
     * @return 末页标识
     */
	public boolean isLastPage() {
		return pageNo >= getTotalPages();
	}
}
