package com.sino.sinoflow.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.sinoflow.dto.SfProjectDTO;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;


/**
 * <p>Title: SfProjectModel</p>
 * <p>Description:程序自动生成SQL构造器“SfProjectModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Hing
 * @version 1.0
 */


public class SfProjectModel extends BaseSQLProducer {

//	private SfUserBaseDTO sfUser = null;

	/**
	 * 功能：工程属性 SF_PROJECT 数据库SQL构造层构造函数
	 * @param userAccount SfUserBaseDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter SfProjectDTO 本次操作的数据
	 */
	public SfProjectModel(SfUserBaseDTO userAccount, SfProjectDTO dtoParameter) {
		super(userAccount, dtoParameter);
//		sfUser = userAccount;
	}

	/**
	 * 功能：框架自动生成工程属性 SF_PROJECT数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDataCreateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
			List sqlArgs = new ArrayList();
			SfProjectDTO sfProject = (SfProjectDTO)dtoParameter;
			String sqlStr = "INSERT INTO "
				+ " SF_PROJECT("
				+ " PROJECT_NAME,"
				+ " CREATED_BY,"
				+ " CREATION_DATE,"
				+ " LAST_UPDATED_BY,"
				+ " LAST_UPDATE_DATE,"
				+ " ENABLED,"
				+ " EFFECTIVE_DATE,"
				+ " VERSION,"
				+ " DESCRIPTION,"
                + " FILENAME"
                + ") VALUES ("
				+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
			sqlArgs.add(sfProject.getProjectName());
			sqlArgs.add(sfProject.getCreatedBy());
			sqlArgs.add(sfProject.getCreationDate());
			sqlArgs.add(sfProject.getLastUpdatedBy());
			sqlArgs.add(sfProject.getLastUpdateDate());
			sqlArgs.add(sfProject.getEnabled());
			sqlArgs.add(sfProject.getEffectiveDate());
			sqlArgs.add(sfProject.getVersion());
			sqlArgs.add(sfProject.getDescription());
			
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成工程属性 SF_PROJECT数据更新SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据更新用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDataUpdateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
			List sqlArgs = new ArrayList();
			SfProjectDTO sfProject = (SfProjectDTO)dtoParameter;
			String sqlStr = "UPDATE SF_PROJECT"
				+ " SET"
				+ " PROJECT_NAME = ?,"
				+ " CREATED_BY = ?,"
				+ " CREATION_DATE = ?,"
				+ " LAST_UPDATED_BY = ?,"
				+ " LAST_UPDATE_DATE = ?,"
				+ " ENABLED = ?,"
				+ " EFFECTIVE_DATE = ?,"
				+ " VERSION = ?,"
				+ " DESCRIPTION = ?,"
                + " FILENAME = ?"
                + " WHERE"
				+ " PROJECT_ID = ?";
		
			sqlArgs.add(sfProject.getProjectName());
			sqlArgs.add(sfProject.getCreatedBy());
			sqlArgs.add(sfProject.getCreationDate());
			sqlArgs.add(sfProject.getLastUpdatedBy());
			sqlArgs.add(sfProject.getLastUpdateDate());
			sqlArgs.add(sfProject.getEnabled());
			sqlArgs.add(sfProject.getEffectiveDate());
			sqlArgs.add(sfProject.getVersion());
			sqlArgs.add(sfProject.getDescription());
            sqlArgs.add(sfProject.getFilename());
            sqlArgs.add(sfProject.getProjectId());
		
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成工程属性 SF_PROJECT数据删除SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfProjectDTO sfProject = (SfProjectDTO)dtoParameter;
		String sqlStr = "DELETE FROM"
				+ " SF_PROJECT"
				+ " WHERE"
				+ " PROJECT_ID = ?";
			sqlArgs.add(sfProject.getProjectId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成工程属性 SF_PROJECT数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfProjectDTO sfProject = (SfProjectDTO)dtoParameter;
		String sqlStr = "SELECT "
			+ " PROJECT_ID,"
			+ " PROJECT_NAME,"
			+ " CREATED_BY,"
			+ " CREATION_DATE,"
			+ " LAST_UPDATED_BY,"
			+ " LAST_UPDATE_DATE,"
			+ " ENABLED,"
			+ " EFFECTIVE_DATE,"
			+ " VERSION,"
			+ " DESCRIPTION,"
            + " FILENAME"
            + " FROM"
			+ " SF_PROJECT"
			+ " WHERE"
			+ " PROJECT_ID = ?";
		sqlArgs.add(sfProject.getProjectId());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成工程属性 SF_PROJECT多条数据信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回多条数据信息查询用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getMuxDataModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
			List sqlArgs = new ArrayList();
			SfProjectDTO sfProject = (SfProjectDTO)dtoParameter;
			String sqlStr = "SELECT "
				+ " PROJECT_ID,"
				+ " PROJECT_NAME,"
				+ " CREATED_BY,"
				+ " CREATION_DATE,"
				+ " LAST_UPDATED_BY,"
				+ " LAST_UPDATE_DATE,"
				+ " ENABLED,"
				+ " EFFECTIVE_DATE,"
				+ " VERSION,"
				+ " DESCRIPTION,"
                + " FILENAME"
                + " FROM"
				+ " SF_PROJECT"
				+ " WHERE"
				+ " (? <= 0 OR PROJECT_ID = ?)"
				+ " AND (? = '' OR PROJECT_NAME LIKE ?)"
				+ " AND (? = '' OR CREATED_BY LIKE ?)"
				+ " AND (? = '' OR CREATION_DATE LIKE ?)"
				+ " AND (? = '' OR LAST_UPDATED_BY LIKE ?)"
				+ " AND (? = '' OR LAST_UPDATE_DATE LIKE ?)"
				+ " AND (? = '' OR ENABLED LIKE ?)"
				+ " AND (? = '' OR EFFECTIVE_DATE LIKE ?)"
				+ " AND (? = '' OR VERSION LIKE ?)"
				+ " AND (? = '' OR DESCRIPTION LIKE ?)"
                + " AND (? = '' OR FILENAME LIKE ?)";
            sqlArgs.add(sfProject.getProjectId());
			sqlArgs.add(sfProject.getProjectId());
			sqlArgs.add(sfProject.getProjectName());
			sqlArgs.add(sfProject.getProjectName());
			sqlArgs.add(sfProject.getCreatedBy());
			sqlArgs.add(sfProject.getCreatedBy());
			sqlArgs.add(sfProject.getCreationDate());
			sqlArgs.add(sfProject.getCreationDate());
			sqlArgs.add(sfProject.getLastUpdatedBy());
			sqlArgs.add(sfProject.getLastUpdatedBy());
			sqlArgs.add(sfProject.getLastUpdateDate());
			sqlArgs.add(sfProject.getLastUpdateDate());
			sqlArgs.add(sfProject.getEnabled());
			sqlArgs.add(sfProject.getEnabled());
			sqlArgs.add(sfProject.getEffectiveDate());
			sqlArgs.add(sfProject.getEffectiveDate());
			sqlArgs.add(sfProject.getVersion());
			sqlArgs.add(sfProject.getVersion());
			sqlArgs.add(sfProject.getDescription());
			sqlArgs.add(sfProject.getDescription());
            sqlArgs.add(sfProject.getFilename());
            sqlArgs.add(sfProject.getFilename());

            sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成工程属性 SF_PROJECT页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
			List sqlArgs = new ArrayList();
			SfProjectDTO sfProject = (SfProjectDTO)dtoParameter;
			String sqlStr = "SELECT "
				+ " CONVERT(INT,PROJECT_ID) PROJECT_ID,"
				+ " PROJECT_NAME,"
				+ " CREATED_BY,"
				+ " CREATION_DATE,"
				+ " LAST_UPDATED_BY,"
				+ " LAST_UPDATE_DATE,"
				+ " ENABLED,"
				+ " EFFECTIVE_DATE,"
				+ " VERSION,"
				+ " DESCRIPTION,"
                + " FILENAME"
                + " FROM"
				+ " SF_PROJECT"
				+ " WHERE"
				+ " (? <= 0 OR PROJECT_ID = ?)"
				+ " AND (? = '' OR PROJECT_NAME LIKE ?)"
				+ " AND (? = '' OR CREATED_BY LIKE ?)"
				+ " AND (? = '' OR CREATION_DATE LIKE ?)"
				+ " AND (? = '' OR LAST_UPDATED_BY LIKE ?)"
				+ " AND (? = '' OR LAST_UPDATE_DATE LIKE ?)"
				+ " AND (? = '' OR ENABLED LIKE ?)"
				+ " AND (? = '' OR EFFECTIVE_DATE LIKE ?)"
				+ " AND (? = '' OR VERSION LIKE ?)"
				+ " AND (? = '' OR DESCRIPTION LIKE ?)"
                + " AND (? = '' OR FILENAME LIKE ?)";
            sqlArgs.add(sfProject.getProjectId());
			sqlArgs.add(sfProject.getProjectId());
			sqlArgs.add(sfProject.getProjectName());
			sqlArgs.add(sfProject.getProjectName());
			sqlArgs.add(sfProject.getCreatedBy());
			sqlArgs.add(sfProject.getCreatedBy());
			sqlArgs.add(sfProject.getCreationDate());
			sqlArgs.add(sfProject.getCreationDate());
			sqlArgs.add(sfProject.getLastUpdatedBy());
			sqlArgs.add(sfProject.getLastUpdatedBy());
			sqlArgs.add(sfProject.getLastUpdateDate());
			sqlArgs.add(sfProject.getLastUpdateDate());
			sqlArgs.add(sfProject.getEnabled());
			sqlArgs.add(sfProject.getEnabled());
			sqlArgs.add(sfProject.getEffectiveDate());
			sqlArgs.add(sfProject.getEffectiveDate());
			sqlArgs.add(sfProject.getVersion());
			sqlArgs.add(sfProject.getVersion());
			sqlArgs.add(sfProject.getDescription());
			sqlArgs.add(sfProject.getDescription());
            sqlArgs.add(sfProject.getFilename());
            sqlArgs.add(sfProject.getFilename());

            sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：得到最新的工程
     * @param projName 工程名称
	 * @return SQLModel 返回页面翻页查询SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getNewestProjectModel(String projName) throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
			List sqlArgs = new ArrayList();
//			SfProjectDTO sfProject = (SfProjectDTO)dtoParameter;
			String sqlStr = "SELECT "
				+ " SP.PROJECT_ID,"
				+ " SP.PROJECT_NAME,"
				+ " SP.CREATED_BY,"
				+ " SP.CREATION_DATE,"
				+ " SP.LAST_UPDATED_BY,"
				+ " SP.LAST_UPDATE_DATE,"
				+ " SP.ENABLED,"
				+ " SP.EFFECTIVE_DATE,"
				+ " SP.VERSION,"
				+ " SP.DESCRIPTION,"
                + " SP.FILENAME"
                + " FROM"
				+ " SF_PROJECT SP, SF_PROJECT_V SPV"
				+ " WHERE"
				+ " SP.PROJECT_ID = SPV.PROJECT_ID"
				+ " AND (? = '' OR SPV.PROJECT_NAME = ?)";
			sqlArgs.add(projName);
			sqlArgs.add(projName);
		
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

    public SQLModel clearOldProjects() {
        SQLModel sqlModel = new SQLModel();

        String sqlStr = "DELETE SF_PROJECT"
                + " WHERE  PROJECT_ID IN"
                + " (SELECT DISTINCT SP2.PROJECT_ID"
                + " FROM SF_PROCEDURE SP2"
                + " WHERE SP2.PROCEDURE_ID NOT IN"
                + " (SELECT DISTINCT SP.PROCEDURE_ID"
                + " FROM SF_PROCEDURE SP"
                + " WHERE EXISTS (SELECT NULL"
                + " FROM SF_ACT_INFO SAI"
                + " WHERE SAI.SFACT_PROC_ID = SP.PROCEDURE_ID)"
                + " OR EXISTS"
                + " (SELECT NULL"
                + " FROM SF_PROJECT_V SFV"
                + " WHERE SFV.PROJECT_ID = SP.PROJECT_ID)))";

        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }

    public SQLModel clearOldProjectsFiles() {
        SQLModel sqlModel = new SQLModel();

        String sqlStr = "DELETE SF_FLOWFILE_STORE"
                + " WHERE  PROJECT_ID IN"
                + " (SELECT DISTINCT SP2.PROJECT_ID"
                + " FROM SF_PROCEDURE SP2"
                + " WHERE SP2.PROCEDURE_ID NOT IN"
                + " (SELECT DISTINCT SP.PROCEDURE_ID"
                + " FROM SF_PROCEDURE SP"
                + " WHERE EXISTS (SELECT NULL"
                + " FROM SF_ACT_INFO SAI"
                + " WHERE SAI.SFACT_PROC_ID = SP.PROCEDURE_ID)"
                + " OR EXISTS"
                + " (SELECT NULL"
                + " FROM SF_PROJECT_V SFV"
                + " WHERE SFV.PROJECT_ID = SP.PROJECT_ID)))";

        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }
}