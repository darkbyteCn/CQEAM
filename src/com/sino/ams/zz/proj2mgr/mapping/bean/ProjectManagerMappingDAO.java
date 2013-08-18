package com.sino.ams.zz.proj2mgr.mapping.bean;

import java.sql.Connection;
import java.util.List;

import com.sino.base.dto.DTO;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;

public class ProjectManagerMappingDAO extends BaseDAO {
	
	public ProjectManagerMappingDAO(BaseUserDTO userAccount,
			DTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	@Override
	protected void initSQLProducer(BaseUserDTO arg0, DTO dtoParamter) {
		ProjectManagerMappingDTO pmmDto = (ProjectManagerMappingDTO)dtoParameter;
	}

	
}
