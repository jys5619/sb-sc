package com.base.sc.biz.vo.root;

import java.util.Map;

public class ObjectVO {

    private String className;
    private Map<String, Object> outData;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Map<String, Object> getOutData() {
        return outData;
    }

    public void setOutData(Map<String, Object> outData) {
        this.outData = outData;
    }

    public void putOutData(String key, Object value) {
        outData.put(key, value);
    }

    public String getOutDataStringValue(String key) {
        if (outData.get(key) == null) {
            return "";
        }

        return outData.get(key).toString();
    }

    public Object getOutDataObjectValue(String key) {
        return outData.get(key);
    }

}
