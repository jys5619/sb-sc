package com.base.sc.biz.dom.npdm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.base.sc.biz.vo.npdm.OrganizationVO;
import com.base.sc.util.db.DBOracleUtil;

public class Organization {
    
    public List<OrganizationVO> getOrganizationList(String tableName) {
        List<Map<String, String>> rows = DBOracleUtil.selectList("organization/ORGANIZATION_LIST", tableName);
        
        List<OrganizationVO> resultList = new ArrayList<>();
        for ( Map<String, String> row : rows ) {
            OrganizationVO organizationVO = new OrganizationVO();
            organizationVO.setPclassName(row.get("pclassName"));
            organizationVO.setPnames(row.get("pnames"));
            organizationVO.setPtitles(row.get("ptitles"));
            organizationVO.setPbusinessUnitCode(row.get("pbusinessUnitCode"));
            organizationVO.setPupperOrganizationCode(row.get("pupperOrganizationCode"));
            resultList.add(organizationVO);
        }

        return resultList;
    }
}
