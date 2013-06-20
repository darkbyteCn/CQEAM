package com.sino.ams.newSite.dto;

import com.sino.ams.appbase.dto.AMSFlowDTO;
import com.sino.ams.bean.CommonRecordDTO;

/**
 * @author 作者 :wangzhipeng
 * @version 创建时间：Apr 12, 2011 10:20:36 AM
 *          类说明：新增地点流程表单  头信息
 */
public class EamAddressAddHDTO extends AMSFlowDTO {

    private String transId = "";             //单据序号   8
    private String transNo = "";             //单据编号   8
    private String transStatus = "";         //单据状态
    private int organizationId;         //公司ID    8
    private String dept = "";                //部门        #
    private String createdReason = "";         //单据创建原因

    private String organizationName = "";       //公司名称       8
    private String deptName = "";               //部门名称
    private String createdByName = "";         //创建人名

    private String company = "";                //公司名称111
    private String username = "";
    private String deptCode = "";
    private String excel="";
    
    private String emergentLevel = ""; //紧急程度
    private String emergentLevelOption = ""; //紧急成度下拉框  
    
    private String addrMaintainTypeOption = ""; //维护类型下拉框

    public String getAddrMaintainTypeOption() {
		return addrMaintainTypeOption;
	}

	public void setAddrMaintainTypeOption(String addrMaintainTypeOption) {
		this.addrMaintainTypeOption = addrMaintainTypeOption;
	}

	public EamAddressAddHDTO() {
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    public String getTransStatus() {
        return transStatus;
    }

    public void setTransStatus(String transStatus) {
        this.transStatus = transStatus;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getCreatedReason() {
        return createdReason;
    }

    public void setCreatedReason(String createdReason) {
        this.createdReason = createdReason;
    }


    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }


    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getExcel() {
        return excel;
    }

    public void setExcel(String excel) {
        this.excel = excel;
    }

	public String getEmergentLevel() {
		return emergentLevel;
	}

	public void setEmergentLevel(String emergentLevel) {
		this.emergentLevel = emergentLevel;
	}

	public String getEmergentLevelOption() {
		return emergentLevelOption;
	}

	public void setEmergentLevelOption(String emergentLevelOption) {
		this.emergentLevelOption = emergentLevelOption;
	}
}
