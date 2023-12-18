package com.base.sc.web.npdm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.base.sc.biz.npdm.dom.Column;
import com.base.sc.biz.npdm.dom.Dom;
import com.base.sc.biz.npdm.dom.Menu;
import com.base.sc.biz.npdm.dom.Organization;
import com.base.sc.biz.npdm.dom.Table;
import com.base.sc.biz.npdm.vo.DomAttributeVO;
import com.base.sc.biz.npdm.vo.DomRelationVO;
import com.base.sc.biz.npdm.vo.DomTreeVO;
import com.base.sc.biz.npdm.vo.MenuVO;
import com.base.sc.biz.npdm.vo.OrganizationVO;
import com.base.sc.biz.npdm.vo.TableColumnVO;
import com.base.sc.biz.npdm.vo.TableIndexVO;
import com.base.sc.biz.npdm.vo.TableVO;

@Service("tableDataService")
public class NpdmServiceImpl implements NpdmService {

    @Override
    public Map<String, Object> searchTableInfo(String keyword) {
        Table table = new Table();

        TableVO tableVO = table.getTable(keyword);
        List<TableColumnVO> columnList = table.getColumnList(keyword);
        List<TableIndexVO> tableIndexList = table.getIndexList(keyword);

        Map<String, Object> result = new HashMap<>();
        result.put("table", tableVO);
        result.put("column", columnList);
        result.put("index", tableIndexList);

        return result;
    }

    @Override
    public Map<String, Object> searchColumnInfo(String keyword) {
        Column column = new Column();
        List<TableColumnVO> columnList = column.getColumnList(keyword);

        Map<String, Object> result = new HashMap<>();
        result.put("column", columnList);
        return result;
    }

    @Override
    public Map<String, Object> searchDomInfo(String keyword) {
        Dom dom = new Dom();
        List<DomTreeVO> domTreeList = dom.getDomTreeList(keyword);
        List<DomAttributeVO> domAttributeList = dom.getDomAttributeList(keyword);
        List<DomRelationVO> domRelationList = dom.getDomRelationList(keyword);

        Map<String, Object> result = new HashMap<>();
        result.put("tree", domTreeList);
        result.put("attribute", domAttributeList);
        result.put("relation", domRelationList);

        if ( domAttributeList.size() > 0 ) {
            Table table = new Table();
            String tableName = domAttributeList.get(0).getDbmsTable();
            result.put("column", table.getColumnList(tableName));
            result.put("index", table.getIndexList(tableName));
        } else {
            result.put("column", new ArrayList<>());
            result.put("index", new ArrayList<>());
        }

        return result;
    }

    @Override
    public Map<String, Object> searchMenuInfo(String keyword) {
        Menu menu = new Menu();
        List<MenuVO> menuList = menu.getMenuList(keyword);

        Map<String, Object> result = new HashMap<>();
        result.put("menu", menuList);
        return result;
    }

    @Override
    public Map<String, Object> searchOrganizationInfo(String keyword) {
        Organization organization = new Organization();
        List<OrganizationVO> organizationList = organization.getOrganizationList(keyword);

        Map<String, Object> result = new HashMap<>();
        result.put("organization", organizationList);
        return result;
    }
    
}
