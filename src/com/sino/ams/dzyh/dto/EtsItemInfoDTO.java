package com.sino.ams.dzyh.dto;

import com.sino.ams.bean.CommonRecordDTO;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.exception.CalendarException;

/**
* <p>Title: (低值易耗品)标签号信息(EAM) EtsItemInfo</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 张星
* @version 1.0
*/

public class EtsItemInfoDTO extends CommonRecordDTO{

	private String systemid = "";	//系统ID(序列号)
	private String faBarcode = "";	//对应MIS固定资产条码;禁止使用字段
	private String vendorBarcode = "";	//对应厂商条码
	private String itemQty = "";	//设备数量
	private SimpleCalendar disableDate = null;	//失效日期
	private String remark = "";		//备注
	private String itemCode = "";	//分类编码
	private int projectid = SyBaseSQLUtil.NULL_INT_VALUE;	//工程ID
	private String itemStatus = "";	//设备状态
	private String attribute1 = "";	//扩展属性1;租赁属性
	private String attribute2 = "";	//扩展属性2
	private String sendtomisFlag = "";	//同步到MIS标志
	private String misItemname = "";	//MIS设备名称
	private String misItemspec = "";	//MIS规格型号
	private int assetId = SyBaseSQLUtil.NULL_INT_VALUE;	//所属资产ID
	private int addressId = SyBaseSQLUtil.NULL_INT_VALUE;	//资产地点ID
	private String financeProp = "";	//财务属性
	private String attribute3 = "";	//扩展属性3（厂家）
	private String parentBarcode = "";	//父标签号
	private SimpleCalendar lastLocChgDate = null;	//设备地点最后变动时间（领用日期）
	private int organizationId = SyBaseSQLUtil.NULL_INT_VALUE;	//组织ID
	private String barcode = "";	//标签号
	private String isParent = "";	//是否是父设备
	private int responsibilityUser = SyBaseSQLUtil.NULL_INT_VALUE;	//责任人
	private String responsibilityDept = "";	//责任部门
	private String maintainUser = "";	//维护人员
	private String maintainDept = "";	//使用部门
	private String isTmp = "";	//1:临时设备
	private String price = "";	//单价

	private String eiiDeptName = "";	//使用部门
	private String eiiUserName = "";	//领用人
	
	//==============================================ETS_SYSTEM_ITEM===========================
	private String eiiItemCategory2 = "";	//目录编号
	private String eiiItemName = "";		//品名
	private String eiiItemSpec = "";		//规格型号
	
	//================================================ETS_0BJECT==============================
	private String eiiWorkorderObjectName = "";	//地点

	public EtsItemInfoDTO(){
		super();
		this.disableDate = new SimpleCalendar();
		this.lastLocChgDate = new SimpleCalendar();
	}
	
	public String getEiiItemCategory2() {
		return eiiItemCategory2;
	}

	public void setEiiItemCategory2(String eiiItemCategory2) {
		this.eiiItemCategory2 = eiiItemCategory2;
	}

	public String getEiiItemName() {
		return eiiItemName;
	}

	public void setEiiItemName(String eiiItemName) {
		this.eiiItemName = eiiItemName;
	}

	public String getEiiItemSpec() {
		return eiiItemSpec;
	}

	public void setEiiItemSpec(String eiiItemSpec) {
		this.eiiItemSpec = eiiItemSpec;
	}

	public String getEiiWorkorderObjectName() {
		return eiiWorkorderObjectName;
	}

	public void setEiiWorkorderObjectName(String eiiWorkorderObjectName) {
		this.eiiWorkorderObjectName = eiiWorkorderObjectName;
	}

	/**
	 * 功能：设置标签号信息(EAM)属性 系统ID(序列号)
	 * @param systemid String
	 */
	public void setSystemid(String systemid){
		this.systemid = systemid;
	}

	/**
	 * 功能：设置标签号信息(EAM)属性 对应MIS固定资产条码;禁止使用字段
	 * @param faBarcode String
	 */
	public void setFaBarcode(String faBarcode){
		this.faBarcode = faBarcode;
	}

	/**
	 * 功能：设置标签号信息(EAM)属性 对应厂商条码
	 * @param vendorBarcode String
	 */
	public void setVendorBarcode(String vendorBarcode){
		this.vendorBarcode = vendorBarcode;
	}

	/**
	 * 功能：设置标签号信息(EAM)属性 设备数量
	 * @param itemQty String
	 */
	public void setItemQty(String itemQty){
		this.itemQty = itemQty;
	}

	/**
	 * 功能：设置标签号信息(EAM)属性 失效日期
	 * @param disableDate String
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setDisableDate(String disableDate) throws CalendarException{
		this.disableDate.setCalendarValue(disableDate);
	}

	/**
	 * 功能：设置标签号信息(EAM)属性 备注
	 * @param remark String
	 */
	public void setRemark(String remark){
		this.remark = remark;
	}

	/**
	 * 功能：设置标签号信息(EAM)属性 分类编码
	 * @param itemCode String
	 */
	public void setItemCode(String itemCode){
		this.itemCode = itemCode;
	}

