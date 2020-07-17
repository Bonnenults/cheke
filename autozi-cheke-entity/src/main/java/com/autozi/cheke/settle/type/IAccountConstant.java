package com.autozi.cheke.settle.type;

import java.util.HashMap;
import java.util.Map;

/**
 * User: long.jin
 * Date: 2017-11-24
 * Time: 14:34
 */
public interface IAccountConstant {

    interface type {
        static int TYPE_CK = 10;
        static int TYPE_TK = 20;

        static String TYPE_CK_CN = "车客";
        static String TYPE_TK_CN = "推客";

        public static Map<String, String> typeMap = new HashMap<String, String>() {{
            this.put(TYPE_CK + "", TYPE_CK_CN);
            this.put(TYPE_TK + "", TYPE_TK_CN);
        }};
    }

    interface AccountLogSourceType{
        static int TYPE_SPREAD_RECHARGE = 0;
        static int TYPE_SPREAD_COST = 10;
        static int TYPE_SPREAD_RETURN = 20;
        static int TYPE_CK_GET_MONEY = 30;
        static int TYPE_CK_ORDER_MONEY= 40;
        static int TYPE_TK_SHARE_MONEY= 50;

        static String TYPE_SPREAD_RECHARGE_CN = "账户充值";
        static String TYPE_SPREAD_COST_CN = "推广付费";
        static String TYPE_SPREAD_RETURN_CN = "推广退回";
        static String TYPE_CK_GET_MONEY_CN = "推客提现";
        static String TYPE_CK_ORDER_MONEY_CN = "任务奖励";
        static String TYPE_CK_SHARE_MONEY_CN = "推广分成";

        public static Map<String, String> typeMap = new HashMap<String, String>() {{
            this.put(TYPE_SPREAD_RECHARGE + "", TYPE_SPREAD_RECHARGE_CN);
            this.put(TYPE_SPREAD_COST + "", TYPE_SPREAD_COST_CN);
            this.put(TYPE_SPREAD_RETURN + "", TYPE_SPREAD_RETURN_CN);
            this.put(TYPE_CK_GET_MONEY + "", TYPE_CK_GET_MONEY_CN);
            this.put(TYPE_CK_ORDER_MONEY + "", TYPE_CK_ORDER_MONEY_CN);
            this.put(TYPE_TK_SHARE_MONEY + "", TYPE_CK_SHARE_MONEY_CN);
        }};
    }

    interface AccountTaskMoneyMsg{
        static int TASK_MONEY_NULL = 0;
        static int TASK_MONEY_SUCCESS = 1;
        static int TASK_MONEY_FAILD = 2;

        static String TASK_MONEY_NULL_CN = "申请的金额为空";
        static String TASK_MONEY_SUCCESS_CN = "申请成功";
        static String TASK_MONEY_FAILD_CN = "余额不足";

        public static Map<String, String> typeMap = new HashMap<String, String>() {{
            this.put(TASK_MONEY_NULL + "", TASK_MONEY_NULL_CN);
            this.put(TASK_MONEY_SUCCESS + "", TASK_MONEY_SUCCESS_CN);
            this.put(TASK_MONEY_FAILD + "", TASK_MONEY_FAILD_CN);
        }};
    }

    interface Bank{
//        static String CARD_NO1 = "622230,622235,622210,622215,622200,955880";
//        static String CARD_NO2 = "622760,409666,438088,622752";
//        static String CARD_NO3 = "436742,436745,622280";
//        static String CARD_NO4 = "552599,404119,404121,519412,403361,558730,520083,520082,519413,49102,404120,404118,53591,404117,622836,622837,622848";

