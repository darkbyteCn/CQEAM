package com.sino.ams.system.assetcatelogMaintenanceLNE.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.system.assetcatelogMaintenanceLNE.dto.AssetcatelogMLneDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.dto.BaseUserDTO;

public class AssetcatelogMlneModel extends AMSSQLProducer{
	private AssetcatelogMLneDTO dto = null;

	public AssetcatelogMlneModel(BaseUserDTO userAccount,AssetcatelogMLneDTO dtoParameter) {
		super(userAccount, dtoParameter);
		this.dto =  dtoParameter;
	}
	
	/**
	 * 功能：构造翻页查询SQL
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		dto = (AssetcatelogMLneDTO) dtoParameter;
		List sqlArgs = new ArrayList(); 
		String sb = "SELECT"
			+ " ANCE.CONTENT_CODE,"
			+ " ANCE.CONTENT_NAME,"
			+ " ANCE.NLE_NAME,"
			+ " ANCE.NLE_CODE,"
			+ " ANCE.MATCH_TYPE,"
			+ " ANCE.MATCH_CODE,"
			+ " ANCE.MATCH_DESC,"
			+ " ANCE.ID"
			+ " FROM"
			+ " AMS_NLE_CONTENT_EX    ANCE" 
			+ " WHERE 1=1";
		 	
		sqlModel.setSqlStr(sb);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	/**
	 * 功能：	通过框架自动根据SQLModel, 删除。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "DELETE "
			+ " AMS_NLE_CONTENT_EX "
			+ " WHERE"
			+ " CONTENT_CODE = '"+dto.getContentCode()+"'"
			+ " AND MATCH_CODE = '"+dto.getMatchCode()+"'";
		sqlModel.setSqlStr(sqlStr);
		return sqlModel;		
	}
	/**
	 * 功能：框架自动生成资产地点表(EAM) ETS_OBJECT数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 */
	public SQLModel getDataCreateModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		dto = (AssetcatelogMLneDTO) dtoParameter;
		String sqlStr = "INSERT INTO "
						+ " AMS_NLE_CONTENT_EX("
						+ " CONTENT_CODE,\n"
						+ " CONTENT_NAME,\n"
						+ " MATCH_TYPE,\n"
						+ " NLE_NAME,\n"
						+ " NLE_CODE,\n"
						+ " MATCH_CODE,\n"
						+ " MATCH_DESC \n"
						+ ") VALUES ("
						+ " ?, ?, ?, ?, ?, ?, ? )";
		sqlArgs.add(dto.getContentCode());
		sqlArgs.add(dto.getContentName());
		sqlArgs.add(dto.getMatchType());
		sqlArgs.add(dto.getLneName());
		sqlArgs.add(dto.getLneCode());
		if(!dto.getNleCode().equals("")){
			sqlArgs.add(dto.getNleCode());
			sqlArgs.add(dto.getNleName());
		}
		if(!dto.getNaCode().equals("")){
			sqlArgs.add(dto.getNaCode());
			sqlArgs.add(dto.getNaCode());
		}
		if(!dto.getMatchCode().equals("")){
			sqlArgs.add(dto.getMatchCode());
			sqlArgs.add(dto.getMatchDesc());
		}
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
