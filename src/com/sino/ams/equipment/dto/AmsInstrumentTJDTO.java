package com.sino.ams.equipment.dto;

import com.sino.ams.bean.CommonRecordDTO;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.exception.CalendarException;

/**
 * <p>Title: AmsInstrumentTJDTO</p>
 * <p>Description:程序自动生成服务程序“AmsInstrumentTJDTO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 张星
 * @version 1.0
 */

public class AmsInstrumentTJDTO extends CommonRecordDTO {

//----------------------------------------以下是ETS_SYSTEM_ITEM表中的字段---------------------------------------//
	private String itemCategory2 = ""; //目录编号
	private String itemName = ""; //品名
	private String itemSpec = ""; //规格型号
	private String itemUnit = ""; //计量单位
	private String itemCode = ""; //分类编码
	
//----------------------------------------以下是ETS_ITEM_INFO表中的字段-----------------------------------------//
	private String barcode = ""; //标签编号   (在EAM_YB_BORROW_LOG中代表仪表条码)
	private String vendorBarcode = ""; //物品编号
	private String itemQty = ""; //数量
	private String price = ""; //成本
	private String attribute3 = ""; //生产厂商，仪表用途
	private String responsibilityDept = ""; //使用部门
	private String deptCode = "";
	private int responsibilityUser = SyBaseSQLUtil.NULL_INT_VALUE; //责任人
	private String responsibilityUser1 = ""; //责任人1
	private String employeeId = "";
	private SimpleCalendar startDate = null; //启用日期
	private String itemStatus = ""; //使用状态
	private String maintainUser = ""; //使用人
	private String remark = ""; //备注
	private SimpleCalendar disableDate = null; //失效日期
	
//----------------------------------------以下是ETS_OBJECT表中的字段--------------------------------------------//
	private String workorderObjectNo = ""; //地点ID
	private String workorderObjectName = ""; //存放地点
	private int organizationId = SyBaseSQLUtil.NULL_INT_VALUE; //公司名称
	
//--------------------------------------以下是EAM_ITEM_DISPOSE表中的字段----------------------------------------//
	private String disposeReason = ""; //减少原因(需要外连接处理)
	
//--------------------------------------以下是除页面需要显示字段以外需要用到的字段-----------------------------------//
	private String deptName = ""; //部门名称
	private String userName = ""; //责任人姓名
	private String barcode1 = ""; //为了区别开始标签编号(做标签编号范围查询时使用)
	private String barcode2 = ""; //为了区别开始标签编号(做标签编号范围查询时使用)
	private String barcodeNo = ""; //为了标识radio标签用
	private String vendorBarcode1 = ""; //为了区别开始物品编号(做物品编号范围查询时使用)
	
//--------------------------------------以下是仪器仪表借用日志(EAM_YB_BORROW_LOG)表中的字段------------------------//
	private String borrowLogId = ""; //表主键(Sequence增长)
	private String status = ""; //状态(新增、已批、借出、关闭、返还) 见字典"YB_BORROW_STATUS"
	private int borrowUserId = SyBaseSQLUtil.NULL_INT_VALUE; //借用人
	private SimpleCalendar borrowDate = null; //借用日期
	private SimpleCalendar borrowDate1 = null; //借用日期1
	private SimpleCalendar returnDatePlan = null; //预计归还时间
	private SimpleCalendar returnDateActual = null; //实际归还日期
	private int approveUserId = SyBaseSQLUtil.NULL_INT_VALUE; //审批人
	private SimpleCalendar approveDate = null; //审批日期
	private int handleUserId = SyBaseSQLUtil.NULL_INT_VALUE; //经办人
	private SimpleCalendar handleDate = null; //经办日期
	private int orgId = SyBaseSQLUtil.NULL_INT_VALUE; //组织ID
	
//--------------------------------------以下是本类的构造方法和SET、GET方法----------------------------------------//

	public AmsInstrumentTJDTO() {
		super();
		this.startDate = new SimpleCalendar();
		this.borrowDate = new SimpleCalendar();
		this.borrowDate1 = new SimpleCalendar();
		this.returnDatePlan = new SimpleCalendar();
		this.returnDateActual= new SimpleCalendar();
		this.approveDate = new SimpleCalendar();
		this.handleDate = new SimpleCalendar();
		this.disableDate = new SimpleCalendar();
	}
	
