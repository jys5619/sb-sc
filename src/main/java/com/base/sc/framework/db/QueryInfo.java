package com.base.sc.framework.db;


import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.base.sc.util.ObjUtil;
import com.base.sc.util.StrUtil;

public class QueryInfo {
    private String sql;
    private String className;
    private List<String> sqlLineList;
    private List<QueryParamField> paramList;
    private List<String> columnList;

    public QueryInfo(String id, String sql) {
        this.sql = sql;
        this.className = "com.base.sc.biz.vo.dev.DevMenuVO";
        this.paramList = new ArrayList<>();
        this.sqlLineList = new ArrayList<>();
        this.columnList = new ArrayList<>();
        setValue();
    }

    public String getSql() {
        return sql;
    }

    private void setValue() {
        String [] sqllins = sql.split("\n");
        
        for ( String line : sqllins) {
            if ( line.indexOf("::") > -1 || line.indexOf("--") > -1 ) {
                QueryParamField queryParamField = new QueryParamField(line);
                paramList.add(queryParamField);
                sqlLineList.add("%" + (paramList.size() - 1));
            } else {
                sqlLineList.add(line);
            }
        }
    }

    public <T extends Object> List<T> selectList(Map<String, Object> params) {
        StringBuilder sb = new StringBuilder();
        List<Object> bindParamList = new ArrayList<>();
        for ( String line : sqlLineList) {
            if ( line.startsWith("%") ) {
                Integer index = Integer.valueOf(line.substring(1));
                QueryParamField queryParamField = paramList.get(index);
                if ( params.keySet().containsAll(queryParamField.getParams()) ) {
                    sb.append(queryParamField.getAndString()).append("\n");
                    bindParamList.addAll(queryParamField.getBindParam(params));
                }
            } else {
                sb.append(line).append("\n");
            }
        }

        System.out.println(sb.toString());
        List<Map<String, Object>> result = searchList(sb.toString(), bindParamList);
        QueryResultData<T> queryResultData = new QueryResultData<>();
        
        List<T> list = new ArrayList<>();
        try {
            Class<?> cls = Class.forName(className);
            
            
            for ( Map<String, Object> map : result ) {
                list.add((T)convertToValueObject(map, cls));
            }
        } catch (Exception ex ) {
            ex.printStackTrace();
            return new ArrayList<>();
        }

        return list;
    }

    
    /**
     * @param <T>
     * @param clazz
     * @return
     */
    private Object getObjectValue(Class<?> clazz) {
        Object obj = null;
        try {
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            if (constructor == null) {
                return null;
            }
            obj = constructor.newInstance();
        } catch (Exception e) {
            if (obj != null) {
                return (T)obj;
            }
            obj = new Object();
        }
        return obj;
    }

    
    // Map -> VO
    public <T> T convertToValueObject(Map<String, Object> map, Class<T> type) {
        try {
            Object instance = getObjectValue(type);
            Field[] fields = type.getDeclaredFields();

            for (Map.Entry<String, Object> entry : map.entrySet()) {
                for (Field field : fields) {
                    if (entry.getKey().equals(field.getName())) {
                        field.setAccessible(true);

                        Object value = Objects.isNull(entry.getValue()) && field.getType().isPrimitive()
                                ? null // getDefaultValue(field.getType())
                                : map.get(field.getName());

                        field.set(instance, value);
                        break;
                    }
                }
            }
            return (T)instance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    
    protected List<Map<String, Object>> searchList(String sql, List<Object> params) {
        List<Map<String, Object>> resultDataList = new ArrayList<>();

        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            psmt = conn.prepareStatement(sql);

            for ( int i = 0; i < params.size(); i++) {
                Object param = params.get(i);
                if ( param instanceof String) {
                    psmt.setString(i+1, param.toString());
                } else if ( param instanceof Integer ) {
                    psmt.setInt(i+1, Integer.valueOf(param.toString()));
                }
            }

            rs = psmt.executeQuery();

            if ( rs == null ) return resultDataList;

            ResultSetMetaData metaData = rs.getMetaData();
            
            for ( int i = 1; i <= metaData.getColumnCount(); i++ ) {
                columnList.add(StrUtil.getCamelCase(metaData.getColumnName(i)));
            }

            while(rs.next()) {
                Map<String, Object> rsData = new HashMap<>();
                for ( int i = 1; i <= columnList.size(); i++ ) {
                    if ( "DATE".equals(metaData.getColumnTypeName(i)) ) {
                        rsData.put(columnList.get(i-1), new Date(rs.getTimestamp(i).getTime()));//(new java.util.Date()).setTime(rs.getDate(i).getTime()));
                    } else {
                        rsData.put(columnList.get(i-1), rs.getObject(metaData.getColumnName(i)));
                    }
                }
                resultDataList.add(rsData);
            }

        } catch ( Exception ex ) {
            ex.printStackTrace();
        } finally {
            if ( rs != null ) try { rs.close(); } catch ( Exception e ) {}
            if ( psmt != null ) try { psmt.close(); } catch ( Exception e ) {}
            if ( conn != null ) try { conn.close(); } catch ( Exception e ) {}
        }
        
        return resultDataList;
    }

    public static Connection getConnection() { 
		Connection conn = null;
		try {
            Class.forName("org.h2.Driver");
			conn = DriverManager.getConnection("jdbc:h2:~/db/data", "sa", "");
		} catch (SQLException | ClassNotFoundException  e) {
			e.printStackTrace();
		}
		return conn;
	}
}
