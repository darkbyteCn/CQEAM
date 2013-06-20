package com.sino.ams.newasset.dto;

import java.util.ArrayList;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.exception.CalendarException;

/**
 * <p>Title: 资产盘点头表(EAM) AmsAssetsCheckHeader</p>
 * <p>Description: 程序自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class AmsAssetsCheckHeaderDTO extends AmsAssetsCheckBatchDTO {

	private String headerId = "";
	private String transNo = "";
	private SimpleCalendar downloadDate = null;
	private int downloadBy;
	private String downloadUser = "";
	private SimpleCalendar scanoverDate = null;
	private int scanoverBy;
	private String scanoverUser = "";
	private SimpleCalendar uploadDate = null;
	private int uploadBy;
	private String uploadUser = "";
	private SimpleCalendar archivedDate = null;
	private int archivedBy = -1;
	private String archivedUser = "";
	private String differenceReason = "";
	private String orderStatus = "";
	private String statusName = "";
	private String responsibleUser = "";
	private int implementBy = -1;
	private String implementUser = "";
	private int implementDays = 0;
	private SimpleCalendar startTime = null;

	private String objectCode = "";
	private String objectName = "";
	private String objectLocation = "";
	private String  checkLocation = "";
	private SimpleCalendar deadlineDate = null;
	private String objectCategory = "";
	private String objectCategoryName = "";
	private String objectCategoryOpt = "";
	private String companyOpt = "";
	private String exportType = "";
	private String maintainCompany = "";
	private String maintainComapnyOpt = "";
	private String costCode = "";
	private String costName = "";
	private String costCenterOpt = "";
	private String deptOpt = "";
	private String groupOpt = "";
	private boolean hasPreviousOrder = false;
	private String tagNumber = "";
	private String assetsDescription = "";
	private String deptCode = "";
	private String responsibilityUser = "";
	private String averageChkTime = "";
	private String disabled = "";//是否禁止成本中心的选择
    private String fromBarcode = "";
    private String toBarcode = "";
    private String threshold = "0"; //阀值
    private String deptCategoryValues = "";
    private ArrayList deptCategoryCodes = new ArrayList(0);

    private String newLocation = "";
    private String checkTpye = "";
    
    private String qryBarcode = "";//正在盘点工单查询所需查询条件
    
    public String getQryBarcode() {
        return qryBarcode;
    }

    public void setQryBarcode(String qryBarcode) {
        this.qryBarcode = qryBarcode;
    }

    public String getCheckTpye() {
        return checkTpye;
    }

    public void setCheckTpye(String checkTpye) {
        this.checkTpye = checkTpye;
    }


    public String getDeptCategoryValues() {
        return deptCategoryValues;
    }

    public void setDeptCategoryValues(String deptCategoryValues) {
        this.deptCategoryValues = deptCategoryValues;
    }

    public ArrayList getDeptCategoryCodes() {
        return deptCategoryCodes;
    }

    public void setDeptCategoryCodes(ArrayList deptCategoryCodes) {
        this.deptCategoryCodes = deptCategoryCodes;
    }

    public String getFromBarcode() {
        return fromBarcode;
    }

    public void setFromBarcode(String fromBarcode) {
        this.fromBarcode = fromBarcode;
    }

    public String getToBarcode() {
        return toBarcode;
    }

    public void setToBarcode(String toBarcode) {
        this.toBarcode = toBarcode;
    }

    public AmsAssetsCheckHeaderDTO() {
		super();
		this.downloadDate = new SimpleCalendar();
		this.scanoverDate = new SimpleCalendar();
		this.uploadDate = new SimpleCalendar();
		this.archivedDate = new SimpleCalendar();
		this.startTime = new SimpleCalendar();
		this.deadlineDate = new SimpleCalendar();
	}

	/**
	 * 功能：设置资产盘点头表(EAM)属性 盘点单序列号
	 * @param headerId String
	 */
	public void setHeaderId(String headerId) {
		this.headerId = headerId;
	}


	/**
	 * 功能：设置资产盘点头表(EAM)属性 单据号
	 * @param transNo String
	 */
	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}

	/**
	 * 功能：设置资产盘点头表(EAM)属性 下载日期
	 * @param downloadDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setDownloadDate(String downloadDate) throws CalendarException {
		this.downloadDate.setCalendarValue(downloadDate);
	}

	/**
	 * 功能：设置资产盘点头表(EAM)属性 截止日期
	 * @param deadlineDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setDeadlineDate(String deadlineDate) throws CalendarException {
		this.deadlineDate.setCalendarValue(deadlineDate);
	}


	/**
	 * 功能：设置资产盘点头表(EAM)属性 下载人
	 * @param downloadBy String
	 */
	public void setDownloadBy(int downloadBy) {
		this.downloadBy = downloadBy;
	}

	/**
	 * 功能：设置资产盘点头表(EAM)属性 扫描完成日期
	 * @param scanoverDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setScanoverDate(String scanoverDate) throws CalendarException {
		this.scanoverDate.setCalendarValue(scanoverDate);
	}

	/**
	 * 功能：设置资产盘点头表(EAM)属性 扫描人
	 * @param scanoverBy String
	 */
	public void setScanoverBy(int scanoverBy) {
		this.scanoverBy = scanoverBy;
	}

	/**
	 * 功能：设置资产盘点头表(EAM)属性 上传日期/实际完成日期
	 * @param uploadDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setUploadDate(String uploadDate) throws CalendarException {
		this.uploadDate.setCalendarValue(uploadDate);
	}

	/**
	 * 功能：设置资产盘点头表(EAM)属性 上传人
	 * @param uploadBy String
	 */
	public void setUploadBy(int uploadBy) {
		this.uploadBy = uploadBy;
	}

	/**
	 * 功能：设置资产盘点头表(EAM)属性 归档日期
	 * @param archivedDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setArchivedDate(String archivedDate) throws CalendarException {
		this.archivedDate.setCalendarValue(archivedDate);
	}

	/**
	 * 功能：设置资产盘点头表(EAM)属性 归档人
	 * @param archivedBy String
	 */
	public void setArchivedBy(int archivedBy) {
		this.archivedBy = archivedBy;
	}

	/**
	 * 功能：设置资产盘点头表(EAM)属性 差异原因
	 * @param differenceReason String
	 */
	public void setDifferenceReason(String differenceReason) {
		this.differenceReason = differenceReason;
	}

	/**
	 * 功能：设置资产盘点头表(EAM)属性 盘点单状态(10:新增；11:已下发；12:已下载；13:已完成；14:已核实；15:已撤销)
	 * @param orderStatus String
	 */
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	/**
	 * 功能：设置资产盘点头表(EAM)属性 责任人
	 * @param responsibleUser String
	 */
	public void setResponsibleUser(String responsibleUser) {
		this.responsibleUser = responsibleUser;
	}

	/**
	 * 功能：设置资产地点表(EAM)属性 地点代码
	 *
	 * @param objectCode String
	 */
	public void setObjectCode(String objectCode) {
		this.objectCode = objectCode;
	}

	/**
	 * 功能：设置资产地点表(EAM)属性 地点名称
	 *
	 * @param objectName String
	 */
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	/**
	 * 功能：设置资产地点表(EAM)属性 所在地点
	 *
	 * @param objectLocation String
	 */
	public void setObjectLocation(String objectLocation) {
		this.objectLocation = objectLocation;
	}

	public void setImplementBy(int implementBy) {
		this.implementBy = implementBy;
	}

	public void setImplementUser(String implementUser) {
		this.implementUser = implementUser;
	}

	public void setImplementDays(int implementDays) {
		this.implementDays = implementDays;
	}

	public void setCheckLocation(String  checkLocation) {
		this.checkLocation = checkLocation;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public void setArchivedUser(String archivedUser) {
		this.archivedUser = archivedUser;
	}

	public void setDownloadUser(String downloadUser) {
		this.downloadUser = downloadUser;
	}

	public void setScanoverUser(String scanoverUser) {
		this.scanoverUser = scanoverUser;
	}

	public void setUploadUser(String uploadUser) {
		this.uploadUser = uploadUser;
	}

	public void setObjectCategory(String objectCategory) {
		this.objectCategory = objectCategory;
	}

	public void setObjectCategoryOpt(String objectCategoryOpt) {
		this.objectCategoryOpt = objectCategoryOpt;
	}

	public void setCompanyOpt(String companyOpt) {
		this.companyOpt = companyOpt;
	}

	public void setStartTime(String startTime) throws CalendarException {
		this.startTime.setCalendarValue(startTime);
	}

	/**
	 * 功能：获取资产盘点头表(EAM)属性 盘点单序列号
	 * @return String
	 */
	public String getHeaderId() {
		return this.headerId;
	}

	/**
	 * 功能：获取资产盘点头表(EAM)属性 单据号
	 * @return String
	 */
	public String getTransNo() {
		return this.transNo;
	}

	/**
	 * 功能：获取资产盘点头表(EAM)属性 下载日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getDownloadDate() throws CalendarException {
		downloadDate.setCalPattern(getCalPattern());
		return this.downloadDate;
	}


	/**
	 * 功能：获取资产盘点头表(EAM)属性 截止日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getDeadlineDate() throws CalendarException {
		deadlineDate.setCalPattern(getCalPattern());
		return this.deadlineDate;
	}

	/**
	 * 功能：获取资产盘点头表(EAM)属性 下载人
	 * @return String
	 */
	public int getDownloadBy() {
		return this.downloadBy;
	}

	/**
	 * 功能：获取资产盘点头表(EAM)属性 扫描完成日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getScanoverDate() throws CalendarException {
		scanoverDate.setCalPattern(getCalPattern());
		return this.scanoverDate;
	}

	/**
	 * 功能：获取资产盘点头表(EAM)属性 扫描人
	 * @return String
	 */
	public int getScanoverBy() {
		return this.scanoverBy;
	}

	/**
	 * 功能：获取资产盘点头表(EAM)属性 上传日期/实际完成日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getUploadDate() throws CalendarException {
		uploadDate.setCalPattern(getCalPattern());
		return this.uploadDate;
	}

	/**
	 * 功能：获取资产盘点头表(EAM)属性 上传人
	 * @return String
	 */
	public int getUploadBy() {
		return this.uploadBy;
	}

	/**
	 * 功能：获取资产盘点头表(EAM)属性 归档日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getArchivedDate() throws CalendarException {
		archivedDate.setCalPattern(getCalPattern());
		return this.archivedDate;
	}

	/**
	 * 功能：获取资产盘点头表(EAM)属性 归档人
	 * @return String
	 */
	public int getArchivedBy() {
		return this.archivedBy;
	}

	/**
	 * 功能：获取资产盘点头表(EAM)属性 差异原因
	 * @return String
	 */
	public String getDifferenceReason() {
		return this.differenceReason;
	}

	/**
	 * 功能：获取资产盘点头表(EAM)属性 盘点单状态(10:新增；11:已下发；12:已下载；13:已完成；14:已核实；15:已撤销)
	 * @return String
	 */
	public String getOrderStatus() {
		return this.orderStatus;
	}

	/**
	 * 功能：获取资产盘点头表(EAM)属性 责任人
	 * @return String
	 */
	public String getResponsibleUser() {
		return this.responsibleUser;
	}

	/**
	 * 功能：获取资产地点表(EAM)属性 地点代码
	 *
	 * @return String
	 */
	public String getObjectCode() {
		return this.objectCode;
	}

	/**
	 * 功能：获取资产地点表(EAM)属性 地点名称
	 *
	 * @return String
	 */
	public String getObjectName() {
		return this.objectName;
	}

	/**
	 * 功能：获取资产地点表(EAM)属性 所在地点
	 *
	 * @return String
	 */
	public String getObjectLocation() {
		return this.objectLocation;
	}

	public int getImplementBy() {
		return this.implementBy;
	}

	public String getImplementUser() {
		return this.implementUser;
	}

	public int getImplementDays() {
		return this.implementDays;
	}

	public SimpleCalendar getStartTime() throws CalendarException {
		this.startTime.setCalPattern(getCalPattern());
		return this.startTime;
	}

	public String  getCheckLocation() {
		return this.checkLocation;
	}

	public String getStatusName() {
		return this.statusName;
	}

	public String getArchivedUser() {
		return this.archivedUser;
	}


	public String getDownloadUser() {
		return this.downloadUser;
	}

	public String getScanoverUser() {
		return this.scanoverUser;
	}

	public String getUploadUser() {
		return this.uploadUser;
	}

	public String getObjectCategory() {
		return this.objectCategory;
	}

	public String getObjectCategoryOpt() {
		return this.objectCategoryOpt;
	}

	public String getCompanyOpt() {
		return this.companyOpt;
	}

	public String getObjectCategoryName() {
		return this.objectCategoryName;
	}

	public String getExportType() {
		return this.exportType;
	}

	public String getMaintainCompany() {
		return this.maintainCompany;
	}

	public String getMaintainComapnyOpt() {
		return this.maintainComapnyOpt;
	}

	public String getGroupOpt() {
		return groupOpt;
	}

	public String getCostCode() {
		return costCode;
	}

	public String getCostCenterOpt() {
		return costCenterOpt;
	}

	public String getDeptOpt() {
		return deptOpt;
	}

	public String getCostName() {
		return costName;
	}

	public String getTagNumber() {
		return this.tagNumber;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public String getAssetsDescription() {
		return assetsDescription;
	}

	public String getResponsibilityUser() {
		return responsibilityUser;
	}

	public String getAverageChkTime() {
		return averageChkTime;
	}

	public String getDisabled() {
		return disabled;
	}

	public boolean hasPreviousOrder() {
		return hasPreviousOrder;
	}

	public void setObjectCategoryName(String objectCategoryName) {
		this.objectCategoryName = objectCategoryName;
	}

	public void setExportType(String exportType) {
		this.exportType = exportType;
	}

	public void setMaintainCompany(String maintainCompany) {
		this.maintainCompany = maintainCompany;
	}

	public void setMaintainComapnyOpt(String maintainComapnyOpt) {
		this.maintainComapnyOpt = maintainComapnyOpt;
	}

	public void setGroupOpt(String groupOpt) {
		this.groupOpt = groupOpt;
	}

	public void setCostCode(String costCode) {
		this.costCode = costCode;
	}

	public void setCostCenterOpt(String costCenterOpt) {

		this.costCenterOpt = costCenterOpt;
	}

	public void setDeptOpt(String deptOpt) {
		this.deptOpt = deptOpt;
	}

	public void setCostName(String costName) {
		this.costName = costName;
	}

	public void setHasPreviousOrder(boolean hasPreviousOrder) {
		this.hasPreviousOrder = hasPreviousOrder;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public void setAssetsDescription(String assetsDescription) {
		this.assetsDescription = assetsDescription;
	}

	public void setTagNumber(String tagNumber) {
		this.tagNumber = tagNumber;
	}

	public void setResponsibilityUser(String responsibilityUser) {
		this.responsibilityUser = responsibilityUser;
	}

	public void setAverageChkTime(String averageChkTime) {
		this.averageChkTime = averageChkTime;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

    public String getThreshold() {
        return threshold;
    }

    public void setThreshold(String threshold) {
        this.threshold = threshold;
    }

    public String getNewLocation() {
        return newLocation;
    }

    public void setNewLocation(String newLocation) {
        this.newLocation = newLocation;
    }
}
