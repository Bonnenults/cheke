/**
 * 
 * 系统异常处理类，专门用于处理control抛出的异常信息
 * 
 * 覆盖SimpleMappingExceptionResolver的getModelAndView方法
 * 
 * 
 */
package com.autozi.common.web.springmvc;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DataAccessException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**
 * 系统业务异常处理
 * 
 * @author lihaifeng
 *
 */
public class BussionExceptionHandler extends SimpleMappingExceptionResolver {
	
	@Override
	protected ModelAndView getModelAndView(String viewName, Exception ex
			, HttpServletRequest request) {
		ModelAndView mv = super.getModelAndView(viewName, ex);
		mv.addObject("errorMsg",getMessage(ex));
		return mv;
	}
	private String getMessage(Throwable e) {
		if (e instanceof DataAccessException) {
			return "数据库操作失败！";
		} else if (e instanceof NullPointerException) {
			return "调用了未经初始化的对象或者是不存在的对象！";
		} else if (e instanceof IOException) {
			return "读写异常！";
		} else if (e instanceof ClassNotFoundException) {
			return "指定的类不存在！";
		} else if (e instanceof ArithmeticException) {
			return "数学运算异常！";
		} else if (e instanceof ArrayIndexOutOfBoundsException) {
			return "数组下标越界！";
		} else if (e instanceof IllegalArgumentException) {
			return "方法的参数错误！";
		} else if (e instanceof ClassCastException) {
			return "类型强制转换错误！";
		} else if (e instanceof SecurityException) {
			return "违背安全原则异常！";
		} else if (e instanceof SQLException) {
			return "操作数据库异常！";
		} else if (e instanceof NoSuchMethodError) {
			return "方法末找到异常！";
		} else if (e instanceof InternalError) {
			return "发生了内部错误！";
		} else if (e instanceof Exception) {
			return "内部错误，操作失败！";
		}
		return e.toString();
	}
}
