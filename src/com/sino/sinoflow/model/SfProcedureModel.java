package com.sino.sinoflow.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.StrUtil;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.sinoflow.dto.SfProcedureDTO;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;


/**
 * <p>Title: SfProcedureModel</p>
 * <p>Description:程序自动生成SQL构造器“SfProcedureModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Hing
 * @version 1.0
 */


public class SfProcedureModel extends BaseSQLProducer {

//	private SfUserBaseDTO sfUser = null;

	/**
	 * 功能：过程属性 SF_PROCEDURE 数据库SQL构造层构造函数
	 * @param userAccount SfUserBaseDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter SfProcedureDTO 本次操作的数据
	 */
	public SfProcedureModel(SfUserBaseDTO userAccount, SfProcedureDTO dtoParameter) {
		super(userAccount, dtoParameter);
//		sfUser = userAccount;
	}

	/**
	 * 功能：框架自动生成过程属性 SF_PROCEDURE数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDataCreateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
			List sqlArgs = new ArrayList();
			SfProcedureDTO sfProcedure = (SfProcedureDTO)dtoParameter;
			String sqlStr = "INSERT INTO "
				+ " SF_PROCEDURE("
				+ " PROJECT_ID,"
				+ " PROCEDURE_NAME,"
				+ " TRAY_TYPE,"
				+ " DURATION,"
				+ " CREATED_BY,"
				+ " CREATION_DATE,"
				+ " LAST_UPDATED_BY,"
				+ " LAST_UPDATE_DATE,"
				+ " DESCRIPTION,"
				+ " MEMO,"
                + " MAIN_PROCEDURE,"
                + " DEFAULT_URL"
				+ ") VALUES ("
				+ " ?, ?, ?, ?, ?, CONVERT(DATETIME, ?),"
                + " ?, CONVERT(DATETIME, ?), ?, ?, ?, ?)";
		
			sqlArgs.add(sfProcedure.getProjectId());
			sqlArgs.add(sfProcedure.getProcedureName());
			sqlArgs.add(sfProcedure.getTrayType());
			sqlArgs.add(sfProcedure.getDuration());
			sqlArgs.add(sfProcedure.getCreatedBy());
			sqlArgs.add(sfProcedure.getCreationDate());
			sqlArgs.add(sfProcedure.getLastUpdatedBy());
			sqlArgs.add(sfProcedure.getLastUpdateDate());
			sqlArgs.add(sfProcedure.getDescription());
			sqlArgs.add(sfProcedure.getMemo());
			sqlArgs.add(sfProcedure.getMainProcedure());
            sqlArgs.add(sfProcedure.getDefaultUrl());

            sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成过程属性 SF_PROCEDURE数据更新SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据更新用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDataUpdateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();

			List sqlArgs = new ArrayList();
			SfProcedureDTO sfProcedure = (SfProcedureDTO)dtoParameter;
			String sqlStr = "UPDATE SF_PROCEDURE"
				+ " SET"
				+ " PROJECT_ID = ?,"
				+ " PROCEDURE_NAME = ?,"
				+ " TRAY_TYPE = ?,"
				+ " DURATION = ?,"
				+ " CREATED_BY = ?,"
				+ " CREATION_DATE = CONVERT(DATETIME, ?),"
				+ " LAST_UPDATED_BY = ?,"
				+ " LAST_UPDATE_DATE = CONVERT(DATETIME, ?),"
				+ " DESCRIPTION = ?,"
				+ " MEMO = ?,"
				+ " MAIN_PROCEDURE = ?,"
                + " DEFAULT_URL = ?"
                + " WHERE"
				+ " PROCEDURE_ID = ?";
		
			sqlArgs.add(sfProcedure.getProjectId());
			sqlArgs.add(sfProcedure.getProcedureName());
			sqlArgs.add(sfProcedure.getTrayType());
			sqlArgs.add(sfProcedure.getDuration());
			sqlArgs.add(sfProcedure.getCreatedBy());
			sqlArgs.add(sfProcedure.getCreationDate());
			sqlArgs.add(sfProcedure.getLastUpdatedBy());
			sqlArgs.add(sfProcedure.getLastUpdateDate());
			sqlArgs.add(sfProcedure.getDescription());
			sqlArgs.add(sfProcedure.getMemo());
			sqlArgs.add(sfProcedure.getMainProcedure());
            sqlArgs.add(sfProcedure.getDefaultUrl());
            sqlArgs.add(sfProcedure.getProcedureId());
		
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成过程属性 SF_PROCEDURE数据删除SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfProcedureDTO sfProcedure = (SfProcedureDTO)dtoParameter;
		String sqlStr = "DELETE FROM"
				+ " SF_PROCEDURE"
				+ " WHERE"
				+ " PROCEDURE_ID = ?";
			sqlArgs.add(sfProcedure.getProcedureId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成过程属性 SF_PROCEDURE数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfProcedureDTO sfProcedure = (SfProcedureDTO)dtoParameter;
		String sqlStr = "SELECT "
			+ " PROJECT_ID,"
			+ " PROCEDURE_ID,"
			+ " PROCEDURE_NAME,"
			+ " TRAY_TYPE,"
			+ " DURATION,"
			+ " CREATED_BY,"
			+ " CREATION_DATE,"
			+ " LAST_UPDATED_BY,"
			+ " LAST_UPDATE_DATE,"
			+ " DESCRIPTION,"
			+ " MEMO,"
			+ " MAIN_PROCEDURE,"
            + " DEFAULT_URL"
            + " FROM"
			+ " SF_PROCEDURE"
			+ " WHERE"
			+ " PROCEDURE_ID = ?";
		sqlArgs.add(sfProcedure.getProcedureId());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成过程属性 SF_PROCEDURE多条数据信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回多条数据信息查询用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getMuxDataModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
			List sqlArgs = new ArrayList();
			SfProcedureDTO sfProcedure = (SfProcedureDTO)dtoParameter;
			String sqlStr = "SELECT "
				+ " PROJECT_ID,"
				+ " PROCEDURE_ID,"
				+ " PROCEDURE_NAME,"
				+ " TRAY_TYPE,"
				+ " DURATION,"
				+ " CREATED_BY,"
				+ " CREATION_DATE,"
				+ " LAST_UPDATED_BY,"
				+ " LAST_UPDATE_DATE,"
				+ " DESCRIPTION,"
				+ " MEMO,"
				+ " MAIN_PROCEDURE,"
                + " DEFAULT_URL"
                + " FROM"
				+ " SF_PROCEDURE"
				+ " WHERE"
				+ " (? <= 0 OR PROJECT_ID = ?)"
				+ " AND (? <= 0 OR PROCEDURE_ID = ?)"
				+ " AND (? = '' OR PROCEDURE_NAME LIKE ?)"
				+ " AND (? <= 0 OR TRAY_TYPE = ?)"
				+ " AND (? <= 0 OR DURATION = ?)"
				+ " AND (? = '' OR CREATED_BY LIKE ?)"
				+ " AND (? = '' OR CREATION_DATE = CONVERT(DATETIME, ?))"
				+ " AND (? = '' OR LAST_UPDATED_BY LIKE ?)"
				+ " AND (? = '' OR LAST_UPDATE_DATE = CONVERT(DATETIME, ?))"
				+ " AND (? = '' OR DESCRIPTION LIKE ?)"
				+ " AND (? = '' OR MEMO LIKE ?)"
				+ " AND (? <= 0 OR MAIN_PROCEDURE = ?)"
                + " AND (? = '' OR DEFAULT_URL LIKE ?)";
            sqlArgs.add(sfProcedure.getProjectId());
			sqlArgs.add(sfProcedure.getProjectId());
			sqlArgs.add(sfProcedure.getProcedureId());
			sqlArgs.add(sfProcedure.getProcedureId());
			sqlArgs.add(sfProcedure.getProcedureName());
			sqlArgs.add(sfProcedure.getProcedureName());
			sqlArgs.add(sfProcedure.getTrayType());
			sqlArgs.add(sfProcedure.getTrayType());
			sqlArgs.add(sfProcedure.getDuration());
			sqlArgs.add(sfProcedure.getDuration());
			sqlArgs.add(sfProcedure.getCreatedBy());
			sqlArgs.add(sfProcedure.getCreatedBy());
			sqlArgs.add(sfProcedure.getCreationDate());
			sqlArgs.add(sfProcedure.getCreationDate());
			sqlArgs.add(sfProcedure.getLastUpdatedBy());
			sqlArgs.add(sfProcedure.getLastUpdatedBy());
			sqlArgs.add(sfProcedure.getLastUpdateDate());
			sqlArgs.add(sfProcedure.getLastUpdateDate());
			sqlArgs.add(sfProcedure.getDescription());
			sqlArgs.add(sfProcedure.getDescription());
			sqlArgs.add(sfProcedure.getMemo());
			sqlArgs.add(sfProcedure.getMemo());
			sqlArgs.add(sfProcedure.getMainProcedure());
			sqlArgs.add(sfProcedure.getMainProcedure());
            sqlArgs.add(sfProcedure.getDefaultUrl());
            sqlArgs.add(sfProcedure.getDefaultUrl());

            sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：根据外键关联字段 projectId 构造查询数据SQL。
	 * 框架自动生成数据过程属性 SF_PROCEDURE详细信息查询SQLModel，请根据实际需要修改。
	 * @param projectId String 
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	private SQLModel getDataByProjectIdModel(int projectId) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT "
			+ " PROCEDURE_ID,"
			+ " PROCEDURE_NAME,"
			+ " TRAY_TYPE,"
			+ " DURATION,"
			+ " CREATED_BY,"
			+ " CREATION_DATE,"
			+ " LAST_UPDATED_BY,"
			+ " LAST_UPDATE_DATE,"
			+ " DESCRIPTION,"
			+ " MEMO,"
			+ " MAIN_PROCEDURE,"
            + " DEFAULT_URL"
            + " FROM"
			+ " SF_PROCEDURE"
			+ " WHERE"
			+ " PROJECT_ID = ?";
		sqlArgs.add(projectId);
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：根据外键获取数据
	 * @param foreignKey 传入的外键字段名称。
	 * @return SQLModel
	 */
	public SQLModel getDataByForeignKeyModel(String foreignKey) {
		SQLModel sqlModel = null;
		SfProcedureDTO sfProcedure = (SfProcedureDTO)dtoParameter;
		if(foreignKey.equals("projectId")){
			sqlModel = getDataByProjectIdModel(sfProcedure.getProjectId());
		}
		return sqlModel;
	}

