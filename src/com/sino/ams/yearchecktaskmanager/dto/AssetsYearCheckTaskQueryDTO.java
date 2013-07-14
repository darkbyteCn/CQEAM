package com.sino.ams.yearchecktaskmanager.dto;

public class AssetsYearCheckTaskQueryDTO extends AssetsYearCheckTaskLineDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1801265976866153134L;
	
	private String taskType = "";//send or received
	
	private int distrubteBy =0;          //下发人
	private String distrubteByName = ""; //下法人
	
//	private String  creationDate = "";//下发日期
	
	public int getDistrubteBy() {
		return distrubteBy;
	}

	public void setDistrubteBy(int distrubteBy) {
		this.distrubteBy = distrubteBy;
	}

	public String getDistrubteByName() {
		return distrubteByName;
	}

	public void setDistrubteByName(String distrubteByName) {
		this.distrubteByName = distrubteByName;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	
	

}