	/**
	 * 功能：设置标签号信息(EAM)属性 工程ID
	 * @param projectid String
	 */
	public void setProjectid(int projectid){
		this.projectid = projectid;
	}

	/**
	 * 功能：设置标签号信息(EAM)属性 设备状态
	 * @param itemStatus String
	 */
	public void setItemStatus(String itemStatus){
		this.itemStatus = itemStatus;
	}

	/**
	 * 功能：设置标签号信息(EAM)属性 扩展属性1;租赁属性
	 * @param attribute1 String
	 */
	public void setAttribute1(String attribute1){
		this.attribute1 = attribute1;
	}

	/**
	 * 功能：设置标签号信息(EAM)属性 扩展属性2
	 * @param attribute2 String
	 */
	public void setAttribute2(String attribute2){
		this.attribute2 = attribute2;
	}

	/**
	 * 功能：设置标签号信息(EAM)属性 同步到MIS标志
	 * @param sendtomisFlag String
	 */
	public void setSendtomisFlag(String sendtomisFlag){
		this.sendtomisFlag = sendtomisFlag;
	}

	/**
	 * 功能：设置标签号信息(EAM)属性 MIS设备名称
	 * @param misItemname String
	 */
	public void setMisItemname(String misItemname){
		this.misItemname = misItemname;
	}

	/**
	 * 功能：设置标签号信息(EAM)属性 MIS规格型号
	 * @param misItemspec String
	 */
	public void setMisItemspec(String misItemspec){
		this.misItemspec = misItemspec;
	}

	/**
	 * 功能：设置标签号信息(EAM)属性 所属资产ID
	 * @param assetId String
	 */
	public void setAssetId(int assetId){
		this.assetId = assetId;
	}

	/**
	 * 功能：设置标签号信息(EAM)属性 资产地点ID
	 * @param addressId String
	 */
	public void setAddressId(int addressId){
		this.addressId = addressId;
	}

	/**
	 * 功能：设置标签号信息(EAM)属性 财务属性
	 * @param financeProp String
	 */
	public void setFinanceProp(String financeProp){
		this.financeProp = financeProp;
	}

	/**
	 * 功能：设置标签号信息(EAM)属性 扩展属性3(仪器仪表用途)
	 * @param attribute3 String
	 */
	public void setAttribute3(String attribute3){
		this.attribute3 = attribute3;
	}

	/**
	 * 功能：设置标签号信息(EAM)属性 父标签号
	 * @param parentBarcode String
	 */
	public void setParentBarcode(String parentBarcode){
		this.parentBarcode = parentBarcode;
	}

	/**
	 * 功能：设置标签号信息(EAM)属性 设备地点最后变动时间
	 * @param lastLocChgDate String
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setLastLocChgDate(String lastLocChgDate) throws CalendarException{
		this.lastLocChgDate.setCalendarValue(lastLocChgDate);
	}

	/**
	 * 功能：设置标签号信息(EAM)属性 组织ID
	 * @param organizationId String
	 */
	public void setOrganizationId(int organizationId){
		this.organizationId = organizationId;
	}

	/**
	 * 功能：设置标签号信息(EAM)属性 标签号
	 * @param barcode String
	 */
	public void setBarcode(String barcode){
		this.barcode = barcode;
	}

	/**
	 * 功能：设置标签号信息(EAM)属性 是否是父设备
	 * @param isParent String
	 */
	public void setIsParent(String isParent){
		this.isParent = isParent;
	}

	/**
	 * 功能：设置标签号信息(EAM)属性 责任人
	 * @param responsibilityUser String
	 */
	public void setResponsibilityUser(int responsibilityUser){
		this.responsibilityUser = responsibilityUser;
	}

	/**
	 * 功能：设置标签号信息(EAM)属性 责任部门
	 * @param responsibilityDept String
	 */
	public void setResponsibilityDept(String responsibilityDept){
		this.responsibilityDept = responsibilityDept;
	}

	/**
	 * 功能：设置标签号信息(EAM)属性 维护人员
	 * @param maintainUser String
	 */
	public void setMaintainUser(String maintainUser){
		this.maintainUser = maintainUser;
	}

	/**
	 * 功能：设置标签号信息(EAM)属性 使用部门
	 * @param maintainDept String
	 */
	public void setMaintainDept(String maintainDept){
		this.maintainDept = maintainDept;
	}

	/**
	 * 功能：设置标签号信息(EAM)属性 1:临时设备
	 * @param isTmp String
	 */
	public void setIsTmp(String isTmp){
		this.isTmp = isTmp;
	}

	/**
	 * 功能：设置标签号信息(EAM)属性 单价
	 * @param price String
	 */
	public void setPrice(String price){
		this.price = price;
	}


	/**
	 * 功能：获取标签号信息(EAM)属性 系统ID(序列号)
	 * @return String
	 */
	public String getSystemid() {
		return this.systemid;
	}

	/**
	 * 功能：获取标签号信息(EAM)属性 对应MIS固定资产条码;禁止使用字段
	 * @return String
	 */
	public String getFaBarcode() {
		return this.faBarcode;
	}

