package com.base.sc.framework.db.queryData;

import java.util.List;

public class QueryExecutorParam {
    private String sql;
    private List<Object> paramList;

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<Object> getParamList() {
        return paramList;
    }

    public Object getParam(int i) {
        return paramList.get(i);
    }

    public void setParamList(List<Object> paramList) {
        this.paramList = paramList;
    }

}
