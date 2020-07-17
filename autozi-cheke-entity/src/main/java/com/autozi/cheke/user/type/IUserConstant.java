package com.autozi.cheke.user.type;

/**
 * User: long.jin
 * Date: 2016-05-19
 * Time: 9:14
 */
public class IUserConstant {
    // 部门、角色引用了其删除字段
    public static final int STATUS_DELETE = -1; // 删除
    public static final int STATUS_LOCKED = 0; //锁定
    public static final int STATUS_PENDING = -2;  //行业前台注册的用户
    public static final int STATUS_ADD = -3;  //新增的用户
    public static final int STATUS_VALID = 1; // 正常

    public static final int CAN_NOT_LOGIN = 0;  //是员工，不可登陆
    public static final int CAN_LOGIN = 1;     //是用户，可以登陆
}
