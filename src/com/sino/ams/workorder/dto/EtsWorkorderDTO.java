package com.sino.ams.workorder.dto;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.CommonRecordDTO;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.exception.CalendarException;
import com.sino.base.util.StrUtil;

/**
 * <p>Title: 工单主表(EAM) EtsWorkorder</p>
 * <p>Description: 程序自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class EtsWorkorderDTO extends CommonRecordDTO {

    private String systemid = "";
    private String workorderBatch = "";//工单批号
    private String workorderNo = "";//工单号
    private String workorderType = "";
    private String workorderObjectNo;
    private int implementDays;
    private int groupId = 0;
    private int implementBy=-1;
    private String implementUser = "";
    private String prjId = "" ;
    private String prjName = "";
    private SimpleCalendar distributeDate = null;
    private int distributeBy;
    private String distributeUser = "";
    private SimpleCalendar downloadDate = null;
    private int downloadBy;
    private String downloadUser = "";
    private SimpleCalendar scanoverDate = null;
    private int scanoverBy;
    private String scanoverUser = "";
    private SimpleCalendar uploadDate = null;
    private int uploadBy;
    private SimpleCalendar checkoverDate = null;
    private int checkoverBy;
    private String checkoverUser = "";
    private String responsibilityUser = "";
    private String differenceReason = "";
    private int organizationId;
    private String workorderFlag = "";//工单状态
    private String workorderFlagDesc = "";//工单状态说明
    private String remark = "";
    private String actid;
    private String caseid = "";
    private int archflag;
    private String attribute1 = "";
    private String attribute2 = "";
    private String attribute3 = "";
    private String attribute4 = "";
    private String attribute5 = "";
    private int attribute6;
    private int distributeGroup;
    private String attribute7 = "";
    private String createUser = "";
    private String lastUpdateUser = "";
    private String executeUserName = "";
    private String loginName = "";
    private String workorderTypeDesc = "";
    private String groupName = "";
    private String workorderObjectCode = "";
    private String workorderObjectName = "";
    private String workorderObjectLocation = "";
    private String transObjectCode = "";//搬迁
    private String transObjectName = "";//搬迁
    private String workorderBatchName = "";
    private String queryType = "";//查询类型

    private String costCenterCode = "";//成本中心代码
    private String costCenterName = "";//成本中心名称

    private String contentCode = "";//资产目录编码
    private String contentName = "";//资产目录名称
    
    private String projectCode = "";//工程编码
    private String projectName = "";//工程名称

    /**
     * ******************added by mshtang*****************************
     */
    private String objectCategory = "";//地点类型
    private String maintainCompany = "";//代维公司
    private String objectCategoryOpt = "";//地点分类列表
    private String maintainComOpt = "";//代维公司列表
    private String company = "";//公司
    private String companyOpt = "";//公司列表
    private String groupOpt = "";//组别下拉框
    private String exportType = "";//Excel导出类型
    private String itemCategory = "";//设备专业
    private String itemName = "";//设备名称
    private String itemSpec = "";//规格型号
    private String deptCode = "";//责任部门代码
    private String deptName = "";//责任部门名称
    private String deptOpt = "";//部门列表
    private String userName = "";//责任人
    private String userId = "";//责任人Id
    private String employeeNumber = "";//责任人Number
    private String diffProcessDesc = "";//差异处理描述
    private String barcode = "";  //标签号
    private String opinionType="";  //用做传参判断
    private List partBarcode=new ArrayList();
    
    private String checkedBarcode = ""; // 被勾选的条码
	/**
     * ******************added by mshtang*****************************
     */
    private String financeProp = "ASSETS";

    public EtsWorkorderDTO() {
        this.distributeDate = new SimpleCalendar();
        this.downloadDate = new SimpleCalendar();
        this.scanoverDate = new SimpleCalendar();
        this.uploadDate = new SimpleCalendar();
        this.checkoverDate = new SimpleCalendar();
    }

    public String getExecuteUserName() {
        return executeUserName;
    }

    public String getUserId() {
		return userId;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setExecuteUserName(String executeUserName) {
        this.executeUserName = executeUserName;
    }

    public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
     * 功能：设置工单主表(EAM)属性 上次修改人
     * @param systemid String
     */
    public void setSystemid(String systemid) {
        this.systemid = systemid;
    }

    /**
     * 功能：设置工单主表(EAM)属性 上次修改人
     * @param workorderBatch String
     */
    public void setWorkorderBatch(String workorderBatch) {
        this.workorderBatch = workorderBatch;
    }

    /**
     * 功能：设置工单主表(EAM)属性 上次修改人
     * @param workorderNo String
     */
    public void setWorkorderNo(String workorderNo) {
        this.workorderNo = workorderNo;
    }

    /**
     * 功能：设置工单主表(EAM)属性 上次修改人
     * @param workorderType String
     */
    public void setWorkorderType(String workorderType) {
        this.workorderType = workorderType;
    }

    /**
     * 功能：设置工单主表(EAM)属性 上次修改人
     * @param workorderObjectNo String
     */
    public void setWorkorderObjectNo(String workorderObjectNo) {
        this.workorderObjectNo = workorderObjectNo;
    }

    /**
     * 功能：设置工单主表(EAM)属性 上次修改人
     *
     * @param startDate SimpleCalendar
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
//    public void setStartDate(String startDate) throws CalendarException {
//        if (!StrUtil.isEmpty(startDate)) {
//            this.startDate = new SimpleCalendar(startDate);
//        }
//    }

    /**
     * 功能：设置工单主表(EAM)属性 上次修改人
     * @param implementDays String
     */
    public void setImplementDays(int implementDays) {
        this.implementDays = implementDays;
    }

    /**
     * 功能：设置工单主表(EAM)属性 上次修改人
     * @param groupId String
     */
    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    /**
     * 功能：设置工单主表(EAM)属性 上次修改人
     * @param implementBy String
     */
    public void setImplementBy(int implementBy) {
        this.implementBy = implementBy;
    }

    /**
     * 功能：设置工单主表(EAM)属性 上次修改人
     * @param prjId String
     */
    public void setPrjId(String prjId) {
        this.prjId = prjId;
    }

    /**
     * 功能：设置工单主表(EAM)属性 上次修改人
     * @param distributeDate SimpleCalendar
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setDistributeDate(String distributeDate) throws CalendarException {
        if (!StrUtil.isEmpty(distributeDate)) {
            this.distributeDate = new SimpleCalendar(distributeDate);
        }
    }

    /**
     * 功能：设置工单主表(EAM)属性 上次修改人
     * @param distributeBy String
     */
    public void setDistributeBy(int distributeBy) {
        this.distributeBy = distributeBy;
    }

    /**
     * 功能：设置工单主表(EAM)属性 上次修改人
     * @param downloadDate SimpleCalendar
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setDownloadDate(String downloadDate) throws CalendarException {
        if (!StrUtil.isEmpty(downloadDate)) {
            this.downloadDate = new SimpleCalendar(downloadDate);
        }
    }

    /**
     * 功能：设置工单主表(EAM)属性 上次修改人
     * @param downloadBy String
     */
    public void setDownloadBy(int downloadBy) {
        this.downloadBy = downloadBy;
    }

    /**
     * 功能：设置工单主表(EAM)属性 上次修改人
     * @param scanoverDate SimpleCalendar
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setScanoverDate(String scanoverDate) throws CalendarException {
        if (!StrUtil.isEmpty(scanoverDate)) {
            this.scanoverDate = new SimpleCalendar(scanoverDate);
        }
    }

    /**
     * 功能：设置工单主表(EAM)属性 上次修改人
     * @param scanoverBy String
     */
    public void setScanoverBy(int scanoverBy) {
        this.scanoverBy = scanoverBy;
    }

    /**
     * 功能：设置工单主表(EAM)属性 上次修改人
     * @param uploadDate SimpleCalendar
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setUploadDate(String uploadDate) throws CalendarException {
        if (!StrUtil.isEmpty(uploadDate)) {
            this.uploadDate = new SimpleCalendar(uploadDate);
        }
    }

    /**
     * 功能：设置工单主表(EAM)属性 上次修改人
     * @param uploadBy String
     */
    public void setUploadBy(int uploadBy) {
        this.uploadBy = uploadBy;
    }

    /**
     * 功能：设置工单主表(EAM)属性 上次修改人
     * @param checkoverDate SimpleCalendar
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setCheckoverDate(String checkoverDate) throws CalendarException {
        if (!StrUtil.isEmpty(checkoverDate)) {
            this.checkoverDate = new SimpleCalendar(checkoverDate);
        }
    }

    /**
     * 功能：设置工单主表(EAM)属性 上次修改人
     * @param checkoverBy String
     */
    public void setCheckoverBy(int checkoverBy) {
        this.checkoverBy = checkoverBy;
    }

    /**
     * 功能：设置工单主表(EAM)属性 上次修改人
     * @param responsibilityUser String
     */
    public void setResponsibilityUser(String responsibilityUser) {
        this.responsibilityUser = responsibilityUser;
    }

    /**
     * 功能：设置工单主表(EAM)属性 上次修改人
     * @param differenceReason String
     */
    public void setDifferenceReason(String differenceReason) {
        this.differenceReason = differenceReason;
    }

    /**
     * 功能：设置工单主表(EAM)属性 上次修改人
     * @param organizationId String
     */
    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }
    
	/**
     * 功能：设置工单主表(EAM)属性 上次修改人
     * @param workorderFlag String
     */
    public void setWorkorderFlag(String workorderFlag) {
        this.workorderFlag = workorderFlag;
    }

    /**
     * 功能：设置工单主表(EAM)属性 上次修改人
     * @param remark String
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 功能：设置工单主表(EAM)属性 上次修改人
     * @param actid String
     */
    public void setActid(String actid) {
        this.actid = actid;
    }

    /**
     * 功能：设置工单主表(EAM)属性 上次修改人
     * @param caseid String
     */
    public void setCaseid(String caseid) {
        this.caseid = caseid;
    }

    /**
     * 功能：设置工单主表(EAM)属性 上次修改人
     * @param archflag String
     */
    public void setArchflag(int archflag) {
        this.archflag = archflag;
    }

    /**
     * 功能：设置工单主表(EAM)属性 上次修改人
     * @param attribute1 String
     */
    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1;
    }

    /**
     * 功能：设置工单主表(EAM)属性 上次修改人
     * @param attribute2 String
     */
    public void setAttribute2(String attribute2) {
        this.attribute2 = attribute2;
    }

    /**
     * 功能：设置工单主表(EAM)属性 上次修改人
     * @param attribute3 String
     */
    public void setAttribute3(String attribute3) {
        this.attribute3 = attribute3;
    }

    /**
     * 功能：设置工单主表(EAM)属性 上次修改人
     * @param attribute4 String
     */
    public void setAttribute4(String attribute4) {
        this.attribute4 = attribute4;
    }

    /**
     * 功能：设置工单主表(EAM)属性 上次修改人
     * @param attribute5 String
     */
    public void setAttribute5(String attribute5) {
        this.attribute5 = attribute5;
    }

    /**
     * 功能：设置工单主表(EAM)属性 上次修改人
     * @param attribute6 String
     */
    public void setAttribute6(int attribute6) {
        this.attribute6 = attribute6;
    }

    /**
     * 功能：设置工单主表(EAM)属性 上次修改人
     * @param distributeGroup String
     */
    public void setDistributeGroup(int distributeGroup) {
        this.distributeGroup = distributeGroup;
    }

    /**
     * 功能：设置工单主表(EAM)属性 上次修改人
     * @param attribute7 String
     */
    public void setAttribute7(String attribute7) {
        this.attribute7 = attribute7;
    }

    /**
     * 功能：设置工单主表(EAM)属性 上次修改人
     *
     * @param creationDate SimpleCalendar
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
//    public void setCreationDate(String creationDate) throws CalendarException {
//        if (!StrUtil.isEmpty(creationDate)) {
//            this.creationDate = new SimpleCalendar(creationDate);
//        }
//    }

    /**
     * 功能：设置工单主表(EAM)属性 上次修改人
     *
     * @param createdBy String
     */
