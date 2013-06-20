package com.sino.ams.expand.dto;


/**
* <p>Title: 资产拓展信息表－ETS_ITEM_INFO_EX EtsItemInfoEx</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class EtsItemInfoExQueRenDTO extends EtsItemInfoExDTO{


	private String systemid="";	//
	
	private String responsibilityDept="";	//责任部门
	
	private String deptName="";	//责任部门
	
	private String financeProp="";	//财务属性
	
	private String financePropName="";	//财务属性
	
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getFinanceProp() {
		return financeProp;
	}
	public void setFinanceProp(String financeProp) {
		this.financeProp = financeProp;
	}
	public String getResponsibilityDept() {
		return responsibilityDept;
	}
	public void setResponsibilityDept(String responsibilityDept) {
		this.responsibilityDept = responsibilityDept;
	}
	public String getFinancePropName() {
		return financePropName;
	}
	public void setFinancePropName(String financePropName) {
		this.financePropName = financePropName;
	}
	public String getSystemid() {
		return systemid;
	}
	public void setSystemid(String systemid) {
		this.systemid = systemid;
	}
}