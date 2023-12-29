package com.base.sc.framework.sysvo;

public class ColumnVO {
    private String tableName;
    private String columnName;
    private Integer sortNo;
    private Object defaults;
    private Boolean nullable;
    private String dataType;
    private Integer len;

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

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    public Object getDefaults() {
        return defaults;
    }

    public void setDefaults(Object defaults) {
        this.defaults = defaults;
    }

    public Boolean getNullable() {
        return nullable;
    }

    public void setNullable(Boolean nullable) {
        this.nullable = nullable;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Integer getLen() {
        return len;
    }

    public void setLen(Integer len) {
        this.len = len;
    }
}
