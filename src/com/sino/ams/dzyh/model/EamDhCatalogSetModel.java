package com.sino.ams.dzyh.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.dzyh.dto.EamDhCatalogSetDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;


/**
 * <p>Title: EamDhCatalogSetModel</p>
 * <p>Description:程序自动生成SQL构造器“EamDhCatalogSetModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 张星
 * @version 1.0
 */


public class EamDhCatalogSetModel extends AMSSQLProducer {


	/**
	 * 功能：低值易耗品类别表(EAM) EAM_DH_CATALOG_SET 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EamDhCatalogSetDTO 本次操作的数据
	 */
	public EamDhCatalogSetModel(SfUserDTO userAccount, EamDhCatalogSetDTO dtoParameter) {
		super(userAccount, dtoParameter);

	}

	/**
	 * 功能：框架自动生成低值易耗品类别表(EAM) EAM_DH_CATALOG_SET数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDataCreateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			EamDhCatalogSetDTO dto = (EamDhCatalogSetDTO)dtoParameter;
			String sqlStr = "INSERT INTO "
				+ " EAM_DH_CATALOG_SET("
				+ " CATLOG_SET_ID,"
				+ " SET_CODE,"
				+ " SET_NAME,"
				+ " ENABLED,"
				+ " END_DATE,"
				+ " CREATED_BY,"
				+ " CREATION_DATE"
				+ ") VALUES ("
				+ "  NEWID() , ?, ?, ?, ?, ?, GETDATE())";
			sqlArgs.add(dto.getSetCode());
			sqlArgs.add(dto.getSetName());
			sqlArgs.add(dto.getEnabled());
			sqlArgs.add(dto.getEndDate());
			sqlArgs.add(userAccount.getUserId());
			
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成低值易耗品类别表(EAM) EAM_DH_CATALOG_SET数据更新SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据更新用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDataUpdateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			EamDhCatalogSetDTO dto = (EamDhCatalogSetDTO)dtoParameter;
			String sqlStr = "UPDATE EAM_DH_CATALOG_SET"
				+ " SET"
				+ " SET_CODE = ?,"
				+ " SET_NAME = ?,"
				+ " ENABLED = ?,"
				+ " END_DATE = ?,"
				+ " LAST_UPDATE_BY = ?,"
				+ " LAST_UPDATE_DATE = GETDATE()"
				+ " WHERE"
				+ " CATLOG_SET_ID = ?";

			sqlArgs.add(dto.getSetCode());
			sqlArgs.add(dto.getSetName());
			sqlArgs.add(dto.getEnabled());
			sqlArgs.add(dto.getEndDate());
			sqlArgs.add(userAccount.getUserId());
			sqlArgs.add(dto.getCatlogSetId());

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成低值易耗品类别表(EAM) EAM_DH_CATALOG_SET数据删除SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EamDhCatalogSetDTO dto = (EamDhCatalogSetDTO)dtoParameter;
		String sqlStr = "DELETE FROM"
				+ " EAM_DH_CATALOG_SET"
				+ " WHERE"
				+ " CATLOG_SET_ID = ?";
			sqlArgs.add(dto.getCatlogSetId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成低值易耗品类别表(EAM) EAM_DH_CATALOG_SET数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EamDhCatalogSetDTO dto = (EamDhCatalogSetDTO)dtoParameter;
		String sqlStr = "SELECT "
			+ " CATLOG_SET_ID,"
			+ " SET_CODE,"
			+ " SET_NAME,"
			+ " ENABLED,"
			+ " END_DATE,"
			+ " CREATED_BY,"
			+ " CREATION_DATE,"
			+ " LAST_UPDATE_BY,"
			+ " LAST_UPDATE_DATE"
			+ " FROM"
			+ " EAM_DH_CATALOG_SET"
			+ " WHERE"
			+ " CATLOG_SET_ID = ?";
		sqlArgs.add(dto.getCatlogSetId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}


	/**
	 * 功能：框架自动生成低值易耗品类别表(EAM) EAM_DH_CATALOG_SET页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EamDhCatalogSetDTO dto = (EamDhCatalogSetDTO)dtoParameter;
		String sqlStr = "SELECT "
			+ " CATLOG_SET_ID,"
			+ " SET_CODE,"
			+ " SET_NAME,"
			+" CASE WHEN ENABLED='1' THEN '是' ELSE '否' END AS ENABLED ,"
			+ " END_DATE,"
			+ " AMS_PUB_PKG.GET_USER_NAME(CREATED_BY) CREATED_BY,"
			+ " CREATION_DATE,"
			+ " AMS_PUB_PKG.GET_USER_NAME(LAST_UPDATE_BY) LAST_UPDATE_BY,"
			+ " LAST_UPDATE_DATE"
			+ " FROM"
			+ " EAM_DH_CATALOG_SET"
			+ " WHERE"
			+ " ( " + SyBaseSQLUtil.isNull() + "  OR SET_CODE LIKE ?)"
			+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR SET_NAME LIKE ?)"
			+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR ENABLED LIKE ?)"
			+ " ORDER BY CREATION_DATE DESC";
		sqlArgs.add(dto.getSetCode());
		sqlArgs.add(dto.getSetCode());
		sqlArgs.add(dto.getSetName());
		sqlArgs.add(dto.getSetName());
		sqlArgs.add(dto.getEnabled());
		sqlArgs.add(dto.getEnabled());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

}
