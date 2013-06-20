package com.sino.soa.mis.srv.transfaassetinfo.dto;

import com.sino.base.dto.CheckBoxDTO;

/**
* <p>Title: SRV_VENDOR_INFO SrvVendorInfo</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author wangzp
* @version 1.0
* 查询资产财务信息服务(ODI)
*/

public class TransFaAssetInfoDTO extends CheckBoxDTO{
	private String projectNum = "";   
	private String taskNum = "";      
	private String assetNumber ="";
	private String stratLastUpdateDate="";
	private String endLastUpdateDate="";
	private String orgOption="";
	private String assetsType ="";
	private String periodName ="";    //期间
	private String bookTypeCode = "";  //服务变更(20120217)，增加参数 资产账簿
	
	/**
	 * @return the orgOption
	 */
	public String getOrgOption() {
		return orgOption;
	}
	/**
	 * @param orgOption the orgOption to set
	 */
	public void setOrgOption(String orgOption) {
		this.orgOption = orgOption;
	}

	/**
	 * @return the assetNumber
	 */
	public String getAssetNumber() {
		return assetNumber;
	}
	/**
	 * @param assetNumber the assetNumber to set
	 */
	public void setAssetNumber(String assetNumber) {
		this.assetNumber = assetNumber;
	}
	/**
	 * @return the stratLastUpdateDate
	 */
	public String getStratLastUpdateDate() {
		return stratLastUpdateDate;
	}
	/**
	 * @param stratLastUpdateDate the stratLastUpdateDate to set
	 */
	public void setStratLastUpdateDate(String stratLastUpdateDate) {
		this.stratLastUpdateDate = stratLastUpdateDate;
	}
	/**
	 * @return the endLastUpdateDate
	 */
	public String getEndLastUpdateDate() {
		return endLastUpdateDate;
	}
	/**
	 * @param endLastUpdateDate the endLastUpdateDate to set
	 */
	public void setEndLastUpdateDate(String endLastUpdateDate) {
		this.endLastUpdateDate = endLastUpdateDate;
	}
	public String getAssetsType() {
		return assetsType;
	}
	public void setAssetsType(String assetsType) {
		this.assetsType = assetsType;
	}
	public String getProjectNum() {
		return projectNum;
	}
	public void setProjectNum(String projectNum) {
		this.projectNum = projectNum;
	}
	public String getTaskNum() {
		return taskNum;
	}
	public void setTaskNum(String taskNum) {
		this.taskNum = taskNum;
	}
	public String getPeriodName() {
		return periodName;
	}
	public void setPeriodName(String periodName) {
		this.periodName = periodName;
	}
	public String getBookTypeCode() {
		return bookTypeCode;
	}
	public void setBookTypeCode(String bookTypeCode) {
		this.bookTypeCode = bookTypeCode;
	}
	
}