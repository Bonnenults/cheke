package com.autozi.common.cache.jedis.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import redis.clients.jedis.ShardedJedis;

import com.autozi.common.cache.jedis.constant.RedisConstant;
import com.autozi.common.cache.jedis.utils.RedisCommonUtil;


/**
 * 集群下redis存储过程
 *
 * @author Lee
 */
@Repository
public class ShardedRedisStored {

    /**
     * 存储 "对象" 到redis中 <br>
     *
     * @param key   存储名称(key)
     * @param value 存储对象(value)
     * @param redis redis对象
     * @return OK(存储成功) NO(存储失败)
     */
    public String setObject(String key, Object value, ShardedJedis redis) {
        if (key != null && value != null && redis != null) {
            String saveState = null;
            try {
                byte[] byteArray = RedisCommonUtil.getByteArrayByObject(value);
                saveState = redis.set(key.getBytes(), byteArray); // 存储状态
            } catch (Exception e) {
                e.printStackTrace();
            }
            return saveState;
        } else {
            return "NO";
        }
    }

    /**
     * 根据key,field存储对象，适应于Map<String,Obeject>
     * @param key
     * @param field
     * @param value
     * @param redis
     * @return
     */
    public Long setMapObject(String key, String field, Object value, ShardedJedis redis) {
        Long saveState = -1L;
        if (key != null && value != null && redis != null) {
            try {
                byte[] byteArray = RedisCommonUtil.getByteArrayByObject(value);
                saveState = redis.hset(key.getBytes(), field.getBytes(), byteArray);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return saveState;
    }
    /**
     * 根据key,field删除对象，适应于Map<String,Obeject>
     * @param key
     * @param field
     * @param redis
     * @return
     */
    public Long delMapObject(String key, String field,ShardedJedis redis) {
        Long saveState = -1L;
        if (key != null && redis != null) {
            try {
                saveState = redis.hdel(key.getBytes(), field.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return saveState;
    }

    /**
     * 存储String类型的数据到Redis
     *
     * @param key
     * @param value
     * @param redis
     * @return
     */
    public String setString(String key, String value, ShardedJedis redis) {
        if (key != null && value != null && redis != null) {
            String saveState = null;
            try {
                saveState = redis.set(key, value); // 存储状态
            } catch (Exception e) {
                e.printStackTrace();
            }
            return saveState;
        } else {
            return "NO";
        }
    }

    /**
     * 存储 "Map<String, String>类型" 的数据类型
     *
     * @param key
     * @param value
     * @param redis
     * @return
     */
    public String hmsetStr(String key, Map<String, String> value, ShardedJedis redis) {
        if (key != null && value != null && redis != null) {
            String saveState = null;
            try {
                saveState = redis.hmset(key, value); // 存储状态
            } catch (Exception e) {
                e.printStackTrace();
            }
            return saveState;
        } else {
            return "NO";
        }
    }

    /**
     * 对指定Map<String, String> 中的指定feild修改, 若没有就新增一个
     *
     * @param key
     * @param mKey
     * @param value
     * @param redis
     * @return
     */
    public Long hsetStr(String key, String mKey, String value, ShardedJedis redis) {
        if (key != null && mKey != null && value != null && redis != null) {
            Long saveState = null;
            try {
                saveState = redis.hset(key, mKey, value);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return saveState;
        } else {
            return null;
        }
    }

    /**
     * 存储Map<String, Object>类型的数据<br>
     * 实质存储Map<byte[],byte[]>
     *
     * @param key
     * @param value
     * @param redis
     * @return
     */
    public String hmsetObject(String key, Map<String, Object> value, ShardedJedis redis) {
        if (key != null && value != null && redis != null) {
            Map<byte[], byte[]> bMap = RedisCommonUtil.getByteMapToConvertObjectMap(value);
            String saveState = redis.hmset(key.getBytes(), bMap);
            if ("OK".equals(saveState)) {
                return RedisConstant.REDIS_SAVESTATE_SUCCESS;
            } else {
                return RedisConstant.REDIS_SAVESTATE_FAIL;
            }
        } else {
            return RedisConstant.REDIS_SAVESTATE_FAIL;
        }
    }

    /**
     * 向List<Object>尾部插入一条数据
     *
     * @param key
     * @param value
     * @param redis
     * @return 正常返回长度/ 错误返回-1
     */
    public Long saveObjLinsertOrLpush(String key, Object value, ShardedJedis redis) {
        if (key != null && value != null && redis != null) {
            Long llen = redis.llen(key.getBytes());// 判断长度
            byte[] byteArray = null;
            if (llen != null && llen > 0L) {
                byteArray = RedisCommonUtil.getByteArrayByObject(value);
                Long saveState = redis.rpush(key.getBytes(), byteArray);
                System.out.println("OutPrint | 主键 : " + key + "  已插入第 " + saveState + " 条结果");
                return saveState;
            } else {
                // 长度为0  新创建一个key-value
                byteArray = RedisCommonUtil.getByteArrayByObject(value);
                Long lpush = redis.lpush(key.getBytes(), byteArray);
                return lpush;
            }
        } else {
            return RedisConstant.REDIS_SAVE_LIST_AFTER_FAIL;// 未能成功
        }
    }


    /**
     * 向List<String>尾部插入一条数据
     *
     * @param key
     * @param value
     * @param redis
     * @return 正常返回长度/ 错误返回-1
     */
    public Long saveStrLinsertOrLpush(String key, String value, ShardedJedis redis) {
        if (key != null && value != null && redis != null) {
            Long llen = redis.llen(key);
            if (llen != null && llen > 0L) {
                Long saveState = redis.rpush(key, value);
                System.out.println(saveState);
                return saveState;
            } else {
                // 长度为0  新创建一个key-value
                Long lpush = redis.lpush(key, value);
                return lpush;
            }
        } else {
            return RedisConstant.REDIS_SAVE_LIST_AFTER_FAIL;// 未能成功
        }
    }

    
    /**
     * 向List<String> 保存String[]数据类型数据
     *
     * @param key
     * @param value
     * @param redis
     * @return 正常返回长度/ 错误返回-1
     */
    public Long saveStrLinsertOrLpush(String key, String[] values, ShardedJedis redis) {
    	if (key != null && values != null && redis != null) {
    		Long llen = redis.llen(key);
    		if (llen != null && llen > 0L) {
    			Long saveState = redis.rpush(key, values);
    			System.out.println(saveState);
    			return saveState;
    		} else {
    			// 长度为0  新创建一个key-value
    			Long lpush = redis.lpush(key, values);
    			return lpush;
    		}
    	} else {
    		return RedisConstant.REDIS_SAVE_LIST_AFTER_FAIL;// 未能成功
    	}
    }
    
    /**
     * 新增一个zset的值
     *
     * @param key
     * @param value
     * @param redis
     * @return 正常返回长度/ 错误返回-1
     */
    public long zadd(String key, String value, double score, ShardedJedis redis) {
        if (key != null && value != null && redis != null) {
            return redis.zadd(key, score, value);
        } else {
            return RedisConstant.REDIS_SAVE_Set_AFTER_FAIL;// 未能成功
        }
    }

    /**
     * 新增一个zset的值(限制最大数值)
     *
     * @param key
     * @param value
     * @param redis
     * @return 正常返回长度/ 错误返回-1
     */
    public long zadd(String key, String value, double score, long maxVolume, ShardedJedis redis) {
        if (key != null && value != null && redis != null) {
            redis.zadd(key, score, value);
            long rank = redis.zcard(key);
            if (rank > maxVolume) {
                redis.zremrangeByRank(key, 0, rank - maxVolume - 1);
                return maxVolume;
            }
            return rank;
        } else {
            return RedisConstant.REDIS_SAVE_Set_AFTER_FAIL;// 未能成功
        }
    }

    /**
     * 获取key对应的 zset<String>有序集合 默认按从小到达排序
     *
     * @param key
     * @param start
     * @param end
     * @param redis
     * @return
     */
    public Set<String> zrange(String key, Long start, Long end, ShardedJedis redis) {
        if (key != null && redis != null) {
            if (start == null || start < 0) {
                start = 0L;
            }
            if (end == null || end < 0) {
                end = -1L;
            }
            return redis.zrange(key, start, end);
        } else {
            return null;
        }
    }


    /**
     * 获取Map<String, Object> <br>
     * 实质通过 map<byte, byte>
     *
     * @param key
     * @param redis
     * @return
     */
    public Map<String, Object> hgetAllObject(String key, ShardedJedis redis) {
        if (key != null && redis != null) {
            Map<byte[], byte[]> bMap = redis.hgetAll(key.getBytes());
            if (bMap != null) {
                Map<String, Object> oMap = RedisCommonUtil.getObjectMapToConvertByteMap(bMap);
                return oMap;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 查询Map<String, String>类型的数据<br>
     *
     * @param key
     * @param redis
     * @return
     */
    public Map<String, String> hgetAllString(String key, ShardedJedis redis) {
        if (key != null && redis != null) {
            Map<String, String> objectMap = redis.hgetAll(key);
            return objectMap;
        } else {
            return null;
        }
    }

    /**
     * 获取Map<String, String>指定的hash field
     *
     * @param key
     * @param field
     * @param redis
     * @return
     */
    public String hgetStr(String key, String field, ShardedJedis redis) {
        if (key != null && field != null && redis != null) {
            String hget = null;
            try {
                hget = redis.hget(key, field);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return hget;
        } else {
            return null;
        }
    }

    /**
     * 获取Map<byte[], byte[]>指定的hash field
     *
     * @param key
     * @param field
     * @param redis
     * @return
     */
    public Object hgetObj(String key, String field, ShardedJedis redis) {
        if (key != null && field != null && redis != null) {
            Object hget = null;
            try {
                byte[] b = redis.hget(key.getBytes(), field.getBytes());
                if (b != null) {
                    hget = RedisCommonUtil.getObjectByByte(b);
                }// 判断
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return hget;
        } else {
            return null;
        }
    }

    /**
     * 根据key，field 删除指定的元素
     *
     * @param key
     * @param field
     * @param redis
     * @return
     */
    public long hdelObj(String key, String field, ShardedJedis redis) {
        if (key != null && field != null && redis != null) {
            return redis.hdel(key.getBytes(), field.getBytes());
        } else {
            return 0L;
        }

    }

    /**
     * 从redis中查询 "对象" 数据 <br>
     * 需要将取出来的数据强制转换成对应对象类型
     *
     * @param key   主键
     * @param redis redis对象
     * @return 泛型
     */
    public Object getObjectClass(String key, ShardedJedis redis) {
        if (key != null && redis != null) {
            Object readObject = null;
            try {
                byte[] bs = redis.get(key.getBytes());
                if (bs == null) {
                    return null;
                }

                // 封装字节流流到object中
                readObject = RedisCommonUtil.getObjectByByte(bs);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return readObject;
        } else {
            return null;
        }
    }

    /**
     * 查询 "String类型" 的数据
     *
     * @param key
     * @param redis
     * @return
     */
    public String getString(String key, ShardedJedis redis) {
        if (key != null && redis != null) {
            String string = null;
            try {
                string = redis.get(key);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return string;
        } else {
            return null;
        }
    }

    /**
     * 查询指定的List<Object>数据 并以分页的方式
     *
     * @param key
     * @param redis
     * @param start
     * @param end
     * @return
     */
    public List<Object> lrangeObject(String key, ShardedJedis redis, Long start, Long end) {
        if (key != null && redis != null) {
            List<Object> oList = new ArrayList<Object>();
            // 判断
            if (start == null || start < 0) {
                start = 0L;
            }
            if (end == null || end < 0) {
                end = -1L;
            }

            List<byte[]> bList = redis.lrange(key.getBytes(), start, end);
            for (byte[] b : bList) {
                Object object = RedisCommonUtil.getObjectByByte(b);
                oList.add(object);
            }
            return oList;
        } else {
            return null;
        }
    }

    /**
     * 查询指定的List<String>数据 并以分页的方式
     *
     * @param key
     * @param redis
     * @param start
     * @param end
     * @return
     */
    public List<String> lrangeString(String key, ShardedJedis redis, Long start, Long end) {
        if (key != null && redis != null) {
            // 判断start end的值
            if (start == null || start < 0) {
                start = 0L;
            }
            if (end == null || end < 0) {
                end = -1L;
            }

            List<String> sList = redis.lrange(key, start, end);
            return sList;
        } else {
            return null;
        }
    }

    /**
     * 删除 "对象类型" "Map<.., ..>类型" 操作 <br>
     *
     * @param key   主键
     * @param redis redis对象
     * @return 1(删除成功)/0(删除失败)
     */
    public Long delObject(String key, ShardedJedis redis) {
        if (key != null && redis != null) {
            Long del = redis.del(key.getBytes());
            return del;
        } else {
            return 0L;
        }
    }

    /**
     * 删除"String类型" 操作
     *
     * @param key
     * @param redis
     * @return 1(删除成功)/0(删除失败)
     */
    public Long delString(String key, ShardedJedis redis) {
        if (key != null && redis != null) {
            Long del = redis.del(key);
            return del;
        } else {
            return 0L;
        }
    }


    /**
     * 获取当前redis中list的长度
     *
     * @param key
     * @param redis
     * @return
     */
    public Long llenObject(String key, ShardedJedis redis) {
        return redis.llen(key.getBytes());
    }

    /**
     * 获取指定list<String>的长度
     *
     * @param key
     * @return
     */
    public Long llenStr(String key, ShardedJedis redis) {
        return redis.llen(key);
    }

    public Long hlenStr(String key, ShardedJedis redis) {
        return redis.hlen(key);
    }

    /**
     * 判断key中是否有值
     *
     * @param key
     * @param redis
     * @return
     */
    public Boolean exists(String key, ShardedJedis redis) {
        return redis.exists(key.getBytes());
    }
    
    /**
     * 判断Map中 key值所在的field域是否存在
     * 
     * @param key
     * @param field
     * @param redis
     * @return
     */
    public Boolean hexists(String key, String field, ShardedJedis redis){
    	return redis.hexists(key.getBytes(), field.getBytes());
    }


}
