package com.base.sc.web.dev.service;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.base.sc.biz.dom.dev.DevProject;
import com.base.sc.biz.vo.dev.DevProjectVO;

@Service("devProjectService")
public class DevProjectServiceImpl implements DevProjectService {

    @Override
    public DevProjectVO getDevProject(String id) {
        DevProject devProject = new DevProject();
        return devProject.get(id);
    }

    @Override
    public List<DevProjectVO> getDevProjectList(Map<String, Object> params) {
        DevProject devProject = new DevProject();
        return devProject.getList(params);
    }

    @Override
    public int saveDevProject(DevProjectVO devProjectVO) {
        DevProject devProject = new DevProject();
        return devProject.save(devProjectVO);
    }

    // @Override
    // public Map<String, Object> getDevProject() {
    //     DevProjectVO devProjectVO = new DevProjectVO();

    //     DateField dateField = new DateField();
    //     dateField.setVal("20231112");

    //     DateTimeField dateTimeField = new DateTimeField();
    //     dateTimeField.setVal("20230322145040");

    //     devProjectVO.setProjectName("TEST Prjoect Name");
    //     devProjectVO.setDiscriptions("Text project Description");
    //     devProjectVO.setPlanEndDate(dateField);
    //     devProjectVO.setPlanStartDate(dateField);
    //     devProjectVO.setCreator("JJJ");
    //     devProjectVO.setCreated(dateTimeField);
    //     devProjectVO.setModifier("JJJ");
    //     devProjectVO.setModified(dateTimeField);

    //     List<DevProjectVO> devProjectList = new ArrayList<>();
    //     devProjectList.add(devProjectVO);
    //     devProjectList.add(devProjectVO);

    //     Map<String, Object> result = new HashMap<>();
    //     result.put("project", devProjectVO);
    //     result.put("projectList", devProjectList);

    //     return result;
    // }
}
