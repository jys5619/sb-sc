package com.base.sc.framework.db.queryData;

import java.util.ArrayList;
import java.util.List;

public class SelectQueryInfo {
    private String sql;
    private List<String> sqlLineList;
    private List<QueryParamField> paramList;
    private List<String> columnList;

    public SelectQueryInfo(String sql) {
        this.sql = sql;
        this.paramList = new ArrayList<>();
        this.sqlLineList = new ArrayList<>();
        this.columnList = new ArrayList<>();
        setValue();
    }

    public String getSql() {
        return sql;
    }

    public List<String> getColumnList() {
        return columnList;
    }

    private void setValue() {
        String[] sqllins = sql.split("\n");

        for (String line : sqllins) {
            if (line.indexOf("::") > -1 || line.indexOf("--") > -1) {
                QueryParamField queryParamField = new QueryParamField(line);
                paramList.add(queryParamField);
                sqlLineList.add("%" + (paramList.size() - 1));
            } else {
                sqlLineList.add(line);
            }
        }
    }

}
