package com.sino.ams.web.bts.dto;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.util.StrUtil;

/**
 * <p>Title: 基站维修成本(EAM) EtsObjectFixfee</p>
 * <p>Description: 程序自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */

public class EtsObjectFixfeeDTO extends CheckBoxDTO {

    private String systemId = "";
    private SimpleCalendar fixDate = null;
    private int amount ;
    private String fixNo = "";
    private String attribute1 = "";
    private String attribute2 = "";
    private String remark = "";
    private SimpleCalendar creationDate = null;
    private int createdBy ;
    private SimpleCalendar lastUpdateDate = null;
    private int lastUpdateBy ;
    private int objectNo ;
    private String fromDate = null;
    private String toDate = null;
    private String workorderObjectName = "";
    private String workorderObjectLocation = "";
    private String workorderObjectNo = "";
    private String workorderObjectCode = "";
    private String objectCategory="";
    private String company = "";
    private int organizationId;

    public EtsObjectFixfeeDTO() {
        this.fixDate = new SimpleCalendar();
        this.creationDate = new SimpleCalendar();
        this.lastUpdateDate = new SimpleCalendar();
    }

    public String getWorkorderObjectCode() {
        return workorderObjectCode;
    }

    public void setWorkorderObjectCode(String workorderObjectCode) {
        this.workorderObjectCode = workorderObjectCode;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getWorkorderObjectNo() {
        return workorderObjectNo;
    }

    public void setWorkorderObjectNo(String workorderObjectNo) {
        this.workorderObjectNo = workorderObjectNo;
    }

    public String getWorkorderObjectLocation() {
        return workorderObjectLocation;
    }

    public void setWorkorderObjectLocation(String workorderObjectLocation) {
        this.workorderObjectLocation = workorderObjectLocation;
    }


    public String getWorkorderObjectName() {
        return workorderObjectName;
    }

    public void setWorkorderObjectName(String workorderObjectName) {
        this.workorderObjectName = workorderObjectName;
    }

    /**
     * 功能：设置基站维修成本(EAM)属性 序列号
     *
     * @param systemId String
     */
    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    /**
     * 功能：设置基站维修成本(EAM)属性 维修日期
     *
     * @throws CalendarException 传入值不是正确的日期
     * @param fixDate SimpleCalendar或者是基础库不能识别的格式时抛出该异常
     */
    public void setFixDate(String fixDate) throws CalendarException {
        if (!StrUtil.isEmpty(fixDate)) {
            this.fixDate = new SimpleCalendar(fixDate);
        }
    }

    /**
     * 功能：设置基站维修成本(EAM)属性 维修费用
     *
     * @param amount String
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * 功能：设置基站维修成本(EAM)属性 null
     *
     * @param fixNo String
     */
    public void setFixNo(String fixNo) {
        this.fixNo = fixNo;
    }

    /**
     * 功能：设置基站维修成本(EAM)属性 null
     *
     * @param attribute1 String
     */
    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1;
    }

    /**
     * 功能：设置基站维修成本(EAM)属性 null
     *
     * @param attribute2 String
     */
    public void setAttribute2(String attribute2) {
        this.attribute2 = attribute2;
    }

    /**
     * 功能：设置基站维修成本(EAM)属性 备注
     *
     * @param remark String
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 功能：设置基站维修成本(EAM)属性 创建日期
     *
     * @param creationDate Timestamp
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setCreationDate(String creationDate) throws CalendarException {
        if (!StrUtil.isEmpty(creationDate)) {
            this.creationDate = new SimpleCalendar(creationDate);
        }
    }

    /**
     * 功能：设置基站维修成本(EAM)属性 创建人
     *
     * @param createdBy String
     */
    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * 功能：设置基站维修成本(EAM)属性 上次修改日期
     *
     * @param lastUpdateDate SimpleCalendar
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setLastUpdateDate(String lastUpdateDate) throws CalendarException {
        if (!StrUtil.isEmpty(lastUpdateDate)) {
            this.lastUpdateDate =  new SimpleCalendar(lastUpdateDate);
        }
    }

    /**
     * 功能：设置基站维修成本(EAM)属性 上次修改人
     *
     * @param lastUpdateBy String
     */
    public void setLastUpdateBy(int lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    /**
     * 功能：设置基站维修成本(EAM)属性 基站序列号
     *
     * @param objectNo String
     */
    public void setObjectNo(int objectNo) {
        this.objectNo = objectNo;
    }


    /**
     * 功能：获取基站维修成本(EAM)属性 序列号
     *
     * @return String
     */
    public String getSystemId() {
        return this.systemId;
    }

    /**
     * 功能：获取基站维修成本(EAM)属性 维修日期
     *
     * @return Timestamp
     */
    public SimpleCalendar getFixDate() {
        return this.fixDate;
    }

    /**
     * 功能：获取基站维修成本(EAM)属性 维修费用
     *
     * @return String
     */
    public int getAmount() {
        return this.amount;
    }

    /**
     * 功能：获取基站维修成本(EAM)属性 null
     *
     * @return String
     */
    public String getFixNo() {
        return this.fixNo;
    }

    /**
     * 功能：获取基站维修成本(EAM)属性 null
     *
     * @return String
     */
    public String getAttribute1() {
        return this.attribute1;
    }

    /**
     * 功能：获取基站维修成本(EAM)属性 null
     *
     * @return String
     */
    public String getAttribute2() {
        return this.attribute2;
    }

    /**
     * 功能：获取基站维修成本(EAM)属性 备注
     *
     * @return String
     */
    public String getRemark() {
        return this.remark;
    }

    /**
     * 功能：获取基站维修成本(EAM)属性 创建日期
     *
     * @return SimpleCalendar
     */
    public SimpleCalendar getCreationDate() {
        return this.creationDate;
    }

    /**
     * 功能：获取基站维修成本(EAM)属性 创建人
     *
     * @return String
     */
    public int getCreatedBy() {
        return this.createdBy;
    }

    /**
     * 功能：获取基站维修成本(EAM)属性 上次修改日期
     *
     * @return SimpleCalendar
     */
    public SimpleCalendar getLastUpdateDate() {
        return this.lastUpdateDate;
    }

    /**
     * 功能：获取基站维修成本(EAM)属性 上次修改人
     *
     * @return String
     */
    public int getLastUpdateBy() {
        return this.lastUpdateBy;
    }

    /**
     * 功能：获取基站维修成本(EAM)属性 基站序列号
     *
     * @return String
     */
    public int getObjectNo() {
        return this.objectNo;
    }

    public String getObjectCategory() {
        return objectCategory;
    }

    public void setObjectCategory(String objectCategory) {
        this.objectCategory = objectCategory;
    }
}
