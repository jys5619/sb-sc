package com.base.sc.biz.vo.npdm;

public class DomTreeVO {
    private String className;
    private String targetClassName;
    private Integer lvl;
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }
    public String getTargetClassName() {
        return targetClassName;
    }
    public void setTargetClassName(String targetClassName) {
        this.targetClassName = targetClassName;
    }
    public Integer getLvl() {
        return lvl;
    }
    public void setLvl(Integer lvl) {
        this.lvl = lvl;
    }
}
