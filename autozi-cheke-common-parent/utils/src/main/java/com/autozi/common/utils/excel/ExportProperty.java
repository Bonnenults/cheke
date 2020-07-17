package com.autozi.common.utils.excel;

/**
 * Created by IntelliJ IDEA.
 * User: kai.liu
 * Date: 11-11-14
 * Time: 下午5:18
 */
class ExportProperty {

    private String propertyName;
    private int rowIndex;
    private int columnIndex;
    private int columnSpan;

    public ExportProperty(String propertyName, int rowIndex, int columnIndex) {
        this.propertyName = propertyName;
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
        this.columnSpan = 1;
    }


    public String getPropertyName() {
        return propertyName;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public int getColumnSpan() {
        return columnSpan;
    }

    public void setColumnSpan(int columnSpan) {
        this.columnSpan = columnSpan;
    }

    public int getLastColumnIndex() {
        return this.columnIndex + this.columnSpan - 1;
    }
}
