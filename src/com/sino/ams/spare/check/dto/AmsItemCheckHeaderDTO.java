package com.sino.ams.spare.check.dto;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;

/**
* <p>Title: 备件盘点头表(AMS) AmsItemCheckHeader</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class AmsItemCheckHeaderDTO extends CheckBoxDTO {

	private String headerId = "";
	private String checkLocation = "";
	private String implementBy = "";
	private String implementDays = "";
	private SimpleCalendar distributeDate = null;
	private String distributeBy = "";
	private SimpleCalendar downloadDate = null;
	private String downloadBy = "";
	private SimpleCalendar scanoverDate = null;
	private String scanoverBy = "";
	private SimpleCalendar uploadDate = null;
	private String uploadBy = "";
	private SimpleCalendar archivedDate = null;
	private String archivedBy = "";
	private String differenceReason = "";
	private String organizationId = "";
	private String responsibleUser = "";
	private SimpleCalendar creationDate = null;
	private int createdBy = -1;
	private SimpleCalendar lastUpdateDate = null;
	private String lastUpdateBy = "";
	private String orderStatus = "";
	private String orderStatusName = "";
	private SimpleCalendar startDate = null;
    private String transNo = "";
    private String transType = "";
    private String createdUser = "";
    private SimpleCalendar fromDate = null;
    private SimpleCalendar toDate = null;
    private String itemName="";
    private String itemSpec="";
    private String   workorderObjectName="";
    private String addressId="";
    private String implementByName="";
    private String checkType="";
    private  String checkLocationName="";


    public AmsItemCheckHeaderDTO() {
		super();
		this.distributeDate = new SimpleCalendar();
		this.downloadDate = new SimpleCalendar();
		this.scanoverDate = new SimpleCalendar();
		this.uploadDate = new SimpleCalendar();
		this.archivedDate = new SimpleCalendar();
		this.creationDate = new SimpleCalendar();
		this.lastUpdateDate = new SimpleCalendar();
		this.startDate = new SimpleCalendar();
        this.fromDate=new SimpleCalendar();
        this.toDate=new SimpleCalendar();
    }

    public String getCheckLocationName() {
        return checkLocationName;
    }

    public void setCheckLocationName(String checkLocationName) {
        this.checkLocationName = checkLocationName;
    }

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    public String getImplementByName() {
        return implementByName;
    }

    public void setImplementByName(String implementByName) {
        this.implementByName = implementByName;
    }

    public SimpleCalendar getFromDate() throws CalendarException {
        return this.fromDate;
    }

    public void setFromDate(String fromDate) throws CalendarException {
        this.fromDate.setCalendarValue(fromDate);
    }

    public void setToDate(String toDate) throws CalendarException{
		this.toDate.setCalendarValue(toDate);
	}

   public SimpleCalendar getToDate() {
		return this.toDate;
	}

    public String getOrderStatusName() {
        return orderStatusName;
    }

    public void setOrderStatusName(String orderStatusName) {
        this.orderStatusName = orderStatusName;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
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

    public String getWorkorderObjectName() {
        return workorderObjectName;
    }

    public void setWorkorderObjectName(String workorderObjectName) {
        this.workorderObjectName = workorderObjectName;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    /**
	 * 功能：设置备件盘点头表(AMS)属性 盘点单序列号
	 * @param headerId String
	 */
	public void setHeaderId(String headerId){
		this.headerId = headerId;
	}

	/**
	 * 功能：设置备件盘点头表(AMS)属性 盘点地点
	 * @param checkLocation String
	 */
	public void setCheckLocation(String checkLocation){
		this.checkLocation = checkLocation;
	}

	/**
	 * 功能：设置备件盘点头表(AMS)属性 单据号
	 * @param transNo String
	 */
	public void setTransNo(String transNo){
		this.transNo = transNo;
	}

	/**
	 * 功能：设置备件盘点头表(AMS)属性 执行人
	 * @param implementBy String
	 */
	public void setImplementBy(String implementBy){
		this.implementBy = implementBy;
	}

	/**
	 * 功能：设置备件盘点头表(AMS)属性 执行周期
	 * @param implementDays String
	 */
	public void setImplementDays(String implementDays){
		this.implementDays = implementDays;
	}

	/**
	 * 功能：设置备件盘点头表(AMS)属性 下发日期
	 * @param distributeDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setDistributeDate(String distributeDate) throws CalendarException{
		this.distributeDate.setCalendarValue(distributeDate);
	}

	/**
	 * 功能：设置备件盘点头表(AMS)属性 下发人
	 * @param distributeBy String
	 */
	public void setDistributeBy(String distributeBy){
		this.distributeBy = distributeBy;
	}

	/**
	 * 功能：设置备件盘点头表(AMS)属性 下载日期
	 * @param downloadDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setDownloadDate(String downloadDate) throws CalendarException{
		this.downloadDate.setCalendarValue(downloadDate);
	}

	/**
	 * 功能：设置备件盘点头表(AMS)属性 下载人
	 * @param downloadBy String
	 */
	public void setDownloadBy(String downloadBy){
		this.downloadBy = downloadBy;
	}

	/**
	 * 功能：设置备件盘点头表(AMS)属性 扫描完成日期
	 * @param scanoverDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setScanoverDate(String scanoverDate) throws CalendarException{
		this.scanoverDate.setCalendarValue(scanoverDate);
	}

	/**
	 * 功能：设置备件盘点头表(AMS)属性 扫描人
	 * @param scanoverBy String
	 */
	public void setScanoverBy(String scanoverBy){
		this.scanoverBy = scanoverBy;
	}

	/**
	 * 功能：设置备件盘点头表(AMS)属性 上传日期/实际完成日期
	 * @param uploadDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setUploadDate(String uploadDate) throws CalendarException{
		this.uploadDate.setCalendarValue(uploadDate);
	}

	/**
	 * 功能：设置备件盘点头表(AMS)属性 上传人
	 * @param uploadBy String
	 */
	public void setUploadBy(String uploadBy){
		this.uploadBy = uploadBy;
	}

	/**
	 * 功能：设置备件盘点头表(AMS)属性 归档日期
	 * @param archivedDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setArchivedDate(String archivedDate) throws CalendarException{
		this.archivedDate.setCalendarValue(archivedDate);
	}

	/**
	 * 功能：设置备件盘点头表(AMS)属性 归档人
	 * @param archivedBy String
	 */
	public void setArchivedBy(String archivedBy){
		this.archivedBy = archivedBy;
	}

	/**
	 * 功能：设置备件盘点头表(AMS)属性 差异原因
	 * @param differenceReason String
	 */
	public void setDifferenceReason(String differenceReason){
		this.differenceReason = differenceReason;
	}

	/**
	 * 功能：设置备件盘点头表(AMS)属性 组织
	 * @param organizationId String
	 */
	public void setOrganizationId(String organizationId){
		this.organizationId = organizationId;
	}

	/**
	 * 功能：设置备件盘点头表(AMS)属性 责任人
	 * @param responsibleUser String
	 */
	public void setResponsibleUser(String responsibleUser){
		this.responsibleUser = responsibleUser;
	}

	/**
	 * 功能：设置备件盘点头表(AMS)属性 创建日期
	 * @param creationDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setCreationDate(String creationDate) throws CalendarException{
		this.creationDate.setCalendarValue(creationDate);
	}

	/**
	 * 功能：设置备件盘点头表(AMS)属性 创建人
	 * @param createdBy String
	 */
	public void setCreatedBy(int createdBy){
		this.createdBy = createdBy;
	}

	/**
	 * 功能：设置备件盘点头表(AMS)属性 上次修改日期
	 * @param lastUpdateDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setLastUpdateDate(String lastUpdateDate) throws CalendarException{
		this.lastUpdateDate.setCalendarValue(lastUpdateDate);
	}

	/**
	 * 功能：设置备件盘点头表(AMS)属性 上次修改人
	 * @param lastUpdateBy String
	 */
	public void setLastUpdateBy(String lastUpdateBy){
		this.lastUpdateBy = lastUpdateBy;
	}

	/**
	 * 功能：设置备件盘点头表(AMS)属性 盘点单状态(10:新增；11:已下发；12:已下载；13:已完成；14:已核实；15:已取消)
	 * @param orderStatus String
	 */
	public void setOrderStatus(String orderStatus){
		this.orderStatus = orderStatus;
	}

	/**
	 * 功能：设置备件盘点头表(AMS)属性 开始时间
	 * @param startDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setStartDate(String startDate) throws CalendarException{
		this.startDate.setCalendarValue(startDate);
	}


	/**
	 * 功能：获取备件盘点头表(AMS)属性 盘点单序列号
	 * @return String
	 */
	public String getHeaderId() {
		return this.headerId;
	}

	/**
	 * 功能：获取备件盘点头表(AMS)属性 盘点地点
	 * @return String
	 */
	public String getCheckLocation() {
		return this.checkLocation;
	}

	/**
	 * 功能：获取备件盘点头表(AMS)属性 单据号
	 * @return String
	 */
	public String getTransNo() {
		return this.transNo;
	}

	/**
	 * 功能：获取备件盘点头表(AMS)属性 执行人
	 * @return String
	 */
	public String getImplementBy() {
		return this.implementBy;
	}

	/**
	 * 功能：获取备件盘点头表(AMS)属性 执行周期
	 * @return String
	 */
	public String getImplementDays() {
		return this.implementDays;
	}

	/**
	 * 功能：获取备件盘点头表(AMS)属性 下发日期
	 * @return SimpleCalendar
	 */
	public SimpleCalendar getDistributeDate() {
		return this.distributeDate;
	}

	/**
	 * 功能：获取备件盘点头表(AMS)属性 下发人
	 * @return String
	 */
	public String getDistributeBy() {
		return this.distributeBy;
	}

	/**
	 * 功能：获取备件盘点头表(AMS)属性 下载日期
	 * @return SimpleCalendar
	 */
	public SimpleCalendar getDownloadDate() {
		return this.downloadDate;
	}

	/**
	 * 功能：获取备件盘点头表(AMS)属性 下载人
	 * @return String
	 */
	public String getDownloadBy() {
		return this.downloadBy;
	}

	/**
	 * 功能：获取备件盘点头表(AMS)属性 扫描完成日期
	 * @return SimpleCalendar
	 */
	public SimpleCalendar getScanoverDate() {
		return this.scanoverDate;
	}

	/**
	 * 功能：获取备件盘点头表(AMS)属性 扫描人
	 * @return String
	 */
	public String getScanoverBy() {
		return this.scanoverBy;
	}

	/**
	 * 功能：获取备件盘点头表(AMS)属性 上传日期/实际完成日期
	 * @return SimpleCalendar
	 */
	public SimpleCalendar getUploadDate() {
		return this.uploadDate;
	}

	/**
	 * 功能：获取备件盘点头表(AMS)属性 上传人
	 * @return String
	 */
	public String getUploadBy() {
		return this.uploadBy;
	}

	/**
	 * 功能：获取备件盘点头表(AMS)属性 归档日期
	 * @return SimpleCalendar
	 */
	public SimpleCalendar getArchivedDate() {
		return this.archivedDate;
	}

	/**
	 * 功能：获取备件盘点头表(AMS)属性 归档人
	 * @return String
	 */
	public String getArchivedBy() {
		return this.archivedBy;
	}

	/**
	 * 功能：获取备件盘点头表(AMS)属性 差异原因
	 * @return String
	 */
	public String getDifferenceReason() {
		return this.differenceReason;
	}

	/**
	 * 功能：获取备件盘点头表(AMS)属性 组织
	 * @return String
	 */
	public String getOrganizationId() {
		return this.organizationId;
	}

	/**
	 * 功能：获取备件盘点头表(AMS)属性 责任人
	 * @return String
	 */
	public String getResponsibleUser() {
		return this.responsibleUser;
	}

	/**
	 * 功能：获取备件盘点头表(AMS)属性 创建日期
	 * @return SimpleCalendar
	 */
	public SimpleCalendar getCreationDate() {
		return this.creationDate;
	}

	/**
	 * 功能：获取备件盘点头表(AMS)属性 创建人
	 * @return String
	 */
	public int getCreatedBy() {
		return this.createdBy;
	}

	/**
	 * 功能：获取备件盘点头表(AMS)属性 上次修改日期
	 * @return SimpleCalendar
	 */
	public SimpleCalendar getLastUpdateDate() {
		return this.lastUpdateDate;
	}

	/**
	 * 功能：获取备件盘点头表(AMS)属性 上次修改人
	 * @return String
	 */
	public String getLastUpdateBy() {
		return this.lastUpdateBy;
	}

	/**
	 * 功能：获取备件盘点头表(AMS)属性 盘点单状态(10:新增；11:已下发；12:已下载；13:已完成；14:已核实；15:已取消)
	 * @return String
	 */
	public String getOrderStatus() {
		return this.orderStatus;
	}

	/**
	 * 功能：获取备件盘点头表(AMS)属性 开始时间
	 * @return SimpleCalendar
	 */
	public SimpleCalendar getStartDate() {
		return this.startDate;
	}

}