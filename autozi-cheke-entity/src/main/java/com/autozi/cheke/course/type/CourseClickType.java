package com.autozi.cheke.course.type;

/**
 * 分享类型常量参数
 * Created by mingxin.li on 2018/5/22 16:47.
 */
public enum CourseClickType {

    WXFRIENDSCIRCLE(1,"朋友圈"),WXFRIENDS(2,"微信好友"),QQZONE(3,"QQ空间"),QQFRIENDS(4,"QQ好友"),SINA(5,"微博"),

    LEAVEWORDS(10,"留言"),LIKES(11,"点赞"),DISLIKES(12,"点LOW");



    public static String getNameByType(Integer type) {
        for(CourseClickType courseeClickType : CourseClickType.values()) {
            if(type.intValue() == courseeClickType.getType().intValue()) {
                return courseeClickType.getName();
            }
        }
        return "";
    }

    private Integer type;
    private String name;

    CourseClickType(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
