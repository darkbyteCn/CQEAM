package com.sino.ams.assetsynchronization.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.assetsynchronization.Model.AssetsynchronizationModel;
import com.sino.ams.assetsynchronization.dto.AssetsynchronizationDTO;
import com.sino.ams.dzyh.dto.EamDhCheckLineDTO;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;

public class AssetsynchronizationCreateDao extends BaseDAO{
	SfUserDTO user=null;
	AssetsynchronizationDTO dto=null;
	AssetsynchronizationModel model=new AssetsynchronizationModel(user, dto);
	Connection conn=null;
	public AssetsynchronizationCreateDao(BaseUserDTO userAccount, AssetsynchronizationDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		this.user=(SfUserDTO)userAccount;
		this.dto=(AssetsynchronizationDTO)dtoParameter;
		this.conn=conn;
	}

	@Override
	protected void initSQLProducer(BaseUserDTO arg0, DTO arg1) {
		// TODO Auto-generated method stub
		
	}
	public void AssetsynchronizationCreateDao(AssetsynchronizationDTO assetsynchronizationDTO) throws DataHandleException {
		SQLModel sqlModel=model.createModel(assetsynchronizationDTO);
		DBOperator.updateRecord(sqlModel, conn);
		
	}

	public void deleteData(AssetsynchronizationDTO dtoParameter) throws DataHandleException {
		SQLModel sqlModel=model.deleteModel(dtoParameter);
		DBOperator.updateRecord(sqlModel, conn);
	}
	
	public void updateData(AssetsynchronizationDTO dtoParameter) throws DataHandleException {
		SQLModel sqlModel=model.updateModel(dtoParameter);
		DBOperator.updateRecord(sqlModel, conn);
	}
	
//	public DTOSet query() throws QueryException {
//		SQLModel sqlModel=model.queryModel();
//		SimpleQuery simp = new SimpleQuery(sqlModel, conn);
//		simp.setSql(sqlModel);
//		simp.setDTOClassName(AssetsynchronizationDTO.class.getName());
//		simp.executeQuery();
//		return simp.getDTOSet();
//	}

}
