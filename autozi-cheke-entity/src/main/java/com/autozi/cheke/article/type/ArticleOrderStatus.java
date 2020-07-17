package com.autozi.cheke.article.type;

/**
 * Created by wang-lei on 2017/12/18.
 */
public enum ArticleOrderStatus {
    ACTIVATING(1,"任务进行中"),ENDING(0,"任务已完成");

    ArticleOrderStatus(Integer type, String name) {
        Type = type;
        this.name = name;
    }

    private Integer Type;
    private String name;


    public Integer getType() {
        return Type;
    }

    public void setType(Integer type) {
        Type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
