package com.base.sc.framework.db.dbfactory;

public enum DbType {

    ORACLE("ORACLE"),
    H2("H2");

    private String db;

    DbType(String db) {
        this.db = db;
    }

    public String getDb() {
        return db;
    }
}
