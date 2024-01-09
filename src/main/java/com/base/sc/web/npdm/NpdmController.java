package com.base.sc.web.npdm;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.base.sc.web.npdm.service.NpdmService;

import jakarta.annotation.Resource;

@RestController
public class NpdmController {
    @Resource(name = "tableDataService")
    private NpdmService tableDataService;
    
    @RequestMapping(value="/api/table/{keyword}", method=RequestMethod.GET, produces="application/json; charset=utf-8")
    public @ResponseBody ResponseEntity<?> getTableInfo(@PathVariable("keyword") String keyword) {
        try {
            Map<String, Object> result = tableDataService.searchTableInfo(keyword);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch(Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @RequestMapping(value="/api/column/{keyword}", method=RequestMethod.GET, produces="application/json; charset=utf-8")
    public @ResponseBody ResponseEntity<?> getColumnInfo(@PathVariable("keyword") String keyword) {
        try {
            Map<String, Object> result = tableDataService.searchColumnInfo(keyword);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch(Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @RequestMapping(value="/api/dom/{keyword}", method=RequestMethod.GET, produces="application/json; charset=utf-8")
    public @ResponseBody ResponseEntity<?> getDomInfo(@PathVariable("keyword") String keyword) {
        try {
            Map<String, Object> result = tableDataService.searchDomInfo(keyword);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch(Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @RequestMapping(value="/api/menu/{keyword}", method=RequestMethod.GET, produces="application/json; charset=utf-8")
    public @ResponseBody ResponseEntity<?> getMenuInfo(@PathVariable("keyword") String keyword) {
        try {
            Map<String, Object> result = tableDataService.searchMenuInfo(keyword);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch(Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @RequestMapping(value="/api/organization/{keyword}", method=RequestMethod.GET, produces="application/json; charset=utf-8")
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
