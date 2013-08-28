package com.sino.ams.zz.proj2mgr.mapping.bean;

import com.sino.framework.dto.BaseLocaleDTO;

public class ProjectManagerMappingDTO extends BaseLocaleDTO {

	private static final long serialVersionUID = -150957621042643542L;
	private int userId = -1;
	private String projectId = "";
	private String projectDesc = "";
	private String projectStatus = "";
	
	public String getProjectDesc() {
		return this.projectDesc;
	}
	
	public void setProjectDesc(String desc) {
		this.projectDesc = desc;
	}
	
	public String getProjectStatus() {
		return this.projectStatus;
	}
	
	public void setProjectStatus(String status) {
		this.projectStatus = status;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getProjectId() {
		return this.projectId;
	}
	
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
}
