package com.base.sc.web.dev;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.base.sc.biz.vo.dev.DevProjectVO;
import com.base.sc.framework.JsonResolver;
import com.base.sc.util.ObjUtil;
import com.base.sc.web.dev.service.DevProjectService;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    public @ResponseBody ResponseEntity<?> saveDevMenuList(@JsonResolver(name="project") DevProjectVO devProjectVO) {
        try {
            // ObjectMapper objectMapper = new ObjectMapper();
            // Class<?> clazz = dataMap.get("projectList").getClass();
            // clazz.newInstance();
            // if ( ArrayList.class.isAssignableFrom(clazz) ) {
            //     DevProjectVO devProjectVO = objectMapper.convertValue(dataMap.get("project"), DevProjectVO.class);
            // }
            // DevProjectVO devProjectVO = objectMapper.convertValue(dataMap.get("project"), DevProjectVO.class);
            // ArrayList<Object> devProjectList = objectMapper.convertValue(dataMap.get("projectList"), ArrayList.class);

            Map<String, Object> result = devProjectService.getDevProject();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
}

// public class DataRender {
//     private Map<String, Object>
//     public 
// }
