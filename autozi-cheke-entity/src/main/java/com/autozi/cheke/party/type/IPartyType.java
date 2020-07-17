package com.autozi.cheke.party.type;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public interface IPartyType {

    public static final int STATUS_NORMAL = 1; // 正常
    public static final int STATUS_ADD_VERIFY = 2; //新增审核
    public static final int STATUS_MODIFY_VERIFY = 3;//修改审核
    public static final int STATUS_DELETE = 4; // 主体已经删除
    public static final int STATUS_REGISTER = 5; // 主体随用户注册，未完善资料
    public static final int STATUS_REFUSE = 6; // 已拒绝

    public static final boolean LOCK = true;   //锁定
    public static final boolean UNLOCK = false;       //解锁


    public static final long QEEGOO_ID = 1L;
    public static final long CHAIN_ID = 2L;

    /**
     * 菜单里使用平台参数、供应商参数用以区分菜单功能。平台目前为空
     */
    /**
     * 供应商
     **/
    public static final String SCENE_SUPPLIER = "supplier";

    public static final int PARTY_TYPE_ADMIN = 0; //管理员用户
    public static final String PARTY_TYPE_ADMIN_CAPTION = "系统管理员";

    public static final int PARTY_TYPE_PLATFORM = 1; //管理员用户
    public static final String PARTY_TYPE_PLATFORM_CAPTION = "监控平台";

    public static final int PARTY_TYPE_CHEKE = 2;//   车客主体
    public static final String PARTY_TYPE_CHEKE_CAPTION = "车客主体";

    public static final int PARTY_TYPE_TUIKE = 3;//   推客主体
    public static final String PARTY_TYPE_TUIKE_CAPTION = "推客主体";

    public static final Map<String, String> partyTypeMap = new LinkedHashMap<String, String>() {
        private static final long serialVersionUID = 2443192573683505356L;

        {
            this.put(String.valueOf(PARTY_TYPE_ADMIN), PARTY_TYPE_ADMIN_CAPTION);
            this.put(String.valueOf(PARTY_TYPE_CHEKE), PARTY_TYPE_CHEKE_CAPTION);
            this.put(String.valueOf(PARTY_TYPE_TUIKE), PARTY_TYPE_TUIKE_CAPTION);
        }
    };

    public static final int PARTY_CLASS_COMPANY = 1;// 主体类型-企业单位
    public static final String PARTY_CLASS_COMPANY_CAPTION = "企业单位";

    public static final int PARTY_CLASS_GOVERNMENT = 2;//主体类型-政府机构
    public static final String PARTY_CLASS_GOVERNMENT_CAPTION = "政府机构";

    public static final int PARTY_CLASS_MEDIA = 3;//主体类型-展览媒介
    public static final String PARTY_CLASS_MEDIA_CAPTION = "展览媒介";

    public static final int PARTY_CLASS_PERSON = 4;//主体类型-个人
    public static final String PARTY_CLASS_PERSON_CAPTION = "个人";

    public static final Map<String, String> partyClassMap = new LinkedHashMap<String, String>() {
        private static final long serialVersionUID = 2443192573683505357L;

        {
            this.put(String.valueOf(PARTY_CLASS_COMPANY), PARTY_CLASS_COMPANY_CAPTION);
            this.put(String.valueOf(PARTY_CLASS_GOVERNMENT), PARTY_CLASS_GOVERNMENT_CAPTION);
            this.put(String.valueOf(PARTY_CLASS_MEDIA), PARTY_CLASS_MEDIA_CAPTION);
            this.put(String.valueOf(PARTY_CLASS_PERSON), PARTY_CLASS_PERSON_CAPTION);
        }
    };

    //企业公司类型
    public static final Map<String, String> COMPANY_CLASS_COMPANY = new LinkedHashMap<String, String>() {
        private static final long serialVersionUID = 2443192573683505357L;

        {
            this.put(String.valueOf(1), "生产");
            this.put(String.valueOf(2), "连锁");
            this.put(String.valueOf(3), "电商");
            this.put(String.valueOf(4), "经销商");
            this.put(String.valueOf(5), "修理厂");
            this.put(String.valueOf(6), "数据");
            this.put(String.valueOf(7), "其他");
        }
    };
    //机构公司类型
    public static final Map<String, String> COMPANY_CLASS_ORGAN = new LinkedHashMap<String, String>() {
        private static final long serialVersionUID = 2443192573683505357L;

        {
            this.put(String.valueOf(1), "协会");
            this.put(String.valueOf(2), "学会");
            this.put(String.valueOf(3), "专业委员会");
            this.put(String.valueOf(4), "学校");
            this.put(String.valueOf(5), "其他");
        }
    };
    //媒体公司类型
    public static final Map<String, String> COMPANY_CLASS_MEDIA = new LinkedHashMap<String, String>() {
        private static final long serialVersionUID = 2443192573683505357L;

        {
            this.put(String.valueOf(1), "展览公司");
            this.put(String.valueOf(2), "报纸");
            this.put(String.valueOf(3), "杂志");
            this.put(String.valueOf(4), "电视");
            this.put(String.valueOf(5), "电台");
            this.put(String.valueOf(6), "通讯社");
            this.put(String.valueOf(7), "新媒体");
            this.put(String.valueOf(8), "公关公司");
            this.put(String.valueOf(9), "其他");
        }
    };
    //个人公司类型
    public static final Map<String, String> COMPANY_CLASS_PERSON = new LinkedHashMap<String, String>() {
        private static final long serialVersionUID = 2443192573683505357L;

        {
            this.put(String.valueOf(1), "生产");
            this.put(String.valueOf(2), "连锁");
            this.put(String.valueOf(3), "电商");
            this.put(String.valueOf(4), "经销商");
            this.put(String.valueOf(5), "修理厂");
            this.put(String.valueOf(6), "数据");
            this.put(String.valueOf(7), "其他");
        }
    };
    //媒体属性
    public static final Map<String, String> mediaType = new LinkedHashMap<String, String>() {
        private static final long serialVersionUID = 2443192573683505357L;

        {
            this.put(String.valueOf(1), "群媒体");
            this.put(String.valueOf(2), "新闻媒体");
        }
    };
    //个人职位类别
    public static final Map<String, String> personType = new LinkedHashMap<String, String>() {
        private static final long serialVersionUID = 2443192573683505357L;

        {
            this.put(String.valueOf(1), "总经理");
            this.put(String.valueOf(2), "销售经理");
            this.put(String.valueOf(3), "技术经理");
            this.put(String.valueOf(4), "生产经理");
            this.put(String.valueOf(5), "采购经理");
            this.put(String.valueOf(6), "财务主管");
            this.put(String.valueOf(7), "人事行政主管");
            this.put(String.valueOf(8), "业务员");
            this.put(String.valueOf(9), "其他");
        }
    };


    /**
     * 供应商合作模式
     */
    public final static int COOPERATION_NORMAL = 0;       //商务
    public final static String COOPERATION_NORMAL_CAPTION = "供应厂商";
    public final static int COOPERATION_TACTIC = 1;           //战略
    public final static String COOPERATION_TACTIC_CAPTION = "供应代理商";
    public final static int COOPERATION_TEMP = 2;     //临时
    public final static String COOPERATION_TEMP_CAPTION = "临时";

    @SuppressWarnings("serial")
    public final static Map<String, String> SUPPLIER_COOPERATION_MAP = new LinkedHashMap<String, String>() {
        {
            this.put(String.valueOf(COOPERATION_NORMAL), COOPERATION_NORMAL_CAPTION);
            this.put(String.valueOf(COOPERATION_TACTIC), COOPERATION_TACTIC_CAPTION);
//            this.put(String.valueOf(COOPERATION_TEMP), COOPERATION_TEMP_CAPTION);
        }
    };
    /**
     * 门店
     */
    public final static int COOPERATION_ALLIANCE = 0;       //加盟
    public final static String COOPERATION_ALLIANCE_CAPTION = "加盟";
    public final static int COOPERATION_CAMP = 1;           //直营
    public final static String COOPERATION_CAMP_CAPTION = "直营";

    @SuppressWarnings("serial")
    public final static Map<String, String> STORE_TYPE_MAP = new LinkedHashMap<String, String>() {
        {
            this.put(String.valueOf(COOPERATION_ALLIANCE), COOPERATION_ALLIANCE_CAPTION);
            this.put(String.valueOf(COOPERATION_CAMP), COOPERATION_CAMP_CAPTION);
        }
    };


    class RepairShopInfo {
        /**
         * 综合修理
         */
        public static int TYPE_MULTI = 0;
        /**
         * 快修
         */
        public static int TYPE_QUICK = 10;
        /**
         * 专修
         */
        public static int TYPE_SPECIALITY = 20;


        public static int RANK_ONE = 1;//一类
        public static int RANK_TWO = 2;//二类
        public static int RANK_THREE = 3;//三类

        public static Map<Integer, String> typeMap = new HashMap<Integer, String>();
        public static Map<Integer, String> rankMap = new HashMap<Integer, String>();

        static {
            typeMap.put(TYPE_MULTI, "综合修理");
            typeMap.put(TYPE_QUICK, "快修");
            typeMap.put(TYPE_SPECIALITY, "专修");

            rankMap.put(RANK_ONE, "一类");
            rankMap.put(RANK_TWO, "二类");
            rankMap.put(RANK_THREE, "三类");
        }

        public static String getTypeName(int type) {
            return typeMap.get(type);
        }

        public static String getRankName(int rank) {
            return rankMap.get(rank);
        }
    }

    /**
     * 汽修厂经营模式
     *
     * @author litongke
     */
    class CooperModel {
        public final static int CHAIN_PLANT = 100;//连锁
        public final static int ALLIANCE_PLANT = 110;//直营
        public final static int CAMP_PLANT = 120;//加盟
    }
}

