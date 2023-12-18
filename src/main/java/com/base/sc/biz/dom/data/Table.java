package com.base.sc.biz.dom.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.base.sc.biz.vo.data.TableColumnVO;
import com.base.sc.biz.vo.data.TableIndexVO;
import com.base.sc.biz.vo.data.TableVO;
import com.base.sc.util.DBUtil;
import com.base.sc.util.StrUtil;

public class Table {
    

    public Table() {
    }

    public TableVO getTable(String tableName) {
        Map<String, String> row = DBUtil.select("table/TABLE", tableName);

        TableVO tableVO = new TableVO();

        if ( row != null ) {
            tableVO.setOwner(row.get("owner"));
            tableVO.setTable(row.get("tableName"));
            tableVO.setComments(row.get("comments"));
        }
        
        return tableVO;
    }

    public List<TableColumnVO> getColumnList(String tableName) {
        List<Map<String, String>> rows = DBUtil.selectList("table/TABLE_COLUMN_LIST", tableName);
        List<Map<String, String>> commentsRows = DBUtil.selectList("table/TABLE_COMMENTS_LIST", tableName);

        Map<String, String> commentsMap = commentsRows.stream().collect(Collectors.toMap(data -> data.get("dbmsColumn"), data -> data.get("comments")));
        
        List<TableColumnVO> columnList = new ArrayList<>();
        for ( Map<String, String> row : rows ) {
            TableColumnVO tableColumnVO = new TableColumnVO();
            tableColumnVO.setSeq(Integer.parseInt(row.get("columnId")));
            tableColumnVO.setColumn(row.get("columnName"));
            // tableColumnVO.setComments(row.get("comments"));
            tableColumnVO.setComments(commentsMap.get(row.get("columnName")));
            tableColumnVO.setDataType(row.get("dataType"));
            tableColumnVO.setLen(Integer.parseInt(row.get("len")));
            tableColumnVO.setScale(Integer.parseInt(row.get("scaleLen")));
            tableColumnVO.setNullable(row.get("nullable"));

            columnList.add(tableColumnVO);
        }

        return columnList;
    }

    public List<TableIndexVO> getIndexList(String tableName) {
        List<Map<String, String>> rows = DBUtil.selectList("table/TABLE_INDEX_LIST", tableName);

        List<TableIndexVO> tableIndexList = new ArrayList<>();

        Map<String, List<String>> colGroup = new HashMap<>();
        String indexName = "";
        List<String> colList = null;
        for ( Map<String, String> row : rows ) {
            if ( !StrUtil.equals(indexName, row.get("indexName")) ) {
                indexName =  row.get("indexName");
                colList = new ArrayList<>();
                colGroup.put(indexName, colList);
            } 
            colList.add(row.get("columnName"));
        }

        for ( String key : colGroup.keySet() ) {
            TableIndexVO tableindexVO = new TableIndexVO();
            tableindexVO.setIndex(key);
            tableindexVO.setColumns(colGroup.get(key));
            tableIndexList.add(tableindexVO);
        }

        return tableIndexList;
    }
}
