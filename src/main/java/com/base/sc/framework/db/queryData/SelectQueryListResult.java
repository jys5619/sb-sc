package com.base.sc.framework.db.queryData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.base.sc.biz.vo.root.ObjectVO;

public class SelectQueryListResult<T extends ObjectVO> {

    private List<SelectQueryColumnInfo> columns;
    private List<T> list;

    public SelectQueryListResult() {
        this.columns = new ArrayList<>();
        this.list = new ArrayList<>();
    }

    public List<SelectQueryColumnInfo> getColumns() {
        return columns;
    }

    public void setColumns(List<SelectQueryColumnInfo> columns) {
        this.columns = columns;
    }

    public void addColumn(String name, String type) {
        SelectQueryColumnInfo selectQueryColumnInfo = new SelectQueryColumnInfo();
        selectQueryColumnInfo.setName(name);
        selectQueryColumnInfo.setType(type);
        columns.add(selectQueryColumnInfo);
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<Map<String, Object>> resultDataList, Class<T> clazz) {
        for (Map<String, Object> resultData : resultDataList) {
            this.list.add(QueryUtil.convertToObjectVO(resultData, clazz));
        }
    }

}
