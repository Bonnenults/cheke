package com.autozi.cheke.article.type;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by wang-lei on 2017/11/22.
 * 资讯类别
 */
public enum ArticleTypeEnum {

    ALL(0,"全部"),COMPANYNEWS(1,"公司新闻"),TRADENEWS(2,"行业新闻"),
    VIDEO(31,"视频推广"),SINGLE(32,"单图推广"),MULTIGAR(33,"多图推广"),
    COURSE(40,"**课程");


    public static Map<String,String> getTypeMap() {
        Map<String,String> map = new TreeMap<>();
        for(ArticleTypeEnum type : ArticleTypeEnum.values()) {
            if(type.getType()<3){
                map.put(type.getType().toString(),type.getName());
            }
        }
        return map;
    }

    public static Map<String,String> getAdTypeMap() {
        Map<String,String> map = new TreeMap<>();
        for(ArticleTypeEnum type : ArticleTypeEnum.values()) {
            if(type.getType()>30 && type.getType()<40){
                map.put(type.getType().toString(),type.getName());
            }
        }
        return map;
    }


    public static String getNameByType(Integer type) {
        for(ArticleTypeEnum at : ArticleTypeEnum.values()) {
            if(at.getType().intValue() == type.intValue()) {
                return at.getName();
            }
        }
        return "";
    }

    private String name;
    private Integer type;


    ArticleTypeEnum(Integer type, String name) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
