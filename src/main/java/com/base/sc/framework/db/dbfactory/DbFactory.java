package com.base.sc.framework.db.dbfactory;

public class DbFactory {

    public static Db getDb() {
        return getDb(DbType.H2);

    }

    public static Db getDb(DbType dbType) {

        Db db = null;
        if (DbType.H2.equals(dbType)) {
            db = new H2Db();
        }

        return db;

    }
}
