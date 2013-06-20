package com.sino.ams.newasset.lease.dto;

import com.sino.ams.appbase.dto.AMSFlowDTO;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.exception.CalendarException;

public class LeaseHeaderDTO extends AMSFlowDTO {
	private String transId = "";
	private String transNo = "";
	private String transStatus = "";
	private int fromOrganizationId = 0;
	private String transType = "";
	private String createdReason = "";
	private String email = ""; // 创建人电子邮件
	private String phoneNumber = ""; // 创建人手机号码

	private String fromCompanyName = "";
	private String transTypeValue = ""; // 资产业务类型描述
	private String created = ""; // 创建人姓名;
	private int createdBy = 0;
	
	private String emergentLevel = ""; //紧急程度
	private String emergentLevelOption = ""; //紧急成都下拉框

	private SimpleCalendar creationDate = null;

	public LeaseHeaderDTO() {
		this.creationDate = new SimpleCalendar();
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public String getTransNo() {
		return transNo;
	}

	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}

	public String getTransStatus() {
		return transStatus;
	}

	public void setTransStatus(String transStatus) {
		this.transStatus = transStatus;
	}

	public int getFromOrganizationId() {
		return fromOrganizationId;
	}

	public void setFromOrganizationId(int fromOrganizationId) {
		this.fromOrganizationId = fromOrganizationId;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getCreatedReason() {
		return createdReason;
	}

	public void setCreatedReason(String createdReason) {
		this.createdReason = createdReason;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getFromCompanyName() {
		return fromCompanyName;
	}

	public void setFromCompanyName(String fromCompanyName) {
		this.fromCompanyName = fromCompanyName;
	}

	public String getTransTypeValue() {
		return transTypeValue;
	}

	public void setTransTypeValue(String transTypeValue) {
		this.transTypeValue = transTypeValue;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public SimpleCalendar getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) throws CalendarException {
		this.creationDate.setCalendarValue(creationDate);
	}

	public String getEmergentLevel() {
		return emergentLevel;
	}

	public void setEmergentLevel(String emergentLevel) {
		this.emergentLevel = emergentLevel;
	}

	public String getEmergentLevelOption() {
		return emergentLevelOption;
	}

	public void setEmergentLevelOption(String emergentLevelOption) {
		this.emergentLevelOption = emergentLevelOption;
	}


}
