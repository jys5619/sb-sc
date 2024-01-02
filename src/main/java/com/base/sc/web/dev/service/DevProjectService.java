package com.base.sc.web.dev.service;

import java.util.List;
import java.util.Map;

import com.base.sc.biz.vo.dev.DevProjectVO;

public interface DevProjectService {
    public DevProjectVO getDevProject(String id);

    public List<DevProjectVO> getDevProjectList(Map<String, Object> params);

    public int saveDevProject(DevProjectVO devProjectVO);
}
