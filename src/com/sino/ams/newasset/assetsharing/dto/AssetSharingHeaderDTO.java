package com.sino.ams.newasset.assetsharing.dto;

import java.util.List;

import com.sino.ams.appbase.dto.AMSFlowDTO;
import com.sino.ams.newasset.assetsharing.constant.AssetSharingConstant;
import com.sino.sinoflow.flowinterface.AppFlowBaseDTO;

public class AssetSharingHeaderDTO extends AMSFlowDTO {
	
	private String transId="";
	private String transNo="";
	 private String transType = "";
	 private String transTypeValue = "";
	 private String transStatus = "";
	 private String transStatusDesc = "";
	 private int fromOrganizationId = 0;//
	 private String company="";
	 
	 private String fromDept;
	 private String deptName="";
	 private String email="";
	 private String mobilePhone ="";
	 private String remark="";
	 
	 private String  currUser="";//经办人
	 private String currUserName="";
	 private String checkUser="";///审核人
	 private String checkUserName="";
	 
	 private String shareStatusOpt="";//共享状态
	 
	 private String groupId="";
	 
	 private String emergentLevel = ""; //紧急程度
	 private String emergentLevelOption = ""; //紧急成都下拉框
	 
     private String specialityDept = ""; //实物管理部门
     private String specialityDeptName = ""; //实物管理部门
     private String specialityDeptOption = ""; //实物管理部门下拉框

	 private List<AssetSharingLineDTO> lines = null;
	 
	 public AppFlowBaseDTO getAppFlowBaseDTO(){
		 AppFlowBaseDTO headerDTO = new AppFlowBaseDTO(){
			   public String getPrimaryKey() {
	                return transId;
	            }

	            public void setPrimaryKey(String primaryKey) {
	            }
		 };
		 headerDTO.setApp_dataID(this.getTransId());
	        headerDTO.setPrimaryKey(this.getTransId());
	        headerDTO.setOrderNo(this.getTransNo());
	        headerDTO.setOrderName(this.getTransNo());
	        headerDTO.setSf_appFieldValue(this.getSf_appFieldValue());
	        headerDTO.setSf_end(this.getSf_end());
	        headerDTO.setSf_actID(this.getSf_actID());
	        headerDTO.setSf_appName(AssetSharingConstant.SHARE_SF_APP_NAME);
	        //headerDTO.setSf_appName("SHARE_ASSETS");
	      
		 return headerDTO;
	 }
	 
	public List<AssetSharingLineDTO> getLines() {
		return lines;
	}
	public void setLines(List<AssetSharingLineDTO> lines) {
		this.lines = lines;
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
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
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
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getFromDept() {
		return fromDept;
	}
	public void setFromDept(String fromDept) {
		this.fromDept = fromDept;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCurrUser() {
		return currUser;
	}
	public void setCurrUser(String currUser) {
		this.currUser = currUser;
	}
	public String getCurrUserName() {
		return currUserName;
	}
	public void setCurrUserName(String currUserName) {
		this.currUserName = currUserName;
	}
	public String getCheckUser() {
		return checkUser;
	}
	public void setCheckUser(String checkUser) {
		this.checkUser = checkUser;
	}
	public String getCheckUserName() {
		return checkUserName;
	}
	public void setCheckUserName(String checkUserName) {
		this.checkUserName = checkUserName;
	}


	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getShareStatusOpt() {
		return shareStatusOpt;
	}

	public void setShareStatusOpt(String shareStatusOpt) {
		this.shareStatusOpt = shareStatusOpt;
	}

	public String getTransStatusDesc() {
		return transStatusDesc;
	}

	public void setTransStatusDesc(String transStatusDesc) {
		this.transStatusDesc = transStatusDesc;
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

	public String getTransTypeValue() {
		return transTypeValue;
	}

	public void setTransTypeValue(String transTypeValue) {
		this.transTypeValue = transTypeValue;
	}

	public String getSpecialityDept() {
		return specialityDept;
	}

	public void setSpecialityDept(String specialityDept) {
		this.specialityDept = specialityDept;
	}

	public String getSpecialityDeptName() {
		return specialityDeptName;
	}

	public void setSpecialityDeptName(String specialityDeptName) {
		this.specialityDeptName = specialityDeptName;
	}

	public String getSpecialityDeptOption() {
		return specialityDeptOption;
	}

	public void setSpecialityDeptOption(String specialityDeptOption) {
		this.specialityDeptOption = specialityDeptOption;
	}
}
