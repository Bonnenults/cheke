package com.autozi.cheke.article.type;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wang-lei on 2017/11/23.
 */
public enum  ArticleChannel {
    ZIXUN(1,"资讯"),VIDEO(2,"培训"),PROJECT(3,"项目"),AD(4,"广告"),COURSE(5,"课程");


    public static Map<String,String> getChannelMap() {
        Map<String,String> map = new HashMap<>();
        for(ArticleChannel channel : ArticleChannel.values()) {
            map.put(channel.getType().toString(),channel.getName());
        }
        return map;
    }

    public static String getChannelName(Integer type) {
        for(ArticleChannel channel : ArticleChannel.values()) {
            if(type.intValue() == channel.getType().intValue()) {
                return channel.getName();
            }
        }
        return "";
    }

    ArticleChannel(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    private Integer type;
    private String name;

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
