package com.sino.ams.newasset.dto;

import com.sino.ams.bean.SyBaseSQLUtil;


/**
 * <p>Title: 固定资产变更表(EAM) AmsFaChgHistory</p>
 * <p>Description: 程序自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class AmsFaChgHistoryDTO extends AmsAssetsTransLineDTO {

    private String chgLogId = "";
    private int fromOrganizationId;
    private int toOrganizationId;
    private int toDept;
    private String fromPerson = "";
    private String toPerson = "";
    private String fromStatus = "";
    private String toStatus = "";
    private int createdBy = SyBaseSQLUtil.NULL_INT_VALUE;
    private String fromDept = "";

    public AmsFaChgHistoryDTO() {
        super();
    }

    /**
     * 功能：设置固定资产变更表(EAM)属性 变更日志ID
     * @param chgLogId String
     */
    public void setChgLogId(String chgLogId) {
        this.chgLogId = chgLogId;
    }

    /**
     * 功能：设置固定资产变更表(EAM)属性 变更前OU组织ID
     * @param fromOrganizationId String
     */
    public void setFromOrganizationId(int fromOrganizationId) {
        this.fromOrganizationId = fromOrganizationId;
    }

    /**
     * 功能：设置固定资产变更表(EAM)属性 变更后OU组织ID
     * @param toOrganizationId String
     */
    public void setToOrganizationId(int toOrganizationId) {
        this.toOrganizationId = toOrganizationId;
    }

    /**
     * 功能：设置固定资产变更表(EAM)属性 变更后部门
     * @param toDept String
     */
    public void setToDept(int toDept) {
        this.toDept = toDept;
    }

    /**
     * 功能：设置固定资产变更表(EAM)属性 变更前保管人
     * @param fromPerson String
     */
    public void setFromPerson(String fromPerson) {
        this.fromPerson = fromPerson;
    }

    /**
     * 功能：设置固定资产变更表(EAM)属性 变更后保管人
     * @param toPerson String
     */
    public void setToPerson(String toPerson) {
        this.toPerson = toPerson;
    }

    /**
     * 功能：设置固定资产变更表(EAM)属性 变更前状态
     * @param fromStatus String
     */
    public void setFromStatus(String fromStatus) {
        this.fromStatus = fromStatus;
    }

    /**
     * 功能：设置固定资产变更表(EAM)属性 变更后状态
     * @param toStatus String
     */
    public void setToStatus(String toStatus) {
        this.toStatus = toStatus;
    }

    /**
     * 功能：设置固定资产变更表(EAM)属性 创建人
     * @param createdBy String
     */
    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public void setFromDept(String fromDept) {
        this.fromDept = fromDept;
    }

    /**
     * 功能：获取固定资产变更表(EAM)属性 变更日志ID
     * @return String
     */
    public String getChgLogId() {
        return this.chgLogId;
    }

    /**
     * 功能：获取固定资产变更表(EAM)属性 变更前OU组织ID
     * @return String
     */
    public int getFromOrganizationId() {
		return fromOrganizationId;
	}    

    /**
     * 功能：获取固定资产变更表(EAM)属性 变更后OU组织ID
     * @return String
     */
    public int getToOrganizationId() {
        return this.toOrganizationId;
    }


    /**
     * 功能：获取固定资产变更表(EAM)属性 变更后部门
     * @return String
     */
    public int getToDept() {
        return this.toDept;
    }


    /**
     * 功能：获取固定资产变更表(EAM)属性 变更前保管人
     * @return String
     */
    public String getFromPerson() {
        return this.fromPerson;
    }

    /**
     * 功能：获取固定资产变更表(EAM)属性 变更后保管人
     * @return String
     */
    public String getToPerson() {
        return this.toPerson;
    }

    /**
     * 功能：获取固定资产变更表(EAM)属性 变更前状态
     * @return String
     */
    public String getFromStatus() {
        return this.fromStatus;
    }

    /**
     * 功能：获取固定资产变更表(EAM)属性 变更后状态
     * @return String
     */
    public String getToStatus() {
        return this.toStatus;
    }

    /**
     * 功能：获取固定资产变更表(EAM)属性 创建人
     * @return String
     */
    public int getCreatedBy() {
        return this.createdBy;
    }

    public String getFromDept() {
        return fromDept;
    }
}
