package com.sino.ams.newasset.dto;

/**
 * <p>Title: MIS员工表 AmsMisEmployee</p>
 * <p>Description: 程序自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class AmsMisEmployeeDTO extends AmsMisDeptDTO {

    private String employeeId = "";
    private String userName = "";
    private String employeeNumber = "";
    private int hrDeptId;
    private String hrDeptName = "";
    private String loginName = "";

    private String transferType = "";

    public AmsMisEmployeeDTO() {
        super();
    }

    /**
     * 功能：设置MIS员工表属性 员工ID
     * @param employeeId String
     */
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * 功能：设置MIS员工表属性 员工姓名
     * @param userName String
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 功能：设置MIS员工表属性 员工编号
     * @param employeeNumber String
     */
    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    /**
     * 功能：设置MIS员工表属性 所属部门编号
     * @param hrDeptId String
     */
    public void setHrDeptId(int hrDeptId) {
        this.hrDeptId = hrDeptId;
    }

    /**
     * 功能：设置MIS员工表属性 所属部门名称
     * @param hrDeptName String
     */
    public void setHrDeptName(String hrDeptName) {
        this.hrDeptName = hrDeptName;
    }

    /**
     * 功能：设置MIS员工表属性 MIS登录名
     * @param loginName String
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    /**
     * 功能：获取MIS员工表属性 员工ID
     * @return String
     */
    public String getEmployeeId() {
        return this.employeeId;
    }

    /**
     * 功能：获取MIS员工表属性 员工姓名
     * @return String
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * 功能：获取MIS员工表属性 员工编号
     * @return String
     */
    public String getEmployeeNumber() {
        return this.employeeNumber;
    }

    /**
     * 功能：获取MIS员工表属性 所属部门编号
     * @return String
     */
    public int getHrDeptId() {
        return this.hrDeptId;
    }

    /**
     * 功能：获取MIS员工表属性 所属部门名称
     * @return String
     */
    public String getHrDeptName() {
        return this.hrDeptName;
    }

    /**
     * 功能：获取MIS员工表属性 MIS登录名
     * @return String
     */
    public String getLoginName() {
        return this.loginName;
    }

    public String getTransferType() {
        return transferType;
    }

}
