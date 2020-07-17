package com.autozi.cheke.settle.type;

import java.util.HashMap;
import java.util.Map;

/**
 * User: long.jin
 * Date: 2017-11-24
 * Time: 14:34
 */
public interface IAccountOrderConstant {

     double rateFee = 0.06;//税费
     double slottingFee = 0.02;//通道费

    interface type {
    static int TYPE_RECHARGE = 10;
    static int TYPE_GETMONEY = 20;

    static String TYPE_RECHARGE_CN = "充值";
    static String TYPE_GETMONEY_CN = "提现";

    public static Map<String, String> typeMap = new HashMap<String, String>() {{
        this.put(TYPE_RECHARGE + "", TYPE_RECHARGE_CN);
        this.put(TYPE_GETMONEY + "", TYPE_GETMONEY_CN);
    }};
}


    interface status {
        //如果是充值订单 则不需要审核 如果是提现订单 需要审核
            static int STATUS_CREATE = 10;
            static  int STATUS_END = 40;
            static  int STATUS_CANCEL = -10;
            static int STATUS_FAILD = -20;

            static String STATUS_CREATE_CN = "待支付";
            static String STATUS_END_CN = "已支付";
            static String STATUS_CANCEL_CN = "已取消";
            static String STATUS_FAILD_CN = "已失败";

            public static Map<String, String> statusMap = new HashMap<String, String>() {{
                this.put(STATUS_CREATE + "", STATUS_CREATE_CN);
                this.put(STATUS_END + "", STATUS_END_CN);
                this.put(STATUS_CANCEL + "", STATUS_CANCEL_CN);
                this.put(STATUS_FAILD + "", STATUS_FAILD_CN);
            }};


        public static Map<String, String> ckStatusMap = new HashMap<String, String>() {{
            this.put(STATUS_CREATE + "", STATUS_CREATE_CN);
            this.put(STATUS_END + "", STATUS_END_CN);
            this.put(STATUS_CANCEL + "", STATUS_CANCEL_CN);
            this.put(STATUS_FAILD + "", STATUS_FAILD_CN);
        }};
        }

}
