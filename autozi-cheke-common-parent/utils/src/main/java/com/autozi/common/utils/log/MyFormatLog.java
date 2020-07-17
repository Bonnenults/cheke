package com.autozi.common.utils.log;

import org.apache.log4j.Logger;

/**
 * 
 * 打印各级别的日志信息   
 * 
 * @author lihf
 * @version V1.1   
 *
 */
public class MyFormatLog {
	
	private static MyFormatLog object;
	private Logger _log;
	
	private MyFormatLog(Class<?> clazz){
		if (null == _log){
			_log = Logger.getLogger(clazz);
		}
	}
	
	/**
	 * 打印WARN级别的日志
	 * @param source 
	 * @param args 
	 */
	public void warn(String source, Object...args){
		_log.warn(String.format(source, args));
	}
	
	/**
	 * 打印ERROR级别的日志
	 * @param exception
	 * @param source 
	 * @param args 
	 */
	public void error(Throwable exception, String source, Object...args){
		_log.error(String.format(source, args), exception);
	}
	
	/**
	 * 打印DEBUG级别的日志
	 * @param source 
	 * @param args 
	 */
	public void debug(String source, Object...args){
		_log.debug(String.format(source, args));
	}
	
	/**
	 * 打印INFO级别的日志
	 * @param source 
	 * @param args 
	 */
	public void info(String source, Object...args){
		_log.info(String.format(source, args));
	}
	
	/** 
	 * 打印FATAL
	 * @param exception
	 * @param source
	 * @param args
	 */
	public void fatal(Throwable exception, String source, Object...args){
		_log.fatal(String.format(source, args), exception);
	}
	
	public static MyFormatLog getLog(Class<?> clazz){
		if (null == object){
			return new MyFormatLog(clazz);
		}
		return object;
	}
}

