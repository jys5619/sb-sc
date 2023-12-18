package com.base.sc.biz.dom.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.base.sc.biz.vo.data.MenuVO;
import com.base.sc.util.DBUtil;

public class Menu {
    
    public List<MenuVO> getMenuList(String tableName) {
        List<Map<String, String>> rows = DBUtil.selectList("menu/MENU_LIST", tableName);
        
        List<MenuVO> resultList = new ArrayList<>();
        for ( Map<String, String> row : rows ) {
            MenuVO menuVO = new MenuVO();
            menuVO.setRn(row.get("rn"));
            menuVO.setLvl(Integer.valueOf(row.get("lvl")));
            menuVO.setSorting(row.get("sorting"));
            menuVO.setPaths(row.get("paths"));
            menuVO.setParentMenu(row.get("parentMenu"));
            menuVO.setChildMenu(row.get("childMenu"));
            menuVO.setLabels(row.get("labels"));
            menuVO.setLabelsKr(row.get("labelsKr"));
            menuVO.setLinkHerf(row.get("linkHerf"));
            menuVO.setLinkAlt(row.get("linkAlt"));
            menuVO.setImages(row.get("images"));
            menuVO.setAccessExpression(row.get("accessExpression"));
            menuVO.setModuleName(row.get("moduleName"));

            resultList.add(menuVO);
        }

        return resultList;
    }

}
