package com.base.sc.biz.dev.vo;

import com.base.sc.biz.common.DateField;
import com.base.sc.biz.common.DateTimeField;

public class DevProjectVO {
	private String id;
	private String projectName;
	private String discriptions;
	private DateField planStartDate;
	private DateField planEndDate;
	private DateField realStartDate;
	private DateField realEndDate;
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
    public String getProjectName() {
        return projectName;
    }
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    public String getDiscriptions() {
        return discriptions;
    }
    public void setDiscriptions(String discriptions) {
        this.discriptions = discriptions;
    }
    public DateField getPlanStartDate() {
        return planStartDate;
    }
    public void setPlanStartDate(DateField planStartDate) {
        this.planStartDate = planStartDate;
    }
    public DateField getPlanEndDate() {
        return planEndDate;
    }
    public void setPlanEndDate(DateField planEndDate) {
        this.planEndDate = planEndDate;
    }
    public DateField getRealStartDate() {
        return realStartDate;
    }
    public void setRealStartDate(DateField realStartDate) {
        this.realStartDate = realStartDate;
    }
    public DateField getRealEndDate() {
        return realEndDate;
    }
    public void setRealEndDate(DateField realEndDate) {
        this.realEndDate = realEndDate;
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
