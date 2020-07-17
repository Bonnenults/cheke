package com.autozi.cheke.article.type;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by wang-lei on 2017/11/24.
 */
public enum  ArticleStatus {
   ALL(-100,"全部"), WAITING(0,"待发布"),REVIEW(1,"审核中"),PUBLISH(2,"已上线"),OFFLINE(4,"已下线"),
    REFUSE(-1,"已拒绝"),CANCEL(-2,"已取消"),DELETE(-3,"已删除");


    public static Map<String,String> getStatusMap() {
        Map<String,String> statusMap = new TreeMap<>();
        for(ArticleStatus status : ArticleStatus.values()) {
            statusMap.put(status.getType().toString(),status.getName());
        }
        return statusMap;
    }


    public static String getNameByType(Integer type) {
        for(ArticleStatus status : ArticleStatus.values()) {
            if(type.intValue() == status.getType().intValue()) {
                return status.getName();
            }
        }
        return "";
    }


    private Integer type;
    private String name;

    ArticleStatus(Integer type, String name) {
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
