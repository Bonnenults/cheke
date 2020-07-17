package com.autozi.common.dal.mapper;

import java.util.List;
import java.util.Map;

import com.autozi.common.core.page.Page;
import com.autozi.common.dal.entity.IdEntity;

public interface BaseReadMapper<T extends IdEntity> {
	
	T get(Long id);
	
	List<T> findListForExample(T entity);
	
	/**
	 * 列表查询方法（启用缓存）
	 * @param map
	 * @return
	 */
	List<T> findListForMap(Map<String,Object> map);

	/**
	 * 列表查询方法（不启用缓存）
	 * @param map
	 * @param useCache
	 * @return
	 */
	List<T> findListForMapWithoutCache(Map<String,Object> map);

	/**
	 * 使用findListForExample(T entity)对应的sql,在MyBatisDao内实现
	 * @param page
	 * @param entity
	 * @return
	 */
	Page<T> findPageForExample(Page<T> page,T entity);
	/**
	 * 使用findListForMap(T entity)对应的sql,在MyBatisDao内实现
	 * @param page
	 * @param map
	 * @return
	 */
	Page<T> findPageForMap(Page<T> page,Map<String,Object> map);
	/**
	 * 使用findListForMap(T entity)对应的sql,在MyBatisDao内实现，
	 * 此方法不使用缓存，仅用于需要即时刷新的场合，功能等同于上面findPageForExample
	 * @param page
	 * @param map
	 * @return
	 */
	Page<T> findPageForMapWithoutCache(Page<T> page,Map<String,Object> map);

}
