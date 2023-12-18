package com.base.sc.data;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.base.sc.data.service.TableDataService;

import jakarta.annotation.Resource;

@RestController
public class TableDataController {
    @Resource(name = "tableDataService")
    private TableDataService tableDataService;
    
    @RequestMapping(value="/table/{keyword}", method=RequestMethod.GET, produces="application/json; charset=utf-8")
    public @ResponseBody ResponseEntity<?> getTableInfo(@PathVariable("keyword") String keyword) {
        try {
            Map<String, Object> result = tableDataService.searchTableInfo(keyword);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch(Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @RequestMapping(value="/column/{keyword}", method=RequestMethod.GET, produces="application/json; charset=utf-8")
    public @ResponseBody ResponseEntity<?> getColumnInfo(@PathVariable("keyword") String keyword) {
        try {
            Map<String, Object> result = tableDataService.searchColumnInfo(keyword);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch(Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @RequestMapping(value="/dom/{keyword}", method=RequestMethod.GET, produces="application/json; charset=utf-8")
    public @ResponseBody ResponseEntity<?> getDomInfo(@PathVariable("keyword") String keyword) {
        try {
            Map<String, Object> result = tableDataService.searchDomInfo(keyword);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch(Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @RequestMapping(value="/menu/{keyword}", method=RequestMethod.GET, produces="application/json; charset=utf-8")
    public @ResponseBody ResponseEntity<?> getMenuInfo(@PathVariable("keyword") String keyword) {
        try {
            Map<String, Object> result = tableDataService.searchMenuInfo(keyword);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch(Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @RequestMapping(value="/organization/{keyword}", method=RequestMethod.GET, produces="application/json; charset=utf-8")
    public @ResponseBody ResponseEntity<?> getOrganizationInfo(@PathVariable("keyword") String keyword) {
        try {
            Map<String, Object> result = tableDataService.searchOrganizationInfo(keyword);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch(Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
}
