package com.autozi.cheke.user.entity;

/**
 * Created with IntelliJ IDEA.
 * User: kai.liu
 * Date: 12-6-1
 * Time: 下午1:44
 */
public class TableAttribute {

    private String tableName;
    private String columnName;
    private String comments;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
