package com.autozi.cheke.settle.type;

import java.util.HashMap;
import java.util.Map;

/**
 * User: long.jin
 * Date: 2017-11-24
 * Time: 14:34
 */
public interface IDrawCashOrderConstant {

    public static Double RATE_FEE=1d;//手续费1块

    interface status {
        //如果是充值订单 则不需要审核 如果是提现订单 需要审核
        static int STATUS_CREATE = 10;
        static int STATUS_CONFIRM = 20;
        static int STATUS_PAYING = 30;
        static int STATUS_END = 40;
        static int STATUS_CANCEL = -20;
        static int STATUS_CANCEL_OVER= -10;

        static String STATUS_CREATE_CN = "已创建";
        static String STATUS_CONFIRM_CN = "已通过";
        static String STATUS_PAYING_CN = "支付中";
        static String STATUS_END_CN = "已到账";
        static String STATUS_CANCEL_CN = "提现失败";
        static String STATUS_CANCEL_OVER_CN = "失败已处理";

        public static Map<String, String> statusMap = new HashMap<String, String>() {{
            this.put(STATUS_CREATE + "", STATUS_CREATE_CN);
            this.put(STATUS_CONFIRM + "", STATUS_CONFIRM_CN);
            this.put(STATUS_PAYING + "", STATUS_PAYING_CN);
            this.put(STATUS_END + "", STATUS_END_CN);
            this.put(STATUS_CANCEL + "", STATUS_CANCEL_CN);
            this.put(STATUS_CANCEL_OVER + "", STATUS_CANCEL_OVER_CN);
        }};
    }
}
