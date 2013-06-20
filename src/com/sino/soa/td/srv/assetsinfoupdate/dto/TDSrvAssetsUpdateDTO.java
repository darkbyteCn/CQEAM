package com.sino.soa.td.srv.assetsinfoupdate.dto;

import com.sino.base.dto.CheckBoxDTO;

/**
* <p>Title: 资产变动直接 AssetsUpdateDTO</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* User: wangzp
* Date: 2011-09-26
* Function:资产基本信息修改_TD
*/

public class TDSrvAssetsUpdateDTO extends CheckBoxDTO{
	
	private String bookTypeCode = "";
	private String tagNumber = "";
	private String itemName = "";
	private String manufacturerName = "";
	private String itemSpec = "";
	private int organizationId = 0;
	private String employeeNumber = "";//制单人员工工号
	private String responsibilityId = "";//导入职责ID
	private String createdBy = "" ;//制单人ID
	private String barcode = ""; //ETS_ITEM_INFO.barcode
	private String modelNumber = "";	
	private String assetsDescription = "";
	private String eammanufname ="";
	
	//补充 10-20
	private String cexId = "";              //投资分类属性 CEX_ID 
	private String snCode = "";           //SN_CODE 支撑网设备类型
	private String opeId = "";              //业务平台属性 OPE_ID 
	private String opeCode = "";           //业务平台编码
	private String nleId = "";              //网络层次属性 NLE_ID
	private String nleCode = "";           //网络层次编码
	private String constructStatus = "";    //共建类型  CONSTRUCT_STATUS
	
	public String getBookTypeCode() {
		return bookTypeCode;
	}
	/**
	 * @param bookTypeCode the bookTypeCode to set
	 */
	public void setBookTypeCode(String bookTypeCode) {
		this.bookTypeCode = bookTypeCode;
	}
	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * @return the employeeNumber
	 */
	public String getEmployeeNumber() {
		return employeeNumber;
	}
	/**
	 * @param employeeNumber the employeeNumber to set
	 */
	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	/**
	 * @return the manufacturerName
	 */
	public String getManufacturerName() {
		return manufacturerName;
	}
	/**
	 * @param manufacturerName the manufacturerName to set
	 */
	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}

	/**
	 * @return the itemSpec
	 */
	public String getItemSpec() {
		return itemSpec;
	}
	/**
	 * @param itemSpec the itemSpec to set
	 */
	public void setItemSpec(String itemSpec) {
		this.itemSpec = itemSpec;
	}
	/**
	 * @return the responsibilityId
	 */
	public String getResponsibilityId() {
		return responsibilityId;
	}
	/**
	 * @param responsibilityId the responsibilityId to set
	 */
	public void setResponsibilityId(String responsibilityId) {
		this.responsibilityId = responsibilityId;
	}
	/**
	 * @return the tagNumber
	 */
	public String getTagNumber() {
		return tagNumber;
	}
	/**
	 * @param tagNumber the tagNumber to set
	 */
	public void setTagNumber(String tagNumber) {
		this.tagNumber = tagNumber;
	}
	/**
	 * @return the organizationId
	 */
	public int getOrganizationId() {
		return organizationId;
	}
	/**
	 * @param organizationId the organizationId to set
	 */
	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}
	/**
	 * @return the itemName
	 */
	public String getItemName() {
		return itemName;
	}
	/**
	 * @param itemName the itemName to set
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * @return the barcode
	 */
	public String getBarcode() {
		return barcode;
	}
	/**
	 * @param barcode the barcode to set
	 */
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	/**
	 * @return the assetsDescription
	 */
	public String getAssetsDescription() {
		return assetsDescription;
	}
	/**
	 * @param assetsDescription the assetsDescription to set
	 */
	public void setAssetsDescription(String assetsDescription) {
		this.assetsDescription = assetsDescription;
	}

	/**
	 * @return the eammanufname
	 */
	public String getEammanufname() {
		return eammanufname;
	}
	/**
	 * @param eammanufname the eammanufname to set
	 */
	public void setEammanufname(String eammanufname) {
		this.eammanufname = eammanufname;
	}
	/**
	 * @return the modelNumber
	 */
	public String getModelNumber() {
		return modelNumber;
	}
	/**
	 * @param modelNumber the modelNumber to set
	 */
	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}
	public String getCexId() {
		return cexId;
	}
	public void setCexId(String cexId) {
		this.cexId = cexId;
	}
	public String getSnCode() {
		return snCode;
	}
	public void setSnCode(String snCode) {
		this.snCode = snCode;
	}
	public String getOpeId() {
		return opeId;
	}
	public void setOpeId(String opeId) {
		this.opeId = opeId;
	}
	public String getOpeCode() {
		return opeCode;
	}
	public void setOpeCode(String opeCode) {
		this.opeCode = opeCode;
	}
	public String getNleId() {
		return nleId;
	}
	public void setNleId(String nleId) {
		this.nleId = nleId;
	}
	public String getNleCode() {
		return nleCode;
	}
	public void setNleCode(String nleCode) {
		this.nleCode = nleCode;
	}
	public String getConstructStatus() {
		return constructStatus;
	}
	public void setConstructStatus(String constructStatus) {
		this.constructStatus = constructStatus;
	}

}