        static String CARD_ICBC_CODE = "ICBC";
        static String CARD_BOC_CODE = "BOC";
        static String CARD_CCB_CODE = "CCB";
        static String CARD_ABC_CODE = "ABC";
        static String CARD_COMM_CODE = "COMM";
        static String CARD_CMB_CODE = "CMB";
        static String CARD_BOBJ_CODE = "BOBJ";
        static String CARD_PAB_CODE = "PAB";
        static String CARD_PSBC_CODE = "PSBC";
        static String CARD_CITIC_CODE = "CITIC";
        static String CARD_CMBC_CODE = "CMBC";
        static String CARD_CIB_CODE = "CIB";
        static String CARD_CEB_CODE = "CEB";
        static String CARD_SPDB_CODE = "SPDB";
        static String CARD_CGB_CODE = "CGB";
        static String CARD_HXB_CODE = "HXB";
        static String CARD_BOSC_CODE = "BOSC";
        static String CARD_BQD_CODE = "BQD";
        static String CARD_HCCB_CODE = "HCCB";
        static String CARD_NJCB_CODE = "NJCB";
        static String CARD_NBCB_CODE = "NBCB";
        static String CARD_TCCB_CODE = "TCCB";
        static String CARD_BEA_CODE= "BEA";

        static String CARD_ICBC = "中国工商银行";
        static String CARD_BOC = "中国银行";
        static String CARD_CCB = "中国建设银行";
        static String CARD_ABC = "中国农业银行";
        static String CARD_COMM = "交通银行";
        static String CARD_CMB = "招商银行";
        static String CARD_BOBJ = "北京银行";
        static String CARD_PAB = "平安银行";
        static String CARD_PSBC = "邮储银行";
        static String CARD_CITIC = "中信银行";
        static String CARD_CMBC = "民生银行";
        static String CARD_CIB = "兴业银行";
        static String CARD_CEB = "光大银行";
        static String CARD_SPDB = "浦发银行";
        static String CARD_CGB = "广发银行";
        static String CARD_HXB = "华夏银行";
        static String CARD_BOSC = "上海银行";
        static String CARD_BQD = "青岛银行";
        static String CARD_HCCB = "杭州银行";
        static String CARD_NJCB = "南京银行";
        static String CARD_NBCB = "宁波银行";
        static String CARD_TCCB = "天津银行";
        static String CARD_BEA= "东亚银行";

        public static Map<String, String> bankMap = new HashMap<String, String>() {{
            this.put(CARD_ICBC, CARD_ICBC_CODE);
            this.put(CARD_BOC, CARD_BOC_CODE);
            this.put(CARD_CCB, CARD_CCB_CODE);
            this.put(CARD_ABC, CARD_ABC_CODE);
            this.put(CARD_COMM, CARD_COMM_CODE);
            this.put(CARD_CMB, CARD_CMB_CODE);
            this.put(CARD_BOBJ, CARD_BOBJ_CODE);
            this.put(CARD_PAB, CARD_PAB_CODE);
            this.put(CARD_PSBC, CARD_PSBC_CODE);
            this.put(CARD_CITIC, CARD_CITIC_CODE);
            this.put(CARD_CMBC, CARD_CMBC_CODE);
            this.put(CARD_CIB, CARD_CIB_CODE);
            this.put(CARD_CEB, CARD_CEB_CODE);
            this.put(CARD_SPDB, CARD_SPDB_CODE);
            this.put(CARD_CGB, CARD_CGB_CODE);
            this.put(CARD_HXB, CARD_HXB_CODE);
            this.put(CARD_BOSC, CARD_BOSC_CODE);
            this.put(CARD_BQD, CARD_BQD_CODE);
            this.put(CARD_HCCB, CARD_HCCB_CODE);
            this.put(CARD_NJCB, CARD_NJCB_CODE);
            this.put(CARD_NBCB, CARD_NBCB_CODE);
            this.put(CARD_TCCB, CARD_TCCB_CODE);
            this.put(CARD_BEA, CARD_BEA_CODE);
        }};
    }

    interface msg {
        static int ACCOUNT_POOR = 0;
        static int SUCCESS = 1;

        static String ACCOUNT_POOR_CN = "已创建";
        static String SUCCESS_CN = "已通过";

        public static Map<String, String> msgMap = new HashMap<String, String>() {{
            this.put(ACCOUNT_POOR + "", ACCOUNT_POOR_CN);
            this.put(SUCCESS + "", SUCCESS_CN);
        }};
    }



}
