package com.autozi.common.dal.entity;

import com.autozi.common.core.utils.ApplicationContextProvider;
import com.autozi.common.dal.mybatis.MyBatisDao;


public final class Id4Entity {
	
	@SuppressWarnings("unchecked")
	public static <T> T get(Class<T> zlass, Long id) throws Exception{
		String beanName=getBeanName(zlass);
		MyBatisDao<IdEntity> dao=ApplicationContextProvider.getBean(beanName);
		if(dao!=null){
			return 	(T) dao.get(id);
		}
	
		throw new Exception("没有对应的DAO");
	}

	private static <T> String getBeanName(Class<T> zlass) {
		return zlass.getSimpleName().substring(0, 1).toLowerCase()
				+ zlass.getSimpleName().substring(1) + "Dao";

	}
	
	public static void main(String[] args){
		String beanName=Id4Entity.getBeanName(String.class);
		System.out.println(beanName);
	}
}
