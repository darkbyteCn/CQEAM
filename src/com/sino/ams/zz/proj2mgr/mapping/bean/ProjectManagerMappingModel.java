package com.sino.ams.zz.proj2mgr.mapping.bean;

import java.util.List;
import java.util.ArrayList;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.sql.BaseSQLProducer;

public class ProjectManagerMappingModel extends BaseSQLProducer {

	private ProjectManagerMappingDTO dto = null;
	private SfUserDTO sfUser = null;
	
	public ProjectManagerMappingModel(SfUserDTO userAccount, DTO dtoParameter) {
		super(userAccount, dtoParameter);
		sfUser = userAccount;
		this.dto = (ProjectManagerMappingDTO) dtoParameter;
	}

	public SQLModel getProjectManagerMappingModel() {
		SQLModel model = new SQLModel();
		List strArg = new ArrayList();
		String strSql = "SELECT A.SEGMENT1, A.NAME, A.PROJECT_STATUS_CODE, C.USERNAME FROM ETS_PA_PROJECTS_ALL A " +
				"INNER JOIN SF_PROJECT_MANAGER_MAPPING B " +
				"ON A.SEGMENT1=B.PROJECT_ID " +
				"LEFT JOIN SF_USER C " +
				"ON B.USER_ID=C.USER_ID";		
		model.setArgs(strArg);
		model.setSqlStr(strSql);
		return model;
	}
	
	public SQLModel getPageQueryModel(){
		return this.getProjectManagerMappingModel();
	}
}
