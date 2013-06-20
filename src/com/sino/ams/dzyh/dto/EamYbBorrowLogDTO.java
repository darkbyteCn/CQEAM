package com.sino.ams.dzyh.dto;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.ct.dto.EtsItemInfoDTO;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.exception.CalendarException;

/**
* <p>Title: 仪器仪表借用日志 EamYbBorrowLog</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class EamYbBorrowLogDTO extends EtsItemInfoDTO{

	private String borrowLogId = "";
	private String status = "";
	private String statusValue = "";
	private int borrowUserId = SyBaseSQLUtil.NULL_INT_VALUE;
	private String borrowUser = "";
	private int approveUserId = SyBaseSQLUtil.NULL_INT_VALUE;
	private String approveUser = "";
	private int handleUserId = SyBaseSQLUtil.NULL_INT_VALUE;
	private String handleUser = "";
	private int orgId = SyBaseSQLUtil.NULL_INT_VALUE;
	private String isApplyed = "";
	private String catalogName = "";
	private String createdUser = "";
	private int groupId = SyBaseSQLUtil.NULL_INT_VALUE;
	private String groupName = "";
	private String groupOption = "";
	private String approveContent = "";
	private String statusRadio = "";

	private SimpleCalendar borrowDate = null;
	private SimpleCalendar cancelDate = null;
	private SimpleCalendar returnDatePlan = null;
	private SimpleCalendar returnDateActual = null;
	private SimpleCalendar approveDate = null;
	private SimpleCalendar handleDate = null;

	public EamYbBorrowLogDTO() {
		super();
		this.borrowDate = new SimpleCalendar();
		this.returnDatePlan = new SimpleCalendar();
		this.returnDateActual = new SimpleCalendar();
		this.approveDate = new SimpleCalendar();
		this.handleDate = new SimpleCalendar();
		this.cancelDate = new SimpleCalendar();
	}

	/**
	 * 功能：设置仪器仪表借用日志属性 PK
	 * @param borrowLogId String
	 */
	public void setBorrowLogId(String borrowLogId){
		this.borrowLogId = borrowLogId;
	}

	/**
	 * 功能：设置仪器仪表借用日志属性 状态(新增、已批、借出、关闭、返还) 见字典"YB_BORROW_STATUS"
	 * @param status String
	 */
	public void setStatus(String status){
		this.status = status;
	}

	/**
	 * 功能：设置仪器仪表借用日志属性 借用人
	 * @param borrowUserId String
	 */
	public void setBorrowUserId(int borrowUserId){
		this.borrowUserId = borrowUserId;
	}

	/**
	 * 功能：设置仪器仪表借用日志属性 借用日期
	 * @param borrowDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setBorrowDate(String borrowDate) throws CalendarException{
		this.borrowDate.setCalendarValue(borrowDate);
	}

	/**
	 * 功能：设置仪器仪表借用日志属性 预计归还时间
	 * @param returnDatePlan SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setReturnDatePlan(String returnDatePlan) throws CalendarException{
		this.returnDatePlan.setCalendarValue(returnDatePlan);
	}

	/**
	 * 功能：设置仪器仪表借用日志属性 实际归还日期
	 * @param returnDateActual SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setReturnDateActual(String returnDateActual) throws CalendarException{
		this.returnDateActual.setCalendarValue(returnDateActual);
	}

	/**
	 * 功能：设置仪器仪表借用日志属性 审批人
	 * @param approveUserId String
	 */
	public void setApproveUserId(int approveUserId){
		this.approveUserId = approveUserId;
	}

	/**
	 * 功能：设置仪器仪表借用日志属性 审批日期
	 * @param approveDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setApproveDate(String approveDate) throws CalendarException{
		this.approveDate.setCalendarValue(approveDate);
	}

	/**
	 * 功能：设置仪器仪表借用日志属性 经办人
	 * @param handleUserId String
	 */
	public void setHandleUserId(int handleUserId){
		this.handleUserId = handleUserId;
	}

	/**
	 * 功能：设置仪器仪表借用日志属性 经办日期
	 * @param handleDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setHandleDate(String handleDate) throws CalendarException{
		this.handleDate.setCalendarValue(handleDate);
	}

	/**
	 * 功能：设置仪器仪表借用日志属性 组织ID
	 * @param orgId String
	 */
	public void setOrgId(int orgId){
		this.orgId = orgId;
	}

	public void setApproveUser(String approveUser) {
		this.approveUser = approveUser;
	}

	public void setHandleUser(String handleUser) {
		this.handleUser = handleUser;
	}

	public void setBorrowUser(String borrowUser) {
		this.borrowUser = borrowUser;
	}

	public void setIsApplyed(String isApplyed) {
		this.isApplyed = isApplyed;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}

	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public void setGroupOption(String groupOption) {
		this.groupOption = groupOption;
	}

	public void setStatusRadio(String statusRadio) {
		this.statusRadio = statusRadio;
	}

	public void setApproveContent(String approveContent) {
		this.approveContent = approveContent;
	}

	public void setCancelDate(String cancelDate) throws CalendarException {
		this.cancelDate.setCalendarValue(cancelDate);
	}

	/**
	 * 功能：获取仪器仪表借用日志属性 PK
	 * @return String
	 */
	public String getBorrowLogId() {
		return this.borrowLogId;
	}

	/**
	 * 功能：获取仪器仪表借用日志属性 状态(新增、已批、借出、关闭、返还) 见字典"YB_BORROW_STATUS"
	 * @return String
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * 功能：获取仪器仪表借用日志属性 借用人
	 * @return String
	 */
	public int getBorrowUserId() {
		return this.borrowUserId;
	}

	/**
	 * 功能：获取仪器仪表借用日志属性 借用日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getBorrowDate() throws CalendarException {
		borrowDate.setCalPattern(getCalPattern());
		return this.borrowDate;
	}

	/**
	 * 功能：获取仪器仪表借用日志属性 预计归还时间
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getReturnDatePlan() throws CalendarException {
		returnDatePlan.setCalPattern(getCalPattern());
		getStatus();
		return this.returnDatePlan;
	}

	/**
	 * 功能：获取仪器仪表借用日志属性 实际归还日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getReturnDateActual() throws CalendarException {
		returnDateActual.setCalPattern(getCalPattern());
		return this.returnDateActual;
	}

	/**
	 * 功能：获取仪器仪表借用日志属性 审批人
	 * @return String
	 */
	public int getApproveUserId() {
		return this.approveUserId;
	}

	/**
	 * 功能：获取仪器仪表借用日志属性 审批日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getApproveDate() throws CalendarException {
		approveDate.setCalPattern(getCalPattern());
		return this.approveDate;
	}

	/**
	 * 功能：获取仪器仪表借用日志属性 经办人
	 * @return String
	 */
	public int getHandleUserId() {
		return this.handleUserId;
	}

	/**
	 * 功能：获取仪器仪表借用日志属性 经办日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getHandleDate() throws CalendarException {
		handleDate.setCalPattern(getCalPattern());
		return this.handleDate;
	}

	/**
	 * 功能：获取仪器仪表借用日志属性 组织ID
	 * @return String
	 */
	public int getOrgId() {
		return this.orgId;
	}

	public String getApproveUser() {
		return approveUser;
	}

	public String getHandleUser() {
		return handleUser;
	}

	public String getBorrowUser() {
		return borrowUser;
	}

	public SimpleCalendar getCancelDate() throws CalendarException {
		cancelDate.setCalPattern(getCalPattern());
		return cancelDate;
	}

	public String getIsApplyed() {
		return isApplyed;
	}

	public String getCatalogName() {
		return catalogName;
	}

	public String getStatusValue() {
		return statusValue;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public int getGroupId() {
		return groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public String getGroupOption() {
		return groupOption;
	}

	public String getStatusRadio() {
		return statusRadio;
	}

	public String getApproveContent() {
		return approveContent;
	}
}
