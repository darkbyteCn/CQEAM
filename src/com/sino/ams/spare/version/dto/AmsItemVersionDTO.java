package com.sino.ams.spare.version.dto;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.util.StrUtil;

/**
 * <p>Title: 备件版本变动表 AmsItemVersion</p>
 * <p>Description: 程序自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */

public class AmsItemVersionDTO extends CheckBoxDTO {
    public AmsItemVersionDTO() {
        super();
        this.creationDate = new SimpleCalendar();
        this.lastUpdateDate = new SimpleCalendar();
    }

    private String barcode = "";
    private String itemCode = "";
    private String vendorBarcode = "";
    private String versionNo = "";
    private String organizationId = "";
    private SimpleCalendar creationDate = null;
    private String createdBy = "";
    private SimpleCalendar lastUpdateDate = null;
    private String lastUpdateBy = "";
    private String transId = "";
    private String remark = "";
    private String versionId = "";
    
    private String itemName = "";
    private String itemSpec = "";
    private String itemCategory = "";

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

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }


    /**
     * 功能：设置备件版本变动表属性 备件条码
     *
     * @param barcode String
     */
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    /**
     * 功能：设置备件版本变动表属性 设备分类代码
     *
     * @param itemCode String
     */
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    /**
     * 功能：设置备件版本变动表属性 对应厂商条码
     *
     * @param vendorBarcode String
     */
    public void setVendorBarcode(String vendorBarcode) {
        this.vendorBarcode = vendorBarcode;
    }

    /**
     * 功能：设置备件版本变动表属性 版本号
     *
     * @param versionNo String
     */
    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }

    /**
     * 功能：设置备件版本变动表属性 OU组织ID
     *
     * @param organizationId String
     */
    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    /**
     * 功能：设置备件版本变动表属性 本版本创建日期
     *
     * @param creationDate SimpleCalendar
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setCreationDate(String creationDate) throws CalendarException {
        if (!StrUtil.isEmpty(creationDate)) {
            this.creationDate = new SimpleCalendar(creationDate);
        } else {
            this.creationDate = null;
        }
    }

    /**
     * 功能：设置备件版本变动表属性 创建人
     *
     * @param createdBy String
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * 功能：设置备件版本变动表属性 上次修改日期
     *
     * @param lastUpdateDate SimpleCalendar
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setLastUpdateDate(String lastUpdateDate) throws CalendarException {
        if (!StrUtil.isEmpty(lastUpdateDate)) {
            this.lastUpdateDate = new SimpleCalendar(lastUpdateDate);
        } else {
            this.lastUpdateDate = null;
        }
    }

    /**
     * 功能：设置备件版本变动表属性 上次修改人
     *
     * @param lastUpdateBy String
     */
    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    /**
     * 功能：设置备件版本变动表属性 发生版本变动的送修返还事务ID
     *
     * @param transId String
     */
    public void setTransId(String transId) {
        this.transId = transId;
    }

    /**
     * 功能：设置备件版本变动表属性 备注
     *
     * @param remark String
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 功能：设置备件版本变动表属性 版本序列号
     *
     * @param versionId String
     */
    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }


    /**
     * 功能：获取备件版本变动表属性 备件条码
     *
     * @return String
     */
    public String getBarcode() {
        return this.barcode;
    }

    /**
     * 功能：获取备件版本变动表属性 设备分类代码
     *
     * @return String
     */
    public String getItemCode() {
        return this.itemCode;
    }

    /**
     * 功能：获取备件版本变动表属性 对应厂商条码
     *
     * @return String
     */
    public String getVendorBarcode() {
        return this.vendorBarcode;
    }

    /**
     * 功能：获取备件版本变动表属性 版本号
     *
     * @return String
     */
    public String getVersionNo() {
        return this.versionNo;
    }

    /**
     * 功能：获取备件版本变动表属性 OU组织ID
     *
     * @return String
     */
    public String getOrganizationId() {
        return this.organizationId;
    }

    /**
     * 功能：获取备件版本变动表属性 本版本创建日期
     *
     * @return SimpleCalendar
     * @throws CalendarException 设置的日历格式不合法时抛出该异常
     */
    public SimpleCalendar getCreationDate() throws CalendarException {
        if (creationDate != null) {
            creationDate.setCalPattern(getCalPattern());
        }
        return this.creationDate;
    }

    /**
     * 功能：获取备件版本变动表属性 创建人
     *
     * @return String
     */
    public String getCreatedBy() {
        return this.createdBy;
    }

    /**
     * 功能：获取备件版本变动表属性 上次修改日期
     *
     * @return SimpleCalendar
     * @throws CalendarException 设置的日历格式不合法时抛出该异常
     */
    public SimpleCalendar getLastUpdateDate() throws CalendarException {
        if (lastUpdateDate != null) {
            lastUpdateDate.setCalPattern(getCalPattern());
        }
        return this.lastUpdateDate;
    }

    /**
     * 功能：获取备件版本变动表属性 上次修改人
     *
     * @return String
     */
    public String getLastUpdateBy() {
        return this.lastUpdateBy;
    }

    /**
     * 功能：获取备件版本变动表属性 发生版本变动的送修返还事务ID
     *
     * @return String
     */
    public String getTransId() {
        return this.transId;
    }

    /**
     * 功能：获取备件版本变动表属性 备注
     *
     * @return String
     */
    public String getRemark() {
        return this.remark;
    }

    /**
     * 功能：获取备件版本变动表属性 版本序列号
     *
     * @return String
     */
    public String getVersionId() {
        return this.versionId;
    }

}