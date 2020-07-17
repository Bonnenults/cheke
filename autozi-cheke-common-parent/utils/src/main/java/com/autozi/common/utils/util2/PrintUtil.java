/**
 * FileName   : PrintUtil.java
 * Product    : B2BV2.0
 * CreateDate : 2012-8-14
 * Update     :
 * Author     : haifeng.li@qeegoo.com
 *
 * Copyright (C) 2012 启购时代 版权所有.
 */
package com.autozi.common.utils.util2;


/**
 * 打印控制
 * 
 * @author lihf
 *
 */
public class PrintUtil {
    
    private static String _config_file = PropertyUtil.getStrValue("application.properties", "printConfigPath", "/mnt/nfs_dir/html/b2bex/printConfig")+"/printConfig.properties";
    
    /**
     * 获取打印偏移值[extWidthNum]
     * 
     * @param partyId
     * @param busType
     * @return
     */
    public static int getExtWidthNum(long partyId,int busType){
        String key = partyId+".extWidthNum."+busType;
        return PropertyUtil.getPrintIntValue(_config_file, key, 22);
    }
    
    /**
     * 获取打印偏移值[extWidthNum]
     * 
     * @param partyId
     * @param busType
     * @return
     */
    public static int getExtHeightNum(long partyId,int busType){
        String key = partyId+".extHeightNum."+busType;
        return PropertyUtil.getPrintIntValue(_config_file, key, 100);
    }
    
    /**
     * 获取打印偏移值[htmlTop]
     * 
     * @param partyId
     * @param busType
     * @return
     */
    public static int getHtmlTop(long partyId,int busType){
        String key = partyId+".htmlTop."+busType;
        return PropertyUtil.getPrintIntValue(_config_file, key, 16);
    }
    
    /**
     * 获取打印偏移值[htmlLeft]
     * 
     * @param partyId
     * @param busType
     * @return
     */
    public static int getHtmlLeft(long partyId,int busType){
        String key = partyId+".htmlLeft."+busType;
        return PropertyUtil.getPrintIntValue(_config_file, key, 11);
    }
    
    
    /**
     * 打印单据类型定义
     * 
     * @author lihf
     *
     */
	public interface PrintBussionType {
		int SUPPLIER_SHIPPING_ORDER = 1; // 供应商发货/取货运单
		int WMS_RECEIVING_ORDER = 2; // 仓库收货入库单
		int WMS_PICK_ORDER = 3;// 仓库拣货单
		int WMS_SHIPPING_ORDER = 4; // 仓库运单
		int WMS_SHIPPING_ORDER_LABEL = 41; // 仓库运单标签
		int WMS_BINADJUST_ORDER = 5; // 货位调整单
		int WMS_STOCKCHECK_ORDER = 6; // 库存盘点
		int WMS_COSTADJUST_ORDER = 7; // 成本调整
		int WMS_BAOSUNBAOYI_ORDER = 8; // 报损报溢
		int WMS_INNER_ORDER = 9;// 内部业务
		int POS_ORDER = 10; // 直接零售订货单
		int POS_RETURN = 11;// 直接零售退货单
		int PUHUO_ORDER = 12;// 铺货单
		int WMS_DISPATCH_ORDER = 13;//发货单
		int QXC_RETURN_ORDER = 15;//退货取货单
	}
    
    /**
     * 获取打印配置文件路径
     * 
     * @return
     */
    public static String getPrintConfigFile(){
        return _config_file;
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
//        System.out.println(PrintUtil.getExtWidthNum(2, PrintUtil.PrintBussionType.pos_order));
//        System.out.println(PrintUtil.getExtHeightNum(2, PrintUtil.PrintBussionType.pos_order));
//        System.out.println(PrintUtil.getHtmlTop(2, PrintUtil.PrintBussionType.pos_order));
//        System.out.println(PrintUtil.getHtmlLeft(2, PrintUtil.PrintBussionType.pos_order));
    }

}
