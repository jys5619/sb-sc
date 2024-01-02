package com.base.sc.framework.db;

import java.util.HashMap;
import java.util.Map;

import com.base.sc.biz.vo.root.ObjectRootVO;
import com.base.sc.biz.vo.root.ObjectVO;
import com.base.sc.framework.db.queryData.ObjectQueryInfo;
import com.base.sc.framework.db.queryData.SelectQueryInfo;

public class QueryFactory {
    private static QueryFactory queryFactory;
    private Map<String, SelectQueryInfo> selectMap;
    private Map<String, ObjectQueryInfo> objectMap;

    protected QueryFactory() {
        selectMap = new HashMap<>();
        objectMap = new HashMap<>();
    }

    public static QueryFactory getQueryFactory() {
        if (queryFactory == null) {
            queryFactory = new QueryFactory();
        }

        return queryFactory;
    }

    public <T extends ObjectVO> ObjectQueryInfo getObjectQueryInfo(Class<T> clazz) {
        ObjectQueryInfo objectQueryInfo = null;
        if (!objectMap.containsKey(clazz.getSimpleName())) {
            objectQueryInfo = new ObjectQueryInfo(clazz);
            objectMap.put(clazz.getSimpleName(), objectQueryInfo);
        } else {
            objectQueryInfo = objectMap.get(clazz.getSimpleName());
        }

        return objectQueryInfo;
    }

    // public <T extends ObjectRootVO> SelectQueryInfo getSelectQueryInfo(Class<T>
    // clazz, String queryId) {
    // SelectQueryInfo selectQueryInfo = null;
    // String key = clazz.getSimpleName() + queryId;
    // if (selectMap.containsKey(key)) {
    // selectQueryInfo = new SelectQueryInfo(clazz);
    // selectMap.put(key, selectQueryInfo);
    // } else {
    // selectQueryInfo = selectMap.get(key);
    // }

    // return selectQueryInfo;
    // }

}
