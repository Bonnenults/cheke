package com.autozi.cheke.web.basic.vo;

import com.autozi.cheke.util.web.BaseView;

/**
 * Created by lbm on 2017/12/1.
 */
public class AreaView extends BaseView {
    private String name;
    private String areaCode;
    private Long parentId;
    private Integer areaLevel;  //IAreaLevel

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getAreaLevel() {
        return areaLevel;
    }

    public void setAreaLevel(Integer areaLevel) {
        this.areaLevel = areaLevel;
    }
}
