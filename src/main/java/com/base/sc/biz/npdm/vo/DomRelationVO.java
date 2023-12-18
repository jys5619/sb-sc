package com.base.sc.biz.npdm.vo;

public class DomRelationVO {
    private String names;
	private String dbmsTable;
	private String fromClass;
	private String toClass;
	private String fromRelationship;
	private String toRelationship;
	private String remarks;
	private String owners;
	public String getNames() {
		return names;
	}
	public void setNames(String names) {
		this.names = names;
	}
	public String getDbmsTable() {
		return dbmsTable;
	}
	public void setDbmsTable(String dbmsTable) {
		this.dbmsTable = dbmsTable;
	}
	public String getFromClass() {
		return fromClass;
	}
	public void setFromClass(String fromClass) {
		this.fromClass = fromClass;
	}
	public String getToClass() {
		return toClass;
	}
	public void setToClass(String toClass) {
		this.toClass = toClass;
	}
	public String getFromRelationship() {
		return fromRelationship;
	}
	public void setFromRelationship(String fromRelationship) {
		this.fromRelationship = fromRelationship;
	}
	public String getToRelationship() {
		return toRelationship;
	}
	public void setToRelationship(String toRelationship) {
		this.toRelationship = toRelationship;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getOwners() {
		return owners;
	}
	public void setOwners(String owners) {
		this.owners = owners;
	}
}
