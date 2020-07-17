package com.autozi.passcar.cache.memcached;

/**
 * @description: Memcache key的常量定义
 * @author zhiyun.chen
 * @time  2016-04-26
 */
public class MemcacheKeyConstants {
	//M站及PC
	public static String KEY_HOT_BRAND = "KEY_HOT_BRAND";//热门品牌
	public static String KEY_HOT_CATEGORY= "KEY_HOT_CATEGORY";//热门分类
	public static String KEY_HOT_CARLOGO= "KEY_HOT_CARLOGO";//热门车型

	public static String KEY_ALL_CAR_BRAND_LETTER_MSITE= "KEY_ALL_CAR_BRAND_LETTER_MSITE";//全部车型
//	public static String KEY_ALL_CAR_BRAND_MSITE= "KEY_ALL_CAR_BRAND_MSITE";//全部车型
	
	public static String KEY_CATEGORY= "KEY_CATEGORY_";//全车件分类（key开头带这个）
	public static String KEY_GET_ALL_CATEGORY= "KEY_GET_ALL_CATEGORY";//获取所有全车件分类
	public static String KEY_WEARING_CATEGORY_PC= "KEY_WEARING_CATEGORY_PC_";//保养件分类（key开头带这个）
	public static String KEY_CAR_YEAR= "KEY_CAR_YEAR_";//车型按年份
	public static String KEY_CAR_CAPACITY= "KEY_CAR_CAPACITY_";//车型按排量
	public static String KEY_GOODS_ITEM_BO= "KEY_GOODS_ITEM_BO";//热销商品
	public static String KEY_PRO_GOODS= "KEY_PRO_GOODS";//促销商品

	public static String KEY_ADVERT= "KEY_ADVERT_";//广告
	public static String KEY_GET_FLAGSHIP_STORESBY_BRAND = "KEY_GET_FLAGSHIP_STORESBY_BRAND";//获取品牌下的旗舰店
	public static String KEY_GET_FLAGSHIP_STORESBY_CAR_LOGO = "KEY_GET_FLAGSHIP_STORESBY_CAR_LOGO";//获取品牌下的旗舰店
	
	//APP使用
	public static String KEY_HOT_BRAND_APP = "KEY_HOT_BRAND_APP";//热门品牌
	public static String KEY_HOT_CATEGORY_APP= "KEY_HOT_CATEGORY_APP";//热门分类
	public static String KEY_HOT_SIMPLE_CARLOGO_APP= "KEY_HOT_SIMPLE_CARLOGO_APP";//热门车型（不带车系、车型）
	public static String KEY_HOT_CARLOGO_APP= "KEY_HOT_CARLOGO_APP";//热门车型（包含车系、车型）
	public static String KEY_ALL_CARLOGO_APP= "KEY_ALL_CARLOGO_APP";//全部车型
	public static String KEY_WEARING_CATEGORY_APP= "KEY_WEARING_CATEGORY_APP_";//保养件分类（key开头带这个）
	
	public static String KEY_HOT_SEARCH_APP= "KEY_HOT_SEARCH_APP";//热门搜索
	public static String KEY_All_BRAND_APP= "KEY_All_BRAND_APP";//全部品牌
	
	public static String KEY_ORDER_LOCK= "KEY_ORDER_LOCK_";//订单锁
	public static String KEY_RETURN_ORDER_LOCK= "KEY_RETURN_ORDER_LOCK_";//退单锁
	
	public static String KEY_PARTY_AREA_STORE_ID = "KEY_PARTY_AREA_STORE_ID_"; // party 保存区域商城id -- binbin.lee
	public static String KEY_SEPARATE_AREA_STORE_ID_MAP = "KEY_SEPARATE_AREA_STORE_ID_MAP"; // 已开通的独立商城Map集合 -- binbin.lee
	
	
	public static String KEY_PRO_ITEM_ALL_RELEASE= "KEY_PRO_ITEM_ALL_RELEASE";//已发布的促销

	public static String KEY_PRO_AREA_SCOPE= "KEY_PRO_AREA_SCOPE";//促销地区缓存

	public static String KEY_SYSTEM_MENU_LIST= "KEY_SYSTEM_MENU_LIST";//系统目录缓存
	
