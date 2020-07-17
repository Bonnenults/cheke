//package com.autozi.common.utils.util3;
//
//import java.lang.reflect.Constructor;
//import java.util.ArrayList;
//import java.util.List;
//
//import com.autozi.common.dal.entity.IdEntity;
//
//public class WrapperUtil {
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	public static List wrap(List list, Class<?> wrapperClass) {
//		List res = new ArrayList();
//		try {
//			Constructor m = wrapperClass.getConstructor(new Class[]{IdEntity.class});
//			for(Object model:list) {
//				res.add(m.newInstance(model));
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		}
//		
//		return res;
//		
//	}
//	
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	public static Object wrap(IdEntity obj, Class wrapperClass) {
//		try {
//			Constructor m = wrapperClass.getConstructor(new Class[]{IdEntity.class});
//			return m.newInstance(obj);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		}
//		
//	}
//}
