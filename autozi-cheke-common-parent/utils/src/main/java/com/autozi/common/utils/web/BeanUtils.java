package com.autozi.common.utils.web;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * 由于spring升级后,copyProperties方法实现逻辑有调整，导致现有程序里复制属性时会出现丢失属性值的问题，因此用PropertyUtils替代
 * 此类用于适配原有使用spring的copyProperties的方法，其功能与apache的PropertyUtils.copyProperties完全一致
 * @author Administrator
 */
public class BeanUtils {
	/**
	 * 复制属性，将source的属性复制到target
	 * @param source
	 * @param target
	 * @throws Exception
	 */
	public static void copyProperties(Object source, Object target) {
		try {
			PropertyUtils.copyProperties(target, source);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
