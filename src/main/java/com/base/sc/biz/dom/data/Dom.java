package com.base.sc.biz.dom.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.base.sc.biz.vo.data.DomAttributeVO;
import com.base.sc.biz.vo.data.DomRelationVO;
import com.base.sc.biz.vo.data.DomTreeVO;
import com.base.sc.util.DBUtil;

public class Dom {
    
    public List<DomTreeVO> getDomTreeList(String tableName) {
        List<Map<String, String>> rows = DBUtil.selectList("dom/DOM_TREE", tableName);
        
        List<DomTreeVO> resultList = new ArrayList<>();
        for ( Map<String, String> row : rows ) {
            DomTreeVO domTreeVO = new DomTreeVO();
            domTreeVO.setClassName(row.get("className"));
            domTreeVO.setTargetClassName(row.get("targetClassName"));
            domTreeVO.setLvl(Integer.parseInt(row.get("lvl")));
            resultList.add(domTreeVO);
        }

        return resultList;
    }

    
    public List<DomAttributeVO> getDomAttributeList(String tableName) {
        List<Map<String, String>> rows = DBUtil.selectList("dom/DOM_ATTRIBUTE", tableName);
        
        List<DomAttributeVO> resultList = new ArrayList<>();
        for ( Map<String, String> row : rows ) {
            DomAttributeVO domAttributeVO = new DomAttributeVO();
            domAttributeVO.setAttributeName(row.get("attributeName"));
            domAttributeVO.setDbmsTable(row.get("dbmsTable"));
            domAttributeVO.setDbmsColumn(row.get("dbmsColumn"));
            domAttributeVO.setColumnAlias(row.get("columnAlias"));
            domAttributeVO.setDataType(Integer.parseInt(row.get("dataType")));
            domAttributeVO.setDataTypeName(row.get("dataTypeName"));
            domAttributeVO.setDefaultValue(row.get("defaultValue"));
            domAttributeVO.setValueSettingInfo(row.get("valueSettingInfo"));
            domAttributeVO.setLengths(Integer.parseInt(row.get("lengths")));
            domAttributeVO.setClassName(row.get("className"));
            domAttributeVO.setDefinedClassName(row.get("definedClassName"));
            resultList.add(domAttributeVO);
        }

        return resultList;
    }
    
    public List<DomRelationVO> getDomRelationList(String tableName) {
        List<Map<String, String>> rows = DBUtil.selectList("dom/DOM_RELATION_LIST", tableName);
        
        List<DomRelationVO> resultList = new ArrayList<>();
        for ( Map<String, String> row : rows ) {
            DomRelationVO domRelationVO = new DomRelationVO();
            domRelationVO.setNames(row.get("names"));
            domRelationVO.setDbmsTable(row.get("dbmsTable"));
            domRelationVO.setFromClass(row.get("fromClass"));
            domRelationVO.setToClass(row.get("toClass"));
            domRelationVO.setFromRelationship(row.get("fromRelationship"));
            domRelationVO.setToRelationship(row.get("toRelationship"));
            domRelationVO.setRemarks(row.get("remarks"));
            domRelationVO.setOwners(row.get("owners"));
            resultList.add(domRelationVO);
        }

        return resultList;
    }

}
