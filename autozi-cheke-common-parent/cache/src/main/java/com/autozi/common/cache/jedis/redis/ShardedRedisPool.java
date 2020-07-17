package com.autozi.common.cache.jedis.redis;

import com.autozi.common.cache.jedis.vo.RedisMachineVo;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.jedis.exceptions.JedisException;

import java.util.ArrayList;
import java.util.List;

/**
 * 集群下redis连接池
 * 
 * @author Lee
 *
 */
//@Repository
public class ShardedRedisPool {
	
	
	/**
	 * 获取集群下的连接池
	 * 
	 * @return
	 */
	public ShardedJedisPool getShardedJedisPool(List<RedisMachineVo> redisMachine){
		GenericObjectPoolConfig objectPoolConfig = new JedisPoolConfig();

		//分片服务器信息
		ArrayList<JedisShardInfo> shardInfoList = new ArrayList<JedisShardInfo>();
		for(RedisMachineVo redisVo: redisMachine){
			int timeOut = 5000; // 设置超时时间 5 秒钟(基数毫秒)
			JedisShardInfo shardInfo = new JedisShardInfo(redisVo.getHostName(), redisVo.getPort(), timeOut);
			if(shardInfo != null) {shardInfoList.add(shardInfo);}
		}
		
		//拿到一个分片集群的客户端连接池
		ShardedJedisPool shardedJedisPool = new ShardedJedisPool(objectPoolConfig, shardInfoList);
		return shardedJedisPool;
	}
	
	/**
	 * 连接池中获取一个连接(一般不用)
	 * @param shardedJedisPool 连接池
	 * @return
	 */
	public ShardedJedis getJedis(ShardedJedisPool shardedJedisPool){
		//从连接池中获取到一个连接 
		if(shardedJedisPool == null){
			return null;
		}
		ShardedJedis redis = shardedJedisPool.getResource();
		return redis;
	}
	
	/**
	 * 返还集群连接池
	 */
	public void returnRedisPool(ShardedJedisPool shardedJedisPool, ShardedJedis redis){
		if(redis != null && shardedJedisPool != null){
			shardedJedisPool.returnResource(redis);
		}
	}
	
	/**
	 * Handle jedisException, write log and return whether the connection is broken.
	 */
	protected boolean handleJedisException(JedisException jedisException) {
		if (jedisException instanceof JedisConnectionException) {
			//	logger.error("Redis connection " + jedisPool.getAddress() + " lost.", jedisException);
		} else if (jedisException instanceof JedisDataException) {
			if ((jedisException.getMessage() != null) && (jedisException.getMessage().indexOf("READONLY") != -1)) {
				//logger.error("Redis connection " + jedisPool.getAddress() + " are read-only slave.", jedisException);
			} else {
				// dataException, isBroken=false
				return false;
			}
		} else {
			//logger.error("Jedis exception happen.", jedisException);
		}
		return true;
	}
	
	
	
}
