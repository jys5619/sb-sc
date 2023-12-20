package com.base.sc.web.dev.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.base.sc.biz.common.DateField;
import com.base.sc.biz.common.DateTimeField;
import com.base.sc.biz.dev.vo.DevProjectVO;

@Service("devProjectService")
public class DevProjectServiceImpl implements DevProjectService {

    @Override
    public Map<String, Object> getDevProject() {
        DevProjectVO devProjectVO = new DevProjectVO();

        DateField dateField = new DateField();
        dateField.setVal("20231112");

        DateTimeField dateTimeField = new DateTimeField();
        dateTimeField.setVal("20230322145040");

        devProjectVO.setProjectName("TEST Prjoect Name");
        devProjectVO.setDiscriptions("Text project Description");
        devProjectVO.setPlanEndDate(dateField);
        devProjectVO.setPlanStartDate(dateField);
        devProjectVO.setCreator("JJJ");
        devProjectVO.setCreated(dateTimeField);
        devProjectVO.setModifier("JJJ");
        devProjectVO.setModified(dateTimeField);

        Map<String, Object> result = new HashMap<>();
        result.put("project", devProjectVO);

        return result;
    }
}
