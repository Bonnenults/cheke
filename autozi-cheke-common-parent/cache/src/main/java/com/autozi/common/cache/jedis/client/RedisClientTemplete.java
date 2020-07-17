package com.autozi.common.cache.jedis.client;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import com.autozi.common.cache.jedis.redis.ShardedRedisPool;
import com.autozi.common.cache.jedis.redis.ShardedRedisStored;
import com.autozi.common.cache.jedis.utils.RedisCommonUtil;

/**
 * 只有RedisProxy能够调用此类方法(外部使用者不准许调用) <br>
 * <p/>
 * 存储过程, "读/写"Redis服务器 "必须" 分离使用 <br>
 *
 * @author Lee
 */
@Repository
public class RedisClientTemplete {

    @Autowired
    private ShardedRedisStored shardedRedisStored;
    
    
    private static ShardedRedisPool redisPool;
    private static ShardedJedisPool _writeRedisPool;
    private static ShardedJedisPool _readRedisPool;

    /**
     * ******************* <br/>
     * *	单例模块 S    * <br/>
     * ******************* <br/>
     */
    private static RedisClientTemplete redisClientTemplete ;
    
    public RedisClientTemplete() { }
    
    public synchronized static RedisClientTemplete getClientTemplete(){
    	if(redisClientTemplete == null){
    		redisClientTemplete = new RedisClientTemplete();
    	}
   		return redisClientTemplete;
    }
    
    public synchronized static ShardedRedisPool _getRedisPool(){
    	if(redisPool == null){
    		redisPool = new ShardedRedisPool();
    	}
    	return redisPool;
    }

    /**
     * ******************* <br/>
     * *	单例模块 E    * <br/>
     * ******************* <br/>
     */
    
    
	/**
     * "写入操作"Redis服务器 连接池
     *
     * @return
     */
    public ShardedJedisPool getWriteRedisPool(){
    	redisPool = _getRedisPool();
    	if(_writeRedisPool == null){
    		_writeRedisPool = redisPool.getShardedJedisPool(RedisCommonUtil.getWriteRedisVo());
    	}
    	return _writeRedisPool;
    }
    
    /**
     * "读操作"Redis服务器 连接池
     *
     * @return
     */
    public ShardedJedisPool getReadRedisPool(){
    	redisPool = _getRedisPool();
    	if(_readRedisPool == null){
    		_readRedisPool = redisPool.getShardedJedisPool(RedisCommonUtil.getReadRedisVo());
    	}
    	return _readRedisPool;
    }
    
    /**
     * 返还连接 给 连接池
     *
     * @param redisPool
     * @param redis
     */
    public void returnRedisResource(ShardedJedisPool redisPool, ShardedJedis redis) {
        if (redis != null && redisPool != null) {
            redisPool.returnResource(redis);
        } else if (redisPool == null) {
            redis.close();
        }
    }

    /**
     * 获取一个连接(不建议用此方法)
     *
     * @return
     */
    public ShardedJedis getRedis(ShardedJedisPool redisPool) {
        ShardedJedis redis = redisPool.getResource();// 获取一个redis连接
        return redis;
    }

    /**
     * 存储 "对象类型" 的数据到redis
     *
     * @param key
     * @param value
     * @return ok(存储成功)/no(存储失败)
     */
    public String setObjClazz(String key, Object value) {
        ShardedJedisPool jedisPool = null;
        ShardedJedis redis = null;
        String saveState = null;
        try {
            jedisPool = getWriteRedisPool();
            redis = jedisPool.getResource();// 获取一个redis连接
            saveState = shardedRedisStored.setObject(key, value, redis);
        } catch (Exception e) {
            e.printStackTrace();
            saveState = "No";
        } finally {
            returnRedisResource(jedisPool, redis);// 返还连接给连接池
        }
        return saveState;
    }

