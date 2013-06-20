package com.sino.ams.web.ele.dto;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.util.StrUtil;

/**
 * <p>Title: 基站电费维护表(EAM) EtsObjectEle</p>
 * <p>Description: 程序自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class EtsObjectEleDTO extends CheckBoxDTO {

    private String systemid = "";
    private String workorderObjectNo = "";
    private String period = "";
    private String unitPrice = "";
    private String quantity = "";
    private String startDate = "";
    private String endDate = "";
    private String remark = "";
    private SimpleCalendar creationDate = null;
    private int createdBy;
    private SimpleCalendar lastUpdateDate = null;
    private int lastUpdateBy;
    private String workorderObjectName = "";
    private String fromDate = null;
    private String toDate = null;
    private String company = "";
    private String ammeterReading = "";

    public EtsObjectEleDTO() {
        this.creationDate = new SimpleCalendar();
        this.lastUpdateDate = new SimpleCalendar();
    }


    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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

    public String getWorkorderObjectName() {
        return workorderObjectName;
    }

    public void setWorkorderObjectName(String workorderObjectName) {
        this.workorderObjectName = workorderObjectName;
    }


    public String getSystemid() {
        return systemid;
    }

    public void setSystemid(String systemid) {
        this.systemid = systemid;
    }

    /**
     * 功能：设置基站电费维护表(EAM)属性 基站地点ID
     * @param workorderObjectNo String
     */
    public void setWorkorderObjectNo(String workorderObjectNo) {
        this.workorderObjectNo = workorderObjectNo;
    }

    /**
     * 功能：设置基站电费维护表(EAM)属性 会计期间
     * @param period String
     */
    public void setPeriod(String period) {
        this.period = period;
    }

    /**
     * 功能：设置基站电费维护表(EAM)属性 单价
     * @param unitPrice String
     */
    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * 功能：设置基站电费维护表(EAM)属性 数量
     * @param quantity String
     */
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    /**
     * 功能：设置基站电费维护表(EAM)属性 开始日期
     * @param startDate String
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * 功能：设置基站电费维护表(EAM)属性 截至日期
     * @param endDate String
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * 功能：设置基站电费维护表(EAM)属性 备注
     * @param remark String
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 功能：设置基站电费维护表(EAM)属性 创建日期
     * @param creationDate SimpleCalendar
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setCreationDate(String creationDate) throws CalendarException {
        if (!StrUtil.isEmpty(creationDate)) {
            this.creationDate = new SimpleCalendar(creationDate);
        }
    }

    /**
     * 功能：设置基站电费维护表(EAM)属性 创建人
     * @param createdBy String
     */
    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * 功能：设置基站电费维护表(EAM)属性 上次修改日期
     * @param lastUpdateDate SimpleCalendar
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setLastUpdateDate(String lastUpdateDate) throws CalendarException {
        if (!StrUtil.isEmpty(lastUpdateDate)) {
            this.lastUpdateDate = new SimpleCalendar(lastUpdateDate);
        }
    }

    /**
     * 功能：设置基站电费维护表(EAM)属性 上次修改人
     * @param lastUpdateBy String
     */
    public void setLastUpdateBy(int lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    /**
     * 功能：获取基站电费维护表(EAM)属性 主键
     * @return String
     */


    /**
     * 功能：获取基站电费维护表(EAM)属性 基站地点ID
     * @return String
     */
    public String getWorkorderObjectNo() {
        return this.workorderObjectNo;
    }

    /**
     * 功能：获取基站电费维护表(EAM)属性 会计期间
     * @return String
     */
    public String getPeriod() {
        return this.period;
    }

    /**
     * 功能：获取基站电费维护表(EAM)属性 单价
     * @return String
     */
    public String getUnitPrice() {
        return this.unitPrice;
    }

    /**
     * 功能：获取基站电费维护表(EAM)属性 数量
     * @return String
     */
    public String getQuantity() {
        return this.quantity;
    }

    /**
     * 功能：获取基站电费维护表(EAM)属性 开始日期
     * @return String
     */
    public String getStartDate() {
        return this.startDate;
    }

    /**
     * 功能：获取基站电费维护表(EAM)属性 截至日期
     * @return String
     */
    public String getEndDate() {
        return this.endDate;
    }

    /**
     * 功能：获取基站电费维护表(EAM)属性 备注
     * @return String
     */
    public String getRemark() {
        return this.remark;
    }

    /**
     * 功能：获取基站电费维护表(EAM)属性 创建日期
     * @return SimpleCalendar
     */
    public SimpleCalendar getCreationDate() {
        return this.creationDate;
    }

    /**
     * 功能：获取基站电费维护表(EAM)属性 创建人
     * @return String
     */
    public int getCreatedBy() {
        return this.createdBy;
    }

    /**
     * 功能：获取基站电费维护表(EAM)属性 上次修改日期
     * @return SimpleCalendar
     */
    public SimpleCalendar getLastUpdateDate() {
        return this.lastUpdateDate;
    }

    /**
     * 功能：获取基站电费维护表(EAM)属性 上次修改人
     * @return String
     */
    public int getLastUpdateBy() {
        return this.lastUpdateBy;
    }

    public String getAmmeterReading() {
        return ammeterReading;
    }

    public void setAmmeterReading(String ammeterReading) {
        this.ammeterReading = ammeterReading;
    }
}