package com.base.sc.biz.vo.npdm;

public class DomAttributeVO {
	private String attributeName;
	private String dbmsTable;
	private String dbmsColumn;
	private String columnAlias;
	private Integer dataType;
	private String dataTypeName;
	private String defaultValue;
	private String valueSettingInfo;
	private Integer lengths;
	private String className;
	private String definedClassName;

	public String getAttributeName() {
		return attributeName;
	}
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	public String getDbmsTable() {
		return dbmsTable;
	}
	public void setDbmsTable(String dbmsTable) {
		this.dbmsTable = dbmsTable;
	}
	public String getDbmsColumn() {
		return dbmsColumn;
	}
	public void setDbmsColumn(String dbmsColumn) {
		this.dbmsColumn = dbmsColumn;
	}
	public String getColumnAlias() {
		return columnAlias;
	}
	public void setColumnAlias(String columnAlias) {
		this.columnAlias = columnAlias;
	}
	public Integer getDataType() {
		return dataType;
	}
	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}
	public String getDataTypeName() {
		return dataTypeName;
	}
	public void setDataTypeName(String dataTypeName) {
		this.dataTypeName = dataTypeName;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public String getValueSettingInfo() {
		return valueSettingInfo;
	}
	public void setValueSettingInfo(String valueSettingInfo) {
		this.valueSettingInfo = valueSettingInfo;
	}
	public Integer getLengths() {
		return lengths;
	}
	public void setLengths(Integer lengths) {
		this.lengths = lengths;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getDefinedClassName() {
		return definedClassName;
	}
	public void setDefinedClassName(String definedClassName) {
		this.definedClassName = definedClassName;
	}
}
