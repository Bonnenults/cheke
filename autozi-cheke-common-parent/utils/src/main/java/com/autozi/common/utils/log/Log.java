package com.autozi.common.utils.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {
	public final static String LOG_SLASH = "--";
	
	public final static String LOG_SYSTEM = "SYSTEM";
	public final static String LOG_TRACE = "TRACE";
	
	public final static String CHAINSTORE = "CHAINSTORE"; 
	public final static String SUPPLIER = "SUPPLIER"; 
	public final static String LOGISTICS = "LOGISTICS"; 
	public final static String SOURCING = "SOURCING"; 
	public final static String CHAINHEADQUARTER = "CHAINHEADQUARTER"; 
	
	private Logger logger;
	private String prefix = "";
	
	public Log(String type) {
		logger = LoggerFactory.getLogger(type);
	}
	
	public Log(String type, String prefix) {
		logger = LoggerFactory.getLogger(type);
		this.prefix = prefix;
	}
	
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public void debug(String msg) {
		logger.debug(prefix + " - " + msg);
	}

	public void debug(String msg, Throwable th) {
		logger.debug(prefix + " - " + msg, th);
	}

	public void info(String msg) {
		logger.info(prefix + " - " + msg);
	}

	public void info(String msg, Throwable th) {
		logger.info(prefix + " - " + msg, th);
	}

	public void error(String msg) {
		logger.error(prefix + " - " + msg);
	}

	public void error(String msg, Throwable th) {
		logger.error(prefix + " - " + msg, th);
	}

	public void trace(String msg) {
		logger.trace(prefix + " - " + msg);
	}
	
	public void trace(String msg, Throwable th) {
		logger.trace(prefix + " - " + msg, th);
	}	
}
