package com.sino.nm.ams.instrument.dto;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.exception.CalendarException;

import com.sino.ams.bean.CommonRecordDTO;

public class AmsInstrumentEamYbChkMaintainDTO extends CommonRecordDTO {

//----------------------------以下是EAM_YB_CHK_MAINSTAIN(仪器仪表检修表)中的字段--------------------------------//
	private String maintainId = ""; //EAM_YB_CHK_MAINSTAIN_S.NEXTVAL
	private String barcode = ""; //仪表条码
	private String barcode1 = ""; //为了区别页面的barcode名称
	private String barcodeNo = ""; //为了区别页面的barcode名称，作为页面checkbox的值
	private String status = ""; //状态,见字典"YB_STATUS"
	private String checkUserId = ""; //检修人
	private SimpleCalendar checkDate = null; //检修日期
	private String checkStatus = ""; //检修结果见字典"YB_CHECK_RESULT"
	private String remark = ""; //备注
	private String orgId = ""; //组织ID

//----------------------------------------以下是ETS_SYSTEM_ITEM表中的字段-------------------------------------//
	private String itemCategory2 = ""; //目录编号
	private String itemName = ""; //品名
	private String itemSpec = ""; //规格型号
	private String itemUnit = ""; //计量单位
	private String itemCode = ""; //分类编码
	private SimpleCalendar minTime = null; //最小检修时间
	private SimpleCalendar maxTime = null; //最大检修时间

//---------------------------------------以下是ETS_ITEM_INFO表中的字段----------------------------------------//
	private int organizationId = -1; //公司ID

//---------------------------------------以下是SF_USER表中的字段----------------------------------------------//
	private String userId = ""; //对应检修人的ID
	private String username = ""; //显示检修人姓名
	private String checkStatusName = ""; //检修结果，取字典中状态的VALUE值，此字段是别名

//---------------------------------------以下是EAM_YB_CHK_TASK表的字段
	private String taskId = ""; //任务ID
	private String taskName = ""; //任务名称
	private SimpleCalendar startDate = null; //开始时间(YYYY-MM-DD HH24:MI:SS)
	private SimpleCalendar endDate = null; //结束时间(YYYY-MM-DD HH24:MI:SS)
	
//---------------------------------------以下是本类的构造方法和SET、GET方法-------------------------------------//
	public AmsInstrumentEamYbChkMaintainDTO() {
		this.checkDate = new SimpleCalendar();
		this.minTime = new SimpleCalendar();
		this.maxTime = new SimpleCalendar();
		this.startDate = new SimpleCalendar();
		this.endDate = new SimpleCalendar();
	}

	/**
	 * 功能：设置设备条码信息(AMS)属性 启用日期
	 * @param checkDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setChckDate(String checkDate) throws CalendarException {
		this.checkDate.setCalendarValue(checkDate);
	}

	/**
	 * 功能：获取设备条码信息(AMS)属性 启用日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getCheckDate() throws CalendarException {
		checkDate.setCalPattern(getCalPattern());
		return this.checkDate;
	}

	/**
	 * 功能：设置设备条码信息(AMS)属性 启用日期
	 * @param minTime SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setMinTime(String minTime) throws CalendarException {
		this.minTime.setCalendarValue(minTime);
	}

	/**
	 * 功能：获取设备条码信息(AMS)属性 启用日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getMinTime() throws CalendarException {
		minTime.setCalPattern(getCalPattern());
		return this.minTime;
	}

	/**
	 * 功能：设置设备条码信息(AMS)属性 启用日期
	 * @param maxTime SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setMaxTime(String maxTime) throws CalendarException {
		this.maxTime.setCalendarValue(maxTime);
	}

	/**
	 * 功能：获取设备条码信息(AMS)属性 启用日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getMaxTime() throws CalendarException {
		maxTime.setCalPattern(getCalPattern());
		return this.maxTime;
	}

	/**
	 * 功能：设置设备条码信息(AMS)属性 启用日期
	 * @param startDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setStartDate(String startDate) throws CalendarException {
		this.startDate.setCalendarValue(startDate);
	}

	/**
	 * 功能：获取设备条码信息(AMS)属性 启用日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getStartDate() throws CalendarException {
		startDate.setCalPattern(getCalPattern());
		return this.startDate;
	}

	/**
	 * 功能：设置设备条码信息(AMS)属性 结束日期
	 * @param endDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setEndDate(String endDate) throws CalendarException {
		this.endDate.setCalendarValue(endDate);
	}

	/**
	 * 功能：获取设备条码信息(AMS)属性 结束日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getEndDate() throws CalendarException {
		endDate.setCalPattern(getCalPattern());
		return this.endDate;
	}

	public String getMaintainId() {
		return maintainId;
	}

	public void setMaintainId(String maintainId) {
		this.maintainId = maintainId;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCheckUserId() {
		return checkUserId;
	}

	public void setCheckUserId(String checkUserId) {
		this.checkUserId = checkUserId;
	}

	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getItemCategory2() {
		return itemCategory2;
	}

	public void setItemCategory2(String itemCategory2) {
		this.itemCategory2 = itemCategory2;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemSpec() {
		return itemSpec;
	}

	public void setItemSpec(String itemSpec) {
		this.itemSpec = itemSpec;
	}

	public String getItemUnit() {
		return itemUnit;
	}

	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public int getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCheckStatusName() {
		return checkStatusName;
	}

	public void setCheckStatusName(String checkStatusName) {
		this.checkStatusName = checkStatusName;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public String getBarcode1() {
		return barcode1;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public void setBarcode1(String barcode1) {
		this.barcode1 = barcode1;
	}

	public String getBarcodeNo() {
		return barcodeNo;
	}

	public void setBarcodeNo(String barcodeNo) {
		this.barcodeNo = barcodeNo;
	}
}
