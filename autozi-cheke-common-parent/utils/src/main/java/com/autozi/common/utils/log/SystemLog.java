package com.autozi.common.utils.log;


public class SystemLog extends Log {
	
	public SystemLog() {
		super(LOG_SYSTEM);
	}
	
	public SystemLog(String prefix) {
		super(LOG_SYSTEM, prefix);
	}
}
