package com.founder.fix.ocx.util;

public class DBEntity {

	private String name;
	
	private String type;
	
	private Boolean isPrimaryKey;
	
	private String columnSize;
	
	private Boolean isNullable;
	
	private String remarks;
	
	public DBEntity() {
	}

	public DBEntity(String name, String type, Boolean isPrimaryKey) {
		this.name = name;
		this.type = type;
		this.isPrimaryKey = isPrimaryKey;
	}
	
	public DBEntity(String name, String type, Boolean isPrimaryKey,
			String columnSize, String remarks) {
		this.name = name;
		this.type = type;
		this.isPrimaryKey = isPrimaryKey;
		this.columnSize = columnSize;
		this.remarks = remarks;
	}
	
	public DBEntity(String name, String type, Boolean isPrimaryKey,
			String columnSize, Boolean isNullable) {
		this.name = name;
		this.type = type;
		this.isPrimaryKey = isPrimaryKey;
		this.columnSize = columnSize;
		this.isNullable = isNullable;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getIsPrimaryKey() {
		return isPrimaryKey;
	}

	public void setIsPrimaryKey(Boolean isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}

	public String getColumnSize() {
		return columnSize;
	}

	public void setColumnSize(String columnSize) {
		this.columnSize = columnSize;
	}

	public Boolean getIsNullable() {
		return isNullable;
	}

	public void setIsNullable(Boolean isNullable) {
		this.isNullable = isNullable;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