    /**
     * 存储 "String类型" 的数据到Redis
     *
     * @param key
     * @param value
     * @return ok(存储成功)/no(存储失败)
     */
    public String setString(String key, String value) {
        ShardedJedisPool jedisPool = null;
        ShardedJedis redis = null;
        String saveState = null;
        try {
            jedisPool = getWriteRedisPool();
            redis = jedisPool.getResource();// 获取一个redis连接
            saveState = shardedRedisStored.setString(key, value, redis);
        } catch (Exception e) {
            e.printStackTrace();
            saveState = "No";
        } finally {
            returnRedisResource(jedisPool, redis);// 返还连接给连接池
        }
        return saveState;
    }

    /**
     * 存储Map<String, Object>类型的数据到redis  <br>
     * 实质通过 Map<byte, byte> 存储
     *
     * @param key
     * @param value
     * @param t     类的泛型
     * @return
     */
    public <T> String setHMapObj(String key, Map<String, Object> value, T t) {
        ShardedJedisPool jedisPool = null;
        ShardedJedis redis = null;
        String saveState = null;
        try {
            jedisPool = getWriteRedisPool();
            redis = jedisPool.getResource();// 获取一个redis连接
            saveState = shardedRedisStored.hmsetObject(key, value, redis);
        } catch (Exception e) {
            e.printStackTrace();
            saveState = "NO";
        } finally {
            returnRedisResource(jedisPool, redis);// 返还连接给连接池
        }
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
        ShardedJedisPool jedisPool = null;
        ShardedJedis redis = null;
        Long saveState = -1L;
        try {
            jedisPool = getWriteRedisPool();
            redis = jedisPool.getResource();// 获取一个redis连接
            saveState = shardedRedisStored.setMapObject(key, field, value, redis);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            returnRedisResource(jedisPool,redis);
        }
        return saveState;
    }

    /**
     * 根据key,field删除对象，适应于Map<String,Obeject>
     * @param key
     * @param field
     * @return
     */
    public Long delMapObject(String key,String field){
        ShardedJedisPool jedisPool = null;
        ShardedJedis redis = null;
        Long saveState = -1L;
        try {
            jedisPool = getWriteRedisPool();
            redis = jedisPool.getResource();// 获取一个redis连接
            saveState = shardedRedisStored.delMapObject(key, field, redis);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            returnRedisResource(jedisPool,redis);
        }
        return saveState;
    }

    /**
     * 存储 "Map<String, String>类型" 的数据到Redis
     *
     * @param key
     * @param value
     * @return
     */
    public String setHMapStr(String key, Map<String, String> value) {
        ShardedJedisPool jedisPool = null;
        ShardedJedis redis = null;
        String saveState = null;
        try {
            jedisPool = getWriteRedisPool();
            redis = jedisPool.getResource();// 获取一个redis连接
            saveState = shardedRedisStored.hmsetStr(key, value, redis);
        } catch (Exception e) {
            e.printStackTrace();
            saveState = "NO";
        } finally {
            returnRedisResource(jedisPool, redis);// 返还连接给连接池
        }
        return saveState;
    }

    /**
     * 存储"zset<String>"
     *
     * @param key    键
     * @param score  排序字段
     * @param value值
     * @return 正常返回长度/失败-1
     */
    public long zadd(String key, double score, String value) {
        ShardedJedisPool jedisPool = null;
        ShardedJedis redis = null;
        long saveState = -1;
        try {
            jedisPool = getWriteRedisPool();
            redis = jedisPool.getResource();// 获取一个redis连接
            saveState = shardedRedisStored.zadd(key, value, score, redis);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnRedisResource(jedisPool, redis);
        }
        return saveState;
    }

    /**
     * 存储"zset<String>"(限制数量)
     *
     * @param key    键
     * @param score  排序字段
     * @param value值
     * @return 正常返回长度/失败-1
     */
    public long zadd(String key, double score, String value, long maxVolume) {
        ShardedJedisPool jedisPool = null;
        ShardedJedis redis = null;
        long saveState = -1;
        try {
            jedisPool = getWriteRedisPool();
            redis = jedisPool.getResource();// 获取一个redis连接
            saveState = shardedRedisStored.zadd(key, value, score,maxVolume, redis);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnRedisResource(jedisPool, redis);
        }
        return saveState;
    }

