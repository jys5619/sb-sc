package com.base.sc.util.db;

import java.lang.reflect.Field;
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
import java.util.Objects;

import com.base.sc.util.StrUtil;

public interface DBUtil {

    public static <T> T search(String sql, List<Object> params) {
        Map<String, Object> result = new HashMap<>();
        List<String> metaList = new ArrayList<>();
        List<Map<String, String>> resultDataList = new ArrayList<>();

        result.put("columns", metaList);
        result.put("data", resultDataList);

        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            psmt = conn.prepareStatement(sql);

            for (int i = 0; i < params.size(); i++) {
                Object param = params.get(i);
                if (param instanceof String) {
                    psmt.setString(i + 1, param.toString());
                } else if (param instanceof Integer) {
                    psmt.setInt(i + 1, Integer.valueOf(param.toString()));
                } else if (param instanceof Long) {
                    psmt.setLong(i + 1, Long.valueOf(param.toString()));
                }
            }

            rs = psmt.executeQuery();

            if (rs == null)
                return null;

            ResultSetMetaData metaData = rs.getMetaData();

            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                metaList.add(StrUtil.getCamelCase(metaData.getColumnName(i)));
            }

            while (rs.next()) {
                Map<String, String> rsData = new HashMap<>();
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    if ("DATE".equals(metaData.getColumnTypeName(i))) {
                        rsData.put(StrUtil.getCamelCase(metaData.getColumnName(i)),
                                String.valueOf(rs.getDate(i).getTime()));
                    } else {
                        rsData.put(StrUtil.getCamelCase(metaData.getColumnName(i)),
                                rs.getString(metaData.getColumnName(i)));
                    }
                }
                resultDataList.add(rsData);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (Exception e) {
                }
            if (psmt != null)
                try {
                    psmt.close();
                } catch (Exception e) {
                }
            if (conn != null)
                try {
                    conn.close();
                } catch (Exception e) {
                }
        }

        return instance;
    }

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection("jdbc:h2:~/db/data", "sa", "");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static <T> T convertToValueObject(Map<String, Object> map) {
        try {
            if (Objects.isNull(map) || map.isEmpty()) {
                throw new IllegalArgumentException("map is null or empty");
            }

            DBUtilA<T> dbUtil = new DBUtilA<>();
            T instance = dbUtil.getT();
            Class<?> type = instance.getClass();
            Field[] fields = type.getDeclaredFields();

            for (Map.Entry<String, Object> entry : map.entrySet()) {
                for (Field field : fields) {
                    if (entry.getKey().equals(field.getName())) {
                        field.setAccessible(true);

                        Object value = Objects.isNull(entry.getValue()) && field.getType().isPrimitive()
                                ? null
                                : map.get(field.getName());

                        field.set(instance, value);
                        break;
                    }
                }
            }
            return instance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
