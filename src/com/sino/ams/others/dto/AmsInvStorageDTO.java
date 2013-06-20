package com.sino.ams.others.dto;

import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.math.AdvancedNumber;

/**
 * <p>Title: AMS_INV_STORAGE AmsInvStorage</p>
 * <p>Description: 程序自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class AmsInvStorageDTO extends CheckBoxDTO {

    private AdvancedNumber storageId = null;
    private String batchNo = "";
    private AdvancedNumber invCode = null;
    private AdvancedNumber organizationId = null;
    private String itemCode = "";
    private AdvancedNumber quantity = null;
    private String itemName = "";
    private String itemSpec = "";
    private String itemUnit = "";

    public AmsInvStorageDTO() {
        super();

        this.storageId = new AdvancedNumber();
        this.invCode = new AdvancedNumber();
        this.organizationId = new AdvancedNumber();
        this.quantity = new AdvancedNumber();
    }

    /**
     * 功能：设置AMS_INV_STORAGE属性 STORAGE_ID
     * @param storageId AdvancedNumber
     */
    public void setStorageId(AdvancedNumber storageId) {
        this.storageId = storageId;
    }

    /**
     * 功能：设置AMS_INV_STORAGE属性 批次
     * @param batchNo String
     */
    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    /**
     * 功能：设置AMS_INV_STORAGE属性 仓库地点
     * @param invCode AdvancedNumber
     */
    public void setInvCode(AdvancedNumber invCode) {
        this.invCode = invCode;
    }

    /**
     * 功能：设置AMS_INV_STORAGE属性 OU
     * @param organizationId AdvancedNumber
     */
    public void setOrganizationId(AdvancedNumber organizationId) {
        this.organizationId = organizationId;
    }

    /**
     * 功能：设置AMS_INV_STORAGE属性 设备代码
     * @param itemCode String
     */
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    /**
     * 功能：设置AMS_INV_STORAGE属性 数量
     * @param quantity AdvancedNumber
     */
    public void setQuantity(AdvancedNumber quantity) {
        this.quantity = quantity;
    }

    /**
     * 功能：设置AMS_INV_STORAGE属性 ITEM_NAME
     * @param itemName String
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * 功能：设置AMS_INV_STORAGE属性 ITEM_SPEC
     * @param itemSpec String
     */
    public void setItemSpec(String itemSpec) {
        this.itemSpec = itemSpec;
    }

    /**
     * 功能：设置AMS_INV_STORAGE属性 ITEM_UNIT
     * @param itemUnit String
     */
    public void setItemUnit(String itemUnit) {
        this.itemUnit = itemUnit;
    }


    /**
     * 功能：获取AMS_INV_STORAGE属性 STORAGE_ID
     * @return AdvancedNumber
     */
    public AdvancedNumber getStorageId() {
        return this.storageId;
    }

    /**
     * 功能：获取AMS_INV_STORAGE属性 批次
     * @return String
     */
    public String getBatchNo() {
        return this.batchNo;
    }

    /**
     * 功能：获取AMS_INV_STORAGE属性 仓库地点
     * @return AdvancedNumber
     */
    public AdvancedNumber getInvCode() {
        return this.invCode;
    }

    /**
     * 功能：获取AMS_INV_STORAGE属性 OU
     * @return AdvancedNumber
     */
    public AdvancedNumber getOrganizationId() {
        return this.organizationId;
    }

    /**
     * 功能：获取AMS_INV_STORAGE属性 设备代码
     * @return String
     */
    public String getItemCode() {
        return this.itemCode;
    }

    /**
     * 功能：获取AMS_INV_STORAGE属性 数量
     * @return AdvancedNumber
     */
    public AdvancedNumber getQuantity() {
        return this.quantity;
    }

    /**
     * 功能：获取AMS_INV_STORAGE属性 ITEM_NAME
     * @return String
     */
    public String getItemName() {
        return this.itemName;
    }

    /**
     * 功能：获取AMS_INV_STORAGE属性 ITEM_SPEC
     * @return String
     */
    public String getItemSpec() {
        return this.itemSpec;
    }

    /**
     * 功能：获取AMS_INV_STORAGE属性 ITEM_UNIT
     * @return String
     */
    public String getItemUnit() {
        return this.itemUnit;
    }

}