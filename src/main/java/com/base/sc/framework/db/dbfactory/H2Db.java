package com.base.sc.framework.db.dbfactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.springframework.core.io.ClassPathResource;

import com.base.sc.biz.common.DateField;
import com.base.sc.biz.common.DateTimeField;
import com.base.sc.util.StrUtil;

public class H2Db implements Db {

    private Connection conn;

    public H2Db() {

    }

    @Override
    public Connection getConnection() {
        ClassPathResource resource = new ClassPathResource("application.properties");
        Properties appProps = new Properties();

        try {
            appProps.load(resource.getInputStream());
            Class.forName("org.h2.Driver");
            this.conn = DriverManager.getConnection("jdbc:h2:~/db/data",
                    appProps.getProperty("spring.datasource.username"),
                    appProps.getProperty("spring.datasource.password"));
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return this.conn;
    }

    @Override
    public void setPreparedStatement(PreparedStatement psmt, List<Object> paramList) throws SQLException {

        for (int i = 0; i < paramList.size(); i++) {
            Object param = paramList.get(i);
            if (param instanceof String) {
                psmt.setString(i + 1, param.toString());
            } else if (param instanceof Integer) {
                psmt.setInt(i + 1, Integer.valueOf(param.toString()));
            } else if (param instanceof Long) {
                psmt.setLong(i + 1, Long.valueOf(param.toString()));
            } else if (param instanceof Float) {
                psmt.setFloat(i + 1, Float.valueOf(param.toString()));
            } else if (param instanceof Double) {
                psmt.setDouble(i + 1, Double.valueOf(param.toString()));
            } else if (param instanceof Set) {
                psmt.setArray(i + 1, conn.createArrayOf("VARCHAR", ((Set<String>) param).toArray()));
            } else if (param instanceof List) {
                psmt.setArray(i + 1, conn.createArrayOf("VARCHAR", ((List<String>) param).toArray()));
            } else if (param instanceof DateField) {
                psmt.setString(i + 1, ((DateField) param).getVal());
            } else if (param instanceof DateTimeField) {
                psmt.setString(i + 1, ((DateTimeField) param).getVal());
            }
        }
    }

    @Override
    public List<Map<String, Object>> getResultDataList(ResultSetMetaData metaData, ResultSet rs) throws SQLException {
        List<Map<String, Object>> resultDataList = new ArrayList<>();
        while (rs.next()) {
            Map<String, Object> resultMap = new HashMap<>();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                if ("CHARACTER VARYING".equals(metaData.getColumnTypeName(i))) {
                    resultMap.put(StrUtil.getCamelCase(metaData.getColumnName(i)),
                            rs.getString(metaData.getColumnName(i)));
                } else if ("DATE".equals(metaData.getColumnTypeName(i))) {
                    resultMap.put(StrUtil.getCamelCase(metaData.getColumnName(i)),
                            new DateField(rs.getDate(metaData.getColumnName(i))));
                } else if ("TIMESTAMP".equals(metaData.getColumnTypeName(i))) {
                    resultMap.put(StrUtil.getCamelCase(metaData.getColumnName(i)),
                            new DateTimeField(rs.getDate(metaData.getColumnName(i))));
                }
            }

            resultDataList.add(resultMap);
        }

        return resultDataList;
    }

}
