package com.sino.ams.dzyh.dto;

import com.sino.ams.bean.CommonRecordDTO;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.exception.CalendarException;

/**
* <p>Title: 表结构定义-L(EAM) EamDhBillL</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 张星
* @version 1.0
*/

public class EamDhBillLDTO extends CommonRecordDTO{

	private String billLineId = "";
	private String billHeaderId = "";
	private int catalogValueId = 0;
	private String itemCode = "";
	private int quantity = 0;
	private float price = 0;
	private String responsibilityDept = "";
	private int responsibilityUser = 0;
	private int workorderObjectNo = 0;
	private String maintainUser = "";
	private SimpleCalendar lastLocChgDate = null;
	private String manufactory = "";
	private String remark = "";

//	=============================================================================

	private String financeProp = "DZYH";
	private String billNo = "";	//单据编号
	private String billStatus = "";	//单据状态
    private String createUser = "";	//创建人
    private int orgId = SyBaseSQLUtil.NULL_INT_VALUE ;	
	private String catalogCode = "";	//目录编号
	private String itemName = "";	//品名
	private String itemSpec = "";	//规格型号
	private String barcode = "";	//条码号
	private String deptName = "";	//使用部门
	private String userName = "";	//领用人
	private String workorderObjectName = "";	//地点

	public EamDhBillLDTO() {
		super();
		this.lastLocChgDate = new SimpleCalendar();
	}

	/**
	 * 功能：设置表结构定义-L(EAM)属性 EAM_DH_BILL_L_S.NEXTVAL
	 * @param billLineId String
	 */
	public void setBillLineId(String billLineId){
		this.billLineId = billLineId;
	}

	/**
	 * 功能：设置表结构定义-L(EAM)属性 EAM_DH_BILL_H. BILL_HEADER_ID
	 * @param billHeaderId String
	 */
	public void setBillHeaderId(String billHeaderId){
		this.billHeaderId = billHeaderId;
	}

	/**
	 * 功能：设置表结构定义-L(EAM)属性 目录编号EAM_DH_CATALOG_VALUES. Catalog_VALUE_ID
	 * @param catalogValueId String
	 */
	public void setCatalogValueId(int catalogValueId){
		this.catalogValueId = catalogValueId;
	}

	/**
	 * 功能：设置表结构定义-L(EAM)属性 设备代码（ETS_SYSTEM_ITEM.ITEM_CODE）
	 * @param itemCode String
	 */
	public void setItemCode(String itemCode){
		this.itemCode = itemCode;
	}

	/**
	 * 功能：设置表结构定义-L(EAM)属性 数量
	 * @param quantity String
	 */
	public void setQuantity(int quantity){
		this.quantity = quantity;
	}

	/**
	 * 功能：设置表结构定义-L(EAM)属性 单价
	 * @param price String
	 */
	public void setPrice(float price){
		this.price = price;
	}

	/**
	 * 功能：设置表结构定义-L(EAM)属性 使用部门(ETS_ITEM_INFO. RESPONSIBILITY_DEPT),数据字典：ams_mis_dept.dept_code
	 * @param responsibilityDept String
	 */
	public void setResponsibilityDept(String responsibilityDept){
		this.responsibilityDept = responsibilityDept;
	}

	/**
	 * 功能：设置表结构定义-L(EAM)属性 领用人（ETS_ITEM_INFO. RESPONSIBILITY_USER）,
数据字典：ams_mis_employ.employee_id

	 * @param responsibilityUser String
	 */
	public void setResponsibilityUser(int responsibilityUser){
		this.responsibilityUser = responsibilityUser;
	}

	/**
	 * 功能：设置表结构定义-L(EAM)属性 地点（ETS_OBJECT. WORKORDER_OBJECT_NAME）
	 * @param workorderObjectNo String
	 */
	public void setWorkorderObjectNo(int workorderObjectNo){
		this.workorderObjectNo = workorderObjectNo;
	}

	/**
	 * 功能：设置表结构定义-L(EAM)属性 保管人（ETS_ITEM_INFO. MAINTAIN_USER）,手工填写
	 * @param maintainUser String
	 */
	public void setMaintainUser(String maintainUser){
		this.maintainUser = maintainUser;
	}

