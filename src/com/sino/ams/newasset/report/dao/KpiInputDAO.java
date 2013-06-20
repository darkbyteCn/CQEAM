package com.sino.ams.newasset.report.dao;

import java.sql.Connection;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.report.dto.KpiInputDTO;
import com.sino.ams.newasset.report.model.KpiInputModel;
import com.sino.ams.system.manydimensions.model.LneModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.QueryException;
import com.sino.framework.dto.BaseUserDTO;

public class KpiInputDAO extends AMSBaseDAO{
	private KpiInputModel kpiInputModel = null;
	public KpiInputDAO(BaseUserDTO userAccount, DTO dtoParameter,
			Connection conn) {
		super(userAccount, dtoParameter, conn);
		kpiInputModel = new KpiInputModel((SfUserDTO) userAccount, dtoParameter);
		this.initSQLProducer(userAccount, dtoParameter); 

	}

	@Override
	protected void initSQLProducer(BaseUserDTO arg0, DTO arg1) {
		SfUserDTO user = (SfUserDTO)userAccount;
		KpiInputDTO dto = (KpiInputDTO) dtoParameter;
		sqlProducer = new KpiInputModel(user, dto);
	}
	
	/**
	 * 功能:检查是否被使用
	 * @return boolean
	 */
	public boolean validity(){
		boolean exist = false;
		try {
			SQLModel sqlModel = kpiInputModel.isValidity();
			SimpleQuery simQuery = new SimpleQuery(sqlModel, conn);
			simQuery.executeQuery();
			exist = simQuery.hasResult();
		} catch (QueryException ex) {
			ex.printLog();
		}
		return exist;
	}

}
