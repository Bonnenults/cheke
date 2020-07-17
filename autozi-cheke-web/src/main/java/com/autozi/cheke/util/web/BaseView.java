package com.autozi.cheke.util.web;


import com.autozi.common.dal.entity.IdEntity;

/**
 * User: 刘宇
 * Date: 14-3-17
 * Time: 下午6:06
 */
public abstract class BaseView extends IdEntity {
    private String formTokenKey;

    public String getFormTokenKey() {
        return formTokenKey;
    }

    public void setFormTokenKey(String formTokenKey) {
        this.formTokenKey = formTokenKey;
    }

    private String carModel;//适用车型
    private String carModelFull;//车型备注的全部信息

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarModelFull() {
        return carModelFull;
    }

    public void setCarModelFull(String carModelFull) {
        this.carModelFull = carModelFull;
    }
}
