package com.autozi.common.core.page;

/**
 * Created by 刘兵 on 2017/5/24.
 */
public class B2RPage<T> extends Page<T>{
    /**
	 * 
	 */
	private static final long serialVersionUID = -6113992708885459044L;

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
        if(count>(this.pageNo+2)){
            count = this.pageNo+2;
        }
        return count;
    }
}
