package com.base.sc.biz.vo.npdm;

import java.util.List;

public class TableIndexVO  {
    private String index;
    private List<String> columns;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }
    
}
