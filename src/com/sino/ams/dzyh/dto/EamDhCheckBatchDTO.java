package com.sino.ams.dzyh.dto;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.dzyh.constant.LvecDicts;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.exception.CalendarException;

/**
* <p>Title: 低值易耗盘点工单批表 EamDhCheckBatch</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class EamDhCheckBatchDTO extends EamCheckTaskDTO{

	private String batchId = "";
	private String batchNo = "";
	private int checkDept = 0;
	private String batchStatus = "";
	private String batchStatusValue = "";
	private String batchStatusOpt = "";
	private SimpleCalendar distributeDate = null;
	private int distributeBy = SyBaseSQLUtil.NULL_INT_VALUE;
	private String distributeUser = "";
	private String checkDeptName = "";
	private String defaultImpBy = "";
	private String defaultImpUser = "";
	private String createdLoginUser = "";

	private int defaultImpDays = 60;
	private String orderType = "";
	private String orderTypeValue = "";

	private String checkTools = "";
	private String checkToolsValue = "";
	private String checkToolsOpt = "";

	public EamDhCheckBatchDTO() {
		super();
		this.distributeDate = new SimpleCalendar();
	}

	/**
	 * 功能：设置低值易耗盘点工单批表属性 盘点批序列号
	 * @param batchId String
	 */
	public void setBatchId(String batchId){
		this.batchId = batchId;
	}

	/**
	 * 功能：设置低值易耗盘点工单批表属性 盘点工单批号
	 * @param batchNo String
	 */
	public void setBatchNo(String batchNo){
		this.batchNo = batchNo;
	}

	/**
	 * 功能：设置低值易耗盘点工单批表属性 盘点部门
	 * @param checkDept String
	 */
	public void setCheckDept(int checkDept){
		this.checkDept = checkDept;
	}

	/**
	 * 功能：设置低值易耗盘点工单批表属性 盘点工单批状态(见单据状态字典)
	 * @param batchStatus String
	 */
	public void setBatchStatus(String batchStatus){
		this.batchStatus = batchStatus;
	}

	/**
	 * 功能：设置低值易耗盘点工单批表属性 下发日期
	 * @param distributeDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setDistributeDate(String distributeDate) throws CalendarException{
		this.distributeDate.setCalendarValue(distributeDate);
	}

	/**
	 * 功能：设置低值易耗盘点工单批表属性 下发人
	 * @param distributeBy String
	 */
	public void setDistributeBy(int distributeBy){
		this.distributeBy = distributeBy;
	}

	public void setBatchStatusValue(String batchStatusValue) {
		this.batchStatusValue = batchStatusValue;
	}

	public void setDistributeUser(String distributeUser) {
		this.distributeUser = distributeUser;
	}

	public void setCheckDeptName(String checkDeptName) {
		this.checkDeptName = checkDeptName;
	}

	public void setBatchStatusOpt(String batchStatusOpt) {
		this.batchStatusOpt = batchStatusOpt;
	}

	public void setDefaultImpBy(String defaultImpBy) {
		this.defaultImpBy = defaultImpBy;
	}

	public void setDefaultImpDays(int defaultImpDays) {
		this.defaultImpDays = defaultImpDays;
	}

	public void setDefaultImpUser(String defaultImpUser) {
		this.defaultImpUser = defaultImpUser;
	}

	public void setCreatedLoginUser(String createdLoginUser) {
		this.createdLoginUser = createdLoginUser;
	}

	/**
	 * 功能：获取低值易耗盘点工单批表属性 盘点批序列号
	 * @return String
	 */
	public String getBatchId() {
		return this.batchId;
	}

	/**
	 * 功能：获取低值易耗盘点工单批表属性 盘点工单批号
	 * @return String
	 */
	public String getBatchNo() {
		return this.batchNo;
	}

	/**
	 * 功能：获取低值易耗盘点工单批表属性 盘点部门
	 * @return String
	 */
	public int getCheckDept() {
		return this.checkDept;
	}

	/**
	 * 功能：获取低值易耗盘点工单批表属性 盘点工单批状态(见单据状态字典)
	 * @return String
	 */
	public String getBatchStatus() {
		return this.batchStatus;
	}

	/**
	 * 功能：获取低值易耗盘点工单批表属性 下发日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getDistributeDate() throws CalendarException {
		distributeDate.setCalPattern(getCalPattern());
		return this.distributeDate;
	}

	/**
	 * 功能：获取低值易耗盘点工单批表属性 下发人
	 * @return String
	 */
	public int getDistributeBy() {
		return this.distributeBy;
	}

	public String getBatchStatusValue() {
		return batchStatusValue;
	}

	public String getDistributeUser() {
		return distributeUser;
	}

	public String getCheckDeptName() {
		return checkDeptName;
	}

	public String getBatchStatusOpt() {
		return batchStatusOpt;
	}

	public String getDefaultImpBy() {
		return defaultImpBy;
	}

	public int getDefaultImpDays() {
		return defaultImpDays;
	}

	public String getDefaultImpUser() {
		return defaultImpUser;
	}

	public String getCreatedLoginUser() {
		return createdLoginUser;
	}


	/**
	 * 功能：设置低值易耗盘点工单头表属性 盘点单据类型
	 * @param orderType String
	 */
	public void setOrderType(String orderType){
		this.orderType = orderType;
	}

	public void setOrderTypeValue(String orderTypeValue) {
		this.orderTypeValue = orderTypeValue;
	}

	/**
	 * 功能：获取低值易耗盘点工单头表属性 盘点单据类型
	 * @return String
	 */
	public String getOrderType() {
		return this.orderType;
	}

	public String getOrderTypeValue() {
		if(orderTypeValue.equals("")){
			String[] orderTypes = LvecDicts.ORD_TYPE1_LIST;
			String[] orderTypeValues = LvecDicts.ORD_TYPE2_LIST;
			int typeCount = orderTypes.length;
			for(int i = 0; i < typeCount; i++){
				if(orderTypes[i].equals(orderType)){
					orderTypeValue = orderTypeValues[i];
					break;
				}
			}
		}
		return orderTypeValue;
	}


	/**
	 * 功能：设置低值易耗盘点工单头表属性 确认方式：0：PDA确认；1：WEB确认
	 * @param checkTools String
	 */
	public void setCheckTools(String checkTools){
		this.checkTools = checkTools;
	}

	public void setCheckToolsOpt(String checkToolsOpt) {
		this.checkToolsOpt = checkToolsOpt;
	}

	public void setCheckToolsValue(String checkToolsValue) {
		this.checkToolsValue = checkToolsValue;
	}

	/**
	 * 功能：获取低值易耗盘点工单头表属性 确认方式：0：PDA确认；1：WEB确认
	 * @return String
	 */
	public String getCheckTools() {
		return this.checkTools;
	}

	public String getCheckToolsOpt() {
		return checkToolsOpt;
	}

	public String getCheckToolsValue() {
		return checkToolsValue;
	}
}