//    public void setCreatedBy(int createdBy) {
//        this.createdBy = createdBy;
//    }

    /**
     * 功能：设置工单主表(EAM)属性 上次修改人
     *
     * @param lastUpdateDate SimpleCalendar
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
//    public void setLastUpdateDate(String lastUpdateDate) throws CalendarException {
//        if (!StrUtil.isEmpty(lastUpdateDate)) {
//            this.lastUpdateDate = new SimpleCalendar(lastUpdateDate);
//        }
//    }

    /**
     * 功能：设置工单主表(EAM)属性 上次修改人
     *
     * @param lastUpdateBy String
     */
//    public void setLastUpdateBy(int lastUpdateBy) {
//        this.lastUpdateBy = lastUpdateBy;
//    }

    /**
     * 功能：设置工单主表(EAM)属性 上次修改人
     * @param queryType String
     */
    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }


    /**
     * 功能：获取工单主表(EAM)属性 上次修改人
     * @return String
     */
    public String getSystemid() {
        return this.systemid;
    }


    /**
     * 功能：获取工单主表(EAM)属性 上次修改人
     * @return String
     */
    public String getWorkorderBatch() {
        return this.workorderBatch;
    }

    /**
     * 功能：获取工单主表(EAM)属性 上次修改人
     * @return String
     */
    public String getWorkorderNo() {
        return this.workorderNo;
    }

    /**
     * 功能：获取工单主表(EAM)属性 上次修改人
     * @return String
     */
    public String getWorkorderType() {
        return this.workorderType;
    }

    /**
     * 功能：获取工单主表(EAM)属性 上次修改人
     * @return String
     */
    public String getWorkorderObjectNo() {
        return this.workorderObjectNo;
    }

    /**
     * 功能：获取工单主表(EAM)属性 上次修改人
     *
     * @return SimpleCalendar
     */
