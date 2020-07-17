package com.autozi.cheke.user.entity;

/**
 * Created with IntelliJ IDEA.
 * User: kai.liu
 * Date: 12-5-31
 * Time: 上午11:36
 */
public class DictAttribute implements Comparable<DictAttribute> {

    private String columnName = "";
    private String dataType = "";
    private String comments;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int compareTo(DictAttribute o) {
        return this.columnName.compareTo(o.getColumnName());
    }

    @Override
    public int hashCode() {
        return (columnName + dataType).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        else if (obj instanceof DictAttribute) {
            DictAttribute another = (DictAttribute) obj;
            return this.getColumnName().equals(another.getColumnName()) && this.getDataType().equals(another.getDataType());
        } else {
            return false;
        }
    }
}
