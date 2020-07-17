package com.autozi.common.cache.jedis.proxy;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autozi.common.cache.jedis.client.RedisClientTemplete;


/**
 * Redis公共调用
 * 
 * @author Lee
 *
 */
@Component
public class RedisProxy {
	@Autowired
	private RedisClientTemplete redisClientTemplete;
	
	/**
	 * 删除 string类型的数据
	 * @param key
	 * @return 1(删除成功)/ 0(删除失败)
	 */
	public Long deleteStr(String key){
		Long state = redisClientTemplete.delString(key);
		return state;
	}

	/**
	 * 删除"对象"以及"其他类型的数据
	 * 
	 * @param key
	 * @return 1(删除成功)/ 0(删除失败)
	 */
	public Long deleteObj(String key){
		Long state = redisClientTemplete.delObject(key);
		return state;
	}
	
	
	/**
	 * 获取"对象类型"(Object.class) 数据(泛型已处理)
	 * @param key
	 * @return 
	 */
	public <T> T getObjectClsValue(String key){
		T object = (T) redisClientTemplete.getObjClazz(key);
		return object;
	}
	
	/**
	 * 获取"String类型" 数据
	 * @param key
	 * @return
	 */
	public String getStrValue(String key){
		String string = redisClientTemplete.getString(key);
		return string;
	}
	
	/**
	 * 获取"Map<String,Object>" 数据
	 * @param key
	 * @return
	 */
	public Map<String, Object> getObjMap(String key){
		Map<String, Object> oMap = redisClientTemplete.getAllObjMap(key);
		return oMap;
	}
	
	/**
	 * 获取"Map<String, String>" 数据
	 * @param key
	 * @return
	 */
	public Map<String, String> getStrMap(String key){
		Map<String, String> stringMap = redisClientTemplete.getStrMap(key);
		return stringMap;
	}
	
	/**
	 * 获取map<String, String>指定的hash field 
	 * 
	 * @param key
	 * @param feild
	 * @return
	 */
	public String hgetStr(String key, String feild){
		String hgetStr = redisClientTemplete.hgetStr(key, feild);
		return hgetStr;
	}
	
	/**
	 * 获取map<String, T>指定的hash field 
	 * 
	 * @param key
	 * @param feild
	 * @return
	 */
	public <T> Object hgetObj(String key, String feild, T t){
		T tObj = (T) redisClientTemplete.hgetObj(key, feild);
		return tObj;
	}
	
	/**
	 * 获取map<String, Objcet>指定的hash field 
	 * 
	 * @param key
	 * @param feild
	 * @return
	 */
	public Object hgetObj(String key, String feild){
		Object object = redisClientTemplete.hgetObj(key, feild);
		return object;
	}

	/**
	 * 根据key，field 删除指定的元素
	 * @param key
	 * @param field
	 * @return
	 */
	public Object hdelObj(String key, String field){
		Object object = redisClientTemplete.hdelObj(key, field);
		return object;
	}

	/**
	 * 获取指定list<T>中的元素(可分页)
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public <T> List<T> getTListOnPage(String key, Long start, Long end){
		List<T> oList = (List<T>) redisClientTemplete.getListObjClz(key, start, end);
		return oList;
	}
	
	/**
	 * 获取指定list<Object>中的元素(可分页)
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public<T> List<Object> getObjListOnPage(String key, Long start, Long end){
		List<Object> oList = redisClientTemplete.getListObjClz(key, start, end);
		return oList;
	}

	/**
	 * 获取指定list<String> 中的元素(可分页)
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public List<String> getStrListOnPage(String key, Long start, Long end){
		List<String> sList = redisClientTemplete.getListStr(key, start, end);
		return sList;
	}
	
	/**
	 * 保存"String"类型数据
	 * 
	 * @param key
	 * @param value
	 * @return OK(保存成功)/NO(保存失败)
	 */
	public String saveStrValue(String key, String value){
		String saveState = redisClientTemplete.setString(key, value);
		return saveState;
	}
	
	/**
	 * 保存"Object"数据类型
	 * 
	 * @param key
	 * @param value
	 * @return OK(保存成功)/NO(保存失败)
	 */
	public String saveObjValue(String key, Object value){
		String saveState = redisClientTemplete.setObjClazz(key, value);
		return saveState;
	}
	
