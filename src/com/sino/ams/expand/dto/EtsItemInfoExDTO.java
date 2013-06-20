package com.sino.ams.expand.dto;

import com.sino.ams.bean.CommonRecordDTO;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.exception.CalendarException;

/**
* <p>Title: 资产拓展信息表－ETS_ITEM_INFO_EX EtsItemInfoEx</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class EtsItemInfoExDTO extends CommonRecordDTO{

	private String itemInfoExId = "";	
	private String systemId = "";	
	private String barcode = "";	//条码
	private String itemType = "";	//设备类型（’CAR‘，‘IT’）
	private String attribute1 = "";	//车牌号			（CPU频率）
	private String attribute2 = "";	//许可证				（内存容量）
	private String attribute3 = "";	//用途				（硬盘容量）
	private String attribute4 = "";	//默认驾驶员				（显示器类型）
	private String attribute5 = "";	//						（显示器尺寸）					
	private String attribute6 = "";
	private String attribute7 = "";
	private String attribute8 = "";
	private String attribute9 = "";
	private String attribute10 = "";
	private String attribute11 = "";
	private String attribute12 = "";
	private String attribute13 = "";
	private String attribute14 = "";
	private String attribute15 = "";
	private String attribute16 = "";
	private String attribute17 = "";
	private String attribute18 = "";
	private String attribute19 = "";
	private String attribute20 = "";
	private String nAttribute1 = "";
	private String nAttribute2 = "";
	private String nAttribute3 = "";
	private String nAttribute4 = "";
	private String nAttribute5 = "";
	private SimpleCalendar dAttribute1 = null;
	private SimpleCalendar dAttribute2 = null;
	private SimpleCalendar dAttribute3 = null;
	private SimpleCalendar dAttribute4 = null;
	private SimpleCalendar dAttribute5 = null;

	//------------------------------------------ETS_SYSTEM_ITEM-------------------------------------------------------

	private String itemName = "";	//名称
	private String itemSpec = "";	//型号
	
	//------------------------------------------ETS_FA_ASSETS-------------------------------------------------------
	
	private SimpleCalendar datePlacedInService=null;	//启用日期
	private String lifeInYears="";	//折旧年限
	
	//------------------------------------------ETS_ITEM_INFO-------------------------------------------------------
	private String responsibilityUser="";	//责任人
	private String employeeName="";	//责任人
	
	public EtsItemInfoExDTO(){
		super();
		this.dAttribute1=new SimpleCalendar();
		this.dAttribute2=new SimpleCalendar();
		this.dAttribute3=new SimpleCalendar();
		this.dAttribute4=new SimpleCalendar();
		this.dAttribute5=new SimpleCalendar();
		this.datePlacedInService=new SimpleCalendar();
	}
	
	public SimpleCalendar getDatePlacedInService() throws CalendarException {
		datePlacedInService.setCalPattern(getCalPattern());
		return datePlacedInService;
	}

	public void setDatePlacedInService(String datePlacedInService) throws CalendarException {
		this.datePlacedInService.setCalendarValue(datePlacedInService);
	}

	/**
	 * 功能：设置资产拓展信息表－ETS_ITEM_INFO_EX属性 ETS_ITEM_INFO_EX_S.NEXTVAL
	 * @param itemInfoExId String
	 */
	public void setItemInfoExId(String itemInfoExId){
		this.itemInfoExId = itemInfoExId;
	}

	/**
	 * 功能：设置资产拓展信息表－ETS_ITEM_INFO_EX属性 ETS_ITEM_INFO.barcode
	 * @param barcode String
	 */
	public void setBarcode(String barcode){
		this.barcode = barcode;
	}

	/**
	 * 功能：设置资产拓展信息表－ETS_ITEM_INFO_EX属性 设备类型（’CAR’）
	 * @param itemType String
	 */
	public void setItemType(String itemType){
		this.itemType = itemType;
	}

	/**
	 * 功能：设置资产拓展信息表－ETS_ITEM_INFO_EX属性 车牌号
	 * @param attribute1 String
	 */
	public void setAttribute1(String attribute1){
		this.attribute1 = attribute1;
	}

	/**
	 * 功能：设置资产拓展信息表－ETS_ITEM_INFO_EX属性 许可证
	 * @param attribute2 String
	 */
	public void setAttribute2(String attribute2){
		this.attribute2 = attribute2;
	}

	/**
	 * 功能：设置资产拓展信息表－ETS_ITEM_INFO_EX属性 用途
	 * @param attribute3 String
	 */
	public void setAttribute3(String attribute3){
		this.attribute3 = attribute3;
	}

	/**
	 * 功能：设置资产拓展信息表－ETS_ITEM_INFO_EX属性 默认驾驶员
	 * @param attribute4 String
	 */
	public void setAttribute4(String attribute4){
		this.attribute4 = attribute4;
	}

	/**
	 * 功能：设置资产拓展信息表－ETS_ITEM_INFO_EX属性 ATTRIBUTE5
	 * @param attribute5 String
	 */
	public void setAttribute5(String attribute5){
		this.attribute5 = attribute5;
	}

	/**
	 * 功能：设置资产拓展信息表－ETS_ITEM_INFO_EX属性 ATTRIBUTE6
	 * @param attribute6 String
	 */
	public void setAttribute6(String attribute6){
		this.attribute6 = attribute6;
	}

	/**
	 * 功能：设置资产拓展信息表－ETS_ITEM_INFO_EX属性 ATTRIBUTE7
	 * @param attribute7 String
	 */
	public void setAttribute7(String attribute7){
		this.attribute7 = attribute7;
	}

	/**
	 * 功能：设置资产拓展信息表－ETS_ITEM_INFO_EX属性 ATTRIBUTE8
	 * @param attribute8 String
	 */
	public void setAttribute8(String attribute8){
		this.attribute8 = attribute8;
	}

	/**
	 * 功能：设置资产拓展信息表－ETS_ITEM_INFO_EX属性 ATTRIBUTE9
	 * @param attribute9 String
	 */
	public void setAttribute9(String attribute9){
		this.attribute9 = attribute9;
	}

	/**
	 * 功能：设置资产拓展信息表－ETS_ITEM_INFO_EX属性 ATTRIBUTE10
	 * @param attribute10 String
	 */
	public void setAttribute10(String attribute10){
		this.attribute10 = attribute10;
	}

	/**
	 * 功能：设置资产拓展信息表－ETS_ITEM_INFO_EX属性 ATTRIBUTE11
	 * @param attribute11 String
	 */
	public void setAttribute11(String attribute11){
		this.attribute11 = attribute11;
	}

	/**
	 * 功能：设置资产拓展信息表－ETS_ITEM_INFO_EX属性 ATTRIBUTE12
	 * @param attribute12 String
	 */
	public void setAttribute12(String attribute12){
		this.attribute12 = attribute12;
	}

	/**
	 * 功能：设置资产拓展信息表－ETS_ITEM_INFO_EX属性 ATTRIBUTE13
	 * @param attribute13 String
	 */
	public void setAttribute13(String attribute13){
		this.attribute13 = attribute13;
	}

	/**
	 * 功能：设置资产拓展信息表－ETS_ITEM_INFO_EX属性 ATTRIBUTE14
	 * @param attribute14 String
	 */
	public void setAttribute14(String attribute14){
		this.attribute14 = attribute14;
	}

	/**
	 * 功能：设置资产拓展信息表－ETS_ITEM_INFO_EX属性 ATTRIBUTE15
	 * @param attribute15 String
	 */
	public void setAttribute15(String attribute15){
		this.attribute15 = attribute15;
	}

	/**
	 * 功能：设置资产拓展信息表－ETS_ITEM_INFO_EX属性 ATTRIBUTE16
	 * @param attribute16 String
	 */
	public void setAttribute16(String attribute16){
		this.attribute16 = attribute16;
	}

	/**
	 * 功能：设置资产拓展信息表－ETS_ITEM_INFO_EX属性 ATTRIBUTE17
	 * @param attribute17 String
	 */
	public void setAttribute17(String attribute17){
		this.attribute17 = attribute17;
	}

	/**
	 * 功能：设置资产拓展信息表－ETS_ITEM_INFO_EX属性 ATTRIBUTE18
	 * @param attribute18 String
	 */
	public void setAttribute18(String attribute18){
		this.attribute18 = attribute18;
	}

	/**
	 * 功能：设置资产拓展信息表－ETS_ITEM_INFO_EX属性 ATTRIBUTE19
	 * @param attribute19 String
	 */
	public void setAttribute19(String attribute19){
		this.attribute19 = attribute19;
	}

	/**
	 * 功能：设置资产拓展信息表－ETS_ITEM_INFO_EX属性 ATTRIBUTE20
	 * @param attribute20 String
	 */
	public void setAttribute20(String attribute20){
		this.attribute20 = attribute20;
	}

	/**
	 * 功能：设置资产拓展信息表－ETS_ITEM_INFO_EX属性 N_ATTRIBUTE1
	 * @param nAttribute1 String
	 */
	public void setNAttribute1(String nAttribute1){
		this.nAttribute1 = nAttribute1;
	}

	/**
	 * 功能：设置资产拓展信息表－ETS_ITEM_INFO_EX属性 N_ATTRIBUTE2
	 * @param nAttribute2 String
	 */
	public void setNAttribute2(String nAttribute2){
		this.nAttribute2 = nAttribute2;
	}

	/**
	 * 功能：设置资产拓展信息表－ETS_ITEM_INFO_EX属性 N_ATTRIBUTE3
	 * @param nAttribute3 String
	 */
	public void setNAttribute3(String nAttribute3){
		this.nAttribute3 = nAttribute3;
	}

	/**
	 * 功能：设置资产拓展信息表－ETS_ITEM_INFO_EX属性 N_ATTRIBUTE4
	 * @param nAttribute4 String
	 */
	public void setNAttribute4(String nAttribute4){
		this.nAttribute4 = nAttribute4;
	}

	/**
	 * 功能：设置资产拓展信息表－ETS_ITEM_INFO_EX属性 N_ATTRIBUTE5
	 * @param nAttribute5 String
	 */
	public void setNAttribute5(String nAttribute5){
		this.nAttribute5 = nAttribute5;
	}

	/**
	 * 功能：设置资产拓展信息表－ETS_ITEM_INFO_EX属性 D_ATTRIBUTE1
	 * @param dAttribute1 String
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setDAttribute1(String dAttribute1) throws CalendarException{
		this.dAttribute1.setCalendarValue(dAttribute1);
	}

	/**
	 * 功能：设置资产拓展信息表－ETS_ITEM_INFO_EX属性 D_ATTRIBUTE2
	 * @param dAttribute2 String
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setDAttribute2(String dAttribute2) throws CalendarException{
		this.dAttribute2.setCalendarValue(dAttribute2);
	}

	/**
	 * 功能：设置资产拓展信息表－ETS_ITEM_INFO_EX属性 D_ATTRIBUTE3
	 * @param dAttribute3 String
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setDAttribute3(String dAttribute3) throws CalendarException{
		this.dAttribute3.setCalendarValue(dAttribute3);
	}

	/**
	 * 功能：设置资产拓展信息表－ETS_ITEM_INFO_EX属性 D_ATTRIBUTE4
	 * @param dAttribute4 String
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setDAttribute4(String dAttribute4) throws CalendarException{
		this.dAttribute4.setCalendarValue(dAttribute4);
	}

	/**
	 * 功能：设置资产拓展信息表－ETS_ITEM_INFO_EX属性 D_ATTRIBUTE5
	 * @param dAttribute5 String
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setDAttribute5(String dAttribute5) throws CalendarException{
		this.dAttribute5.setCalendarValue(dAttribute5);
	}

	/**
	 * 功能：获取资产拓展信息表－ETS_ITEM_INFO_EX属性 ETS_ITEM_INFO_EX_S.NEXTVAL
	 * @return String
	 */
	public String getItemInfoExId() {
		return this.itemInfoExId;
	}

	/**
	 * 功能：获取资产拓展信息表－ETS_ITEM_INFO_EX属性 ETS_ITEM_INFO.barcode
	 * @return String
	 */
	public String getBarcode() {
		return this.barcode;
	}

	/**
	 * 功能：获取资产拓展信息表－ETS_ITEM_INFO_EX属性 设备类型（’CAR’）
	 * @return String
	 */
	public String getItemType() {
		return this.itemType;
	}

	/**
	 * 功能：获取资产拓展信息表－ETS_ITEM_INFO_EX属性 车牌号
	 * @return String
	 */
	public String getAttribute1() {
		return this.attribute1;
	}

	/**
	 * 功能：获取资产拓展信息表－ETS_ITEM_INFO_EX属性 许可证
	 * @return String
	 */
	public String getAttribute2() {
		return this.attribute2;
	}

	/**
	 * 功能：获取资产拓展信息表－ETS_ITEM_INFO_EX属性 用途
	 * @return String
	 */
	public String getAttribute3() {
		return this.attribute3;
	}

	/**
	 * 功能：获取资产拓展信息表－ETS_ITEM_INFO_EX属性 默认驾驶员
	 * @return String
	 */
	public String getAttribute4() {
		return this.attribute4;
	}

	/**
	 * 功能：获取资产拓展信息表－ETS_ITEM_INFO_EX属性 ATTRIBUTE5
	 * @return String
	 */
	public String getAttribute5() {
		return this.attribute5;
	}

	/**
	 * 功能：获取资产拓展信息表－ETS_ITEM_INFO_EX属性 ATTRIBUTE6
	 * @return String
	 */
	public String getAttribute6() {
		return this.attribute6;
	}

	/**
	 * 功能：获取资产拓展信息表－ETS_ITEM_INFO_EX属性 ATTRIBUTE7
	 * @return String
	 */
	public String getAttribute7() {
		return this.attribute7;
	}

	/**
	 * 功能：获取资产拓展信息表－ETS_ITEM_INFO_EX属性 ATTRIBUTE8
	 * @return String
	 */
	public String getAttribute8() {
		return this.attribute8;
	}

	/**
	 * 功能：获取资产拓展信息表－ETS_ITEM_INFO_EX属性 ATTRIBUTE9
	 * @return String
	 */
	public String getAttribute9() {
		return this.attribute9;
	}

	/**
	 * 功能：获取资产拓展信息表－ETS_ITEM_INFO_EX属性 ATTRIBUTE10
	 * @return String
	 */
	public String getAttribute10() {
		return this.attribute10;
	}

	/**
	 * 功能：获取资产拓展信息表－ETS_ITEM_INFO_EX属性 ATTRIBUTE11
	 * @return String
	 */
	public String getAttribute11() {
		return this.attribute11;
	}

	/**
	 * 功能：获取资产拓展信息表－ETS_ITEM_INFO_EX属性 ATTRIBUTE12
	 * @return String
	 */
	public String getAttribute12() {
		return this.attribute12;
	}

	/**
	 * 功能：获取资产拓展信息表－ETS_ITEM_INFO_EX属性 ATTRIBUTE13
	 * @return String
	 */
	public String getAttribute13() {
		return this.attribute13;
	}

	/**
	 * 功能：获取资产拓展信息表－ETS_ITEM_INFO_EX属性 ATTRIBUTE14
	 * @return String
	 */
	public String getAttribute14() {
		return this.attribute14;
	}

	/**
	 * 功能：获取资产拓展信息表－ETS_ITEM_INFO_EX属性 ATTRIBUTE15
	 * @return String
	 */
	public String getAttribute15() {
		return this.attribute15;
	}

	/**
	 * 功能：获取资产拓展信息表－ETS_ITEM_INFO_EX属性 ATTRIBUTE16
	 * @return String
	 */
	public String getAttribute16() {
		return this.attribute16;
	}

	/**
	 * 功能：获取资产拓展信息表－ETS_ITEM_INFO_EX属性 ATTRIBUTE17
	 * @return String
	 */
	public String getAttribute17() {
		return this.attribute17;
	}

	/**
	 * 功能：获取资产拓展信息表－ETS_ITEM_INFO_EX属性 ATTRIBUTE18
	 * @return String
	 */
	public String getAttribute18() {
		return this.attribute18;
	}

	/**
	 * 功能：获取资产拓展信息表－ETS_ITEM_INFO_EX属性 ATTRIBUTE19
	 * @return String
	 */
	public String getAttribute19() {
		return this.attribute19;
	}

	/**
	 * 功能：获取资产拓展信息表－ETS_ITEM_INFO_EX属性 ATTRIBUTE20
	 * @return String
	 */
	public String getAttribute20() {
		return this.attribute20;
	}

	/**
	 * 功能：获取资产拓展信息表－ETS_ITEM_INFO_EX属性 N_ATTRIBUTE1
	 * @return String
	 */
	public String getNAttribute1() {
		return this.nAttribute1;
	}

	/**
	 * 功能：获取资产拓展信息表－ETS_ITEM_INFO_EX属性 N_ATTRIBUTE2
	 * @return String
	 */
	public String getNAttribute2() {
		return this.nAttribute2;
	}

	/**
	 * 功能：获取资产拓展信息表－ETS_ITEM_INFO_EX属性 N_ATTRIBUTE3
	 * @return String
	 */
	public String getNAttribute3() {
		return this.nAttribute3;
	}

	/**
	 * 功能：获取资产拓展信息表－ETS_ITEM_INFO_EX属性 N_ATTRIBUTE4
	 * @return String
	 */
	public String getNAttribute4() {
		return this.nAttribute4;
	}

	/**
	 * 功能：获取资产拓展信息表－ETS_ITEM_INFO_EX属性 N_ATTRIBUTE5
	 * @return String
	 */
	public String getNAttribute5() {
		return this.nAttribute5;
	}

	/**
	 * 功能：获取资产拓展信息表－ETS_ITEM_INFO_EX属性 D_ATTRIBUTE1
	 * @return String
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getDAttribute1() throws CalendarException {
		dAttribute1.setCalPattern(getCalPattern());
		return this.dAttribute1;
	}

	/**
	 * 功能：获取资产拓展信息表－ETS_ITEM_INFO_EX属性 D_ATTRIBUTE2
	 * @return String
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getDAttribute2() throws CalendarException {
		dAttribute2.setCalPattern(getCalPattern());
		return this.dAttribute2;
	}

	/**
	 * 功能：获取资产拓展信息表－ETS_ITEM_INFO_EX属性 D_ATTRIBUTE3
	 * @return String
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getDAttribute3() throws CalendarException {
		dAttribute3.setCalPattern(getCalPattern());
		return this.dAttribute3;
	}

	/**
	 * 功能：获取资产拓展信息表－ETS_ITEM_INFO_EX属性 D_ATTRIBUTE4
	 * @return String
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getDAttribute4() throws CalendarException {
		dAttribute4.setCalPattern(getCalPattern());
		return this.dAttribute4;
	}

	/**
	 * 功能：获取资产拓展信息表－ETS_ITEM_INFO_EX属性 D_ATTRIBUTE5
	 * @return String
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getDAttribute5() throws CalendarException {
		dAttribute5.setCalPattern(getCalPattern());
		return this.dAttribute5;
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
	public String getLifeInYears() {
		return lifeInYears;
	}
	public void setLifeInYears(String lifeInYears) {
		this.lifeInYears = lifeInYears;
	}
	public String getResponsibilityUser() {
		return responsibilityUser;
	}
	public void setResponsibilityUser(String responsibilityUser) {
		this.responsibilityUser = responsibilityUser;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

}