package com.base.sc.biz.vo.root;

import java.util.HashMap;
import java.util.Map;

import com.base.sc.biz.common.DateTimeField;

public class ObjectRootVO {
    private String id;
    private String className;
    private String modifier;
    private DateTimeField modified;
    private String creator;
    private DateTimeField created;
    private Map<String, Object> outData;

    public ObjectRootVO() {
        outData = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setOutData(Map<String, Object> outData) {
        this.outData = outData;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public DateTimeField getModified() {
        return modified;
    }

    public void setModified(DateTimeField modified) {
        this.modified = modified;
    }

    public void setModified(long time) {
        this.modified = new DateTimeField(time);
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public DateTimeField getCreated() {
        return created;
    }

    public void setCreated(DateTimeField created) {
        this.created = created;
    }

    public void setCreated(long time) {
        this.created = new DateTimeField(time);
    }

    public void putOutData(String key, Object value) {
        outData.put(key, value);
    }

    public Map<String, Object> getOutData() {
        return outData;
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

    public String getClassName() {
        return className;
    }
}
