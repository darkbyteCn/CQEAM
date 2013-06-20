package com.sino.ams.system.object.dto;

import com.sino.base.dto.DTO;

public class CommonObjectPrintDTO implements DTO{
	private String objectNo = "";
	private String companyName = "";
	private String objectNoImg = "";

	public String getObjectNo() {
		return objectNo;
	}

	public void setObjectNo(String objectNo) {
		this.objectNo = objectNo;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getObjectNoImg() {
		return objectNoImg;
	}

	public void setObjectNoImg(String objectNoImg) {
		this.objectNoImg = objectNoImg;
	}
}
