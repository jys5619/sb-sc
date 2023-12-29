package com.base.sc.framework.sysvo;

public class TableVO {
    private String dbmsName;
    private String tableName;
    private String comments;

    public String getDbmsName() {
        return dbmsName;
    }

    public void setDbmsName(String dbmsName) {
        this.dbmsName = dbmsName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