	/**
	 * 功能：获取标签号信息(EAM)属性 对应厂商条码
	 * @return String
	 */
	public String getVendorBarcode() {
		return this.vendorBarcode;
	}

	/**
	 * 功能：获取标签号信息(EAM)属性 设备数量
	 * @return String
	 */
	public String getItemQty() {
		return this.itemQty;
	}

	/**
	 * 功能：获取标签号信息(EAM)属性 失效日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getDisableDate() throws CalendarException {
		disableDate.setCalPattern(getCalPattern());
		return this.disableDate;
	}

	/**
	 * 功能：获取标签号信息(EAM)属性 备注
	 * @return String
	 */
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 功能：获取标签号信息(EAM)属性 分类编码
	 * @return String
	 */
	public String getItemCode() {
		return this.itemCode;
	}

	/**
	 * 功能：获取标签号信息(EAM)属性 工程ID
	 * @return String
	 */
	public int getProjectid() {
		return this.projectid;
	}

	/**
	 * 功能：获取标签号信息(EAM)属性 设备状态
	 * @return String
	 */
	public String getItemStatus() {
		return this.itemStatus;
	}

	/**
	 * 功能：获取标签号信息(EAM)属性 扩展属性1;租赁属性
	 * @return String
	 */
	public String getAttribute1() {
		return this.attribute1;
	}

	/**
	 * 功能：获取标签号信息(EAM)属性 扩展属性2
	 * @return String
	 */
	public String getAttribute2() {
		return this.attribute2;
	}

	/**
	 * 功能：获取标签号信息(EAM)属性 同步到MIS标志
	 * @return String
	 */
	public String getSendtomisFlag() {
		return this.sendtomisFlag;
	}

	/**
	 * 功能：获取标签号信息(EAM)属性 MIS设备名称
	 * @return String
	 */
	public String getMisItemname() {
		return this.misItemname;
	}

	/**
	 * 功能：获取标签号信息(EAM)属性 MIS规格型号
	 * @return String
	 */
	public String getMisItemspec() {
		return this.misItemspec;
	}

	/**
	 * 功能：获取标签号信息(EAM)属性 所属资产ID
	 * @return String
	 */
	public int getAssetId() {
		return this.assetId;
	}

	/**
	 * 功能：获取标签号信息(EAM)属性 资产地点ID
	 * @return String
	 */
	public int getAddressId() {
		return this.addressId;
	}

	/**
	 * 功能：获取标签号信息(EAM)属性 财务属性
	 * @return String
	 */
	public String getFinanceProp() {
		return this.financeProp;
	}

	/**
	 * 功能：获取标签号信息(EAM)属性 扩展属性3(仪器仪表用途)
	 * @return String
	 */
	public String getAttribute3() {
		return this.attribute3;
	}

	/**
	 * 功能：获取标签号信息(EAM)属性 父标签号
	 * @return String
	 */
	public String getParentBarcode() {
		return this.parentBarcode;
	}

	/**
	 * 功能：获取标签号信息(EAM)属性 设备地点最后变动时间
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getLastLocChgDate() throws CalendarException {
		lastLocChgDate.setCalPattern(getCalPattern());
		return this.lastLocChgDate;
	}

	/**
	 * 功能：获取标签号信息(EAM)属性 组织ID
	 * @return String
	 */
	public int getOrganizationId() {
		return this.organizationId;
	}

	/**
	 * 功能：获取标签号信息(EAM)属性 标签号
	 * @return String
	 */
	public String getBarcode() {
		return this.barcode;
	}

	/**
	 * 功能：获取标签号信息(EAM)属性 是否是父设备
	 * @return String
	 */
	public String getIsParent() {
		return this.isParent;
	}

	/**
	 * 功能：获取标签号信息(EAM)属性 责任人
	 * @return String
	 */
	public int getResponsibilityUser() {
		return this.responsibilityUser;
	}

	/**
	 * 功能：获取标签号信息(EAM)属性 责任部门
	 * @return String
	 */
	public String getResponsibilityDept() {
		return this.responsibilityDept;
	}

	/**
	 * 功能：获取标签号信息(EAM)属性 维护人员
	 * @return String
	 */
	public String getMaintainUser() {
		return this.maintainUser;
	}

	/**
	 * 功能：获取标签号信息(EAM)属性 使用部门
	 * @return String
	 */
	public String getMaintainDept() {
		return this.maintainDept;
	}

	/**
	 * 功能：获取标签号信息(EAM)属性 1:临时设备
	 * @return String
	 */
	public String getIsTmp() {
		return this.isTmp;
	}

	/**
	 * 功能：获取标签号信息(EAM)属性 单价
	 * @return String
	 */
	public String getPrice() {
		return this.price;
	}

	public String getEiiDeptName() {
		return eiiDeptName;
	}

	public void setEiiDeptName(String eiiDeptName) {
		this.eiiDeptName = eiiDeptName;
	}

	public String getEiiUserName() {
		return eiiUserName;
	}

	public void setEiiUserName(String eiiUserName) {
		this.eiiUserName = eiiUserName;
	}
}