    /**
     * 获取指定key的Set  默认score从小到大
     *
     * @param key
     * @return
     */
    public Set<String> zrange(String key, Long start, Long end) {
        ShardedJedisPool jedisPool = null;
        ShardedJedis redis = null;
        Set<String> set = null;
        try {
            jedisPool = getReadRedisPool();
            redis = jedisPool.getResource();// 获取一个redis连接
            set = shardedRedisStored.zrange(key, start, end, redis);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnRedisResource(jedisPool, redis);
        }
        return set;
    }

    /**
     * 对指定Map<String, String> 中的指定feild修改, 若没有就新增一个
     *
     * @param key
     * @param mKey
     * @param value
     * @return
     */
    public Long setHSetStr(String key, String mKey, String value) {
        ShardedJedisPool jedisPool = null;
        ShardedJedis redis = null;
        Long saveState = -1L;
        try {
            jedisPool = getWriteRedisPool();
            redis = jedisPool.getResource();// 获取一个redis连接
            saveState = shardedRedisStored.hsetStr(key, mKey, value, redis);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnRedisResource(jedisPool, redis);// 返还连接给连接池
        }
        return saveState;

    }

    /**
     * 向Redis__中__"List<Object>"数据尾部插入一条数据
     *
     * @param key
     * @param String
     * @return 正常返回长度/ 错误返回-1
     */
    public Long saveObjLinsertOrLpush(String key, Object value) {
        ShardedJedisPool jedisPool = null;
        ShardedJedis redis = null;
        Long saveState = -1L;
        try {
            jedisPool = getWriteRedisPool();
            redis = jedisPool.getResource();// 获取一个redis连接
            saveState = shardedRedisStored.saveObjLinsertOrLpush(key, value, redis);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnRedisResource(jedisPool, redis);// 返还连接给连接池
        }
        return saveState;
    }

    /**
     * 向Redis__中__"List<String>"数据尾部插入一条数据
     *
     * @param key
     * @param object
     * @return 正常返回长度/ 错误返回-1
     */
    public Long saveStrLinsertOrLpush(String key, String value) {
        ShardedJedisPool jedisPool = null;
        ShardedJedis redis = null;
        Long saveState = -1L;
        try {
            jedisPool = getWriteRedisPool();
            redis = jedisPool.getResource();// 获取一个redis连接
            saveState = shardedRedisStored.saveStrLinsertOrLpush(key, value, redis);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnRedisResource(jedisPool, redis);// 返还连接给连接池
        }
        return saveState;
    }
    
    /**
     * 向Redis__中__"List<String>"数据尾部插入String[]数组数据
     *
     * @param key
     * @param object
     * @return 正常返回长度/ 错误返回-1
     */
    public Long saveStrLinsertOrLpush(String key, String[] values) {
    	ShardedJedisPool jedisPool = null;
    	ShardedJedis redis = null;
    	Long saveState = -1L;
    	try {
    		jedisPool = getWriteRedisPool();
    		redis = jedisPool.getResource();// 获取一个redis连接
    		saveState = shardedRedisStored.saveStrLinsertOrLpush(key, values, redis);
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		returnRedisResource(jedisPool, redis);// 返还连接给连接池
    	}
    	return saveState;
    }


