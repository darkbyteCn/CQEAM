package com.sino.ams.dzyh.dto;

import com.sino.ams.bean.CommonRecordDTO;
import com.sino.ams.bean.SyBaseSQLUtil;

/**
* <p>Title: 权限定义表(EAM) EamDhPrivi</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 张星
* @version 1.0
*/

public class EamDhPriviDTO extends CommonRecordDTO{

	private String priviId = "";
	private int userId = SyBaseSQLUtil.NULL_INT_VALUE;
	private String deptCode = "";
	private int orgId = SyBaseSQLUtil.NULL_INT_VALUE ;
	private int enabled = 1;
	private String defaultflag = "N";

    private String srcPage = "";
    private String companyName = "";
    private String deptName = "";
    private String userName = "";
    private String loginName = "";
    private String employeeNumber = "";
    private String groupId = "";

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getSrcPage() {
		return srcPage;
	}

	public void setSrcPage(String srcPage) {
		this.srcPage = srcPage;
	}

	/**
	 * 功能：设置权限定义表(EAM)属性 EAM_DH_PRIVI_S.NEXTVAL
	 * @param priviId String
	 */
	public void setPriviId(String priviId){
		this.priviId = priviId;
	}

	/**
	 * 功能：设置权限定义表(EAM)属性 用户ID
	 * @param userId String
	 */
	public void setUserId(int userId){
		this.userId = userId;
	}

	/**
	 * 功能：设置权限定义表(EAM)属性 部门代码AMS_MIS_DEPT
	 * @param deptCode String
	 */
	public void setDeptCode(String deptCode){
		this.deptCode = deptCode;
	}

	/**
	 * 功能：设置权限定义表(EAM)属性 ETS_ORGANIZATION_DEF. ORG_ID
	 * @param orgId String
	 */
	public void setOrgId(int orgId){
		this.orgId = orgId;
	}

	/**
	 * 功能：设置权限定义表(EAM)属性 是否生效
	 * @param enabled String
	 */
	public void setEnabled(int enabled){
		this.enabled = enabled;
	}

	/**
	 * 功能：设置权限定义表(EAM)属性 是否默认低值易耗管理员
	 * @param defaultflag String
	 */
	public void setDefaultflag(String defaultflag){
		this.defaultflag = defaultflag;
	}


	/**
	 * 功能：获取权限定义表(EAM)属性 EAM_DH_PRIVI_S.NEXTVAL
	 * @return String
	 */
	public String getPriviId() {
		return this.priviId;
	}

	/**
	 * 功能：获取权限定义表(EAM)属性 用户ID
	 * @return String
	 */
	public int getUserId() {
		return this.userId;
	}

	/**
	 * 功能：获取权限定义表(EAM)属性 部门代码AMS_MIS_DEPT
	 * @return String
	 */
	public String getDeptCode() {
		return this.deptCode;
	}

	/**
	 * 功能：获取权限定义表(EAM)属性 ETS_ORGANIZATION_DEF. ORG_ID
	 * @return String
	 */
	public int getOrgId() {
		return this.orgId;
	}

	/**
	 * 功能：获取权限定义表(EAM)属性 是否生效
	 * @return String
	 */
	public int getEnabled() {
		return this.enabled;
	}

	/**
	 * 功能：获取权限定义表(EAM)属性 是否默认低值易耗管理员
	 * @return String
	 */
	public String getDefaultflag() {
		return this.defaultflag;
	}

}