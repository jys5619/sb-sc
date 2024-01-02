package com.base.sc.framework.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.base.sc.biz.vo.root.ObjectRootVO;
import com.base.sc.biz.vo.root.ObjectVO;
import com.base.sc.framework.db.queryData.QueryExecutorParam;
import com.base.sc.framework.db.queryData.QueryUtil;
import com.base.sc.framework.db.dbfactory.Db;
import com.base.sc.framework.db.dbfactory.DbFactory;
import com.base.sc.framework.db.queryData.ObjectQueryInfo;
import com.base.sc.framework.db.queryData.SelectQueryListResult;
import com.base.sc.framework.db.queryData.SelectQueryResult;

public class QueryExecutor {
    private static QueryExecutor queryExecutor;

    protected QueryExecutor() {

    }

    public static QueryExecutor getQueryExecutor() {
        if (queryExecutor == null) {
            queryExecutor = new QueryExecutor();
        }

        return queryExecutor;
    }

    public static <T> T select(String queryId, List<Object> params, Class<T> clazz) {
        return QueryExecutor.getQueryExecutor().search(queryId, params, clazz);
    }

    public static <T> List<T> selectList(String queryId, List<Object> params, Class<T> clazz) {
        return QueryExecutor.getQueryExecutor().searchList(queryId, params, clazz);
    }

    public static <T extends ObjectVO> SelectQueryResult<? extends ObjectVO> select(T searchObject) {
        ObjectQueryInfo objectQueryInfo = QueryFactory.getQueryFactory().getObjectQueryInfo(searchObject.getClass());
        QueryExecutorParam queryExecutorParam = objectQueryInfo.getSelectExecutorParam(searchObject);
        return QueryExecutor.getQueryExecutor().search(queryExecutorParam, searchObject.getClass());

    }

    public static <T extends ObjectVO> SelectQueryListResult<? extends ObjectVO> selectList(T searchObject) {
        ObjectQueryInfo objectQueryInfo = QueryFactory.getQueryFactory().getObjectQueryInfo(searchObject.getClass());
        QueryExecutorParam queryExecutorParam = objectQueryInfo.getSelectExecutorParam(searchObject);
        return QueryExecutor.getQueryExecutor().searchList(queryExecutorParam, searchObject.getClass());
    }

    public static <T extends ObjectRootVO> int save(T saveObject) {
        ObjectQueryInfo objectQueryInfo = QueryFactory.getQueryFactory().getObjectQueryInfo(saveObject.getClass());
        QueryExecutorParam queryExecutorParam = objectQueryInfo.getSaveExecutorParam(saveObject);
        return QueryExecutor.getQueryExecutor().executeUpdate(queryExecutorParam);
    }

    public static <T extends ObjectRootVO> int saveList(List<T> saveObjectList) {
        List<QueryExecutorParam> queryExecutorParamList = new ArrayList<>();
        for (T saveObject : saveObjectList) {
            ObjectQueryInfo objectQueryInfo = QueryFactory.getQueryFactory().getObjectQueryInfo(saveObject.getClass());
            QueryExecutorParam queryExecutorParam = objectQueryInfo.getSaveExecutorParam(saveObject);
            queryExecutorParamList.add(queryExecutorParam);
        }
        return QueryExecutor.getQueryExecutor().executeUpdateList(queryExecutorParamList);
    }

    private int executeUpdate(QueryExecutorParam queryExecutorParam) {
        List<QueryExecutorParam> queryExecutorParamList = new ArrayList<>();
        queryExecutorParamList.add(queryExecutorParam);
        return executeUpdateList(queryExecutorParamList);
    }

    private <T> T search(String queryId, List<Object> params, Class<T> clazz) {
        List<T> list = searchList(queryId, params, clazz);
        if (list.size() == 1) {
            return list.get(0);
        } else if (list.size() > 1) {
            throw new RuntimeException("조회됨 데이터가 2건 이상입니다.");
        } else {
            return null;
        }
    }

