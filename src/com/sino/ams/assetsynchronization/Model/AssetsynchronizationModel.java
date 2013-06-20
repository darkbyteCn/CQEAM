package com.sino.ams.assetsynchronization.Model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.assetsynchronization.dto.AssetsynchronizationDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.dto.BaseUserDTO;

public class AssetsynchronizationModel extends AMSSQLProducer{

	public AssetsynchronizationModel(BaseUserDTO userAccount, DTO dtoParameter) {
		super(userAccount, dtoParameter);
		// TODO Auto-generated constructor stub
	}
	public SQLModel createModel(AssetsynchronizationDTO assetsynchronizationDTO)
	{
		SQLModel sqlModel=new SQLModel();
		List args=new ArrayList();
		StringBuilder sql = new StringBuilder();
		 sql.append("INSERT INTO dbo.ETS_MISFA_TRANSACTION_RESP( USER_ID,ORGANIZATION_ID, RESP_ID, RESP_APPL_ID, EMPLOYEE_NUMBER ,IS_DEFAULT,COMPANY) VALUES(?,?,?,?,?,?,?)");
		args.add(assetsynchronizationDTO.getUserId());
		args.add(assetsynchronizationDTO.getOrganizationId());
		args.add(assetsynchronizationDTO.getRespId());
		args.add(assetsynchronizationDTO.getRespApplId());
		args.add(assetsynchronizationDTO.getEmployeeNumber());
		args.add(assetsynchronizationDTO.getIsDefault());
		args.add(assetsynchronizationDTO.getCompany());
		sqlModel.setArgs(args);
		sqlModel.setSqlStr(sql.toString());
		return sqlModel;
	}
	public SQLModel deleteModel(AssetsynchronizationDTO assetsynchronizationDTO)
	{
		SQLModel sqlModel=new SQLModel();
		List args=new ArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM ETS_MISFA_TRANSACTION_RESP WHERE  EMPLOYEE_NUMBER = ?");
		args.add(assetsynchronizationDTO.getEmployeeNumber());
		sqlModel.setArgs(args);
		sqlModel.setSqlStr(sql.toString());
		return sqlModel;
	}
	@Override
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		AssetsynchronizationDTO assetsynchronizationDTO=(AssetsynchronizationDTO)dtoParameter;
		List sqlArgs = new ArrayList();
		String sql = "SELECT EMTR.USER_ID,EMTR.ORGANIZATION_ID,EMTR.RESP_ID,EMTR.RESP_APPL_ID,EMTR.EMPLOYEE_NUMBER,EMTR.IS_DEFAULT,EOCM.COMPANY FROM ETS_MISFA_TRANSACTION_RESP EMTR,ETS_OU_CITY_MAP EOCM WHERE EOCM.ORGANIZATION_ID = EMTR.ORGANIZATION_ID AND (? = -1 or EMTR.ORGANIZATION_ID = ?)";
		sqlArgs.add(assetsynchronizationDTO.getOrganizationId());
		sqlArgs.add(assetsynchronizationDTO.getOrganizationId());
		if(!assetsynchronizationDTO.getEmployeeNumber().equals("")){
			sql += " AND EMTR.EMPLOYEE_NUMBER = ?";
			sqlArgs.add(assetsynchronizationDTO.getEmployeeNumber());
		}
		sqlModel.setArgs(sqlArgs);
		sqlModel.setSqlStr(sql);
		return sqlModel;
	}
	
//	public SQLModel queryModel(){
//		SQLModel sqlModel = new SQLModel();
//		AssetsynchronizationDTO assetsynchronizationDTO=(AssetsynchronizationDTO)dtoParameter;
//		String sql = "SELECT EMPLOYEE_NUMBER FROM ETS_MISFA_TRANSACTION_RESP ";
//		sqlModel.setSqlStr(sql);
//		return sqlModel;
//	}
	
	public SQLModel updateModel(AssetsynchronizationDTO assetsynchronizationDTO){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sql = "UPDATE ETS_MISFA_TRANSACTION_RESP"
			+ " SET"
			+ " USER_ID = ?,"
			+ " RESP_ID = ?,"
			+ " RESP_APPL_ID = ?,"
			+ " IS_DEFAULT = ?"
			+ " WHERE"
			+ " EMPLOYEE_NUMBER = ?";
		sqlArgs.add(assetsynchronizationDTO.getUserId());
		sqlArgs.add(assetsynchronizationDTO.getRespId());
		sqlArgs.add(assetsynchronizationDTO.getRespApplId());
		sqlArgs.add(assetsynchronizationDTO.getIsDefault());
		sqlArgs.add(assetsynchronizationDTO.getEmployeeNumber());
		sqlModel.setArgs(sqlArgs);
		sqlModel.setSqlStr(sql);
		return sqlModel;
	}
	
//	public SQLModel findDate(int organizationId)
//	{
//		SQLModel sqlModel = new SQLModel();
//		String sql = "SELECT * FROM ETS_OU_CITY_MAP WHERE ORGANIZATION_ID = "+organizationId+"";
//		sqlModel.setSqlStr(sql);		
//		return sqlModel;
//	}


}
