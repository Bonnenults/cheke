package com.autozi.passcar.cache.memcached;


import java.util.HashMap;
import java.util.Map;

/**
 * @Title: ThreadLocalUtil.java 
 * @Description: 用于在本地线程中存放变量  
 * @author xujinlei  
 * @Modified xujinlei      
 * @date 2013-5-9 下午1:44:14   
 * @version V1.0     
 */
public class ThreadLocalUtil {
	
	private static final ThreadLocal<Map<String,Object>> context = new ThreadLocal<Map<String,Object>>();

	public static Object get(String key){
		Map<String,Object> varMap = context.get();
		if (varMap == null) {
			return null;
		}
		return varMap.get(key);
	}
	public static void set(String key, Object obj){
		Map<String,Object> varMap = context.get();
		if (varMap == null) {
			varMap = new HashMap<String,Object>();
			context.set(varMap);
		}
		varMap.put( key, obj );
	}
}
