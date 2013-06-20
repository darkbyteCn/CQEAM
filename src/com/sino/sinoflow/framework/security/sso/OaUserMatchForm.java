package com.sino.sinoflow.framework.security.sso;

import com.sino.base.dto.CheckBoxDTO;

public class OaUserMatchForm extends CheckBoxDTO{
	private String id = "";
	private String oaLoginName = "";
	private String cmsLoginName = "";
	private String roleDesc = "";
   
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOaLoginName() {
		return oaLoginName;
	}
	public void setOaLoginName(String oaLoginName) {
		this.oaLoginName = oaLoginName;
	}
	public String getCmsLoginName() {
		return cmsLoginName;
	}
	public void setCmsLoginName(String cmsLoginName) {
		this.cmsLoginName = cmsLoginName;
	}
	public String getRoleDesc() {
		return roleDesc;
	}
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
}
