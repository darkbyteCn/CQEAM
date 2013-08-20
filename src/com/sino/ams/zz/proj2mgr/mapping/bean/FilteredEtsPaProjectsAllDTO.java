package com.sino.ams.zz.proj2mgr.mapping.bean;

import com.sino.ams.system.project.dto.EtsPaProjectsAllDTO;

public class FilteredEtsPaProjectsAllDTO extends EtsPaProjectsAllDTO {

	private int userId;
	
	public int getUserId() {
		return this.userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
}
