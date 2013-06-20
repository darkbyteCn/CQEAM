package com.sino.ams.newasset.dto;

import com.sino.base.dto.CheckBoxDTO;

/**
 * <p>Title: 固定资产调拨启用配置 AmsAssetsTransConfig</p>
 * <p>Description: 程序自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class AmsAssetsTransConfigDTO extends CheckBoxDTO {

    private int organizationId = -1;
    private String transferType = "";
    private String faContentCode = "";
    private String faContentName = "";
    private String enabled = "";

    /**
     * 功能：设置固定资产调拨启用配置属性 OU组织ID
     * @param organizationId String
     */
    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    /**
     * 功能：设置固定资产调拨启用配置属性 调拨类型：部门内调拨，部门间调拨，地市间调拨
     * @param transferType String
     */
    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    /**
     * 功能：设置固定资产调拨启用配置属性 资产大类字典代码
     * @param faContentCode String
     */
    public void setFaContentCode(String faContentCode) {
        this.faContentCode = faContentCode;
    }

    /**
     * 功能：设置固定资产调拨启用配置属性 是否有效
     * @param enabled String
     */
    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public void setFaContentName(String faContentName) {
        this.faContentName = faContentName;
    }

    /**
     * 功能：获取固定资产调拨启用配置属性 OU组织ID
     * @return String
     */
    public int getOrganizationId() {
        return this.organizationId;
    }

    /**
     * 功能：获取固定资产调拨启用配置属性 调拨类型：部门内调拨，部门间调拨，地市间调拨
     * @return String
     */
    public String getTransferType() {
        return this.transferType;
    }

    /**
     * 功能：获取固定资产调拨启用配置属性 资产大类字典代码
     * @return String
     */
    public String getFaContentCode() {
        return this.faContentCode;
    }

    /**
     * 功能：获取固定资产调拨启用配置属性 是否有效
     * @return String
     */
    public String getEnabled() {
        return this.enabled;
    }

    public String getFaContentName() {
        return faContentName;
    }

}
