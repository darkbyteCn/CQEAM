package com.sino.ams.system.misuser.dto;

import com.sino.ams.bean.CommonRecordDTO;

/**
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2009-1-6
 * Time: 11:36:01
 * To change this template use File | Settings | File Templates.
 */
public class EamMisUserDTO extends CommonRecordDTO {
    private String userName="";
    private String employeeNumber="";
    private String deptName="";
    private int organizationId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }
}
