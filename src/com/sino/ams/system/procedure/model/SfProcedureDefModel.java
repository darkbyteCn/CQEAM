package com.sino.ams.system.procedure.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.procedure.dto.SfProcedureDefDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: SfProcedureDefModel</p>
 * <p>Description:程序自动生成SQL构造器“SfProcedureDefModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author V-yuanshuai
 * @version 1.0
 */


public class SfProcedureDefModel extends BaseSQLProducer {

	private SfUserDTO sfUser = null;

	/**
	 * 功能：流转过程定义 SF_PROCEDURE_DEF 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter SfProcedureDefDTO 本次操作的数据
	 */
	public SfProcedureDefModel(SfUserDTO userAccount, SfProcedureDefDTO dtoParameter) {
		super(userAccount, dtoParameter);
		sfUser = userAccount;
	}
	/**
	 * 功能：框架自动生成流转过程定义 SF_PROCEDURE_DEF数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 */
	public SQLModel getDataCreateModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfProcedureDefDTO sfProcedureDef = (SfProcedureDefDTO)dtoParameter;
		String sqlStr = "INSERT INTO "
			+ " SF_PROCEDURE_DEF("
			+ " PROC_ID,"
			+ " PROC_NAME,"
			+ " DESCRIPTION,"
			+ " APP_TABLE_NAME,"
			+ " APP_PK_NAME,"
			+ " ORGANIZATION_ID,"
			+ " DISABLED,"
			+ " APPROVE_PATH,"
			+ " EDIT_PATH,"
			+ " QUERY_PATH,"
			+ " CANCEL_PATH"
			+ ") VALUES ("
			+ "  SF_PROCEDURE_DEF , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		sqlArgs.add(sfProcedureDef.getProcName());
		sqlArgs.add(sfProcedureDef.getDescription());
		sqlArgs.add(sfProcedureDef.getAppTableName());
		sqlArgs.add(sfProcedureDef.getAppPkName());
		sqlArgs.add(sfProcedureDef.getOrganizationId());
		sqlArgs.add(sfProcedureDef.getDisabled());
		sqlArgs.add(sfProcedureDef.getApprovePath());
		sqlArgs.add(sfProcedureDef.getEditPath());
		sqlArgs.add(sfProcedureDef.getQueryPath());
		sqlArgs.add(sfProcedureDef.getCancelPath());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成流转过程定义 SF_PROCEDURE_DEF数据更新SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据更新用SQLModel
	 */
	public SQLModel getDataUpdateModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfProcedureDefDTO sfProcedureDef = (SfProcedureDefDTO)dtoParameter;
		String sqlStr = "UPDATE SF_PROCEDURE_DEF"
			+ " SET"
			+ " PROC_NAME = ?,"
			+ " DESCRIPTION = ?,"
			+ " APP_TABLE_NAME = ?,"
			+ " APP_PK_NAME = ?,"
			+ " ORGANIZATION_ID = ?,"
			+ " DISABLED = ?,"
			+ " APPROVE_PATH = ?,"
			+ " EDIT_PATH = ?,"
			+ " QUERY_PATH = ?,"
			+ " CANCEL_PATH = ?"
			+ " WHERE"
			+ " PROC_ID = ?";
		
