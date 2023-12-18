package com.base.sc.util.db;

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

import com.base.sc.util.SqlUtil;
import com.base.sc.util.StrUtil;

public class DBH2Util implements DBUtil {
    
    public static Map<String, String> select(String id) {
        String sql = SqlUtil.getSql("main/" + id);

        List<Object> params = new ArrayList<>();

        Map<String, Object> result = search(sql, params);
        List<Map<String, String>> list = (List<Map<String, String>>)result.get("data");
        if ( list.size() == 0 ) return null;
        return list.get(0);
    }

    public static Map<String, String> select(String id, String param) {
        String sql = SqlUtil.getSql("main/" + id);

        List<Object> params = new ArrayList<>();
        params.add(param);

        Map<String, Object> result = search(sql, params);
        List<Map<String, String>> list = (List<Map<String, String>>)result.get("data");
        if ( list.size() == 0 ) return null;
        return list.get(0);
    }

    public static Map<String, String> select(String id, List<Object>  params) {
        String sql = SqlUtil.getSql("main/" + id);

        Map<String, Object> result = search(sql, params);
        List<Map<String, String>> list = (List<Map<String, String>>)result.get("data");
        if ( list.size() == 0 ) return null;
        return list.get(0);
    }

    public static List<Map<String, String>> selectList(String id) {
        String sql = SqlUtil.getSql("main/" + id);

        List<Object> params = new ArrayList<>();

        Map<String, Object> result = search(sql, params);
        return (List<Map<String, String>>)result.get("data");
    }

    public static List<Map<String, String>> selectList(String id, String param) {
        String sql = SqlUtil.getSql("main/" + id);

        List<Object> params = new ArrayList<>();
        params.add(param);

        Map<String, Object> result = search(sql, params);
        return (List<Map<String, String>>)result.get("data");
    }

    public static List<Map<String, String>> selectList(String id, List<Object> params) {
        String sql = SqlUtil.getSql("main/" + id);

        Map<String, Object> result = search(sql, params);
        return (List<Map<String, String>>)result.get("data");
    }

    protected static Map<String, Object> search(String sql, List<Object> params) {
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

            for ( int i = 0; i < params.size(); i++) {
                Object param = params.get(i);
                if ( param instanceof String) {
                    psmt.setString(i+1, param.toString());
                } else if ( param instanceof Integer ) {
                    psmt.setInt(i+1, Integer.valueOf(param.toString()));
                }
            }


            rs = psmt.executeQuery();

            if ( rs == null ) return result;

            ResultSetMetaData metaData = rs.getMetaData();
            
            for ( int i = 1; i <= metaData.getColumnCount(); i++ ) {
                metaList.add(StrUtil.getCamelCase(metaData.getColumnName(i)));
            }

            while(rs.next()) {
                Map<String, String> rsData = new HashMap<>();
                for ( int i = 1; i <= metaData.getColumnCount(); i++ ) {
                    if ( "DATE".equals(metaData.getColumnTypeName(i)) ) {
                        rsData.put(StrUtil.getCamelCase(metaData.getColumnName(i)), String.valueOf(rs.getDate(i).getTime()));
                    } else {
                        rsData.put(StrUtil.getCamelCase(metaData.getColumnName(i)), rs.getString(metaData.getColumnName(i)));
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
        
        return result;
    }

    public static Connection getConnection() { 
		Connection conn = null;
		try {
            Class.forName("org.h2.Driver");
			conn = DriverManager.getConnection("jdbc:h2:~/db/data", "sa", "");
		} catch (SQLException| ClassNotFoundException  e) {
			e.printStackTrace();
		}
		return conn;
	}

}