	/**
	 * 保存"Map<String,Object>"类型数据
	 * 
	 * @param key
	 * @param value
	 * @param t
	 * @return OK(保存成功)/NO(保存失败)
	 */
	public <T> String saveObjMap(String key, Map<String, Object> value, T t){
		String saveState = redisClientTemplete.setHMapObj(key, value, t.getClass());
		return saveState;
	}

	/**
	 * 根据key,field存储对象，适应于Map<String,Obeject>
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public Long setMapObject(String key,String field,Object value){
		return redisClientTemplete.setMapObject(key,field,value);
	}

	/**
	 * 根据key,field存储对象，适应于Map<String,Obeject>
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public Long delMapObject(String key,String field){
		return redisClientTemplete.delMapObject(key,field);
	}

	/**
	 * 保存"Map<String, String>"类型的数据
	 * 
	 * @param key
	 * @param value
	 * @return OK(保存成功)/NO(保存失败)
	 */
	public String saveStrMap(String key, Map<String, String> value){
		String saveState = redisClientTemplete.setHMapStr(key, value);
		return saveState;
	}
	
	
	/**
	 * 向redis 中 指定的list<Object>数据的 "尾部" 添加一条数据
	 * 
	 * @param key
	 * @param value
	 * @return 正常返回长度/ 错误返回-1
	 */
	public Long saveListObjLinsertOrLpush(String key, Object value){
		Long saveState = redisClientTemplete.saveObjLinsertOrLpush(key, value);
		return saveState;
	}

	/**
	 * 向名称为key的zset中添加元素member，score用于排序。如果该元素已经存在，则根据score更新该元素的顺序。
	 *
	 * @param key
	 * @param value
	 * @param score
	 * @return 正常返回长度/ 错误返回-1
	 */
	public long zadd(String key, String value, double score) {
		return redisClientTemplete.zadd(key, score, value);
	}

	/**
	 * 向名称为key的zset中添加元素member，score用于排序。如果该元素已经存在，则根据score更新该元素的顺序。
	 *(限制数量)
	 * @param key
	 * @param value
	 * @param score
	 * @return 正常返回长度/ 错误返回-1
	 */
	public long zadd(String key, String value, double score, long maxVolume) {
		return redisClientTemplete.zadd(key, score, value,maxVolume);
	}

	/**
	 * 返回名称为key的zset（元素已按score从小到大排序）中的index从start到end的所有元素
	 *
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public Set<String> zrange(String key, Long start, Long end) {
		return redisClientTemplete.zrange(key, start, end);
	}
	
	/**
	 * 向redis 中 指定的list<String>数据的 "尾部" 添加一条数据
	 * 
	 * @param key
	 * @param value
	 * @return 正常返回长度/ 错误返回-1
	 */
	public Long saveListStrLinsertOrLpush(String key, String value){
		Long saveState = redisClientTemplete.saveStrLinsertOrLpush(key, value);
		return saveState;
	}
	
	 /**
     * 向Redis__中__"List<String>"数据尾部插入String[]数组数据
     *
     * @param key
     * @param object
     * @return 正常返回长度/ 错误返回-1
     */
	public Long saveListStrLinsertOrLpush(String key, String[] values){
		Long saveState = redisClientTemplete.saveStrLinsertOrLpush(key, values);
		return saveState;
	}
	
	/**
	 * 对指定Map<String, String> 中的指定feild修改, 若没有就新增一个
	 * 
	 * @param key
	 * @param mKey
	 * @param value
	 * @return
	 */
	public Long updateMapStr(String key, String mKey, String value){
		Long save = redisClientTemplete.setHSetStr(key, mKey, value);
		return save;
	}
	
	/**
	 * 获取指定list<Object>的长度
	 * 
	 * @param key
	 * @return
	 */
	public Long getListObjLength(String key){
		return redisClientTemplete.llenObject(key);
	}
	
	/**
	 * 获取指定list<String>的长度
	 * 
	 * @param key
	 * @return
	 */
	public Long getListStrLength(String key){
		return redisClientTemplete.llenStr(key);
	}
	
	/**
	 * 获取指定Map<String,String> 的长度
	 * @param key
	 * @return
	 */
	public Long getMapStrLength(String key){
		return redisClientTemplete.hlenStr(key);
	}
	
	/**
	 * 判断key中是否含有值
	 * 
	 * @param key
	 * @return
	 */
	public Boolean exists(String key){
		return redisClientTemplete.exists(key);
	}
	
	/**
	 * 判断key中是否含有field域的值
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public Boolean hexists(String key, String field){
		return redisClientTemplete.hexists(key, field);
	}
}