//    public SimpleCalendar getStartDate() {
//        return this.startDate;
//    }

    /**
     * 功能：获取工单主表(EAM)属性 上次修改人
     * @return String
     */
    public int getImplementDays() {
        return this.implementDays;
    }

    /**
     * 功能：获取工单主表(EAM)属性 上次修改人
     * @return String
     */
    public int getGroupId() {
        return this.groupId;
    }

    /**
     * 功能：获取工单主表(EAM)属性 上次修改人
     * @return String
     */
    public int getImplementBy() {
        return this.implementBy;
    }

    /**
     * 功能：获取工单主表(EAM)属性 上次修改人
     * @return String
     */
    public String getPrjId() {
        return this.prjId;
    }

    /**
     * 功能：获取工单主表(EAM)属性 上次修改人
     * @return SimpleCalendar
     */
    public SimpleCalendar getDistributeDate() {
        return this.distributeDate;
    }

    /**
     * 功能：获取工单主表(EAM)属性 上次修改人
     * @return String
     */
    public int getDistributeBy() {
        return this.distributeBy;
    }

    /**
     * 功能：获取工单主表(EAM)属性 上次修改人
     * @return SimpleCalendar
     */
    public SimpleCalendar getDownloadDate() {
        return this.downloadDate;
    }

    /**
     * 功能：获取工单主表(EAM)属性 上次修改人
     * @return String
     */
    public int getDownloadBy() {
        return this.downloadBy;
    }

    /**
     * 功能：获取工单主表(EAM)属性 上次修改人
     * @return SimpleCalendar
     */
    public SimpleCalendar getScanoverDate() {
        return this.scanoverDate;
    }

    /**
     * 功能：获取工单主表(EAM)属性 上次修改人
     * @return String
     */
    public int getScanoverBy() {
        return this.scanoverBy;
    }

    /**
     * 功能：获取工单主表(EAM)属性 上次修改人
     * @return SimpleCalendar
     */
    public SimpleCalendar getUploadDate() {
        return this.uploadDate;
    }

    /**
     * 功能：获取工单主表(EAM)属性 上次修改人
     * @return String
     */
    public int getUploadBy() {
        return this.uploadBy;
    }

    /**
     * 功能：获取工单主表(EAM)属性 上次修改人
     * @return SimpleCalendar
     */
    public SimpleCalendar getCheckoverDate() {
        return this.checkoverDate;
    }

    /**
     * 功能：获取工单主表(EAM)属性 上次修改人
     * @return String
     */
    public int getCheckoverBy() {
        return this.checkoverBy;
    }

    /**
     * 功能：获取工单主表(EAM)属性 上次修改人
     * @return String
     */
    public String getResponsibilityUser() {
        return this.responsibilityUser;
    }

    /**
     * 功能：获取工单主表(EAM)属性 上次修改人
     * @return String
     */
    public String getDifferenceReason() {
        return this.differenceReason;
    }

    /**
     * 功能：获取工单主表(EAM)属性 上次修改人
     * @return String
     */
    public int getOrganizationId() {
        return this.organizationId;
    }

	/**
     * 功能：获取工单主表(EAM)属性 上次修改人
     * @return String
     */
    public String getWorkorderFlag() {
        return this.workorderFlag;
    }

    /**
     * 功能：获取工单主表(EAM)属性 上次修改人
     * @return String
     */
    public String getRemark() {
        return this.remark;
    }

    /**
     * 功能：获取工单主表(EAM)属性 上次修改人
     * @return String
     */
    public String getActid() {
        return this.actid;
    }

    /**
     * 功能：获取工单主表(EAM)属性 上次修改人
     * @return String
     */
    public String getCaseid() {
        return this.caseid;
    }

    /**
     * 功能：获取工单主表(EAM)属性 上次修改人
     * @return String
     */
    public int getArchflag() {
        return this.archflag;
    }

    /**
     * 功能：获取工单主表(EAM)属性 上次修改人
     * @return String
     */
    public String getAttribute1() {
        return this.attribute1;
    }

    /**
     * 功能：获取工单主表(EAM)属性 上次修改人
     * @return String
     */
    public String getAttribute2() {
        return this.attribute2;
    }

    /**
     * 功能：获取工单主表(EAM)属性 上次修改人
     * @return String
     */
    public String getAttribute3() {
        return this.attribute3;
    }

    /**
     * 功能：获取工单主表(EAM)属性 上次修改人
     * @return String
     */
    public String getAttribute4() {
        return this.attribute4;
    }

    /**
     * 功能：获取工单主表(EAM)属性 上次修改人
     * @return String
     */
    public String getAttribute5() {
        return this.attribute5;
    }

    /**
     * 功能：获取工单主表(EAM)属性 上次修改人
     * @return String
     */
    public int getAttribute6() {
        return this.attribute6;
    }

    /**
     * 功能：获取工单主表(EAM)属性 上次修改人
     * @return String
     */
    public int getDistributeGroup() {
        return this.distributeGroup;
    }

    /**
     * 功能：获取工单主表(EAM)属性 上次修改人
     * @return String
     */
    public String getAttribute7() {
        return this.attribute7;
    }

    /**
     * 功能：获取工单主表(EAM)属性 上次修改人
     *
     * @return SimpleCalendar
     */
