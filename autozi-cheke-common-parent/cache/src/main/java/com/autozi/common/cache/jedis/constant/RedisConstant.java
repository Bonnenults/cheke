package com.autozi.common.cache.jedis.constant;

/**
 * Redis常量信息
 * 
 * @author Lee
 *
 */
public interface RedisConstant {
	
	public static final Integer REDIS_MACHINE_COUNT = 3; // Redis服务器的数量
	
	public static final String REDIS_PROPERTIES = "redis.properties"; // redis的配置文件
	
	public static final String REDIS_SAVESTATE_SUCCESS = "OK";// 存储成功
	public static final String REDIS_SAVESTATE_FAIL = "NO";// 存储失败
	
	public static final Long REDIS_DELETE_SUCCESS = 1L; // 删除成功 
	public static final Long REDIS_DELETE_FAIL = 0L; // 删除失败
	
	public static final Long REDIS_SAVE_LIST_AFTER_FAIL = -1L; // "不成功"的向list集合后添加一条数据
	public static final Long REDIS_SAVE_Set_AFTER_FAIL = -1L; // "不成功"的向Set集合后添加一条数据


	/**
	 * key常量<br>
	 * key格式-> Value值类型 : 查询字段.. : 条件是对相同字段的id(按照需求) <br>
	 * 
	 * @author Lee
	 *
	 */
	public interface Key{
		String Redis_List_UserWalk = "list:userWalk:uid:"; // 用户足迹key形式(Type:list)+用户id
		
		String Redis_List_UserSearch_Uid = "list:userSearch:uid:"; // 用户搜索 "登录" 状态key形式(list)+用户id
		String Redis_List_UserSearch_Uip = "list:userSearch:uip:"; // 用户搜索 "未登录" 状态key形式(list)+用户ip
		String Redis_List_UserSearch_RandomKey = "list:userSearch:RandomKey:"; // 用户搜索 "未登录/登陆" 状态key形式(list)+ key: randomNum + Date(yyyyMMdd) 保存
		
		
		String Redis_ZSet_HisGoods_Uid = "zset:hisGoods:uid:"; // 用户浏览过的商品key(ZSet) + 用户id
		
		String Redis_Map_UserOrderSnap_Uid = "map:userOrderSnap:uid:";// 订单快照 {mKey: 用户id, fKey:订单单号}
		
		String Redis_Map_UserShoppingCart_Uid = "map:userShoppingcart:uid:" ; // 用户购物车 map:key-> goodsId
		
		// ADD BY LIHF@Autozi.com 2017-6-5下午5:40:13 START 需求描述：用户登录信息改用Redis进行存取;
		String mb_user_login_pre = "autozi.b2b.user:mblogin:"; // 手机用户登录session【mb_user_login_status】
		String mb_user_login_updateIds_pre ="autozi.b2b.user:mblogin:update:ids"; // 手机用户登录session，每日更新列表
		String pc_user_login_time_pre = "autozi.b2b.user:pclogin:"; // 用户从PC登录更新登录时间【aut_user】
		String pc_user_login_time_updateIds_pre = "autozi.b2b.user:pclogin:ids";// 用户从PC登录，每日更新列表
		// ADD BY LIHF@Autozi.com 2017-6-5下午5:40:13 END
		
		String api_limit_uid = "obj:apiLimit:uid:";//vin码搜索限制
		String mobile_api_limit_uid = "obj:mobileApiLimit:uid:";
	}
}

