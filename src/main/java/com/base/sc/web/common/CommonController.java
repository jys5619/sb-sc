package com.base.sc.web.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.base.sc.web.common.service.CommonService;

import jakarta.annotation.Resource;

@RestController
public class CommonController {
    @Resource(name = "commonService")
    private CommonService commonService;

    @RequestMapping(value = "/api/common/javascript.js", method = RequestMethod.GET, produces = "application/text; charset=utf-8")
    public @ResponseBody ResponseEntity<?> getJavascript() {
        try {
            String result = commonService.getScript();
            System.out.println(result);

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
}
