package com.autozi.cheke.user.type;

import com.autozi.cheke.party.type.IPartyType;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by lbm on 2017/11/29.
 */
public interface IUserType {

    public Long AdminUserId = 0L;

    public static final int IS_ADMIN = 1;
    public static final int NOT_ADMIN = 0;

    /**
     * 推客用户状态定义
     */
    public static final int TUIKE_STATUS_NORMAL = 1; // 正常 能够提现操作
    public static final String TUIKE_STATUS_NORMAL_CN = "正常"; // 正常 能够提现操作
    public static final int TUIKE_STATUS_STOP = 2; // 停用锁定
    public static final String TUIKE_STATUS_STOP_CN = "停用";
    public static final int TUIKE_STATUS_VERIFY = 3; // 身份证审核中
    public static final String UIKE_STATUS_VERIFY_CN = "审核中";
    public static final int TUIKE_STATUS_REFUSE = 4; // 身份证审核被拒绝
    public static final String TUIKE_STATUS_REFUSE_CN = "拒绝";
    public static final int TUIKE_STATUS_REGISTER = 5; // 用户注册未审核身份证'
    public static final String TUIKE_STATUS_REGISTER_CN = "新注册用户";

    public static final Map<String, String> tuikeUserStatusMap = new LinkedHashMap<String, String>() {
        private static final long serialVersionUID = 2443192573683505355L;

        {
            this.put(String.valueOf(TUIKE_STATUS_NORMAL),TUIKE_STATUS_NORMAL_CN);
            this.put(String.valueOf(TUIKE_STATUS_STOP), TUIKE_STATUS_STOP_CN);
            this.put(String.valueOf(TUIKE_STATUS_VERIFY), UIKE_STATUS_VERIFY_CN);
            this.put(String.valueOf(TUIKE_STATUS_REFUSE), TUIKE_STATUS_REFUSE_CN);
            this.put(String.valueOf(TUIKE_STATUS_REGISTER), TUIKE_STATUS_REGISTER_CN);
        }
    };

    /**
     * 推客来源
     */
    public static final int TUIKE_SOURCE_TYPE_B2B = 1; // 来自B2B
    public static final String TUIKE_SOURCE_TYPE_B2B_CN = "b2b商城用户"; // 来自B2B
    public static final int TUIKE_SOURCE_TYPE_REGISTER = 2; // 来自注册
    public static final String TUIKE_SOURCE_TYPE_REGISTER_CN = "注册用户"; // 来自注册
    public static final Map<String, String> tuikeUserSourceTypeMap = new LinkedHashMap<String, String>() {
        private static final long serialVersionUID = 2443192573683505355L;

        {
            this.put(String.valueOf(TUIKE_SOURCE_TYPE_B2B),TUIKE_SOURCE_TYPE_B2B_CN);
            this.put(String.valueOf(TUIKE_SOURCE_TYPE_REGISTER), TUIKE_SOURCE_TYPE_REGISTER_CN);
        }
    };

    /**
     * 管理员、车客用户状态
     */
    public static final int STATUS_NORMAL = 1; // 正常
    public static final int STATUS_STOP = 2; //停用

    public static final String STATUS_NORMAL_CN = "正常"; // 正常
     public static final String STATUS_STOP_CN = "锁定"; //停用

    public static final Map<String, String> userStatusMap = new LinkedHashMap<String, String>() {
           private static final long serialVersionUID = 2443192573683505355L;

           {
               this.put(String.valueOf(STATUS_NORMAL),STATUS_NORMAL_CN);
               this.put(String.valueOf(STATUS_STOP), STATUS_STOP_CN);
           }
       };

    /**
     * 管理员用户
     */
    public static final int USER_TYPE_ADMIN = IPartyType.PARTY_TYPE_ADMIN; //管理员用户
    public static final String USER_TYPE_ADMIN_CAPTION = "管理员用户";
    /**
     * 车客用户
     */
    public static final int USER_TYPE_CHEKE = IPartyType.PARTY_TYPE_CHEKE;//   车客用户
    public static final String USER_TYPE_CHEKE_CAPTION = "车客用户";
    /**
     * 推客用户
     */
    public static final int USER_TYPE_TUIKE = IPartyType.PARTY_TYPE_TUIKE;//   推客用户
    public static final String USER_TYPE_TUIKE_CAPTION = "推客用户";


    public static final Map<String, String> userTypeMap = new LinkedHashMap<String, String>() {
        private static final long serialVersionUID = 2443192573683505355L;

        {
            this.put(String.valueOf(USER_TYPE_ADMIN), USER_TYPE_ADMIN_CAPTION);
            this.put(String.valueOf(USER_TYPE_CHEKE), USER_TYPE_CHEKE_CAPTION);
            this.put(String.valueOf(USER_TYPE_TUIKE), USER_TYPE_TUIKE_CAPTION);
        }
    };
}
