package com.sino.ams.dzyh.dto;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.exception.CalendarException;

/**
* <p>Title: 低值易耗盘点工单头表 EamDhCheckHeader</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class EamDhCheckHeaderDTO extends EamDhCheckBatchDTO{

	private String headerId = "";
	private int checkLocation = SyBaseSQLUtil.NULL_INT_VALUE;
	private String orderNo = "";
	private int implementBy = SyBaseSQLUtil.NULL_INT_VALUE;
	private String implementUser = "";
	private SimpleCalendar startTime = null;
	private String implementDays = "";
	private SimpleCalendar downloadDate = null;
	private int downloadBy = SyBaseSQLUtil.NULL_INT_VALUE;
	private String downloadUser = "";
	private SimpleCalendar scanoverDate = null;
	private int scanoverBy = SyBaseSQLUtil.NULL_INT_VALUE ;
	private String scanoverUser = "";
	private SimpleCalendar uploadDate = null;
	private int uploadBy = SyBaseSQLUtil.NULL_INT_VALUE ;
	private String uploadUser = "";
	private SimpleCalendar archivedDate = null;
	private int archivedBy = SyBaseSQLUtil.NULL_INT_VALUE;
	private String archivedUser = "";
	private String differenceReason = "";
	private String responsibleUser = "";
	private String orderStatus = "";
	private String orderStatusValue = "";
	private String orderStatusOpt = "";
	private String impDeptCode = "";
	private String impDeptName = "";
	private String impDeptOpt = "";
	private String locationCode = "";
	private String locationName = "";
	private SimpleCalendar deadlineDate = null;
	private String orderTypeOpt = "";

	public EamDhCheckHeaderDTO() {
		super();
		this.startTime = new SimpleCalendar();
		this.downloadDate = new SimpleCalendar();
		this.scanoverDate = new SimpleCalendar();
		this.uploadDate = new SimpleCalendar();
		this.archivedDate = new SimpleCalendar();
		this.deadlineDate = new SimpleCalendar();
	}

	/**
	 * 功能：设置低值易耗盘点工单头表属性 盘点单序列号
	 * @param headerId String
	 */
	public void setHeaderId(String headerId){
		this.headerId = headerId;
	}

	/**
	 * 功能：设置低值易耗盘点工单头表属性 盘点地点
	 * @param checkLocation String
	 */
	public void setCheckLocation(int checkLocation){
		this.checkLocation = checkLocation;
	}

	/**
	 * 功能：设置低值易耗盘点工单头表属性 单据号
	 * @param orderNo String
	 */
	public void setOrderNo(String orderNo){
		this.orderNo = orderNo;
	}

	/**
	 * 功能：设置低值易耗盘点工单头表属性 执行人
	 * @param implementBy String
	 */
	public void setImplementBy(int implementBy){
		this.implementBy = implementBy;
	}

	/**
	 * 功能：设置低值易耗盘点工单头表属性 开始时间
	 * @param startTime SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setStartTime(String startTime) throws CalendarException{
		this.startTime.setCalendarValue(startTime);
	}

	/**
	 * 功能：设置低值易耗盘点工单头表属性 执行周期
	 * @param implementDays String
	 */
	public void setImplementDays(String implementDays){
		this.implementDays = implementDays;
	}


	/**
	 * 功能：设置低值易耗盘点工单头表属性 下载日期
	 * @param downloadDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setDownloadDate(String downloadDate) throws CalendarException{
		this.downloadDate.setCalendarValue(downloadDate);
	}

	/**
	 * 功能：设置低值易耗盘点工单头表属性 下载人
	 * @param downloadBy String
	 */
	public void setDownloadBy(int downloadBy){
		this.downloadBy = downloadBy;
	}

	/**
	 * 功能：设置低值易耗盘点工单头表属性 扫描完成日期
	 * @param scanoverDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setScanoverDate(String scanoverDate) throws CalendarException{
		this.scanoverDate.setCalendarValue(scanoverDate);
	}

	/**
	 * 功能：设置低值易耗盘点工单头表属性 扫描人
	 * @param scanoverBy String
	 */
	public void setScanoverBy(int scanoverBy){
		this.scanoverBy = scanoverBy;
	}

	/**
	 * 功能：设置低值易耗盘点工单头表属性 上传日期/实际完成日期
	 * @param uploadDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setUploadDate(String uploadDate) throws CalendarException{
		this.uploadDate.setCalendarValue(uploadDate);
	}

	/**
	 * 功能：设置低值易耗盘点工单头表属性 上传人
	 * @param uploadBy String
	 */
	public void setUploadBy(int uploadBy){
		this.uploadBy = uploadBy;
	}

	/**
	 * 功能：设置低值易耗盘点工单头表属性 归档日期
	 * @param archivedDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setArchivedDate(String archivedDate) throws CalendarException{
		this.archivedDate.setCalendarValue(archivedDate);
	}

	/**
	 * 功能：设置低值易耗盘点工单头表属性 归档人
	 * @param archivedBy String
	 */
	public void setArchivedBy(int archivedBy){
		this.archivedBy = archivedBy;
	}

	/**
	 * 功能：设置低值易耗盘点工单头表属性 差异原因
	 * @param differenceReason String
	 */
	public void setDifferenceReason(String differenceReason){
		this.differenceReason = differenceReason;
	}

	/**
	 * 功能：设置低值易耗盘点工单头表属性 责任人
	 * @param responsibleUser String
	 */
	public void setResponsibleUser(String responsibleUser){
		this.responsibleUser = responsibleUser;
	}

	/**
	 * 功能：设置低值易耗盘点工单头表属性 盘点单状态
	 * @param orderStatus String
	 */
	public void setOrderStatus(String orderStatus){
		this.orderStatus = orderStatus;
	}

	public void setImpDeptCode(String impDeptCode) {
		this.impDeptCode = impDeptCode;
	}

	public void setImpDeptName(String impDeptName) {

		this.impDeptName = impDeptName;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public void setImplementUser(String implementUser) {
		this.implementUser = implementUser;
	}

	public void setArchivedUser(String archivedUser) {
		this.archivedUser = archivedUser;
	}

	public void setDownloadUser(String downloadUser) {
		this.downloadUser = downloadUser;
	}

	public void setOrderStatusValue(String orderStatusValue) {
		this.orderStatusValue = orderStatusValue;
	}

	public void setUploadUser(String uploadUser) {
		this.uploadUser = uploadUser;
	}

	public void setOrderStatusOpt(String orderStatusOpt) {
		this.orderStatusOpt = orderStatusOpt;
	}

	public void setScanoverUser(String scanoverUser) {
		this.scanoverUser = scanoverUser;
	}

	public void setImpDeptOpt(String impDeptOpt) {
		this.impDeptOpt = impDeptOpt;
	}

	public void setOrderTypeOpt(String orderTypeOpt) {
		this.orderTypeOpt = orderTypeOpt;
	}

	public void setDeadlineDate(String deadlineDate) throws CalendarException {
		this.deadlineDate.setCalendarValue(deadlineDate);
	}

	/**
	 * 功能：获取低值易耗盘点工单头表属性 盘点单序列号
	 * @return String
	 */
	public String getHeaderId() {
		return this.headerId;
	}

	/**
	 * 功能：获取低值易耗盘点工单头表属性 盘点地点
	 * @return String
	 */
	public int getCheckLocation() {
		return this.checkLocation;
	}

	/**
	 * 功能：获取低值易耗盘点工单头表属性 单据号
	 * @return String
	 */
	public String getOrderNo() {
		return this.orderNo;
	}

	/**
	 * 功能：获取低值易耗盘点工单头表属性 执行人
	 * @return String
	 */
	public int getImplementBy() {
		return this.implementBy;
	}

	/**
	 * 功能：获取低值易耗盘点工单头表属性 开始时间
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getStartTime() throws CalendarException {
		startTime.setCalPattern(getCalPattern());
		return this.startTime;
	}

	/**
	 * 功能：获取低值易耗盘点工单头表属性 执行周期
	 * @return String
	 */
	public String getImplementDays() {
		return this.implementDays;
	}

	/**
	 * 功能：获取低值易耗盘点工单头表属性 下载日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getDownloadDate() throws CalendarException {
		downloadDate.setCalPattern(getCalPattern());
		return this.downloadDate;
	}

	/**
	 * 功能：获取低值易耗盘点工单头表属性 下载人
	 * @return String
	 */
	public int getDownloadBy() {
		return this.downloadBy;
	}

	/**
	 * 功能：获取低值易耗盘点工单头表属性 扫描完成日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getScanoverDate() throws CalendarException {
		scanoverDate.setCalPattern(getCalPattern());
		return this.scanoverDate;
	}

	/**
	 * 功能：获取低值易耗盘点工单头表属性 扫描人
	 * @return String
	 */
	public int getScanoverBy() {
		return this.scanoverBy;
	}

	/**
	 * 功能：获取低值易耗盘点工单头表属性 上传日期/实际完成日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getUploadDate() throws CalendarException {
		uploadDate.setCalPattern(getCalPattern());
		return this.uploadDate;
	}

	/**
	 * 功能：获取低值易耗盘点工单头表属性 上传人
	 * @return String
	 */
	public int getUploadBy() {
		return this.uploadBy;
	}

	/**
	 * 功能：获取低值易耗盘点工单头表属性 归档日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getArchivedDate() throws CalendarException {
		archivedDate.setCalPattern(getCalPattern());
		return this.archivedDate;
	}

	/**
	 * 功能：获取低值易耗盘点工单头表属性 归档人
	 * @return String
	 */
	public int getArchivedBy() {
		return this.archivedBy;
	}

	/**
	 * 功能：获取低值易耗盘点工单头表属性 差异原因
	 * @return String
	 */
	public String getDifferenceReason() {
		return this.differenceReason;
	}

	/**
	 * 功能：获取低值易耗盘点工单头表属性 责任人
	 * @return String
	 */
	public String getResponsibleUser() {
		return this.responsibleUser;
	}

	/**
	 * 功能：获取低值易耗盘点工单头表属性 盘点单状态
	 * @return String
	 */
	public String getOrderStatus() {
		return this.orderStatus;
	}


	public String getImpDeptName() {
		return impDeptName;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public String getLocationName() {
		return locationName;
	}

	public String getImpDeptCode() {
		return impDeptCode;
	}

	public String getImplementUser() {
		return implementUser;
	}

	public SimpleCalendar getDeadlineDate() throws CalendarException {
		deadlineDate.setCalPattern(getCalPattern());
		return deadlineDate;
	}

	public String getArchivedUser() {
		return archivedUser;
	}

	public String getDownloadUser() {
		return downloadUser;
	}

	public String getOrderStatusValue() {
		return orderStatusValue;
	}

	public String getUploadUser() {
		return uploadUser;
	}

	public String getOrderStatusOpt() {
		return orderStatusOpt;
	}

	public String getScanoverUser() {
		return scanoverUser;
	}

	public String getImpDeptOpt() {
		return impDeptOpt;
	}

	public String getOrderTypeOpt() {
		return orderTypeOpt;
	}
}