	/**
	 * 
	 * <PRE>
	 * 
	 * 中文描述：
	 * 
	 * </PRE>
	 * @version 1.0.0
	 */
	public interface 基础数据缓存配置{
    	int 基础数据缓存时间=60*30;//缓存半小时
    	
    	String _主体列表 = "_主体列表";
    	int _主体列表_过期时间 = 60*15; // 15分钟

        String 单条主体 = "单条主体_";
        int 单条主体_过期时间 = 60*15; //15分钟
        
        String PersonalParty = "PersonalParty_";
        int PersonalParty_过期时间 = 60*15; //15分钟
        
    }
	
	/**
	 * 
	 * <PRE>
	 * 
	 * 中文描述：仓库相关
	 * 
	 * </PRE>
	 * @version 1.0.0
	 */
	public interface WareHouse{
		String _仓库列表权限 = "_仓库列表权限";
		int _仓库列表权限_过期时间 = 60*15; // 15分钟
		
		String _仓库列表权限_客户服务商 = "_仓库列表权限_客户服务商";
		int _仓库列表权限_客户服务商_过期时间 = 60*15; // 15分钟
		
		String _所有仓库列表 = "_所有仓库列表";
		int _所有仓库列表_过期时间 = 60*15; // 15分钟
		
		String _RDC下的CDC列表="_RDC下的CDC列表";
		
		String _单条仓库="_单条仓库";
		
	}
	
	/**
	 * 
	 * <PRE>
	 * 
	 * 中文描述：品牌相关
	 * 
	 * </PRE>
	 * @version 1.0.0
	 */
	public interface Brand{
		String _所有品牌列表 = "_所有品牌列表";
		int _所有品牌列表_过期时间 = 60*15; // 15分钟
		String _单条品牌="_单条品牌";
	}
	
	/**
	 * 
	 * <PRE>
	 * 
	 * 中文描述：分类
	 * 
	 * </PRE>
	 * @version 1.0.0
	 */
	public interface Product{
		String _所有分类列表 = "_所有分类列表";
		int _所有分类列表_过期时间 = 60*15; // 15分钟
		String _单条品类="_单条品类";
	}
	
	
	/**
	 * 
	 * <PRE>
	 * 
	 * 中文描述：数据权限列表
	 * 
	 * </PRE>
	 * @version 1.0.0
	 */
	public interface Domain{
		String _数据权限 = "_数据权限";
		int _数据权限_过期时间 = 60* 15;
	}
	
	/**
	 * 
	 * <PRE>
	 * 
	 * 中文描述：Portal首页数据
	 * 
	 * </PRE>
	 * @version 1.0.0
	 */
	public interface PortalData{
		String _昨日销售金额 = "_昨日销售金额";
		int _昨日销售金额_过期时间 = 60*15; // 24小时-->15分钟
		
		String _今日关闭销售金额 = "_今日关闭销售金额";
		int _今日关闭销售金额_过期时间 = 60*15; // 15分钟
	}
	/**
	 * 
	 * <PRE>
	 * 
	 * 中文描述：库存缓存--慎用
	 * mwj
	 * </PRE>
	 * @version 1.0.0
	 */
	public interface Stock{
		String _单条库存 = "_单条库存";
		int _单条库存_过期时间 = 60*5; // 5分钟
		
		String _货位缓存="_货位缓存";
		int _货位缓存_过期时间 = 60*5; // 5分钟
		
	}

    /**
     * <PRE>
     * <p>
     * 中文描述：用户(User)缓存
     * wangwei
     * </PRE>
     * @version 1.0.0
     */
    public interface User {
        String KEY_USER_ID = "KEY_USER_ID_";
        int USER_CACHE_EXPIRATION_TIME = 60 * 15; // 15分钟
    }

    /**
     * <PRE>
     * <p>
     * 中文描述：主体(Party)缓存 - 结算组件工程专用
     * wei.wang
     * @since 2017年05月19日16:00:25
     * </PRE>
     * @version 1.0.0
     */
    public interface Party {
        String KEY_PARTY_ID = "KEY_PARTY_ID_";
        int PARTY_CACHE_EXPIRATION_TIME = 60 * 15; // 15分钟
    }

