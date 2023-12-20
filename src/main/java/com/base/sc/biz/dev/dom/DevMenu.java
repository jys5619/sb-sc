package com.base.sc.biz.dev.dom;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.base.sc.biz.dev.vo.DevMenuVO;
import com.base.sc.util.db.DBH2Util;

public class DevMenu {
    
    public List<DevMenuVO> getDevMenuList() {
        List<Map<String, String>> rows = DBH2Util.selectList("menu/DEV_MENU_LIST");
        
        List<DevMenuVO> devMenuList = new ArrayList<>();
        for ( Map<String, String> row : rows ) {
            DevMenuVO devMenuVO = new DevMenuVO();
            devMenuVO.setId(row.get("id"));
            devMenuVO.setNames(row.get("names"));
            devMenuVO.setKorName(row.get("korName"));
            devMenuVO.setMenuPath(row.get("menuPath"));
            devMenuVO.setComments(row.get("comments"));
            devMenuList.add(devMenuVO);
        }

        return devMenuList;
    }
}