    /**
     * 取指定List<String>集合中数据集(可分页)
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<String> getListStr(String key, Long start, Long end) {
        ShardedJedisPool jedisPool = null;
        ShardedJedis redis = null;
        List<String> sList = null;
        try {
            jedisPool = getReadRedisPool();
            redis = jedisPool.getResource();// 获取一个redis连接
            sList = shardedRedisStored.lrangeString(key, redis, start, end);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnRedisResource(jedisPool, redis);// 返还连接给连接池
        }
        return sList;
    }

    /**
     * 取指定List<Object>集合中数据集(可分页)
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<Object> getListObjClz(String key, Long start, Long end) {
        ShardedJedisPool jedisPool = null;
        ShardedJedis redis = null;
        List<Object> oList = null;
        try {
            jedisPool = getReadRedisPool();
            redis = jedisPool.getResource();// 获取一个redis连接
            oList = shardedRedisStored.lrangeObject(key, redis, start, end);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnRedisResource(jedisPool, redis);// 返还连接给连接池
        }
        return oList;
    }

    /**
     * 获取Map<String, Object>类型的数据  <br>
     * 实质通过Map<byte,byte>查询 <br>
     * 查询出来的数据需要由用户自行强制转换
     *
     * @param key
     * @return
     */
    public Map<String, Object> getAllObjMap(String key) {
        ShardedJedisPool jedisPool = null;
        ShardedJedis redis = null;
        Map<String, Object> oMap = null;
        if (key != null) {
            try {
                jedisPool = getReadRedisPool();
                redis = jedisPool.getResource();// 获取一个redis连接
                oMap = shardedRedisStored.hgetAllObject(key, redis);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                returnRedisResource(jedisPool, redis);// 返还连接给连接池
            }
            return oMap;
        } else {
            return null;
        }
    }

    /**
     * 获取 "Map<String,String>对象类型" 的数据<br>
     *
     * @param key
     * @return
     */
    public Map<String, String> getStrMap(String key) {
        ShardedJedisPool jedisPool = null;
        ShardedJedis redis = null;
        Map<String, String> strMap = null;
        try {
            jedisPool = getReadRedisPool();
            redis = jedisPool.getResource();// 获取一个redis连接
            strMap = shardedRedisStored.hgetAllString(key, redis);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnRedisResource(jedisPool, redis);// 返还连接给连接池
        }
        return strMap;
    }

    /**
     * 获取Map<String, String>指定的hash field
     *
     * @param key
     * @param feild
     * @return
     */
    public String hgetStr(String key, String feild) {
        ShardedJedisPool jedisPool = null;
        ShardedJedis redis = null;
        String strMap = null;
        try {
            jedisPool = getReadRedisPool();
            redis = jedisPool.getResource();// 获取一个redis连接
            strMap = shardedRedisStored.hgetStr(key, feild, redis);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnRedisResource(jedisPool, redis);// 返还连接给连接池
        }
        return strMap;
    }

    /**
     * 获取Map<byte[],byte[]>指定的hash field
     *
     * @param key
     * @param feild
     * @return
     */
    public Object hgetObj(String key, String feild) {
        ShardedJedisPool jedisPool = null;
        ShardedJedis redis = null;
        Object strMap = null;
        try {
            jedisPool = getReadRedisPool();
            redis = jedisPool.getResource();// 获取一个redis连接
            strMap = shardedRedisStored.hgetObj(key, feild, redis);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnRedisResource(jedisPool, redis);// 返还连接给连接池
        }
        return strMap;
    }

    /**
     * 根据key，field 删除指定的元素
     *
     * @param key
     * @param field
     * @return
     */
    public Object hdelObj(String key, String feild) {
        ShardedJedisPool jedisPool = null;
        ShardedJedis redis = null;
        Object strMap = null;
        try {
            jedisPool = getWriteRedisPool();
            redis = jedisPool.getResource();// 获取一个redis连接
            strMap = shardedRedisStored.hdelObj(key, feild, redis);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnRedisResource(jedisPool, redis);// 返还连接给连接池
        }
        return strMap;
    }

    /**
     * 获取 "Object对象类型" 的数据<br>
     * 需要强制转换成对应的数据类型<br>
     *
     * @param key
     * @return object
     */
    public Object getObjClazz(String key) {
        ShardedJedisPool jedisPool = null;
        ShardedJedis redis = null;
        Object object = null;
        try {
            jedisPool = getReadRedisPool();
            redis = jedisPool.getResource();// 获取一个redis连接
            object = shardedRedisStored.getObjectClass(key, redis);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnRedisResource(jedisPool, redis);// 返还连接给连接池
        }
        return object;
    }

