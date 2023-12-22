package com.base.sc.biz.vo.root;

import com.base.sc.biz.common.DateTimeField;

public class ObjectRootVO {
    private String id;
	private String modifier;
	private DateTimeField modified;
	private String creator;
	private DateTimeField created;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
    
}
