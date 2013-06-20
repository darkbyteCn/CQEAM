package com.sino.soa.td.srv.vendor.dto;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;

/**
* <p>Title: SRV_VENDOR_INFO SrvVendorInfo</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class TdSrvVendorInfoDTO extends CheckBoxDTO{

	private String vendorId = null;
	private String vendorName = "";
	private String vendorNumber = "";
	private String vendorTypeLookupCode = "";
	private String vendorTypeDisp = "";
	private SimpleCalendar vendorCreationDate = null;
	private String createdBy = null;
	private String vatFlag = "";
	private String vatRegistrationNum = "";
	private SimpleCalendar vendorEndDateActive = null;
	private String attribute1 = "";
	private String conVendorId = null;
	private String employeeName = "";
	private String personId = null;
	private String employeeNumber = "";
	private SimpleCalendar lastUpdateDate = null;
	private String vendorNameAlt = "";
	private String companyCode = "";
	private SimpleCalendar inactiveDate = null;
	private String allowSubstituteReceiptsFlag = "";
	private String assetsType ="";

	public TdSrvVendorInfoDTO() {
		super();
		this.vendorCreationDate = new SimpleCalendar();
		this.vendorEndDateActive = new SimpleCalendar();
		this.lastUpdateDate = new SimpleCalendar();
		this.inactiveDate = new SimpleCalendar();

	}

	/**
	 * 功能：设置SRV_VENDOR_INFO属性 供应商ID
	 * @param vendorId String
	 */
	public void setVendorId(String vendorId){
		this.vendorId = vendorId;
	}

	/**
	 * 功能：设置SRV_VENDOR_INFO属性 供应商名称
	 * @param vendorName String
	 */
	public void setVendorName(String vendorName){
		this.vendorName = vendorName;
	}

	/**
	 * 功能：设置SRV_VENDOR_INFO属性 供应商编号
	 * @param vendorNumber String
	 */
	public void setVendorNumber(String vendorNumber){
		this.vendorNumber = vendorNumber;
	}

	/**
	 * 功能：设置SRV_VENDOR_INFO属性 供应商类别代码
	 * @param vendorTypeLookupCode String
	 */
	public void setVendorTypeLookupCode(String vendorTypeLookupCode){
		this.vendorTypeLookupCode = vendorTypeLookupCode;
	}

	/**
	 * 功能：设置SRV_VENDOR_INFO属性 供应商类别
	 * @param vendorTypeDisp String
	 */
	public void setVendorTypeDisp(String vendorTypeDisp){
		this.vendorTypeDisp = vendorTypeDisp;
	}

	/**
	 * 功能：设置SRV_VENDOR_INFO属性 供应商创建时间
	 * @param vendorCreationDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setVendorCreationDate(String vendorCreationDate) throws CalendarException{
		this.vendorCreationDate.setCalendarValue(vendorCreationDate);
	}

	/**
	 * 功能：设置SRV_VENDOR_INFO属性 创建人
	 * @param createdBy String
	 */
	public void setCreatedBy(String createdBy){
		this.createdBy = createdBy;
	}

	/**
	 * 功能：设置SRV_VENDOR_INFO属性 纳税人标识
	 * @param vatFlag String
	 */
	public void setVatFlag(String vatFlag){
		this.vatFlag = vatFlag;
	}

	/**
	 * 功能：设置SRV_VENDOR_INFO属性 供应商税务登记号
	 * @param vatRegistrationNum String
	 */
	public void setVatRegistrationNum(String vatRegistrationNum){
		this.vatRegistrationNum = vatRegistrationNum;
	}

	/**
	 * 功能：设置SRV_VENDOR_INFO属性 供应商截止日期 
	 * @param vendorEndDateActive SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setVendorEndDateActive(String vendorEndDateActive) throws CalendarException{
		this.vendorEndDateActive.setCalendarValue(vendorEndDateActive);
	}

	/**
	 * 功能：设置SRV_VENDOR_INFO属性 关联供应商名称
	 * @param attribute1 String
	 */
	public void setAttribute1(String attribute1){
		this.attribute1 = attribute1;
	}

	/**
	 * 功能：设置SRV_VENDOR_INFO属性 关联供应商ID 
	 * @param conVendorId String
	 */
	public void setConVendorId(String conVendorId){
		this.conVendorId = conVendorId;
	}

	/**
	 * 功能：设置SRV_VENDOR_INFO属性 员工姓名
	 * @param employeeName String
	 */
	public void setEmployeeName(String employeeName){
		this.employeeName = employeeName;
	}

	/**
	 * 功能：设置SRV_VENDOR_INFO属性 人员ID 
	 * @param personId String
	 */
	public void setPersonId(String personId){
		this.personId = personId;
	}

	/**
	 * 功能：设置SRV_VENDOR_INFO属性 员工编号
	 * @param employeeNumber String
	 */
	public void setEmployeeNumber(String employeeNumber){
		this.employeeNumber = employeeNumber;
	}

	/**
	 * 功能：设置SRV_VENDOR_INFO属性 供应商地点最后更新时间
	 * @param lastUpdateDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setLastUpdateDate(String lastUpdateDate) throws CalendarException{
		this.lastUpdateDate.setCalendarValue(lastUpdateDate);
	}

	/**
	 * 功能：设置SRV_VENDOR_INFO属性 供应商别名
	 * @param vendorNameAlt String
	 */
	public void setVendorNameAlt(String vendorNameAlt){
		this.vendorNameAlt = vendorNameAlt;
	}

	/**
	 * 功能：设置SRV_VENDOR_INFO属性 关联供应商代码
	 * @param companyCode String
	 */
	public void setCompanyCode(String companyCode){
		this.companyCode = companyCode;
	}

	/**
	 * 功能：设置SRV_VENDOR_INFO属性 失效日期
	 * @param inactiveDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setInactiveDate(String inactiveDate) throws CalendarException{
		this.inactiveDate.setCalendarValue(inactiveDate);
	}

	/**
	 * 功能：设置SRV_VENDOR_INFO属性 是否卡类
	 * @param allowSubstituteReceiptsFlag String
	 */
	public void setAllowSubstituteReceiptsFlag(String allowSubstituteReceiptsFlag){
		this.allowSubstituteReceiptsFlag = allowSubstituteReceiptsFlag;
	}


	/**
	 * 功能：获取SRV_VENDOR_INFO属性 供应商ID
	 * @return String
	 */
	public String getVendorId() {
		return this.vendorId;
	}

	/**
	 * 功能：获取SRV_VENDOR_INFO属性 供应商名称
	 * @return String
	 */
	public String getVendorName() {
		return this.vendorName;
	}

	/**
	 * 功能：获取SRV_VENDOR_INFO属性 供应商编号
	 * @return String
	 */
	public String getVendorNumber() {
		return this.vendorNumber;
	}

	/**
	 * 功能：获取SRV_VENDOR_INFO属性 供应商类别代码
	 * @return String
	 */
	public String getVendorTypeLookupCode() {
		return this.vendorTypeLookupCode;
	}

	/**
	 * 功能：获取SRV_VENDOR_INFO属性 供应商类别
	 * @return String
	 */
	public String getVendorTypeDisp() {
		return this.vendorTypeDisp;
	}

	/**
	 * 功能：获取SRV_VENDOR_INFO属性 供应商创建时间
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getVendorCreationDate() throws CalendarException {
		vendorCreationDate.setCalPattern(getCalPattern());
		return this.vendorCreationDate;
	}

	/**
	 * 功能：获取SRV_VENDOR_INFO属性 创建人
	 * @return String
	 */
	public String getCreatedBy() {
		return this.createdBy;
	}

	/**
	 * 功能：获取SRV_VENDOR_INFO属性 纳税人标识
	 * @return String
	 */
	public String getVatFlag() {
		return this.vatFlag;
	}

	/**
	 * 功能：获取SRV_VENDOR_INFO属性 供应商税务登记号
	 * @return String
	 */
	public String getVatRegistrationNum() {
		return this.vatRegistrationNum;
	}

	/**
	 * 功能：获取SRV_VENDOR_INFO属性 供应商截止日期 
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getVendorEndDateActive() throws CalendarException {
		vendorEndDateActive.setCalPattern(getCalPattern());
		return this.vendorEndDateActive;
	}

	/**
	 * 功能：获取SRV_VENDOR_INFO属性 关联供应商名称
	 * @return String
	 */
	public String getAttribute1() {
		return this.attribute1;
	}

	/**
	 * 功能：获取SRV_VENDOR_INFO属性 关联供应商ID 
	 * @return String
	 */
	public String getConVendorId() {
		return this.conVendorId;
	}

	/**
	 * 功能：获取SRV_VENDOR_INFO属性 员工姓名
	 * @return String
	 */
	public String getEmployeeName() {
		return this.employeeName;
	}

	/**
	 * 功能：获取SRV_VENDOR_INFO属性 人员ID 
	 * @return String
	 */
	public String getPersonId() {
		return this.personId;
	}

	/**
	 * 功能：获取SRV_VENDOR_INFO属性 员工编号
	 * @return String
	 */
	public String getEmployeeNumber() {
		return this.employeeNumber;
	}

	/**
	 * 功能：获取SRV_VENDOR_INFO属性 供应商地点最后更新时间
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getLastUpdateDate() throws CalendarException {
		lastUpdateDate.setCalPattern(getCalPattern());
		return this.lastUpdateDate;
	}

	/**
	 * 功能：获取SRV_VENDOR_INFO属性 供应商别名
	 * @return String
	 */
	public String getVendorNameAlt() {
		return this.vendorNameAlt;
	}

	/**
	 * 功能：获取SRV_VENDOR_INFO属性 关联供应商代码
	 * @return String
	 */
	public String getCompanyCode() {
		return this.companyCode;
	}

	/**
	 * 功能：获取SRV_VENDOR_INFO属性 失效日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getInactiveDate() throws CalendarException {
		inactiveDate.setCalPattern(getCalPattern());
		return this.inactiveDate;
	}

	/**
	 * 功能：获取SRV_VENDOR_INFO属性 是否卡类
	 * @return String
	 */
	public String getAllowSubstituteReceiptsFlag() {
		return this.allowSubstituteReceiptsFlag;
	}

	public String getAssetsType() {
		return assetsType;
	}

	public void setAssetsType(String assetsType) {
		this.assetsType = assetsType;
	}

}