package com.autozi.common.utils.util2;

public class ApplicationPropertiesUtils {

	/**
     * @Description: 获取商品范围标识（中驰、驰加）
     * @author: zhiyun.chen
     * 2015-12-31上午09:22:44
     */
    public static String getGoodsScope () {
   	 	String goods_scope = ApplicationProperties.getValue("part.goodsScope");
        if (goods_scope==null) {
            return "1";//默认为中驰
        }
        return goods_scope;
    }
    
    /**
     * @Description: 默认仓库
     * @author: zhiyun.chen
     * 2016-5-9下午02:20:13
     */
    public static String getDefaultAreaShoppingStoreId(){
    	String defaultAreaShoppingStoreId = ApplicationProperties.getValue("default.AreaShoppingStoreId");
        if (defaultAreaShoppingStoreId==null) {
            return "1603311432390038";//默认正式库
        }
        return defaultAreaShoppingStoreId;
    }
}
