package com.base.sc.framework.db.queryData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.base.sc.biz.vo.root.ObjectVO;

public class SelectQueryResult<T extends ObjectVO> {

    private List<SelectQueryColumnInfo> columns;
    private T data;

    public SelectQueryResult() {
        this.columns = new ArrayList<>();
        this.data = null;
    }

    public List<SelectQueryColumnInfo> getColumns() {
        return columns;
    }

    public void setColumns(List<SelectQueryColumnInfo> columns) {
        this.columns = columns;
    }

    public void addColumn(String name, String type) {
        SelectQueryColumnInfo columnInfo = new SelectQueryColumnInfo();
        columnInfo.setName(name);
        columnInfo.setType(type);
        columns.add(columnInfo);
    }

    public T getData() {
        return data;
    }

    public void setData(Map<String, Object> resultData, Class<T> clazz) {
        this.data = QueryUtil.convertToObjectVO(resultData, clazz);
    }

    public void setData(T objectVO) {
        this.data = objectVO;
    }

}
