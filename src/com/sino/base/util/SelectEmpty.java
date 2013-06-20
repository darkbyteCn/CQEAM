package com.sino.base.util;

/**
 * 功能：SQL语句中条件，列，所对应列的约束
 * @author Administrator
 *
 */
public class SelectEmpty {
	
	private String columnName;
	private String likeName;
	private boolean isInt;
	public boolean isInt() {
		return isInt;
	}
	
	public SelectEmpty(String columnName,String likeName,boolean bool){
		this.columnName = columnName;
		this.likeName = likeName;
		this.isInt = bool;
	}

	public void setInt(boolean isInt) {
		this.isInt = isInt;
	}

	public SelectEmpty(){
		
	}
	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getLikeName() {
		return likeName;
	}

	public void setLikeName(String likeName) {
		this.likeName = likeName;
	}
}
