package com.sino.ams.system.LocationInfoConfirm.dto;

import com.sino.ams.system.item.dto.EtsSystemItemDTO;

/**
 * @author Administrator
 *
 */
public class LocationInfoConfirmDTO extends EtsSystemItemDTO {

	private String workorderObjectNo = "";
	
	private String workorderObjectName = "";
	
	private String confirmUserName = "";
	
	private String confirmDate = "";

	public String getConfirmUserName() {
		return confirmUserName;
	}

	public void setConfirmUserName(String confirmUserName) {
		this.confirmUserName = confirmUserName;
	}

	public String getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(String confirmDate) {
		this.confirmDate = confirmDate;
	}

	public String getWorkorderObjectNo() {
		return workorderObjectNo;
	}

	public void setWorkorderObjectNo(String workorderObjectNo) {
		this.workorderObjectNo = workorderObjectNo;
	}

	public String getWorkorderObjectName() {
		return workorderObjectName;
	}

	public void setWorkorderObjectName(String workorderObjectName) {
		this.workorderObjectName = workorderObjectName;
	}
	
}
