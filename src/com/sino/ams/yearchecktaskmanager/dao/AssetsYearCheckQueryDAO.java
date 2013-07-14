package com.sino.ams.yearchecktaskmanager.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.sino.ams.appbase.dao.AMSProcedureBaseDAO;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.yearchecktaskmanager.dto.AssetsYearCheckTaskHeaderDTO;
import com.sino.ams.yearchecktaskmanager.dto.AssetsYearCheckTaskLineDTO;
import com.sino.ams.yearchecktaskmanager.dto.AssetsYearCheckTaskQueryDTO;
import com.sino.ams.yearchecktaskmanager.model.AssetsYearCheckQueryModel;
import com.sino.ams.yearchecktaskmanager.model.AssetsYearCheckTaskCityModel;
import com.sino.ams.yearchecktaskmanager.util.AssetsCheckTaskConstant;
import com.sino.ams.yearchecktaskmanager.util.AssetsCheckTaskOrderGeneretor;
import com.sino.ams.yearchecktaskmanager.util.CommonUtil;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DataHandleException;
import com.sino.base.log.Logger;
import com.sino.framework.dto.BaseUserDTO;

public class AssetsYearCheckQueryDAO extends AMSProcedureBaseDAO{
	
	private AssetsYearCheckQueryModel myModel;
	
	public AssetsYearCheckQueryDAO(SfUserDTO userAccount, AssetsYearCheckTaskQueryDTO dtoParameter,
			Connection conn) {
		super(userAccount, dtoParameter, conn);
		myModel = new  AssetsYearCheckQueryModel((SfUserDTO) userAccount,dtoParameter);
	}

	protected void initSQLProducer(BaseUserDTO arg0, DTO arg1) {
		AssetsYearCheckTaskQueryDTO dtoPara = (AssetsYearCheckTaskQueryDTO) dtoParameter;
		sqlProducer = new AssetsYearCheckQueryModel((SfUserDTO) userAccount,dtoPara);
	}
	
	
}
