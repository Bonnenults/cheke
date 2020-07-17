package com.autozi.cheke.article.type;

/**
 * Created by wang-lei on 2017/11/24.
 * 分享类型常量参数
 */
public enum ArticleClickType {

    WXFRIENDSCIRCLE(1,"朋友圈"),WXFRIENDS(2,"微信好友"),QQZONE(3,"QQ空间"),QQFRIENDS(4,"QQ好友"),SINA(5,"微博"),

    LEAVEWORDS(10,"留言"),LIKES(11,"点赞"),DISLIKES(12,"点LOW"),CANCLELIKES(13,"取消点赞"),CANCLEDISLIKES(14,"取消点LOW");



    public static String getNameByType(Integer type) {
        for(ArticleClickType articleClickType : ArticleClickType.values()) {
            if(type.intValue() == articleClickType.getType().intValue()) {
                return articleClickType.getName();
            }
        }
        return "";
    }

    private Integer type;
    private String name;

    ArticleClickType(Integer type, String name) {
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
