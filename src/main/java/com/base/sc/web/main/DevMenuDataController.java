package com.base.sc.web.main;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.base.sc.web.main.service.DevMenuDataService;

import jakarta.annotation.Resource;

@RestController
public class DevMenuDataController {
    @Resource(name = "devMenuDataService")
    private DevMenuDataService devMenuDataService;
    
    @RequestMapping(value="/menu", method=RequestMethod.GET, produces="application/json; charset=utf-8")
    public @ResponseBody ResponseEntity<?> getTableInfo() {
        try {
            devMenuDataService.test();
            Map<String, Object> result = new HashMap<>();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch(Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
}
