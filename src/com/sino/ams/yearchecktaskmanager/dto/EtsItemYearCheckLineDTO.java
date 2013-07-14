package com.sino.ams.yearchecktaskmanager.dto;

public class EtsItemYearCheckLineDTO extends EtsItemYearCheckDTO{

	private static final long serialVersionUID = -6170115326331086674L;

	private String errorMsg= "";

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	
}
