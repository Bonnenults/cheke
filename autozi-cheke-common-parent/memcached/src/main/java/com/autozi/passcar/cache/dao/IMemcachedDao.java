package com.autozi.passcar.cache.dao;

public interface IMemcachedDao {
	/**
	 * Set方法.带过期时间
	 */
	<T> void set(String key, int expiredTime, T value,BussinessType bt);
	/**
	 * Set方法.不过期
	 */
	<T> void set(String key, T value,BussinessType bt);

	/**
	 * Get方法.	 
	 */
	<T> T get(String key,BussinessType bt);
	
	/**
	 * Delete方法.	 
	 */
	void delete(String key,BussinessType bt);
}
