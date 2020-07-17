package com.autozi.common.utils.util2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.autozi.common.utils.util1.ReflectionUtils;
import com.autozi.common.utils.util1.StringUtils;

public class CollectionUtil {

	public static String[] letters={"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	
	
	/**
	 * 
	 * @param t 一个需要按字符聚合的集合
	 * @param property 根据哪个字段比较,逗号分隔，如果为null就找下一个属性是否有值
	 * @return 一个字母：一个对象 
	 */
	public static <T> Map<String,List<T>> make(List<T> t,String propertys){
		CollectionUtil u = new CollectionUtil();
		LinkedHashMap<String, List<T>> result = u.firstResult();
		//循环列表
		Iterator<T>  t_ = t.iterator();
		while(t_.hasNext()){
			T n = t_.next();
			u.eachOne(propertys, result, n);
		}
		
		
		return result;
		
	}
	private <T> void eachOne(String propertys,LinkedHashMap<String, List<T>> result, T n) {
		Object o = eachProperty(n,propertys);
		if(o instanceof String && !StringUtils.isEmpty(o)){
			String f = StringUtils.getFirstLetter(o.toString());
			f = toList(result, n, f);
			if(null!=f){
				result.get("other").add(n);
			}
		}
	}
	private <T> Object eachProperty(T n,String propertys) {
		String [] p = propertys.split(",");
		for(int i=0;i<p.length;i++){
			Object o = ReflectionUtils.invokeGetterMethod(n, p[i]);
			if(null!=o) return o;
		}
		return null;
	}
	private <T> String toList(LinkedHashMap<String, List<T>> result, T n,String f) {
		for (Entry<String, List<T>> entry : result.entrySet()) {
			String k = entry.getKey();
			if(k.equalsIgnoreCase(f)){
				entry.getValue().add(n);
				f = null;
			}
		}
		return f;
	}
	private <T> LinkedHashMap<String, List<T>> firstResult() {
		LinkedHashMap<String,List<T>> result = new LinkedHashMap<String,List<T>>();
		for (String letter : letters) {
			if(!result.containsKey(letter)){
				result.put(letter, new LinkedList<T>());
			}
		}
		result.put("other", new LinkedList<T>());
		return result;
	}
	
	public static Map<String,Object> mapUpperCase(Map<String, Object> object){
		Map<String, Object> newMap=new HashMap<String, Object>();
		for (String key : object.keySet()) {
			newMap.put(key.toUpperCase(), object.get(key));
		}
		return newMap;
	}
	public static Map<Object,Object> mapUpperCaseOne(Map<Object, Object> object){
		Map<Object, Object> newMap=new HashMap<Object, Object>();
		for (Object key : object.keySet()) {
			newMap.put(key.toString().toUpperCase(), object.get(key));
		}
		return newMap;
	}
	public static List<Map<String,Object>> mapUpperCaseList(List<Map<String, Object>> object){
		List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>() ;
		for(Map<String, Object> map:object) {
			Map<String, Object> newMap = new HashMap<String, Object>();
			for (String key : map.keySet()) {
				newMap.put(key.toUpperCase(), map.get(key));
			}
			listMap.add(newMap);
		}
		return listMap;
	}
	public static void main(String[] args) {
		List<Integer> list=new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);
		
		List<List<?>> result=CollectionUtil.splitList(list,6);
		for (List<?> list2 : result) {
			for (Object object : list2) {
				System.out.println(object);
			}
			System.out.println("------------------------");
		}
	}
	/**
	 * 将传入集合list拆分为多个，拆分后每个集合大小为lenth
	 * @param list
	 * @param len
	 * @return
	 */
	public static List<List<?>> splitList(List<?> list, int lenth){
		if (list == null || list.size() == 0 || lenth < 1) {
			return null;
		}
		List<List<?>> result = new ArrayList<List<?>>();
		int size = list.size();
		int count = (size + lenth - 1) / lenth;
		for (int i = 0; i < count; i++) {
			List<?> subList = list.subList(i * lenth, ((i + 1) * lenth > size ? size : lenth * (i + 1)));
			result.add(subList);
		}
		return result;
	}
}
