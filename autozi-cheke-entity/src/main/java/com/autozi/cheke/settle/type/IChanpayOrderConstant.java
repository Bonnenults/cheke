package com.autozi.cheke.settle.type;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lbm on 2018/1/11.
 */
public interface IChanpayOrderConstant {

    interface orderType {
        static int ORDER_TYPE_RECHARGE = 1;
        static int ORDER_TYPE_WITHDRAW = 2;

        static String ORDER_TYPE_RECHARGE_CN = "充值";
        static String ORDER_TYPE_WITHDRAW_CN = "提现";

        public static Map<String, String> statusMap = new HashMap<String, String>() {{
            this.put(ORDER_TYPE_RECHARGE + "", ORDER_TYPE_RECHARGE_CN);
            this.put(ORDER_TYPE_WITHDRAW + "", ORDER_TYPE_WITHDRAW_CN);
        }};
    }

    interface payType {
        static int PAY_TYPE_DSF = 1;
        static int PAY_TYPE_WX = 2;
        static int PAY_TYPE_ALI = 3;
        static int PAY_TYPE_EBANK = 4;

        static String PAY_TYPE_DSF_CN = "代收付";
        static String PAY_TYPE_WX_CN = "微信";
        static String PAY_TYPE_ALI_CN = "支付宝";
        static String PAY_TYPE_EBANK_CN = "网银";

        public static Map<String, String> statusMap = new HashMap<String, String>() {{
            this.put(PAY_TYPE_DSF + "", PAY_TYPE_DSF_CN);
            this.put(PAY_TYPE_WX + "", PAY_TYPE_WX_CN);
            this.put(PAY_TYPE_ALI + "", PAY_TYPE_ALI_CN);
            this.put(PAY_TYPE_EBANK + "", PAY_TYPE_EBANK_CN);

        }};
    }

}
