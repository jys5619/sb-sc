package com.base.sc.biz.vo.dev;

import com.base.sc.biz.common.DateField;
import com.base.sc.biz.vo.root.ObjectRootVO;

public class DevProjectVO extends ObjectRootVO {
	private String projectName;
	private String discriptions;
	private DateField planStartDate;
	private DateField planEndDate;
	private DateField realStartDate;
	private DateField realEndDate;
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
    
}
