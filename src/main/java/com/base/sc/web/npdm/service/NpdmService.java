package com.base.sc.web.npdm.service;

import java.util.Map;

public interface NpdmService {
    Map<String, Object> searchTableInfo(String keyword);
    Map<String, Object> searchColumnInfo(String keyword);
    Map<String, Object> searchDomInfo(String keyword);
    Map<String, Object> searchMenuInfo(String keyword);
    Map<String, Object> searchOrganizationInfo(String keyword);
}