	/**
	 * 功能：设置标签号信息(EAM)属性 启用日期
	 * @param startDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setStartDate(String startDate) throws CalendarException{
		this.startDate.setCalendarValue(startDate);
	}
	
	/**
	 * 功能：获取标签号信息(EAM)属性 启用日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getStartDate() throws CalendarException {
		startDate.setCalPattern(getCalPattern());
		return this.startDate;
	}
	
	/**
	 * 功能：设置标签号信息(EAM)属性 启用日期
	 * @param borrowDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setBorrowDate(String borrowDate) throws CalendarException{
		this.borrowDate.setCalendarValue(borrowDate);
	}
	
	/**
	 * 功能：获取标签号信息(EAM)属性 启用日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getBorrowDate() throws CalendarException {
		borrowDate.setCalPattern(getCalPattern());
		return this.borrowDate;
	}
	
	/**
	 * 功能：设置标签号信息(EAM)属性 启用日期
	 * @param borrowDate1 SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setBorrowDate1(String borrowDate1) throws CalendarException{
		this.borrowDate1.setCalendarValue(borrowDate1);
	}
	
	/**
	 * 功能：获取标签号信息(EAM)属性 启用日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getBorrowDate1() throws CalendarException {
		borrowDate1.setCalPattern(getCalPattern());
		return this.borrowDate1;
	}
	
	/**
	 * 功能：设置标签号信息(EAM)属性 启用日期
	 * @param returnDatePlan SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setReturnDatePlan(String returnDatePlan) throws CalendarException{
		this.returnDatePlan.setCalendarValue(returnDatePlan);
	}
	
	/**
	 * 功能：获取标签号信息(EAM)属性 启用日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getReturnDatePlan() throws CalendarException {
		returnDatePlan.setCalPattern(getCalPattern());
		return this.returnDatePlan;
	}
	
	/**
	 * 功能：设置标签号信息(EAM)属性 启用日期
	 * @param returnDateActual SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setReturnDateActual(String returnDateActual) throws CalendarException{
		this.returnDateActual.setCalendarValue(returnDateActual);
	}
	
	/**
	 * 功能：获取标签号信息(EAM)属性 启用日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getReturnDateActual() throws CalendarException {
		returnDateActual.setCalPattern(getCalPattern());
		return this.returnDateActual;
	}
	
	/**
	 * 功能：设置标签号信息(EAM)属性 启用日期
	 * @param approveDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setApproveDate(String approveDate) throws CalendarException{
		this.approveDate.setCalendarValue(approveDate);
	}
	
	/**
	 * 功能：获取标签号信息(EAM)属性 启用日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getApproveDate() throws CalendarException {
		approveDate.setCalPattern(getCalPattern());
		return this.approveDate;
	}
	
	/**
	 * 功能：设置标签号信息(EAM)属性 启用日期
	 * @param handleDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setHandleDate(String handleDate) throws CalendarException{
		this.handleDate.setCalendarValue(handleDate);
	}
	
	/**
	 * 功能：获取标签号信息(EAM)属性 启用日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getHandleDate() throws CalendarException {
		handleDate.setCalPattern(getCalPattern());
		return this.handleDate;
	}
	
	/**
	 * 功能：设置标签号信息(EAM)属性 启用日期
	 * @param disableDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setDisableDate(String disableDate) throws CalendarException{
		this.disableDate.setCalendarValue(disableDate);
	}
	
	/**
	 * 功能：获取标签号信息(EAM)属性 启用日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getDisableDate() throws CalendarException {
		disableDate.setCalPattern(getCalPattern());
		return this.disableDate;
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

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getVendorBarcode() {
		return vendorBarcode;
	}

	public void setVendorBarcode(String vendorBarcode) {
		this.vendorBarcode = vendorBarcode;
	}

	public String getItemQty() {
		return itemQty;
	}

	public void setItemQty(String itemQty) {
		this.itemQty = itemQty;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getAttribute3() {
		return attribute3;
	}

	public void setAttribute3(String attribute3) {
		this.attribute3 = attribute3;
	}

	public String getResponsibilityDept() {
		return responsibilityDept;
	}

	public void setResponsibilityDept(String responsibilityDept) {
		this.responsibilityDept = responsibilityDept;
	}

	public int getResponsibilityUser() {
		return responsibilityUser;
	}

	public void setResponsibilityUser(int responsibilityUser) {
		this.responsibilityUser = responsibilityUser;
	}

	public String getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(String itemStatus) {
		this.itemStatus = itemStatus;
	}

	public String getMaintainUser() {
		return maintainUser;
	}

	public void setMaintainUser(String maintainUser) {
		this.maintainUser = maintainUser;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getWorkorderObjectName() {
		return workorderObjectName;
	}

	public void setWorkorderObjectName(String workorderObjectName) {
		this.workorderObjectName = workorderObjectName;
	}

	public int getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}

	public String getDisposeReason() {
		return disposeReason;
	}

	public void setDisposeReason(String disposeReason) {
		this.disposeReason = disposeReason;
	}
	
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getBarcode1() {
		return barcode1;
	}

	public void setBarcode1(String barcode1) {
		this.barcode1 = barcode1;
	}

	public String getVendorBarcode1() {
		return vendorBarcode1;
	}

	public void setVendorBarcode1(String vendorBarcode1) {
		this.vendorBarcode1 = vendorBarcode1;
	}

	public String getBarcodeNo() {
		return barcodeNo;
	}

	public void setBarcodeNo(String barcodeNo) {
		this.barcodeNo = barcodeNo;
	}

	public String getWorkorderObjectNo() {
		return workorderObjectNo;
	}

	public void setWorkorderObjectNo(String workorderObjectNo) {
		this.workorderObjectNo = workorderObjectNo;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getBorrowLogId() {
		return borrowLogId;
	}

	public void setBorrowLogId(String borrowLogId) {
		this.borrowLogId = borrowLogId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getBorrowUserId() {
		return borrowUserId;
	}

	public void setBorrowUserId(int borrowUserId) {
		this.borrowUserId = borrowUserId;
	}

	public int getApproveUserId() {
		return approveUserId;
	}

	public void setApproveUserId(int approveUserId) {
		this.approveUserId = approveUserId;
	}

	public int getHandleUserId() {
		return handleUserId;
	}

	public void setHandleUserId(int handleUserId) {
		this.handleUserId = handleUserId;
	}

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getBarcode2() {
		return barcode2;
	}

	public void setBarcode2(String barcode2) {
		this.barcode2 = barcode2;
	}

	public String getResponsibilityUser1() {
		return responsibilityUser1;
	}

	public void setResponsibilityUser1(String responsibilityUser1) {
		this.responsibilityUser1 = responsibilityUser1;
	}
}