		sqlArgs.add(sfProcedureDef.getProcName());
		sqlArgs.add(sfProcedureDef.getDescription());
		sqlArgs.add(sfProcedureDef.getAppTableName());
		sqlArgs.add(sfProcedureDef.getAppPkName());
		sqlArgs.add(sfProcedureDef.getOrganizationId());
		sqlArgs.add(sfProcedureDef.getDisabled());
		sqlArgs.add(sfProcedureDef.getApprovePath());
		sqlArgs.add(sfProcedureDef.getEditPath());
		sqlArgs.add(sfProcedureDef.getQueryPath());
		sqlArgs.add(sfProcedureDef.getCancelPath());
		sqlArgs.add(sfProcedureDef.getProcId());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成流转过程定义 SF_PROCEDURE_DEF数据删除SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfProcedureDefDTO sfProcedureDef = (SfProcedureDefDTO)dtoParameter;
		String sqlStr = "DELETE FROM"
			+ " SF_PROCEDURE_DEF"
			+ " WHERE"
			+ " PROC_ID = ?";
		sqlArgs.add(sfProcedureDef.getProcId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成流转过程定义 SF_PROCEDURE_DEF数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfProcedureDefDTO sfProcedureDef = (SfProcedureDefDTO)dtoParameter;
		String sqlStr = "SELECT "
			+ " PROC_ID,"
			+ " PROC_NAME,"
			+ " DESCRIPTION,"
			+ " APP_TABLE_NAME,"
			+ " APP_PK_NAME,"
			+ " ORGANIZATION_ID,"
			+ " DISABLED,"
			+ " APPROVE_PATH,"
			+ " EDIT_PATH,"
			+ " QUERY_PATH,"
			+ " CANCEL_PATH"
			+ " FROM"
			+ " SF_PROCEDURE_DEF"
			+ " WHERE"
			+ " PROC_ID = ?";
		sqlArgs.add(sfProcedureDef.getProcId());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成流转过程定义 SF_PROCEDURE_DEF多条数据信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回多条数据信息查询用SQLModel
	 */
	public SQLModel getDataMuxModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfProcedureDefDTO sfProcedureDef = (SfProcedureDefDTO)dtoParameter;
		String sqlStr = "SELECT "
			+ " PROC_ID,"
			+ " PROC_NAME,"
			+ " DESCRIPTION,"
			+ " APP_TABLE_NAME,"
			+ " APP_PK_NAME,"
			+ " ORGANIZATION_ID,"
			+ " DISABLED,"
			+ " APPROVE_PATH,"
			+ " EDIT_PATH,"
			+ " QUERY_PATH,"
			+ " CANCEL_PATH"
			+ " FROM"
			+ " SF_PROCEDURE_DEF"
			+ " WHERE"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR PROC_ID LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR PROC_NAME LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR DESCRIPTION LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR APP_TABLE_NAME LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR APP_PK_NAME LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ORGANIZATION_ID LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR DISABLED LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR APPROVE_PATH LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR EDIT_PATH LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR QUERY_PATH LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR CANCEL_PATH LIKE ?)";
		sqlArgs.add(sfProcedureDef.getProcId());
		sqlArgs.add(sfProcedureDef.getProcId());
		sqlArgs.add(sfProcedureDef.getProcName());
		sqlArgs.add(sfProcedureDef.getProcName());
		sqlArgs.add(sfProcedureDef.getDescription());
		sqlArgs.add(sfProcedureDef.getDescription());
		sqlArgs.add(sfProcedureDef.getAppTableName());
		sqlArgs.add(sfProcedureDef.getAppTableName());
		sqlArgs.add(sfProcedureDef.getAppPkName());
		sqlArgs.add(sfProcedureDef.getAppPkName());
		sqlArgs.add(sfProcedureDef.getOrganizationId());
		sqlArgs.add(sfProcedureDef.getOrganizationId());
		sqlArgs.add(sfProcedureDef.getDisabled());
		sqlArgs.add(sfProcedureDef.getDisabled());
		sqlArgs.add(sfProcedureDef.getApprovePath());
		sqlArgs.add(sfProcedureDef.getApprovePath());
		sqlArgs.add(sfProcedureDef.getEditPath());
		sqlArgs.add(sfProcedureDef.getEditPath());
		sqlArgs.add(sfProcedureDef.getQueryPath());
		sqlArgs.add(sfProcedureDef.getQueryPath());
		sqlArgs.add(sfProcedureDef.getCancelPath());
		sqlArgs.add(sfProcedureDef.getCancelPath());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成流转过程定义 SF_PROCEDURE_DEF页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 */
	public SQLModel getPageQueryModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfProcedureDefDTO sfProcedureDef = (SfProcedureDefDTO)dtoParameter;
		String sqlStr = "SELECT "
			+ " PROC_ID,"
			+ " PROC_NAME,"
			+ " DESCRIPTION,"
			+ " APP_TABLE_NAME,"
			+ " APP_PK_NAME,"
			+ " ORGANIZATION_ID,"
			+ " DISABLED,"
			+ " APPROVE_PATH,"
			+ " EDIT_PATH,"
			+ " QUERY_PATH,"
			+ " CANCEL_PATH"
			+ " FROM"
			+ " SF_PROCEDURE_DEF"
			+ " WHERE"
			+ " ( " + SyBaseSQLUtil.isNull() + "  OR PROC_ID LIKE ?)"
			+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR PROC_NAME LIKE ?)"
			+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR DESCRIPTION LIKE ?)"
			+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR APP_TABLE_NAME LIKE ?)"
			+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR APP_PK_NAME LIKE ?)"
			+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR ORGANIZATION_ID LIKE ?)"
			+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR DISABLED LIKE ?)"
			+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR APPROVE_PATH LIKE ?)"
			+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR EDIT_PATH LIKE ?)"
			+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR QUERY_PATH LIKE ?)"
			+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR CANCEL_PATH LIKE ?)"
			+ " ORDER BY PROC_NAME";
		sqlArgs.add(sfProcedureDef.getProcId());
		sqlArgs.add(sfProcedureDef.getProcId());
		sqlArgs.add(sfProcedureDef.getProcName());
		sqlArgs.add(sfProcedureDef.getProcName());
		sqlArgs.add(sfProcedureDef.getDescription());
		sqlArgs.add(sfProcedureDef.getDescription());
		sqlArgs.add(sfProcedureDef.getAppTableName());
		sqlArgs.add(sfProcedureDef.getAppTableName());
		sqlArgs.add(sfProcedureDef.getAppPkName());
		sqlArgs.add(sfProcedureDef.getAppPkName());
		sqlArgs.add(sfProcedureDef.getOrganizationId());
		sqlArgs.add(sfProcedureDef.getOrganizationId());
		sqlArgs.add(sfProcedureDef.getDisabled());
		sqlArgs.add(sfProcedureDef.getDisabled());
		sqlArgs.add(sfProcedureDef.getApprovePath());
		sqlArgs.add(sfProcedureDef.getApprovePath());
		sqlArgs.add(sfProcedureDef.getEditPath());
		sqlArgs.add(sfProcedureDef.getEditPath());
		sqlArgs.add(sfProcedureDef.getQueryPath());
		sqlArgs.add(sfProcedureDef.getQueryPath());
		sqlArgs.add(sfProcedureDef.getCancelPath());
		sqlArgs.add(sfProcedureDef.getCancelPath());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

}