//    public SimpleCalendar getCreationDate() {
//        return this.creationDate;
//    }

    /**
     * 功能：获取工单主表(EAM)属性 上次修改人
     *
     * @return String
     */
//    public int getCreatedBy() {
//        return this.createdBy;
//    }

    /**
     * 功能：获取工单主表(EAM)属性 上次修改人
     *
     * @return SimpleCalendar
     */
//    public SimpleCalendar getLastUpdateDate() {
//        return this.lastUpdateDate;
//    }

    /**
     * 功能：获取工单主表(EAM)属性 上次修改人
     * @return String
     */
//    public int getLastUpdateBy() {
//        return this.lastUpdateBy;
//    }
    public String getWorkorderTypeDesc() {
        return workorderTypeDesc;
    }

    public void setWorkorderTypeDesc(String workorderTypeDesc) {
        this.workorderTypeDesc = workorderTypeDesc;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getWorkorderObjectName() {
        return workorderObjectName;
    }

    public void setWorkorderObjectName(String workorderObjectName) {
        this.workorderObjectName = workorderObjectName;
    }

    public String getWorkorderObjectCode() {
        return workorderObjectCode;
    }

    public void setWorkorderObjectCode(String workorderObjectCode) {
        this.workorderObjectCode = workorderObjectCode;
    }

    public String getWorkorderBatchName() {
        return workorderBatchName;
    }

    public void setWorkorderBatchName(String workorderBatchName) {
        this.workorderBatchName = workorderBatchName;
    }

    public String getDistributeUser() {
        return distributeUser;
    }

    public void setDistributeUser(String distributeUser) {
        this.distributeUser = distributeUser;
    }

    public String getDownloadUser() {
        return downloadUser;
    }

    public void setDownloadUser(String downloadUser) {
        this.downloadUser = downloadUser;
    }

    public String getScanoverUser() {
        return scanoverUser;
    }

    public void setScanoverUser(String scanoverUser) {
        this.scanoverUser = scanoverUser;
    }

    public String getCheckoverUser() {
        return checkoverUser;
    }

    public void setCheckoverUser(String checkoverUser) {
        this.checkoverUser = checkoverUser;
    }

    public String getWorkorderFlagDesc() {
        return workorderFlagDesc;
    }

    public void setWorkorderFlagDesc(String workorderFlagDesc) {
        this.workorderFlagDesc = workorderFlagDesc;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getLastUpdateUser() {
        return lastUpdateUser;
    }

    public void setLastUpdateUser(String lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public String getTransObjectCode() {
        return transObjectCode;
    }

    public void setTransObjectCode(String transObjectCode) {
        this.transObjectCode = transObjectCode;
    }

    public String getTransObjectName() {
        return transObjectName;
    }

    public void setTransObjectName(String transObjectName) {
        this.transObjectName = transObjectName;
    }

    public String getImplementUser() {
        return implementUser;
    }

    public void setImplementUser(String implementUser) {
        this.implementUser = implementUser;
    }

    public String getPrjName() {
        return prjName;
    }

    public void setPrjName(String prjName) {
        this.prjName = prjName;
    }

    public void setMaintainCompany(String maintainCompany) {
        this.maintainCompany = maintainCompany;
    }

    public void setObjectCategory(String objectCategory) {
        this.objectCategory = objectCategory;
    }

    public void setMaintainComOpt(String maintainComOpt) {
        this.maintainComOpt = maintainComOpt;
    }

    public void setObjectCategoryOpt(String objectCategoryOpt) {
        this.objectCategoryOpt = objectCategoryOpt;
    }

    public void setGroupOpt(String groupOpt) {
        this.groupOpt = groupOpt;
    }

    public void setWorkorderObjectLocation(String workorderObjectLocation) {
        this.workorderObjectLocation = workorderObjectLocation;
    }

    public void setExportType(String exportType) {
        this.exportType = exportType;
    }

    public void setCompanyOpt(String companyOpt) {
        this.companyOpt = companyOpt;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }
    
    public void setItemName(String itemName) {
		this.itemName = itemName;
	}
    
	public void setItemSpec(String itemSpec) {
		this.itemSpec = itemSpec;
	}

	public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public void setDeptOpt(String deptOpt) {
        this.deptOpt = deptOpt;
    }

    public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getQueryType() {
        return queryType;
    }

    public String getMaintainCompany() {
        return maintainCompany;
    }

    public String getObjectCategory() {
        return objectCategory;
    }

    public String getMaintainComOpt() {
        return maintainComOpt;
    }

    public String getObjectCategoryOpt() {
        return objectCategoryOpt;
    }

    public String getGroupOpt() {
        return groupOpt;
    }

    public String getWorkorderObjectLocation() {
        return workorderObjectLocation;
    }

    public String getExportType() {
        return exportType;
    }

    public String getCompanyOpt() {
        return companyOpt;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public String getItemName() {
		return itemName;
	}

	public String getItemSpec() {
		return itemSpec;
	}

	public String getDeptCode() {
        return deptCode;
    }

    public String getDeptOpt() {
        return deptOpt;
    }

    public String getUserName() {
		return userName;
	}

	public String getDeptName() {
        return deptName;
    }

    public String getCompany() {
        return company;
    }

    public String getCostCenterCode() {
        return costCenterCode;
    }

    public void setCostCenterCode(String costCenterCode) {
        this.costCenterCode = costCenterCode;
    }

    public String getCostCenterName() {
        return costCenterName;
    }

    public void setCostCenterName(String costCenterName) {
        this.costCenterName = costCenterName;
    }
    
    public String getDiffProcessDesc() {
		return diffProcessDesc;
	}

	public void setDiffProcessDesc(String diffProcessDesc) {
		this.diffProcessDesc = diffProcessDesc;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getCheckedBarcode() {
		return checkedBarcode;
	}

	public void setCheckedBarcode(String checkedBarcode) {
		this.checkedBarcode = checkedBarcode;
	}

	public String getFinanceProp() {
		return financeProp;
	}

	public void setFinanceProp(String financeProp) {
		this.financeProp = financeProp;
	}
	
	public String getOpinionType() {
		return opinionType;
	}

	public void setOpinionType(String opinionType) {
		this.opinionType = opinionType;
	}

	public List getPartBarcode() {
		return partBarcode;
	}

	public void setPartBarcode(List partBarcode) {
		this.partBarcode = partBarcode;
	}

	public String getContentCode() {
		return contentCode;
	}

	public void setContentCode(String contentCode) {
		this.contentCode = contentCode;
	}

	public String getContentName() {
		return contentName;
	}

	public void setContentName(String contentName) {
		this.contentName = contentName;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
}
