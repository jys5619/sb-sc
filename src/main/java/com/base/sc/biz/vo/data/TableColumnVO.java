package com.base.sc.biz.vo.data;

public class TableColumnVO {
    private String table;
    private Integer seq;
    private String column;
    private String comments;
    private String dataType;
    private Integer len;
    private Integer scale;
    private String nullable;

    public String getTable() {
        return table;
    }
    public void setTable(String table) {
        this.table = table;
    }
    public Integer getSeq() {
        return seq;
    }
    public void setSeq(Integer seq) {
        this.seq = seq;
    }
    public String getColumn() {
        return column;
    }
    public void setColumn(String column) {
        this.column = column;
    }
    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }
    public String getDataType() {
        return dataType;
    }
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
    public Integer getLen() {
        return len;
    }
    public void setLen(Integer len) {
        this.len = len;
    }
    public Integer getScale() {
        return scale;
    }
    public void setScale(Integer scale) {
        this.scale = scale;
    }
    public String getNullable() {
        return nullable;
    }
    public void setNullable(String nullable) {
        this.nullable = nullable;
    }
}
