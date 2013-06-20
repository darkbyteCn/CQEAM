package com.sino.ams.workorder.dto;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.util.StrUtil;

/**
 * <p>Title: 工单临时表(EAM) EtsWorkorderTmp</p>
 * <p>Description: 程序自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class EtsWorkorderTmpDTO extends CheckBoxDTO {

    private String systemid = "";
    private String workorderBatch = "";
    private String workorderNo = "";
    private String workorderType = "";
    private String workorderObjectNo;
    private SimpleCalendar startDate = null;
    private int implementDays;
    private int groupId;
    private int implementBy;
    private String prjId;
    private SimpleCalendar distributeDate = null;
    private int distributeBy;
    private SimpleCalendar downloadDate = null;
    private int downloadBy;
    private SimpleCalendar scanoverDate = null;
    private int scanoverBy;
    private SimpleCalendar uploadDate = null;
    private int uploadBy;
    private SimpleCalendar checkoverDate = null;
    private int checkoverBy;
    private String responsibilityUser;
    private String differenceReason = "";
    private int organizationId ;
    private String workorderFlag;
    private String remark = "";
    private String actid;
    private String caseid = "";
    private int archflag ;
    private String attribute1 = "";
    private String attribute2 = "";
    private String attribute3 = "";
    private String attribute4 = "";
    private String attribute5 = "";
    private int attribute6 ;
    private int distributeGroup;
    private String attribute7 = "";
    private SimpleCalendar creationDate = null;
    private int createdBy ;
    private SimpleCalendar lastUpdateDate = null;
    private int lastUpdateBy ;

    private String workorderTypeDesc = "";
    private String groupName = "";
    private String workorderObjectCode = "";
    private String workorderObjectName = "";
    private String transObjectCode = "";//搬迁
    private String transObjectName = "";//搬迁

    private String costCenterCode = "";//成本中心代码
    private String costCenterName = "";//成本中心名称


    public EtsWorkorderTmpDTO() {
        this.startDate = new SimpleCalendar();
        this.distributeDate = new SimpleCalendar();
        this.downloadDate = new SimpleCalendar();
        this.scanoverDate = new SimpleCalendar();
        this.uploadDate = new SimpleCalendar();
        this.checkoverDate = new SimpleCalendar();
        this.creationDate = new SimpleCalendar();
        this.lastUpdateDate = new SimpleCalendar();
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

    /**
     * 功能：设置工单临时表(EAM)属性 系统ID
     * @param systemid String
     */
    public void setSystemid(String systemid) {
        this.systemid = systemid;
    }

    /**
     * 功能：设置工单临时表(EAM)属性 工单批号
     * @param workorderBatch String
     */
    public void setWorkorderBatch(String workorderBatch) {
        this.workorderBatch = workorderBatch;
    }

    /**
     * 功能：设置工单临时表(EAM)属性 工单号(年月6位+序列号3位，工单号唯一)
     * @param workorderNo String
     */
    public void setWorkorderNo(String workorderNo) {
        this.workorderNo = workorderNo;
    }

    /**
     * 功能：设置工单临时表(EAM)属性 工单类型
     * @param workorderType String
     */
    public void setWorkorderType(String workorderType) {
        this.workorderType = workorderType;
    }

    /**
     * 功能：设置工单临时表(EAM)属性 工单对象(即基站编码)
     * @param workorderObjectNo String
     */
    public void setWorkorderObjectNo(String workorderObjectNo) {
        this.workorderObjectNo = workorderObjectNo;
    }

    /**
     * 功能：设置工单临时表(EAM)属性 工单开始日期
     * @param startDate SimpleCalendar
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setStartDate(String startDate) throws CalendarException {
        if (!StrUtil.isEmpty(startDate)) {
            this.startDate = new SimpleCalendar(startDate);
        }
    }

    /**
     * 功能：设置工单临时表(EAM)属性 null
     * @param implementDays String
     */
    public void setImplementDays(int implementDays) {
        this.implementDays = implementDays;
    }

    /**
     * 功能：设置工单临时表(EAM)属性 派往部门/接单部门组别ID
     * @param groupId String
     */
    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    /**
     * 功能：设置工单临时表(EAM)属性 工单执行人
     * @param implementBy String
     */
    public void setImplementBy(int implementBy) {
        this.implementBy = implementBy;
    }

    /**
     * 功能：设置工单临时表(EAM)属性 null
     * @param prjId String
     */
    public void setPrjId(String prjId) {
        this.prjId = prjId;
    }

    /**
     * 功能：设置工单临时表(EAM)属性 下发日期
     * @param distributeDate SimpleCalendar
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setDistributeDate(String distributeDate) throws CalendarException {
        if (!StrUtil.isEmpty(distributeDate)) {
            this.distributeDate = new SimpleCalendar(distributeDate);
        }
    }

    /**
     * 功能：设置工单临时表(EAM)属性 下发人
     * @param distributeBy String
     */
    public void setDistributeBy(int distributeBy) {
        this.distributeBy = distributeBy;
    }

    /**
     * 功能：设置工单临时表(EAM)属性 下载日期
     * @param downloadDate SimpleCalendar
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setDownloadDate(String downloadDate) throws CalendarException {
        if (!StrUtil.isEmpty(downloadDate)) {
            this.downloadDate = new SimpleCalendar(downloadDate);
        }
    }

    /**
     * 功能：设置工单临时表(EAM)属性 下载人
     * @param downloadBy String
     */
    public void setDownloadBy(int downloadBy) {
        this.downloadBy = downloadBy;
    }

    /**
     * 功能：设置工单临时表(EAM)属性 扫描完成日期
     * @param scanoverDate SimpleCalendar
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setScanoverDate(String scanoverDate) throws CalendarException {
        if (!StrUtil.isEmpty(scanoverDate)) {
            this.scanoverDate = new SimpleCalendar(scanoverDate);
        }
    }

    /**
     * 功能：设置工单临时表(EAM)属性 扫描人
     * @param scanoverBy String
     */
    public void setScanoverBy(int scanoverBy) {
        this.scanoverBy = scanoverBy;
    }

    /**
     * 功能：设置工单临时表(EAM)属性 上传日期/实际完成日期
     * @param uploadDate SimpleCalendar
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setUploadDate(String uploadDate) throws CalendarException {
        if (!StrUtil.isEmpty(uploadDate)) {
            this.uploadDate = new SimpleCalendar(uploadDate);
        }
    }

    /**
     * 功能：设置工单临时表(EAM)属性 上传人
     * @param uploadBy String
     */
    public void setUploadBy(int uploadBy) {
        this.uploadBy = uploadBy;
    }

    /**
     * 功能：设置工单临时表(EAM)属性 核实日期
     * @param checkoverDate SimpleCalendar
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setCheckoverDate(String checkoverDate) throws CalendarException {
        if (!StrUtil.isEmpty(checkoverDate)) {
            this.checkoverDate = new SimpleCalendar(checkoverDate);
        }
    }

    /**
     * 功能：设置工单临时表(EAM)属性 核实人
     * @param checkoverBy String
     */
    public void setCheckoverBy(int checkoverBy) {
        this.checkoverBy = checkoverBy;
    }

    /**
     * 功能：设置工单临时表(EAM)属性 责任人
     * @param responsibilityUser String
     */
    public void setResponsibilityUser(String responsibilityUser) {
        this.responsibilityUser = responsibilityUser;
    }

    /**
     * 功能：设置工单临时表(EAM)属性 差异原因
     * @param differenceReason String
     */
    public void setDifferenceReason(String differenceReason) {
        this.differenceReason = differenceReason;
    }

    /**
     * 功能：设置工单临时表(EAM)属性 组织
     * @param organizationId String
     */
    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    /**
     * 功能：设置工单临时表(EAM)属性 工单状态(10:新增；11:已下发；12:已下载；13:已完成；14:已核实；15:已取消)
     * @param workorderFlag String
     */
    public void setWorkorderFlag(String workorderFlag) {
        this.workorderFlag = workorderFlag;
    }

    /**
     * 功能：设置工单临时表(EAM)属性 备注
     * @param remark String
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 功能：设置工单临时表(EAM)属性 ACTIVE ID
     * @param actid String
     */
    public void setActid(String actid) {
        this.actid = actid;
    }

    /**
     * 功能：设置工单临时表(EAM)属性 CASEID
     * @param caseid String
     */
    public void setCaseid(String caseid) {
        this.caseid = caseid;
    }

    /**
     * 功能：设置工单临时表(EAM)属性 归档标志(1:已归档；0:未归档)
     * @param archflag String
     */
    public void setArchflag(int archflag) {
        this.archflag = archflag;
    }

    /**
     * 功能：设置工单临时表(EAM)属性 null
     * @param attribute1 String
     */
    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1;
    }

    /**
     * 功能：设置工单临时表(EAM)属性 null
     * @param attribute2 String
     */
    public void setAttribute2(String attribute2) {
        this.attribute2 = attribute2;
    }

    /**
     * 功能：设置工单临时表(EAM)属性 null
     * @param attribute3 String
     */
    public void setAttribute3(String attribute3) {
        this.attribute3 = attribute3;
    }

    /**
     * 功能：设置工单临时表(EAM)属性 null
     * @param attribute4 String
     */
    public void setAttribute4(String attribute4) {
        this.attribute4 = attribute4;
    }

    /**
     * 功能：设置工单临时表(EAM)属性 null
     * @param attribute5 String
     */
    public void setAttribute5(String attribute5) {
        this.attribute5 = attribute5;
    }

    /**
     * 功能：设置工单临时表(EAM)属性 null
     * @param attribute6 String
     */
    public void setAttribute6(int attribute6) {
        this.attribute6 = attribute6;
    }

    /**
     * 功能：设置工单临时表(EAM)属性 null
     * @param distributeGroup String
     */
    public void setDistributeGroup(int distributeGroup) {
        this.distributeGroup = distributeGroup;
    }

    /**
     * 功能：设置工单临时表(EAM)属性 null
     * @param attribute7 String
     */
    public void setAttribute7(String attribute7) {
        this.attribute7 = attribute7;
    }

    /**
     * 功能：设置工单临时表(EAM)属性 创建日期
     * @param creationDate SimpleCalendar
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setCreationDate(String creationDate) throws CalendarException {
        if (!StrUtil.isEmpty(creationDate)) {
            this.creationDate = new SimpleCalendar(creationDate);
        }
    }

    /**
     * 功能：设置工单临时表(EAM)属性 创建人
     * @param createdBy String
     */
    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * 功能：设置工单临时表(EAM)属性 上次修改日期
     * @param lastUpdateDate SimpleCalendar
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setLastUpdateDate(String lastUpdateDate) throws CalendarException {
        if (!StrUtil.isEmpty(lastUpdateDate)) {
            this.lastUpdateDate = new SimpleCalendar(lastUpdateDate);
        }
    }

    /**
     * 功能：设置工单临时表(EAM)属性 上次修改人
     * @param lastUpdateBy String
     */
    public void setLastUpdateBy(int lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }


    /**
     * 功能：获取工单临时表(EAM)属性 系统ID
     * @return String
     */
    public String getSystemid() {
        return this.systemid;
    }

    /**
     * 功能：获取工单临时表(EAM)属性 工单批号
     * @return String
     */
    public String getWorkorderBatch() {
        return this.workorderBatch;
    }

    /**
     * 功能：获取工单临时表(EAM)属性 工单号(年月6位+序列号3位，工单号唯一)
     * @return String
     */
    public String getWorkorderNo() {
        return this.workorderNo;
    }

    /**
     * 功能：获取工单临时表(EAM)属性 工单类型
     * @return String
     */
    public String getWorkorderType() {
        return this.workorderType;
    }

    /**
     * 功能：获取工单临时表(EAM)属性 工单对象(即基站编码)
     * @return String
     */
    public String getWorkorderObjectNo() {
        return this.workorderObjectNo;
    }

    /**
     * 功能：获取工单临时表(EAM)属性 工单开始日期
     * @return SimpleCalendar
     */
    public SimpleCalendar getStartDate() {
        return this.startDate;
    }

    /**
     * 功能：获取工单临时表(EAM)属性 null
     * @return String
     */
    public int getImplementDays() {
        return this.implementDays;
    }

    /**
     * 功能：获取工单临时表(EAM)属性 派往部门/接单部门组别ID
     * @return String
     */
    public int getGroupId() {
        return this.groupId;
    }

    /**
     * 功能：获取工单临时表(EAM)属性 工单执行人
     * @return String
     */
    public int getImplementBy() {
        return this.implementBy;
    }

    /**
     * 功能：获取工单临时表(EAM)属性 null
     * @return String
     */
    public String getPrjId() {
        return this.prjId;
    }

    /**
     * 功能：获取工单临时表(EAM)属性 下发日期
     * @return SimpleCalendar
     */
    public SimpleCalendar getDistributeDate() {
        return this.distributeDate;
    }

    /**
     * 功能：获取工单临时表(EAM)属性 下发人
     * @return String
     */
    public int getDistributeBy() {
        return this.distributeBy;
    }

    /**
     * 功能：获取工单临时表(EAM)属性 下载日期
     * @return SimpleCalendar
     */
    public SimpleCalendar getDownloadDate() {
        return this.downloadDate;
    }

    /**
     * 功能：获取工单临时表(EAM)属性 下载人
     * @return String
     */
    public int getDownloadBy() {
        return this.downloadBy;
    }

    /**
     * 功能：获取工单临时表(EAM)属性 扫描完成日期
     * @return SimpleCalendar
     */
    public SimpleCalendar getScanoverDate() {
        return this.scanoverDate;
    }

    /**
     * 功能：获取工单临时表(EAM)属性 扫描人
     * @return String
     */
    public int getScanoverBy() {
        return this.scanoverBy;
    }

    /**
     * 功能：获取工单临时表(EAM)属性 上传日期/实际完成日期
     * @return SimpleCalendar
     */
    public SimpleCalendar getUploadDate() {
        return this.uploadDate;
    }

    /**
     * 功能：获取工单临时表(EAM)属性 上传人
     * @return String
     */
    public int getUploadBy() {
        return this.uploadBy;
    }

    /**
     * 功能：获取工单临时表(EAM)属性 核实日期
     * @return SimpleCalendar
     */
    public SimpleCalendar getCheckoverDate() {
        return this.checkoverDate;
    }

    /**
     * 功能：获取工单临时表(EAM)属性 核实人
     * @return String
     */
    public int getCheckoverBy() {
        return this.checkoverBy;
    }

    /**
     * 功能：获取工单临时表(EAM)属性 责任人
     * @return String
     */
    public String getResponsibilityUser() {
        return this.responsibilityUser;
    }

    /**
     * 功能：获取工单临时表(EAM)属性 差异原因
     * @return String
     */
    public String getDifferenceReason() {
        return this.differenceReason;
    }

    /**
     * 功能：获取工单临时表(EAM)属性 组织
     * @return String
     */
    public int getOrganizationId() {
        return this.organizationId;
    }

    /**
     * 功能：获取工单临时表(EAM)属性 工单状态(10:新增；11:已下发；12:已下载；13:已完成；14:已核实；15:已取消)
     * @return String
     */
    public String getWorkorderFlag() {
        return this.workorderFlag;
    }

    /**
     * 功能：获取工单临时表(EAM)属性 备注
     * @return String
     */
    public String getRemark() {
        return this.remark;
    }

    /**
     * 功能：获取工单临时表(EAM)属性 ACTIVE ID
     * @return String
     */
    public String getActid() {
        return this.actid;
    }

    /**
     * 功能：获取工单临时表(EAM)属性 CASEID
     * @return String
     */
    public String getCaseid() {
        return this.caseid;
    }

    /**
     * 功能：获取工单临时表(EAM)属性 归档标志(1:已归档；0:未归档)
     * @return String
     */
    public int getArchflag() {
        return this.archflag;
    }

    /**
     * 功能：获取工单临时表(EAM)属性 null
     * @return String
     */
    public String getAttribute1() {
        return this.attribute1;
    }

    /**
     * 功能：获取工单临时表(EAM)属性 null
     * @return String
     */
    public String getAttribute2() {
        return this.attribute2;
    }

    /**
     * 功能：获取工单临时表(EAM)属性 null
     * @return String
     */
    public String getAttribute3() {
        return this.attribute3;
    }

    /**
     * 功能：获取工单临时表(EAM)属性 null
     * @return String
     */
    public String getAttribute4() {
        return this.attribute4;
    }

    /**
     * 功能：获取工单临时表(EAM)属性 null
     * @return String
     */
    public String getAttribute5() {
        return this.attribute5;
    }

    /**
     * 功能：获取工单临时表(EAM)属性 null
     * @return String
     */
    public int getAttribute6() {
        return this.attribute6;
    }

    /**
     * 功能：获取工单临时表(EAM)属性 null
     * @return String
     */
    public int getDistributeGroup() {
        return this.distributeGroup;
    }

    /**
     * 功能：获取工单临时表(EAM)属性 null
     * @return String
     */
    public String getAttribute7() {
        return this.attribute7;
    }

    /**
     * 功能：获取工单临时表(EAM)属性 创建日期
     * @return SimpleCalendar
     */
    public SimpleCalendar getCreationDate() {
        return this.creationDate;
    }

    /**
     * 功能：获取工单临时表(EAM)属性 创建人
     * @return String
     */
    public int getCreatedBy() {
        return this.createdBy;
    }

    /**
     * 功能：获取工单临时表(EAM)属性 上次修改日期
     * @return SimpleCalendar
     */
    public SimpleCalendar getLastUpdateDate() {
        return this.lastUpdateDate;
    }

    /**
     * 功能：获取工单临时表(EAM)属性 上次修改人
     * @return String
     */
    public int getLastUpdateBy() {
        return this.lastUpdateBy;
    }

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

    public String getWorkorderObjectCode() {
        return workorderObjectCode;
    }

    public void setWorkorderObjectCode(String workorderObjectCode) {
        this.workorderObjectCode = workorderObjectCode;
    }

    public String getWorkorderObjectName() {
        return workorderObjectName;
    }

    public void setWorkorderObjectName(String workorderObjectName) {
        this.workorderObjectName = workorderObjectName;
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
}