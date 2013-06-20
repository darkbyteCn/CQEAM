package com.sino.ams.sampling.dto;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.exception.CalendarException;

/**
* <p>Title: 抽查工单表 AmsAssetsSamplingHeader</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class AmsAssetsSamplingHeaderDTO extends AmsAssetsSamplingBatchDTO{

	private String headerId = "";
	private String samplingLocation = "";
	private String orderNo = "";
	private int implementBy;
	private SimpleCalendar startTime = null;
	private int implementDays;
	private SimpleCalendar distributeDate = null;
	private int distributeBy;
	private String distributeUser = "";
	private SimpleCalendar downloadDate = null;
	private int downloadBy;
	private String downloadUser = "";
	private SimpleCalendar uploadDate = null;
	private int uploadBy;
	private String uploadUser = "";
	private String orderStatus = "";
	private String orderType = "";
	private String orderTypeName = "";
	private String samplingLocationName = "";
	private String implementUser = "";
	private SimpleCalendar taskCreationDate = null;
	private String taskCreatedUser = "";
	private String orderStatusValue = "";
	private String samplingLocationCode = "";
	private String orderStatusOpt = "";
	private SimpleCalendar deadlineDate = null;
	private SimpleCalendar scanoverDate = null;
	private int scanoverBy;
    private String company = "";

    public AmsAssetsSamplingHeaderDTO() {
		super();
		this.startTime = new SimpleCalendar();
		this.distributeDate = new SimpleCalendar();
		this.downloadDate = new SimpleCalendar();
		this.uploadDate = new SimpleCalendar();
		this.taskCreationDate = new SimpleCalendar();
		this.deadlineDate = new SimpleCalendar();
		this.scanoverDate = new SimpleCalendar();
	}

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    /**
	 * 功能：设置抽查工单表属性 工单ID
	 * @param headerId String
	 */
	public void setHeaderId(String headerId){
		this.headerId = headerId;
	}

	/**
	 * 功能：设置抽查工单表属性 抽查地点
	 * @param samplingLocation String
	 */
	public void setSamplingLocation(String samplingLocation){
		this.samplingLocation = samplingLocation;
	}

	/**
	 * 功能：设置抽查工单表属性 工单号
	 * @param orderNo String
	 */
	public void setOrderNo(String orderNo){
		this.orderNo = orderNo;
	}

	/**
	 * 功能：设置抽查工单表属性 执行人
	 * @param implementBy String
	 */
	public void setImplementBy(int implementBy){
		this.implementBy = implementBy;
	}

	/**
	 * 功能：设置抽查工单表属性 开始日期
	 * @param startTime SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setStartTime(String startTime) throws CalendarException{
		this.startTime.setCalendarValue(startTime);
	}

	/**
	 * 功能：设置抽查工单表属性 执行周期
	 * @param implementDays String
	 */
	public void setImplementDays(int implementDays){
		this.implementDays = implementDays;
	}

	/**
	 * 功能：设置抽查工单表属性 下发日期
	 * @param distributeDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setDistributeDate(String distributeDate) throws CalendarException{
		this.distributeDate.setCalendarValue(distributeDate);
	}

	/**
	 * 功能：设置抽查工单表属性 下发人
	 * @param distributeBy String
	 */
	public void setDistributeBy(int distributeBy){
		this.distributeBy = distributeBy;
	}

	/**
	 * 功能：设置抽查工单表属性 下载日期
	 * @param downloadDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setDownloadDate(String downloadDate) throws CalendarException{
		this.downloadDate.setCalendarValue(downloadDate);
	}

	/**
	 * 功能：设置抽查工单表属性 下载人
	 * @param downloadBy String
	 */
	public void setDownloadBy(int downloadBy){
		this.downloadBy = downloadBy;
	}

	/**
	 * 功能：设置抽查工单表属性 上载日期
	 * @param uploadDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setUploadDate(String uploadDate) throws CalendarException{
		this.uploadDate.setCalendarValue(uploadDate);
	}


	/**
	 * 功能:设置任务创建日期
	 * @param taskCreationDate String
	 * @throws CalendarException
	 */
	public void setTaskCreationDate(String taskCreationDate) throws CalendarException {
		this.taskCreationDate.setCalendarValue(taskCreationDate);
	}

	/**
	 * 功能：设置抽查工单表属性 上载人
	 * @param uploadBy String
	 */
	public void setUploadBy(int uploadBy){
		this.uploadBy = uploadBy;
	}

	/**
	 * 功能：设置抽查工单表属性 工单状态
	 * @param orderStatus String
	 */
	public void setOrderStatus(String orderStatus){
		this.orderStatus = orderStatus;
	}


	/**
	 * 功能：设置资产抽查任务表属性 工单类型，默认为资产抽查
	 * @param orderType String
	 */
	public void setOrderType(String orderType){
		this.orderType = orderType;
	}

	public void setSamplingLocationName(String samplingLocationName) {
		this.samplingLocationName = samplingLocationName;
	}

	public void setImplementUser(String implementUser) {
		this.implementUser = implementUser;
	}


	public void setTaskCreatedUser(String taskCreatedUser) {
		this.taskCreatedUser = taskCreatedUser;
	}

	public void setOrderStatusValue(String orderStatusValue) {
		this.orderStatusValue = orderStatusValue;
	}

	public void setSamplingLocationCode(String samplingLocationCode) {
		this.samplingLocationCode = samplingLocationCode;
	}

	public void setDistributeUser(String distributeUser) {
		this.distributeUser = distributeUser;
	}

	public void setDownloadUser(String downloadUser) {
		this.downloadUser = downloadUser;
	}

	public void setUploadUser(String uploadUser) {
		this.uploadUser = uploadUser;
	}

	public void setOrderStatusOpt(String orderStatusOpt) {
		this.orderStatusOpt = orderStatusOpt;
	}

	public void setOrderTypeName(String orderTypeName) {
		this.orderTypeName = orderTypeName;
	}

	public void setScanoverBy(int scanoverBy) {
		this.scanoverBy = scanoverBy;
	}

	public void setScanoverDate(String scanoverDate) throws CalendarException {
		this.scanoverDate.setCalendarValue(scanoverDate);
	}

	public void setDeadlineDate(String deadlineDate) throws CalendarException {
		this.deadlineDate.setCalendarValue(deadlineDate);
	}

	/**
	 * 功能：获取抽查工单表属性 工单ID
	 * @return String
	 */
	public String getHeaderId() {
		return this.headerId;
	}

	/**
	 * 功能：获取抽查工单表属性 抽查地点
	 * @return String
	 */
	public String getSamplingLocation() {
		return this.samplingLocation;
	}

	/**
	 * 功能：获取抽查工单表属性 工单号
	 * @return String
	 */
	public String getOrderNo() {
		return this.orderNo;
	}

	/**
	 * 功能：获取抽查工单表属性 执行人
	 * @return String
	 */
	public int getImplementBy() {
		return this.implementBy;
	}

	/**
	 * 功能：获取抽查工单表属性 开始日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getStartTime() throws CalendarException {
		startTime.setCalPattern(getCalPattern());
		return this.startTime;
	}

	/**
	 * 功能：获取抽查工单表属性 执行周期
	 * @return String
	 */
	public int getImplementDays() {
		return this.implementDays;
	}

	/**
	 * 功能：获取抽查工单表属性 下发日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getDistributeDate() throws CalendarException {
		distributeDate.setCalPattern(getCalPattern());
		return this.distributeDate;
	}

	/**
	 * 功能：获取抽查工单表属性 下发人
	 * @return String
	 */
	public int getDistributeBy() {
		return this.distributeBy;
	}

	/**
	 * 功能：获取抽查工单表属性 下载日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getDownloadDate() throws CalendarException {
		downloadDate.setCalPattern(getCalPattern());
		return this.downloadDate;
	}

	/**
	 * 功能：获取抽查工单表属性 下载人
	 * @return String
	 */
	public int getDownloadBy() {
		return this.downloadBy;
	}

	/**
	 * 功能：获取抽查工单表属性 上载日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getUploadDate() throws CalendarException {
		uploadDate.setCalPattern(getCalPattern());
		return this.uploadDate;
	}


	/**
	 * 功能：获取抽查工单表属性 任务创建日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getTaskCreationDate() throws CalendarException {
		taskCreationDate.setCalPattern(getCalPattern());
		return this.taskCreationDate;
	}

	/**
	 * 功能：获取抽查工单表属性 上载人
	 * @return String
	 */
	public int getUploadBy() {
		return this.uploadBy;
	}

	/**
	 * 功能：获取抽查工单表属性 工单状态
	 * @return String
	 */
	public String getOrderStatus() {
		return this.orderStatus;
	}

	/**
	 * 功能：获取资产抽查任务表属性 工单类型，默认为资产抽查
	 * @return String
	 */
	public String getOrderType() {
		return this.orderType;
	}

	public String getSamplingLocationName() {
		return samplingLocationName;
	}

	public String getImplementUser() {
		return implementUser;
	}

	public String getTaskCreatedUser() {
		return taskCreatedUser;
	}

	public String getOrderStatusValue() {
		return orderStatusValue;
	}

	public String getSamplingLocationCode() {
		return samplingLocationCode;
	}

	public String getDistributeUser() {
		return distributeUser;
	}

	public String getDownloadUser() {
		return downloadUser;
	}

	public String getUploadUser() {
		return uploadUser;
	}

	public String getOrderStatusOpt() {
		return orderStatusOpt;
	}

	public String getOrderTypeName() {
		return orderTypeName;
	}

	public SimpleCalendar getDeadlineDate() throws CalendarException {
		deadlineDate.setCalPattern(getCalPattern());
		return deadlineDate;
	}

	public SimpleCalendar getScanoverDate() throws CalendarException {
		scanoverDate.setCalPattern(getCalPattern());
		return scanoverDate;
	}

	public int getScanoverBy() {
		return scanoverBy;
	}
}
