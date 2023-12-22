package com.base.sc.biz.dom.npdm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.base.sc.biz.vo.npdm.TableColumnVO;
import com.base.sc.util.db.DBOracleUtil;

public class Column {
    
    public List<TableColumnVO> getColumnList(String tableName) {
        List<Map<String, String>> rows = DBOracleUtil.selectList("column/COLUMN_LIST", tableName);
        
        List<TableColumnVO> columnList = new ArrayList<>();
        for ( Map<String, String> row : rows ) {
            TableColumnVO tableColumnVO = new TableColumnVO();
            tableColumnVO.setTable(row.get("tableName"));
            tableColumnVO.setSeq(Integer.parseInt(row.get("columnId")));
            tableColumnVO.setColumn(row.get("columnName"));
            tableColumnVO.setComments(row.get("comments"));
            tableColumnVO.setDataType(row.get("dataType"));
            tableColumnVO.setLen(Integer.parseInt(row.get("len")));
            tableColumnVO.setScale(Integer.parseInt(row.get("scaleLen")));
            tableColumnVO.setNullable(row.get("nullable"));

            columnList.add(tableColumnVO);
        }

        return columnList;
    }
}
