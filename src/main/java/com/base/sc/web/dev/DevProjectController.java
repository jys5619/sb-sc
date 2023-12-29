package com.base.sc.web.dev;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import com.base.sc.framework.db.QueryExecutor;
import com.base.sc.framework.db.queryData.SelectQueryListResult;
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

    @RequestMapping(value = "/javascript.js", method = RequestMethod.GET, produces = "application/text; charset=utf-8")
    public @ResponseBody ResponseEntity<?> getJavascript() {
        try {
            String result = "alert(3);";
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
            // String sql = "SELECT *\n" +
            // " FROM DEV_MENU\n" +
            // " WHERE MENU_LEVEL_1 = ::menuLevel1\n" +
            // " AND MENU_LEVEL_2 = ::menuLevel2\n";

            // SelectQueryInfo queryInfo = new SelectQueryInfo(sql);

            // Map<String, Object> params = new HashMap<>();
            // params.put("menuLevel1", "LV1");
            // params.put("menuLevel2", "LV2");
            // List<DevMenuVO> devMenuList = queryInfo.selectList(params, DevMenuVO.class);

            DevMenuVO devMenuVO = new DevMenuVO();
            devMenuVO.setId("6022b95bUf0cdU40c9U891cU3d285d44e933");
            // devMenuVO.setModified(new DateTimeField(new Date()));
            // devMenuVO.setLevel1("LV11");
            // devMenuVO.setLevel2("LV22");
            // devMenuVO.setLevel3("LV33");
            // devMenuVO.setLevel4("LV44");
            // devMenuVO.setMenuType("TP");
            // devMenuVO.setDescriptions("DESC...");

            // int i = QueryExecutor.save(devMenuVO);
            SelectQueryListResult resultData = QueryExecutor.selectList(devMenuVO);

            Map<String, Object> result = new HashMap<>();
            result.put("devMenu", resultData);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
}
