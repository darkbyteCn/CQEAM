package com.sino.soa.mis.srv.ouorganization.dto;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.base.dto.CheckBoxDTO;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-9-8
 * Time: 16:05:40
 * To change this template use File | Settings | File Templates.
 */
public class SBFIGLOuOrganizationDTO extends CheckBoxDTO {

    private int orgId = SyBaseSQLUtil.NULL_INT_VALUE;;
    private String orgName = "";
    private String setOfBooksName = "";
    private String setOfBooksId = "";
    private String attribute1 = "";
    private String enableFlag = "";

    public SBFIGLOuOrganizationDTO() {
        super();
    }

    /**
     * 功能：设置OU组织信息服务属性 组织ID
     * @param orgId String
     */
    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    /**
     * 功能：设置OU组织信息服务属性 OU名称
     * @param orgName String
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    /**
     * 功能：设置OU组织信息服务属性 账套名称
     * @param setOfBooksName String
     */
    public void setSetOfBooksName(String setOfBooksName) {
        this.setOfBooksName = setOfBooksName;
    }

    /**
     * 功能：设置OU组织信息服务属性 账套标识
     * @param setOfBooksId String
     */
    public void setSetOfBooksId(String setOfBooksId) {
        this.setOfBooksId = setOfBooksId;
    }

    /**
     * 功能：设置OU组织信息服务属性 公司编码
     * @param attribute1 String
     */
    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1;
    }

    /**
     * 功能：设置OU组织信息服务属性 是否有效
     * @param enableFlag String
     */
    public void setEnableFlag(String enableFlag) {
        this.enableFlag = enableFlag;
    }


    /**
     * 功能：获取OU组织信息服务属性 组织ID
     * @return String
     */
    public int getOrgId() {
        return this.orgId;
    }

    /**
     * 功能：获取OU组织信息服务属性 OU名称
     * @return String
     */
    public String getOrgName() {
        return this.orgName;
    }

    /**
     * 功能：获取OU组织信息服务属性 账套名称
     * @return String
     */
    public String getSetOfBooksName() {
        return this.setOfBooksName;
    }

    /**
     * 功能：获取OU组织信息服务属性 账套标识
     * @return String
     */
    public String getSetOfBooksId() {
        return this.setOfBooksId;
    }

    /**
     * 功能：获取OU组织信息服务属性 公司编码
     * @return String
     */
    public String getAttribute1() {
        return this.attribute1;
    }

    /**
     * 功能：获取OU组织信息服务属性 是否有效
     * @return String
     */
    public String getEnableFlag() {
        return this.enableFlag;
    }

}