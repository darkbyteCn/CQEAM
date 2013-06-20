package com.sino.ams.workorder.dto;

import java.util.List;

import com.sino.base.dto.CheckBoxDTO;

public class ZeroTurnBursurHDTO extends CheckBoxDTO{
	

	
	private String  reimburseStatus = "";//±¨ÕË×´Ì¬
	private String  reimburseStatusName = "" ;//±¨ÕË×´Ì¬Ãû³Æ 
	private String  reimburseBeginDate = "";
	private String  reimburseEndDate = "";
	private String  reimbursementDate = "" ;//±¨ÕËÈÕÆÚ  REIMBURSEMENT_DATE

	private String  arrivalStatus = "";//  ×´Ì¬
	private String  arrivalStatusName       = "";//  ×´Ì¬
	private String  arrivalDate  = "";//±¨ÕË×´Ì¬
	private String  isReceived = "";//  
	private String  costCenterName       = "";//  
	private String  manufacturerName  = "";//
	private String misProcureCode = "";
	private String  creationDate = "";
	private String  creationBeginDate = "";
	private String  creationEndDate = "";
	
	public String getCreationBeginDate() {
		return creationBeginDate;
	}

	public void setCreationBeginDate(String creationBeginDate) {
		this.creationBeginDate = creationBeginDate;
	}

	public String getCreationEndDate() {
		return creationEndDate;
	}

	public void setCreationEndDate(String creationEndDate) {
		this.creationEndDate = creationEndDate;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getMisProcureCode() {
		return misProcureCode;
	}

	public void setMisProcureCode(String misProcureCode) {
		this.misProcureCode = misProcureCode;
	}

	public String getArrivalStatus() {
		return arrivalStatus;
	}

	public void setArrivalStatus(String arrivalStatus) {
		this.arrivalStatus = arrivalStatus;
	}

	public String getArrivalStatusName() {
		return arrivalStatusName;
	}

	public void setArrivalStatusName(String arrivalStatusName) {
		this.arrivalStatusName = arrivalStatusName;
	}

	public String getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public String getIsReceived() {
		return isReceived;
	}

	public void setIsReceived(String isReceived) {
		this.isReceived = isReceived;
	}

	public String getCostCenterName() {
		return costCenterName;
	}

	public void setCostCenterName(String costCenterName) {
		this.costCenterName = costCenterName;
	}

	public String getManufacturerName() {
		return manufacturerName;
	}

	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}

	public String getReimbursementDate() {
		return reimbursementDate;
	}

	public void setReimbursementDate(String reimbursementDate) {
		this.reimbursementDate = reimbursementDate;
	}

	public String getReimburseBeginDate() {
		return reimburseBeginDate;
	}

	public void setReimburseBeginDate(String reimburseBeginDate) {
		this.reimburseBeginDate = reimburseBeginDate;
	}

	public String getReimburseEndDate() {
		return reimburseEndDate;
	}

	public void setReimburseEndDate(String reimburseEndDate) {
		this.reimburseEndDate = reimburseEndDate;
	}

	public String getReimburseStatusName() {
		return reimburseStatusName;
	}

	public void setReimburseStatusName(String reimburseStatusName) {
		this.reimburseStatusName = reimburseStatusName;
	}

	public String getReimburseStatus() {
		return reimburseStatus;
	}

	public void setReimburseStatus(String reimburseStatus) {
		this.reimburseStatus = reimburseStatus;
	}

	private List<ZeroturnLineBursurDTO> lines = null;


	public List<ZeroturnLineBursurDTO> getLines() {
		return lines;
	}

	public void setLines(List<ZeroturnLineBursurDTO> lines) {
		this.lines = lines;
	}
}
