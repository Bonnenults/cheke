package com.autozi.common.utils.o2o;


import com.autozi.common.core.page.Page;
import com.autozi.common.utils.util2.CommonUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class PageUtil {

	/**
	 * 获取当前页
	 * @param request
	 * @return
	 */
	public static int getPageNo(HttpServletRequest request){
		String pageNo = request.getParameter("pageNum");
		if (pageNo == null || pageNo.equals("")) {
			pageNo = "1";
		}
		return Integer.parseInt(pageNo);
	}
	
	
	/**
	 * 分页信息转换
	 * @param <T>
	 * @param <T>
	 * @param <E>
	 * @param sourcePage
	 * @param targetPage
	 */
	public static <T, E> void convertPage(Page<E> sourcePage, Page<T> targetPage, List<T> result){
		CommonUtils.pageConversion(sourcePage, targetPage);
		targetPage.setResult(result);
	}
	
	
	
	
	
	
	
	
	
	
}
