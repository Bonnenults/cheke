package com.autozi.common.cache.jedis.utils;

import com.autozi.common.cache.jedis.constant.RedisConstant;
import com.autozi.common.cache.jedis.vo.RedisMachineVo;
import com.autozi.common.utils.util2.PropertyUtil;
import org.apache.commons.lang.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Redis工具包
 * 
 * @author Lee
 *
 */
public class RedisCommonUtil {

	/**
	 *  "写入" redis服务器集群<br> 
	 *  redis.properties中
	 *  
	 * @param propertyFile redis配置文件名称 x.properties
	 * @param maxRedises 最大台数
	 * @return 主机地址集合
	 */
	public static List<RedisMachineVo> getWriteRedisHostAndPort(String propertyFile, int maxRedises){
		List<RedisMachineVo> redisVoList = new ArrayList<RedisMachineVo>();
		for(int i=1;i<=maxRedises;i++){
			RedisMachineVo redisVo = new RedisMachineVo();
			// TODO 读取配置文件有问题
			String hostName = PropertyUtil.getStrValue(propertyFile, "whostName"+i, "");
			String port = PropertyUtil.getStrValue(propertyFile, "wport"+i, "6379");
			// 如果查询不到hostName 或者 port 跳过
			if(StringUtils.isNotBlank(hostName) && StringUtils.isNotBlank(port)){
				redisVo.setHostName(hostName);
				redisVo.setPort(Integer.parseInt(port));
				redisVoList.add(redisVo);
			}
		}
		if(redisVoList != null && redisVoList.size() > 0){
			return redisVoList;
		}else{
			return null;
		}
	}
	
	/**
	 *  "读取" redis服务器集群<br> 
	 *  redis.properties中
	 *  
	 * @param propertyFile redis配置文件名称 x.properties
	 * @param maxRedises 最大台数
	 * @return 主机地址集合
	 */
	public static List<RedisMachineVo> getReadRedisHostAndPort(String propertyFile, int maxRedises){
		List<RedisMachineVo> redisVoList = new ArrayList<RedisMachineVo>();
		for(int i=1;i<=maxRedises;i++){
			RedisMachineVo redisVo = new RedisMachineVo();
			// TODO 读取配置文件有问题
			String hostName = PropertyUtil.getStrValue(propertyFile, "rhostName"+i, "");
			String port = PropertyUtil.getStrValue(propertyFile, "rport"+i, "6379");
			// 如果查询不到hostName 或者 port 跳过
			if(StringUtils.isNotBlank(hostName) && StringUtils.isNotBlank(port)){
				redisVo.setHostName(hostName);
				redisVo.setPort(Integer.parseInt(port));
				redisVoList.add(redisVo);
			}
		}
		if(redisVoList != null && redisVoList.size() > 0){
			return redisVoList;
		}else{
			return null;
		}
	}
	
	/**
	 * 将Map<String, Object> 转换成 Map<byte, byte>类型数据
	 * @param oMap
	 * @return
	 */
	public static Map<byte[], byte[]> getByteMapToConvertObjectMap(Map<String, Object> oMap){
		Map<byte[], byte[]> bMap = new HashMap<byte[], byte[]>();
		for(Map.Entry<String, Object> entryMap : oMap.entrySet()){
			byte[] byteArray = getByteArrayByObject(entryMap.getValue());
			bMap.put(entryMap.getKey().getBytes(), byteArray);
		}
		return bMap;
	}
	
	/**
	 * 将Map<byte[], byte[]> 转换成 Map<String, Object>
	 * @param bMap
	 * @return
	 */
	public static Map<String, Object> getObjectMapToConvertByteMap(Map<byte[], byte[]> bMap){
		Map<String, Object> oMap = new HashMap<String, Object>();
		for(Map.Entry<byte[], byte[]> entryMap : bMap.entrySet()){
			Object value = getObjectByByte(entryMap.getValue());
			String key = getStringByByte(entryMap.getKey());
			oMap.put(key, value);
		}
		return oMap;
	}
	
	
	/**
	 * byte数据类型装换成String类型
	 * @param bs
	 * @return
	 */
	public static String getStringByByte(byte[] bs){
		String str = new String(bs);
		return str;
	}
	
	/**
	 * 将Object类型转换成ByteArray数组类型
	 * 
	 * @param object
	 * @return
	 */
	public static byte[] getByteArrayByObject(Object object){
			ByteArrayOutputStream baos = null;
			ObjectOutputStream oos = null;
			byte[] byteArray = null;
			try {
				baos = new ByteArrayOutputStream();
				oos = new ObjectOutputStream(baos);
				oos.writeObject(object);
				byteArray = baos.toByteArray();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (oos != null) {
						oos.close();
						if (baos != null) {
							baos.close();
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}finally{
					if(baos != null){
						try {
							baos.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
			return byteArray;
	}
	
	/**
	 * 将字节数组byte转换成Object
	 * @param bs
	 * @return
	 */
	public static Object getObjectByByte(byte[] bs){
		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;
		Object readObject = null;
		try {
			if (bs == null) { return null; }
			
			// 封装字节流流到object中
			bais = new ByteArrayInputStream(bs);
			ois = new ObjectInputStream(bais);
			readObject = ois.readObject();
			
			if(readObject == null){ return null; }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ois != null) {
					ois.close();
					if (bais != null) {
						bais.close();
					}
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}finally{
				if(bais != null){
					try {
						bais.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return readObject;
	}
	
	public static List<RedisMachineVo> getWriteRedisVo(){
		List<RedisMachineVo> redis = RedisCommonUtil.getWriteRedisHostAndPort(
	            RedisConstant.REDIS_PROPERTIES,
	            RedisConstant.REDIS_MACHINE_COUNT);
		return redis;
	}
	public static List<RedisMachineVo> getReadRedisVo(){
		List<RedisMachineVo> redis = RedisCommonUtil.getReadRedisHostAndPort(
				RedisConstant.REDIS_PROPERTIES,
				RedisConstant.REDIS_MACHINE_COUNT);
		return redis;
	}
}
