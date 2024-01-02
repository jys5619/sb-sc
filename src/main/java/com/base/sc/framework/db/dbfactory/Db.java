package com.base.sc.framework.db.dbfactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface Db {
    Connection getConnection();

    void setPreparedStatement(PreparedStatement psmt, List<Object> paramList) throws SQLException;

    List<Map<String, Object>> getResultDataList(ResultSetMetaData metaData, ResultSet rs) throws SQLException;
}
