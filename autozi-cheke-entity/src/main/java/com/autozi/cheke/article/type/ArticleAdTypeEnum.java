//package com.autozi.cheke.article.type;
//
//import java.util.Map;
//import java.util.TreeMap;
//
///**
// * Created by Administrator on 2018/5/14.
// */
//public enum ArticleAdTypeEnum {
//    VIDEO(1,"视频"),SINGLE(2,"单图推广"),MULTIGAR(3,"多图推广");
//
//
//    public static Map<String,String> getTypeMap() {
//        Map<String,String> map = new TreeMap<>();
//        for(ArticleAdTypeEnum type : ArticleAdTypeEnum.values()) {
//            map.put(type.getType().toString(),type.getName());
//        }
//        return map;
//    }
//
//
//    public static String getNameByType(Integer type) {
//        for(ArticleAdTypeEnum at : ArticleAdTypeEnum.values()) {
//            if(at.getType().intValue() == type.intValue()) {
//                return at.getName();
//            }
//        }
//        return "";
//    }
//
//    private String name;
//    private Integer type;
//
//
//    ArticleAdTypeEnum(Integer type, String name) {
//        this.name = name;
//        this.type = type;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public Integer getType() {
//        return type;
//    }
//
//    public void setType(Integer type) {
//        this.type = type;
//    }
//}
