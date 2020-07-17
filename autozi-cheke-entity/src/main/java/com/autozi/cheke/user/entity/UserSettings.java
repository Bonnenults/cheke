package com.autozi.cheke.user.entity;

import org.apache.commons.lang.StringUtils;

import com.autozi.common.dal.entity.IdEntity;
/**
 * Created by IntelliJ IDEA.
 * User: kai.liu
 * Date: 11-11-1
 * Time: 上午10:09
 */
public class UserSettings extends IdEntity {
	private static final long serialVersionUID = 7859714849037273684L;
	private String favorOperations;
    private Integer dataRows;

    public String getFavorOperations() {
        return favorOperations;
    }

    public void setFavorOperations(String favorOperations) {
        this.favorOperations = favorOperations;
    }

    public String[] getFavorOperationIds() {
        if (getFavorOperations() == null) {
            return new String[0];
        } else {
            return getFavorOperations().split(",");
        }
    }

    public void setFavorOperationIds(String[] ids) {
        setFavorOperations(StringUtils.join(ids, ","));
    }

    public Integer getDataRows() {
        return dataRows;
    }

    public void setDataRows(Integer dataRows) {
        this.dataRows = dataRows;
    }
}