    /**
     * <PRE>
     * <p>
     * 中文描述：实体供应商(ActualParty)缓存
     * wangwei
     * </PRE>
     * @version 1.0.0
     */
    public interface ActualParty {
        String KEY_ACTUALPARTY_ID = "KEY_ACTUALPARTY_ID_";
        int ACTUALPARTY_CACHE_EXPIRATION_TIME = 60 * 15; // 15分钟
    }

    /**
	 * 
	 * <PRE>
	 * 
	 * 中文描述：物料
	 * 
	 * </PRE>
	 * @version 1.0.0
	 */
	public interface Part{
		String _单个物料="_单个物料";
		int _单个物料_过期时间 = 60*60*24*30; // 一个月
		
		String _物料列表="_物料列表";
		int _物料列表_过期时间 = 60*60*24; // 一天
	}
	
	/**
	 * 功能描述：根据品牌和最小分类查询商品类型
	 * @author shixin.zhang
	 * Created on：2017年8月11日
	 */
	public interface TypeProductBrand{
		String _商品类型="_商品类型";
		int _商品类型_过期时间 = 60*60*24*30; // 一个月
	}
	
	public interface RpartyIdStaticInfo{
		String _小R统计="_小R统计";
		int _小R统计_过期时间 = 60*60*2; // 二小时
	}
	
	/**
	 * 功能描述：小r主体身份类型
	 * @author shixin.zhang
	 * Created on：2017年8月15日
	 */
	public interface PartyBuildType{
		String _主体身份="_主体身份";
		int _主体身份_过期时间 = 60*60*24*30; // 一个月
	}
	
	/**
	 * 
	 * <PRE>
	 * 
	 * 中文描述：货位
	 * 
	 * </PRE>
	 * @version 1.0.0
	 */
	public interface Bin{
		String _单个货位="_单个货位";
		int _单个货位_过期时间 = 60*60*24*30; // 一个月
		
		String _货位列表="_货位列表";
		int _货位列表_过期时间 = 60*30; // 半小时
	}
	
	/**
	 * 
	 * <PRE>
	 * 
	 * 中文描述：商品
	 * 
	 * </PRE>
	 * @version 1.0.0
	 */
	public interface Goods{
		String _单个组合商品="_单个组合商品";
		int _单个组合商品_过期时间 = 60*30; // 半小时
		String _单个商品="_单个商品";
		
		String _单个车型="_单个车型";

		String _单个商品视图="_单个商品视图";
		int _单个商品视图_过期时间 = 60*30; // 半小时
		
	}
	
	/**
	 * 
	 * <PRE>
	 * 
	 * 中文描述：承运商
	 * 
	 * </PRE>
	 * @version 1.0.0
	 */
	public interface Carrier{
		String _单个承运商=" _单个承运商";
		int  _单个承运商_过期时间 = 60*30; // 半小时
		
		String _仓库绑定_承运商列表="_仓库绑定_承运商列表";
		int _仓库绑定_承运商列表_过期时间 = 60*30; // 半小时
	}
	
	/**
	 * 
	 * <PRE>
	 * 
	 * 中文描述：商品税收编码相关
	 * 
	 * </PRE>
	 * @version 1.0.0
	 */
	public interface GoodsTaxCode{
		String _所有商品税收关系列表 = "_所有商品税收关系列表";
		int _所有商品税收关系列表_过期时间 = 60*30; // 半小时
		String _单条商品税收关系="_单条商品税收关系";
		
		String _所有商品税收列表 = "_所有商品税收列表";
		int _所有商品税收列表_过期时间 = 60*30; // 半小时
		String _单条商品税收="_单条商品税收";
		
	}

	public interface CategoryAndBrandId{
		String _所有品类与品牌的组合 = "_所有品类与品牌的组合";
		int _所有品类与品牌的组合_过期时间 = 60*15; // 15分钟
	}
	public interface SystemMenus{
		String _所有系统目录 = "_所有系统目录";
		int _所有系统目录_过期时间 = 60*15; // 15分钟
	}
	
	/**
	 * 
	 * <PRE>
	 * 
	 * 中文描述：部门数据权限
	 * 
	 * </PRE>
	 * @version 1.0.0
	 */
	public interface DataScope{
		String _部门数据权限 = "_部门数据权限";
		int _部门数据权限_过期时间 = 60*15; // 15分钟
	}
}