	/**
	 * 功能：根据外键关联字段 projectId 构造数据删除SQL。
	 * 框架自动生成数据过程属性 SF_PROCEDURE数据删除SQLModel，请根据实际需要修改。
	 * @param projectId String 
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	private SQLModel getDeleteByProjectIdModel(int projectId) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "DELETE "
/*
            + " PROCEDURE_ID,"
			+ " PROCEDURE_NAME,"
			+ " TRAY_TYPE,"
			+ " DURATION,"
			+ " CREATED_BY,"
			+ " CREATION_DATE,"
			+ " LAST_UPDATED_BY,"
			+ " LAST_UPDATE_DATE,"
			+ " DESCRIPTION,"
			+ " MEMO,"
			+ " MAIN_PROCEDURE,"
            + " DEFAULT_URL"
*/
            + " FROM"
			+ " SF_PROCEDURE"
			+ " WHERE"
			+ " PROJECT_ID = ?";
		sqlArgs.add(projectId);
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：根据外键字段删除数据
	 * @param foreignKey 传入的外键字段名称。
	 * @return SQLModel
	 */
	public SQLModel getDeleteByForeignKeyModel(String foreignKey) {
		SQLModel sqlModel = null;
		SfProcedureDTO sfProcedure = (SfProcedureDTO)dtoParameter;
		if(foreignKey.equals("projectId")){
			sqlModel = getDeleteByProjectIdModel(sfProcedure.getProjectId());
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成过程属性 SF_PROCEDURE页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();		
			List sqlArgs = new ArrayList();
			SfProcedureDTO sfProcedure = (SfProcedureDTO)dtoParameter;
			String sqlStr = "SELECT "
				+ " PROJECT_ID,"
				+ " CONVERT(INT,PROCEDURE_ID) PROCEDURE_ID,"
				+ " PROCEDURE_NAME,"
				+ " TRAY_TYPE,"
				+ " DURATION,"
				+ " CREATED_BY,"
				+ " CREATION_DATE,"
				+ " LAST_UPDATED_BY,"
				+ " LAST_UPDATE_DATE,"
				+ " DESCRIPTION,"
				+ " MEMO,"
				+ " MAIN_PROCEDURE,"
                + " DEFAULT_RUL"
                + " FROM"
				+ " SF_PROCEDURE"
				+ " WHERE"
				+ " (? <= 0 OR PROJECT_ID = ?)"
				+ " AND (? <= 0 OR PROCEDURE_ID = ?)"
				+ " AND (? = '' OR PROCEDURE_NAME LIKE ?)"
				+ " AND (? <= 0 OR TRAY_TYPE = ?)"
				+ " AND (? <= 0 OR DURATION = ?)"
				+ " AND (? = '' OR CREATED_BY LIKE ?)"
				+ " AND (? = '' OR CREATION_DATE = CONVERT(DATETIME, ?))"
				+ " AND (? = '' OR LAST_UPDATED_BY LIKE ?)"
				+ " AND (? = '' OR LAST_UPDATE_DATE = CONVERT(DATETIME, ?))"
				+ " AND (? = '' OR DESCRIPTION LIKE ?)"
				+ " AND (? = '' OR MEMO LIKE ?)"
				+ " AND (? <= 0 OR MAIN_PROCEDURE = ?)"
                + " AND (? = '' OR DEFAULT_URL LIKE ?)";
            sqlArgs.add(sfProcedure.getProjectId());
			sqlArgs.add(sfProcedure.getProjectId());
			sqlArgs.add(sfProcedure.getProcedureId());
			sqlArgs.add(sfProcedure.getProcedureId());
			sqlArgs.add(sfProcedure.getProcedureName());
			sqlArgs.add(sfProcedure.getProcedureName());
			sqlArgs.add(sfProcedure.getTrayType());
			sqlArgs.add(sfProcedure.getTrayType());
			sqlArgs.add(sfProcedure.getDuration());
			sqlArgs.add(sfProcedure.getDuration());
			sqlArgs.add(sfProcedure.getCreatedBy());
			sqlArgs.add(sfProcedure.getCreatedBy());
			sqlArgs.add(sfProcedure.getCreationDate());
			sqlArgs.add(sfProcedure.getCreationDate());
			sqlArgs.add(sfProcedure.getLastUpdatedBy());
			sqlArgs.add(sfProcedure.getLastUpdatedBy());
			sqlArgs.add(sfProcedure.getLastUpdateDate());
			sqlArgs.add(sfProcedure.getLastUpdateDate());
			sqlArgs.add(sfProcedure.getDescription());
			sqlArgs.add(sfProcedure.getDescription());
			sqlArgs.add(sfProcedure.getMemo());
			sqlArgs.add(sfProcedure.getMemo());
			sqlArgs.add(sfProcedure.getMainProcedure());
			sqlArgs.add(sfProcedure.getMainProcedure());
            sqlArgs.add(sfProcedure.getDefaultUrl());
            sqlArgs.add(sfProcedure.getDefaultUrl());

            sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}



	/**
	 * 功能：返回对应工程下的过程
     * @param projectId project ID
	 */
	public SQLModel getProjectProcedureModel(int projectId) {
	    SQLModel sqlModel = new SQLModel();
	    List sqlArgs = new ArrayList();
	    String sqlStr = "SELECT "
	    				+ " PROCEDURE_ID,"
	    				+ " PROCEDURE_NAME"
	    				+ " FROM SF_PROCEDURE"
	    				+ " WHERE "
	    				+ " PROJECT_ID = ?"
	    				+ " ORDER BY PROCEDURE_NAME";
	    sqlArgs.add(projectId);
	    sqlModel.setSqlStr(sqlStr);
	    sqlModel.setArgs(sqlArgs);        
	    return sqlModel;
	}

    public SQLModel getProjectProcedureModel(String projectId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT "
                        + " PROCEDURE_ID,"
                        + " PROCEDURE_NAME"
                        + " FROM SF_PROCEDURE"
                        + " WHERE "
                        + " PROJECT_ID = ?"
                        + " ORDER BY PROCEDURE_NAME";
        sqlArgs.add(StrUtil.strToInt(projectId));
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
	 * 功能：返回对应工程下的过程
     * @param projectId project ID
	 */
	public SQLModel getProjectProcedureModel2(int projectId) {
	    SQLModel sqlModel = new SQLModel();
	    List sqlArgs = new ArrayList();
	    String sqlStr = "SELECT "
	    				+ " PROCEDURE_NAME,"
	    				+ " PROCEDURE_NAME,"
	    				+ " PROCEDURE_NAME OBY"
	    				+ " FROM SF_PROCEDURE"
	    				+ " WHERE "
	    				+ " PROJECT_ID = ?"
	    				+ " ORDER BY OBY";
	    sqlArgs.add(projectId);
	    sqlModel.setSqlStr(sqlStr);
	    sqlModel.setArgs(sqlArgs);        
	    return sqlModel;
	}

    public SQLModel getDescModel(int procId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT"
                        + " SPJ.DESCRIPTION PROJECT_DESC,"
                        + " SP.DESCRIPTION PROCEDURE_DESC"
                        + " FROM SF_PROCEDURE SP, SF_PROJECT SPJ"
                        + " WHERE "
                        + " SP.PROCEDURE_ID = ?"
                        + " AND SPJ.PROJECT_ID = SP.PROJECT_ID";
        sqlArgs.add(procId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：得到最后提交的过程
     * @param procName 过程名
     * @return SQLModel 返回页面翻页查询SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getLattestProcedureModel(String procName) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();

        List sqlArgs = new ArrayList();
        String sqlStr = "Select SP.*"
                + " From SF_PROCEDURE SP,"
                + " (SELECT PROCEDURE_NAME, Max(PROCEDURE_ID) PROCEDURE_ID "
                + " FROM SF_PROCEDURE  GROUP BY PROCEDURE_NAME) T"
                + " Where SP.PROCEDURE_ID = T.PROCEDURE_ID"
                + " AND (? = '' OR SP.PROCEDURE_NAME LIKE ?)";
        sqlArgs.add(procName);
        sqlArgs.add(procName);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    public SQLModel clearOldProjectsProcs() {
        SQLModel sqlModel = new SQLModel();

        String sqlStr = "DELETE SF_PROCEDURE"
                + " WHERE  PROCEDURE_ID NOT IN"
                + " (SELECT DISTINCT SP.PROCEDURE_ID"
                + " FROM SF_PROCEDURE SP"
                + " WHERE  EXISTS (SELECT NULL"
                + " FROM SF_ACT_INFO SAI"
                + " WHERE  SAI.SFACT_PROC_ID = SP.PROCEDURE_ID)"
                + " OR EXISTS (SELECT NULL"
                + " FROM SF_PROJECT_V SFV"
                + " WHERE  SFV.PROJECT_ID = SP.PROJECT_ID))";

        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }        
}