    /**
     * 获取 "String类型" 的数据<br>
     *
     * @param key
     * @return
     */
    public String getString(String key) {
        ShardedJedisPool jedisPool = null;
        ShardedJedis redis = null;
        String string = null;
        try {
            jedisPool = getReadRedisPool();
            redis = jedisPool.getResource();// 获取一个redis连接
            string = shardedRedisStored.getString(key, redis);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnRedisResource(jedisPool, redis);// 返还连接给连接池
        }
        return string;
    }

    /**
     * 删除 "对象类型" 数据操作 <br>
     *
     * @param key 主键
     * @return 1(删除成功)/0(删除失败)
     */
    public Long delObject(String key) {
        ShardedJedisPool jedisPool = null;
        ShardedJedis redis = null;
        Long del = 0L;
        try {
            jedisPool = getWriteRedisPool();
            redis = jedisPool.getResource();// 获取一个redis连接
            del = shardedRedisStored.delObject(key, redis);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnRedisResource(jedisPool, redis);// 返还连接给连接池
        }
        return del;

    }

    /**
     * 删除 "String类型" 数据操作 <br>
     *
     * @param key 主键
     * @return 1(删除成功)/0(删除失败)
     */
    public Long delString(String key) {
        ShardedJedisPool jedisPool = null;
        ShardedJedis redis = null;
        Long del = 0L;
        try {
            jedisPool = getWriteRedisPool();
            redis = jedisPool.getResource();// 获取一个redis连接
            del = shardedRedisStored.delString(key, redis);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnRedisResource(jedisPool, redis);// 返还连接给连接池
        }
        return del;
    }

    /**
     * 获取指定list<Object>的长度
     *
     * @param key
     * @return
     */
    public Long llenObject(String key) {
        ShardedJedisPool jedisPool = null;
        ShardedJedis redis = null;
        Long length = 0L;
        try {
            jedisPool = getReadRedisPool();
            redis = jedisPool.getResource();// 获取一个redis连接
            length = shardedRedisStored.llenObject(key, redis);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnRedisResource(jedisPool, redis);// 返还连接给连接池
        }
        return length;
    }

    /**
     * 获取指定list<String>的长度
     *
     * @param key
     * @return
     */
    public Long llenStr(String key) {
        ShardedJedisPool jedisPool = null;
        ShardedJedis redis = null;
        Long len = 0L;
        try {
            jedisPool = getReadRedisPool();
            redis = jedisPool.getResource();// 获取一个redis连接
            len = shardedRedisStored.llenStr(key, redis);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnRedisResource(jedisPool, redis);// 返还连接给连接池
        }
        return len;
    }

    public Long hlenStr(String key) {
        ShardedJedisPool jedisPool = null;
        ShardedJedis redis = null;
        Long len = 0L;
        try {
            jedisPool = getReadRedisPool();
            redis = jedisPool.getResource();// 获取一个redis连接
            len = shardedRedisStored.hlenStr(key, redis);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnRedisResource(jedisPool, redis);// 返还连接给连接池
        }
        return len;
    }

    /**
     * 判断key中是否有值
     *
     * @param key
     * @return
     */
    public Boolean exists(String key) {
        ShardedJedisPool jedisPool = null;
        ShardedJedis redis = null;
        Boolean value = false;
        try {
            jedisPool = getReadRedisPool();
            redis = jedisPool.getResource();// 获取一个redis连接
            value = shardedRedisStored.exists(key, redis);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            returnRedisResource(jedisPool, redis);// 返还连接给连接池
        }
        return value;
    }
    
    /**
     * 判断Map中的是否含有field域的值
     * 
     * @param key
     * @param field
     * @return
     */
    public Boolean hexists(String key, String field){
    	ShardedJedisPool jedisPool = null;
        ShardedJedis redis = null;
        Boolean value = false;
        try {
            jedisPool = getReadRedisPool();
            redis = jedisPool.getResource();// 获取一个redis连接
            value = shardedRedisStored.hexists(key, field, redis);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            returnRedisResource(jedisPool, redis);// 返还连接给连接池
        }
        return value;
    }

}
