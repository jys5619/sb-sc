package com.base.sc.web.dev;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.h2.command.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.base.sc.biz.vo.dev.DevMenuVO;
import com.base.sc.biz.vo.dev.DevProjectVO;
import com.base.sc.framework.annotation.JsonResolver;
import com.base.sc.framework.db.QueryInfo;
import com.base.sc.web.dev.service.DevProjectService;

import jakarta.annotation.Resource;

@RestController
public class DevProjectController {
    @Resource(name = "devProjectService")
    private DevProjectService devProjectService;

    @RequestMapping(value = "/dev/project", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public @ResponseBody ResponseEntity<?> getDevMenuList() {
        try {
            Map<String, Object> result = devProjectService.getDevProject();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @RequestMapping(value = "/dev/project", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody ResponseEntity<?> saveDevMenuList(
            @JsonResolver(name = "project") DevProjectVO devProjectVO) {
        try {
            String sql = "SELECT ID\n" +
                        "     , MENU_LEVEL_1\n" + 
                        "     , MENU_LEVEL_2\n" + 
                        "     , MENU_LEVEL_3\n" + 
                        "     , MENU_LEVEL_4\n" + 
                        "     , MENU_TYPE\n" + 
                        "     , MENU_PATH\n" + 
                        "     , MENU_TYPE\n" + 
                        "     , DISCRIPTIONS\n" + 
                        "     , MODIFIER\n" + 
                        "     , MODIFIED\n" + 
                        "     , CREATOR\n" + 
                        "     , CREATED\n" + 
                        "  FROM DEV_MENU\n" + 
                        " WHERE MENU_LEVEL_1 = ::menuLevel1\n" + 
                        "   AND MENU_LEVEL_2 = ::menuLevel2\n";


            QueryInfo queryInfo = new QueryInfo("devMenu", sql);

            Map<String, Object> params = new HashMap<>();
            params.put("menuLevel1", "LV1");
            params.put("menuLevel2", "LV2");
            List<DevMenuVO> devMenuList = queryInfo.selectList(params);
            

            Map<String, Object> result = new HashMap<>();
            result.put("devMenu", devMenuList);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
}