    private <T> List<T> searchList(String queryId, List<Object> params, Class<T> clazz) {
        List<T> list = new ArrayList<>();

        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try {
            String sql = QueryUtil.getSql(queryId);
            System.out.println("Execute Query :: ");
            System.out.println(sql);
            if (params != null) {
                System.out.println("[" +
                        String.join(",", params.stream().map(vo -> vo.toString()).toList())
                        + "]");
            }

            Db db = DbFactory.getDb();

            conn = db.getConnection();
            psmt = conn.prepareStatement(sql);

            if (params != null) {
                db.setPreparedStatement(psmt, params);
            }

            rs = psmt.executeQuery();

            if (rs == null)
                return null;

            ResultSetMetaData metaData = rs.getMetaData();

            List<Map<String, Object>> resultDataList = db.getResultDataList(metaData, rs);

            for (Map<String, Object> resultData : resultDataList) {
                list.add(QueryUtil.convertToObject(resultData, clazz));
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

        return list;
    }

    private <T extends ObjectVO> SelectQueryResult<T> search(QueryExecutorParam queryExecutorParam, Class<T> clazz) {
        SelectQueryListResult<T> selectQueryListResult = searchList(queryExecutorParam, clazz);
        SelectQueryResult<T> selectQueryResult = new SelectQueryResult<>();
        selectQueryResult.setColumns(selectQueryResult.getColumns());
        if (selectQueryListResult.getList().size() == 1) {
            selectQueryResult.setData(selectQueryListResult.getList().get(0));
        } else if (selectQueryListResult.getList().size() > 1) {
            throw new RuntimeException("조회됨 데이터가 2건 이상입니다.");
        } else {
            selectQueryResult.setData(null);
        }

        return selectQueryResult;
    }

    private <T extends ObjectVO> SelectQueryListResult<T> searchList(
            QueryExecutorParam queryExecutorParam,
            Class<T> clazz) {
        SelectQueryListResult<T> selectQueryResult = new SelectQueryListResult<>();
        List<Map<String, Object>> resultDataList = new ArrayList<>();

        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try {
            System.out.println("Execute Query :: ");
            System.out.println(queryExecutorParam.getSql());
            System.out.println("[" +
                    String.join(",", queryExecutorParam.getParamList().stream().map(vo -> vo.toString()).toList())
                    + "]");

            Db db = DbFactory.getDb();

            conn = db.getConnection();
            psmt = conn.prepareStatement(queryExecutorParam.getSql());

            db.setPreparedStatement(psmt, queryExecutorParam.getParamList());

            rs = psmt.executeQuery();

            if (rs == null)
                return null;

            ResultSetMetaData metaData = rs.getMetaData();

            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                selectQueryResult.addColumn(metaData.getColumnName(i), metaData.getColumnTypeName(i));
            }

            resultDataList = db.getResultDataList(metaData, rs);
            selectQueryResult.setList(resultDataList, clazz);
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

        return selectQueryResult;
    }

    private int executeUpdateList(List<QueryExecutorParam> queryExecutorParamList) {
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        int processCount = 0;

        try {
            Db db = DbFactory.getDb();
            conn = db.getConnection();
            conn.setAutoCommit(false);

            for (QueryExecutorParam queryExecutorParam : queryExecutorParamList) {
                System.out.println("Execute Query :: ");
                System.out.println(queryExecutorParam.getSql());
                System.out.println("[" +
                        String.join(",", queryExecutorParam.getParamList().stream().map(vo -> vo.toString()).toList())
                        + "]");

                psmt = conn.prepareStatement(queryExecutorParam.getSql());
                db.setPreparedStatement(psmt, queryExecutorParam.getParamList());
                // for (int i = 0; i < queryExecutorParam.getParamList().size(); i++) {
                // Object param = queryExecutorParam.getParam(i);
                // if (param instanceof String) {
                // psmt.setString(i + 1, param.toString());
                // } else if (param instanceof Integer) {
                // psmt.setInt(i + 1, Integer.valueOf(param.toString()));
                // }
                // }

                processCount += psmt.executeUpdate();
            }
            conn.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException e) {
                }
            }
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

        return processCount;
    }

}
