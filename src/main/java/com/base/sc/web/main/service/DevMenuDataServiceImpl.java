package com.base.sc.web.main.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.base.sc.util.db.DBH2Util;

@Service("devMenuDataService")
public class DevMenuDataServiceImpl implements DevMenuDataService {

    @Override
    public void test() {
        List<Map<String, String>> menuFileList = DBH2Util.selectList("menu/DEV_MENU_LIST");
        for ( Map<String, String> row : menuFileList) {
            System.out.println(row.get("id"));
            System.out.println(row.get("menuFile"));
        }
    }
}
