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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.base.sc.biz.vo.dev.DevMenuVO;
import com.base.sc.biz.vo.dev.DevProjectVO;
import com.base.sc.framework.annotation.JsonResolver;
import com.base.sc.web.dev.service.DevProjectService;

import jakarta.annotation.Resource;

@RestController
public class DevProjectController {
    @Resource(name = "devProjectService")
    private DevProjectService devProjectService;

    @RequestMapping(value = "/dev/project/{id}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public @ResponseBody ResponseEntity<?> getDevProject(@PathVariable("id") String id) {
        try {
            DevProjectVO result = devProjectService.getDevProject(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @RequestMapping(value = "/dev/project/list", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public @ResponseBody ResponseEntity<?> getDevProjectList(@RequestBody Map<String, Object> params) {
        try {
            List<DevProjectVO> result = devProjectService.getDevProjectList(params);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @RequestMapping(value = "/dev/project", method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
    public @ResponseBody ResponseEntity<?> saveDevProject(@JsonResolver(name = "project") DevProjectVO devProjectVO) {
        try {
            int result = devProjectService.saveDevProject(devProjectVO);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
}