	/**
	 * 功能：设置表结构定义-L(EAM)属性 领用日期（ETS_ITEM_INFO. LAST_LOC_CHG_DATE）
	 * @param lastLocChgDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setLastLocChgDate(String lastLocChgDate) throws CalendarException{
		this.lastLocChgDate.setCalendarValue(lastLocChgDate);
	}

	/**
	 * 功能：设置表结构定义-L(EAM)属性 厂家（ETS_ITEM_INFO.ATTRIBUTE3）
	 * @param manufactory String
	 */
	public void setManufactory(String manufactory){
		this.manufactory = manufactory;
	}

	/**
	 * 功能：设置表结构定义-L(EAM)属性 备注ETS_ITEM_INFO.REMARK
	 * @param remark String
	 */
	public void setRemark(String remark){
		this.remark = remark;
	}

	/**
	 * 功能：获取表结构定义-L(EAM)属性 EAM_DH_BILL_L_S.NEXTVAL
	 * @return String
	 */
	public String getBillLineId() {
		return this.billLineId;
	}

	/**
	 * 功能：获取表结构定义-L(EAM)属性 EAM_DH_BILL_H. BILL_HEADER_ID
	 * @return String
	 */
	public String getBillHeaderId() {
		return this.billHeaderId;
	}

	/**
	 * 功能：获取表结构定义-L(EAM)属性 目录编号EAM_DH_CATALOG_VALUES. Catalog_VALUE_ID
	 * @return String
	 */
	public int getCatalogValueId() {
		return this.catalogValueId;
	}

	/**
	 * 功能：获取表结构定义-L(EAM)属性 设备代码（ETS_SYSTEM_ITEM.ITEM_CODE）
	 * @return String
	 */
	public String getItemCode() {
		return this.itemCode;
	}

	/**
	 * 功能：获取表结构定义-L(EAM)属性 数量
	 * @return String
	 */
	public int getQuantity() {
		return this.quantity;
	}

	/**
	 * 功能：获取表结构定义-L(EAM)属性 单价
	 * @return String
	 */
	public float getPrice() {
		return this.price;
	}

	/**
	 * 功能：获取表结构定义-L(EAM)属性 使用部门(ETS_ITEM_INFO. RESPONSIBILITY_DEPT),数据字典：ams_mis_dept.dept_code
	 * @return String
	 */
	public String getResponsibilityDept() {
		return this.responsibilityDept;
	}

	/**
	 * 功能：获取表结构定义-L(EAM)属性 领用人（ETS_ITEM_INFO. RESPONSIBILITY_USER）,
数据字典：ams_mis_employ.employee_id

	 * @return String
	 */
	public int getResponsibilityUser() {
		return this.responsibilityUser;
	}

	/**
	 * 功能：获取表结构定义-L(EAM)属性 地点（ETS_OBJECT. WORKORDER_OBJECT_NAME）
	 * @return String
	 */
	public int getWorkorderObjectNo() {
		return this.workorderObjectNo;
	}

	/**
	 * 功能：获取表结构定义-L(EAM)属性 保管人（ETS_ITEM_INFO. MAINTAIN_USER）,手工填写
	 * @return String
	 */
	public String getMaintainUser() {
		return this.maintainUser;
	}

	/**
	 * 功能：获取表结构定义-L(EAM)属性 领用日期（ETS_ITEM_INFO. LAST_LOC_CHG_DATE）
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getLastLocChgDate() throws CalendarException {
		lastLocChgDate.setCalPattern(getCalPattern());
		return this.lastLocChgDate;
	}

	/**
	 * 功能：获取表结构定义-L(EAM)属性 厂家（ETS_ITEM_INFO.ATTRIBUTE3）
	 * @return String
	 */
	public String getManufactory() {
		return this.manufactory;
	}

	/**
	 * 功能：获取表结构定义-L(EAM)属性 备注ETS_ITEM_INFO.REMARK
	 * @return String
	 */
	public String getRemark() {
		return this.remark;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getBillStatus() {
		return billStatus;
	}

	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus;
	}

	public String getCatalogCode() {
		return catalogCode;
	}

	public void setCatalogCode(String catalogCode) {
		this.catalogCode = catalogCode;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getFinanceProp() {
		return financeProp;
	}

	public void setFinanceProp(String financeProp) {
		this.financeProp = financeProp;
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

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getWorkorderObjectName() {
		return workorderObjectName;
	}

	public void setWorkorderObjectName(String workorderObjectName) {
		this.workorderObjectName = workorderObjectName;
	}
}